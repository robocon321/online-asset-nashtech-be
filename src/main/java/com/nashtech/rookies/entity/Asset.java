package com.nashtech.rookies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private String specification;
    private String location;
    private String state;

    @ManyToOne
    private Category category;

    public Asset(String name, String code, String specification, String location, String state, Category category) {
        this.name = name;
        this.code = code;
        this.specification = specification;
        this.location = location;
        this.state = state;
        this.category = category;
    }
}
