package com.nashtech.rookies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date returnDate = new Date();

    private String state;

    @OneToOne
    Assignment assignment;

    public ReturnRequest(String state, Assignment assignment) {
        this.state = state;
        this.assignment = assignment;
    }

    public ReturnRequest(Date returnDate, String state, Assignment assignment) {
        this.returnDate = returnDate;
        this.state = state;
        this.assignment = assignment;
    }
}
