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
    @JoinColumn(name="id_group")
    private Group group;

//    @Column(name="id_test")
//    private long testId;

    @ManyToOne
    @JoinColumn(name="id_test")
    private Test test;

    @Column(name="count_questions")
    private long questionsCount;
}
