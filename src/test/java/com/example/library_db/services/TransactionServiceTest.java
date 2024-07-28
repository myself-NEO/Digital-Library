package com.example.library_db.services;

import com.example.library_db.dto.SearchBookRequest;
import com.example.library_db.model.Book;
import com.example.library_db.model.Student;
import com.example.library_db.model.Transaction;
import com.example.library_db.repository.TransactionRepository;
import com.example.library_db.service.BookService;
import com.example.library_db.service.StudentService;
import com.example.library_db.service.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    BookService bookService;

    @Mock
    StudentService studentService;

    @Mock
     TransactionRepository transactionRepository;

    @Test
    public void issueTxnTest() throws Exception {
        String bookName = "ABC";
        int studentId = 1;
        String externalTxnId = UUID.randomUUID().toString();

        List<Book> bookList = Arrays.asList(
                Book.builder()
                        .id(1)
                        .name("ABC")
                        .build()
        );

        Student student = Student.builder()
                .id(1)
                .name("Neo")
                .build();


        SearchBookRequest searchBookRequest = SearchBookRequest.builder()
                .searchKey("name")
                .searchValue(bookName)
                .available(true)
                .operator("=")
                .build();

        Transaction transaction = Transaction.builder()
                .externalTxnId(externalTxnId)
                .book(bookList.get(0))
                .student(student)
                .build();

        when(bookService.searchBooks(any())).thenReturn(bookList);
        when(studentService.getStudentById(studentId)).thenReturn(student);

        when(transactionRepository.save(any())).thenReturn(transaction);

//        Mockito.doNothing().when(bookService.assignBookToStudent(Mockito.any(), Mockito.any()));
        String txnIdReturned = transactionService.issueTxn(bookName, studentId);

        // expected result == actual result
        Assert.assertEquals(externalTxnId, txnIdReturned);

        // Checks for test case correctness
        // Assertions
        // Verification

        verify(studentService, times(1))
                .getStudentById(studentId);

        verify(transactionRepository, times(2))
                .save(any());

        verify(bookService, times(1))
                .assignBookToStudent(any(), any());
    }
}
