package com.bot.botfortesting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="groups_to_questions", schema = "alldata")
@Data
public class QuestionsToGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="question_id")
    private long questionId;

    @Column(name="group_id")
    private long groupId;

    public QuestionsToGroups(long questionId,long groupId){
        this.groupId=groupId;
        this.questionId=questionId;
    }
}
