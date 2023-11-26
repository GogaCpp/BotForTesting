package jenya.gogacpypy.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="students", schema = "alldata")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "studentname")
    private String name;

    @Column(name="chat_id")
    private long chatId;

    @Column(name="groupname")
    private String group;

    @ManyToOne
    @JoinColumn(name="university_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private University university;

}