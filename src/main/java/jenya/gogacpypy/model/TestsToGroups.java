package jenya.gogacpypy.model;

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

//    @Column(name="id_group")
//    private long groupId;

    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

//    @Column(name="id_test")
//    private long testId;

    @ManyToOne
    @JoinColumn(name="test_id")
    private Test test;

    @Column(name="count_questions")
    private long questionsCount;
}