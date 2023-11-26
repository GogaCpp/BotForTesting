package jenya.gogacpypy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="answers", schema = "alldata")
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="answer")
    private String answer;

    @Column(name="correct")
    private boolean correct;

//    @Column(name="question_id",insertable=false, updatable=false)
//    private long questionId;


    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;

//    public Answer(String answer,boolean correct,long questionId) {
//        this.answer=answer;
//        this.correct=correct;
//        this.questionId=questionId;
//    }
}
