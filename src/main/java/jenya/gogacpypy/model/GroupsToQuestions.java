package jenya.gogacpypy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="questions_to_group", schema = "alldata")
@Data
public class GroupsToQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="question_id")
    private long questionId;

    @Column(name="group_id")
    private long groupId;
}
