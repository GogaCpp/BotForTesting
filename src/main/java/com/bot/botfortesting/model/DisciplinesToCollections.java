package com.bot.botfortesting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="disciplines_to_collections", schema = "alldata")
@Data
public class DisciplinesToCollections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="disciplines_id")
    private long disciplineId;

    @Column(name="collection_id")
    private long collectionId;
}
