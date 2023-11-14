package com.bot.botfortesting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="collections_to_questions", schema = "alldata")
@Data
public class CollectionsToQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="id_question")
    private long questionId;

    @Column(name="id_collection")
    private long collectionId;


}
