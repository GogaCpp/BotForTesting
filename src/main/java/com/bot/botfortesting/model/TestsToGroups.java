package com.bot.botfortesting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tests_to_groups", schema = "alldata")
@Data
public class TestsToGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="id_group")
    private long groupId;

    @Column(name="id_test")
    private long testId;

    @Column(name="count_questions")
    private long questionsCount;
}
