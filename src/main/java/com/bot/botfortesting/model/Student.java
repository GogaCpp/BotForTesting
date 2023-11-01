package com.bot.botfortesting.model;

import jakarta.persistence.*;
import lombok.*;


//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="students", schema = "alldata")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "studentname")
    private String name;

    @Column(name = "studentpass")
    private String pass;

    public Student(String name, String pass) {

        this.name = name;
        this.pass = pass;
    }

}