package com.bot.botfortesting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="teachers", schema = "alldata")
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="pass")
    private String pass;

    @Column(name="login")
    private String login;

    @Column(name="tg_account")
    private String tgAccount;

    @Column(name = "university_id",nullable = false)
    private Long universityId;
}
