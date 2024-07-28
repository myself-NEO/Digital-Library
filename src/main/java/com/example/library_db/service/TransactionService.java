package com.example.library_db.service;

import com.example.library_db.dto.SearchBookRequest;
import com.example.library_db.model.Book;
import com.example.library_db.model.Student;
import com.example.library_db.model.Transaction;
import com.example.library_db.model.enums.TxnStatus;
import com.example.library_db.model.enums.TxnType;
import com.example.library_db.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Value("${student.issue.number_of_days}")
    private int numberOfDaysForIssuance;

    @Autowired
    StudentService studentService;

    @Autowired
    BookService bookService;

    @Autowired
    TransactionRepository transactionRepository;

    @Value("${student.issue.max_books}")
    private int maxBooksForIssuance;

    public String issueTxn(String bookName, int studentId) throws Exception {

        List<Book> bookList;

        try {
            bookList = bookService.searchBooks(
                    SearchBookRequest.builder()
                            .searchKey("name")
                            .searchValue(bookName)
                            .operator("=")
                            .available(true)
                            .build()
            );
        } catch (Exception e) {
            throw new Exception("Book not found");
        }

        Student student = studentService.getStudentById(studentId);

        // Validations

        if(student.getBookList() != null && student.getBookList().size() >= maxBooksForIssuance){
            throw new Exception("Book limit reached");
        }

        if(bookList.isEmpty()){
            throw new Exception("Not able to find any book in the library");
        }

        Book book = bookList.get(0);
        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .txnType(TxnType.ISSUE)
                .student(student)
                .book(book)
                .txnStatus(TxnStatus.PENDING)
                .build();

        transaction = transactionRepository.save(transaction);

        try {
            book.setMy_student(student);
            bookService.assignBookToStudent(book, student);

            transaction.setTxnStatus(TxnStatus.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            transaction.setTxnStatus(TxnStatus.FAILED);
        }finally {
            return transactionRepository.save(transaction).getExternalTxnId();
        }

    }

    public String returnTxn(int bookId, int studentId) throws Exception {
        Book book;
        try {
            book = this.bookService.searchBooks(
                    SearchBookRequest.builder()
                            .searchKey("id")
                            .searchValue(String.valueOf(bookId))
                            .build()
            ).get(0);
        }catch (Exception e){
            throw new Exception("not able to fetch book details");
        }

        // Validation
        if(book.getMy_student() == null || book.getMy_student().getId() != studentId){
            throw new Exception("Book is not assigned to this student");
        }

        Student student = this.studentService.getStudentById(studentId);

        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .txnType(TxnType.RETURN)
                .student(student)
                .book(book)
                .txnStatus(TxnStatus.PENDING)
                .build();

        transaction = transactionRepository.save(transaction);

        // Get the corresponding issue Txn

        Transaction issueTransaction = this.transactionRepository.findTopByStudentAndBookAndTxnTypeAndTxnStatusOrderByTransactionTimeDesc(student, book, TxnType.ISSUE, TxnStatus.SUCCESS);

        // Fine calculation
        long issueTxnInMillis = issueTransaction.getTransactionTime().getTime();

        long currentTimeMillis = System.currentTimeMillis();

        long timeDifferenceInMillis = currentTimeMillis - issueTxnInMillis;

        long timeDifferenceInDays = TimeUnit.DAYS.convert(timeDifferenceInMillis, TimeUnit.MILLISECONDS);

        Double fine = 0.0;
        if(timeDifferenceInDays > numberOfDaysForIssuance){
            fine = (timeDifferenceInDays - numberOfDaysForIssuance) * 10.0;
        }

        try {

            book.setMy_student(null);
            this.bookService.unassignBookFromStudent(book);

            transaction.setTxnStatus(TxnStatus.SUCCESS);
            return transaction.getExternalTxnId();
        }catch (Exception e){
            e.printStackTrace();
            transaction.setTxnStatus(TxnStatus.FAILED);
        }finally {
            transaction.setFine(fine);
            return transactionRepository.save(transaction).getExternalTxnId();
        }
    }
}
