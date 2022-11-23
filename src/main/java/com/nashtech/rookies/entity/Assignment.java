package com.nashtech.rookies.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String note;
    private String state;
    private Date assigned_date;
    private Date returned_date;
    private Date created_date;

    @ManyToOne
    private Users assigned_by;

    @ManyToOne
    private Users assigned_to;

    public Assignment(String note, String state, Date assigned_date, Date returned_date, Date created_date, Users assigned_by, Users assigned_to) {
        this.note = note;
        this.state = state;
        this.assigned_date = assigned_date;
        this.returned_date = returned_date;
        this.created_date = created_date;
        this.assigned_by = assigned_by;
        this.assigned_to = assigned_to;
    }
}
