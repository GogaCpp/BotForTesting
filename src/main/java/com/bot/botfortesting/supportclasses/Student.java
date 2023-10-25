package com.bot.botfortesting.supportclasses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

    private int id;
    private String name;
    private String pass;
    public Student(int id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public Student( String name, String pass) {

        this.name = name;
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}