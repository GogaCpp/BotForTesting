package jenya.gogacpypy.model;

import jakarta.persistence.*;
import lombok.*;

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
    private University university;

    public Student(String name, long chatId) {

        this.name = name;
        this.chatId=chatId;
    }
    public Student(String name ) {

        this.name = name;
    }
}