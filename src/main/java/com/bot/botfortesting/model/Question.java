package com.bot.botfortesting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="questions", schema = "alldata")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER)
    private Set<Answer> answers;

    Boolean isValid;


    public Question(String name,String type){
        this.name =name;
        this.type=type;
    }


}
