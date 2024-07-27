package com.example.library_db.model;

import com.example.library_db.model.enums.TxnStatus;
import com.example.library_db.model.enums.TxnType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String externalTxnId;

    @CreationTimestamp
    private Date transactionTime;

    @UpdateTimestamp
    private Date updatedOn;

    @Enumerated(value = EnumType.STRING)
    private TxnType txnType;

    @Enumerated(value = EnumType.STRING)
    private TxnStatus txnStatus;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("transactionList")
    private Book my_book;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("transactionList")
    private Student my_student;

    private Double fine;
}
