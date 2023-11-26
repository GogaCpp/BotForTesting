package com.bot.botfortesting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="answers", schema = "alldata")
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="correct")
    private boolean correct;


    @Column(name="question_id")
    private long questionId;

    public Answer(String name,boolean correct,long question){
        this.name=name;
        this.correct=correct;
        this.questionId=question;

    }


}
