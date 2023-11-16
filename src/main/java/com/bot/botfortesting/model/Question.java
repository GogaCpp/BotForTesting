package com.bot.botfortesting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="questions", schema = "alldata")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="question")
    private String question;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private TypeQuestion type;



    public enum TypeQuestion{
        SingleChoice,
        MultipleChoice,
        CalculationTask
    }

}
