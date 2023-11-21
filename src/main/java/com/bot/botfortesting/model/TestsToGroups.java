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

    @Column(name="group_id")
    private long groupId;

    @Column(name="test_id")
    private long testId;

    @Column(name="count_questions")
    private long questionsCount;

    public TestsToGroups(long groupId,long testId,long questionsCount){
        this.groupId=groupId;
        this.testId=testId;
        this.questionsCount=questionsCount;


    }


}
