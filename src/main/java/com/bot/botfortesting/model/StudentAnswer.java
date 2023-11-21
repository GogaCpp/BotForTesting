package com.bot.botfortesting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="students_answers", schema = "alldata")
@Data
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "student_id")
    private long studentsId;

    @Column(name = "question_id")
    private long questionId;

    @Column(name="name")
    private String name;

    @Column(name="time_start")
    private Date timeStart;

    @Column(name="time_end")
    private Date timeEnd;

    @Column(name = "test_id")
    private long testId;

}
