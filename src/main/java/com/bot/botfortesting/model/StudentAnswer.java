package com.bot.botfortesting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="students_answers", schema = "alldata")
@Data
@Builder
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "student_id")
    private long studentsId;

    @Column(name = "question_id")
    private long questionId;


    private long answerId;

    @Column(name="time_start")
    private LocalDateTime timeStart;

    @Column(name="time_end")
    private LocalDateTime timeEnd;

    @Column(name = "test_id")
    private long testId;

}
