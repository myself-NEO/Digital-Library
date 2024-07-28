package com.example.library_db.repository;

import com.example.library_db.model.Book;
import com.example.library_db.model.Student;
import com.example.library_db.model.Transaction;
import com.example.library_db.model.enums.TxnStatus;
import com.example.library_db.model.enums.TxnType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findTopByStudentAndBookAndTxnTypeAndTxnStatusOrderByTransactionTimeDesc(
            Student student, Book book, TxnType transactionType, TxnStatus transactionStatus
    );

    @Query("""
            select t from Transaction t
            where t.student = ?1 and t.book = ?2 and t.txnType = ?3 and t.txnStatus = ?4
            order by t.transactionTime DESC""")
    Transaction findByStudentAndBookAndTxnTypeAndTxnStatusOrderByTransactionTimeDesc(Student student, Book book, TxnType txnType, TxnStatus txnStatus);


}