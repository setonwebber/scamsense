package com.example.scamsense;

public class levels {
    private int questions;
    private int rightAnswers;
    private int wrongAnswers;


    public levels(){
        this.questions = 0;
        this.rightAnswers = 0;
        this.wrongAnswers = 0;
    }

    public levels(int questions){
        this.questions = questions;
        this.rightAnswers = 0;
        this.wrongAnswers = 0;
    }

    public int getQuestions(){
        return questions;
    }
}
