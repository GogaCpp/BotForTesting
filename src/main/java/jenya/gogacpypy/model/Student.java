package jenya.gogacpypy.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="students",schema = "alldata")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "studentname")
    private String name;

    @Column(name = "studentpass")
    private String pass;

    @Column(name="chat_id")
    private long chatId;

    @Column(name="university_id")
    private long uniId=-1;

    public Student(String name, String pass,long chatId) {

        this.name = name;
        this.pass = pass;
        this.chatId=chatId;
    }
    public Student(String name, String pass) {

        this.name = name;
        this.pass = pass;
    }

}