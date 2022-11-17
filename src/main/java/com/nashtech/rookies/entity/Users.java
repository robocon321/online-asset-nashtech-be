package com.nashtech.rookies.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    @Size(max = 15)
    private String username;

    private String password;

    private int enabled;

    @Size(min = 1, max = 10)
    private String firstName;

    @Size(min = 1, max = 50)
    private String lastName;

    private String fullName;

    private Date dob;

    private String location;

    private Date joinedDate;

    private boolean gender;

    private String role;

    private String code;

    private Date createdDate = new Date();
    private Date updatedDate;

    public Users(String username, String password, int enabled, String firstName, String lastName , boolean gender,  Date dob, String location, Date joinedDate, String role, String code) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.location = location;
        this.joinedDate = joinedDate;
        this.gender = gender;
        this.role = role;
        this.code = code;
    }

}
