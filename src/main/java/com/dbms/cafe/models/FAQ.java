package com.dbms.cafe.models;

public class FAQ {
    private int id;
    private String question;
    private String answer;
    private  String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStatus() {return status;}

    public void setStatus(String status){this.status=status;}
}
