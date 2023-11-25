package com.bot.botfortesting.model;

import jakarta.persistence.*;
import lombok.*;


//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="students", schema = "alldata")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "studentname")
    private String name;

    @Column(name = "groupname")
    private String group;

    @Column
    private long chatId;

    @Column(name="university_id")

    private Long universityId;

    public Student(String name, String group,long chatId,long universityId) {

        this.name = name;
        this.group = group;
        this.chatId=chatId;
        this.universityId=universityId;
    }
    public Student(String name, String pass) {

        this.name = name;
        this.group = pass;
    }

}