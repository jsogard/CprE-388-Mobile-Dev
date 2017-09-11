package com.cpre388_hw1.joesogard.geoquiz;

/**
 * Created by Joe Sogard on 9/1/2017.
 */

public class MultipleChoiceQuestion {

    private String question;
    private String correctAnswer;
    private String[] incorrectAnswers;

    public MultipleChoiceQuestion(String question,
                                  String correctAnswer,
                                  String... incorrectAnswers){
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getIncorrectAnswer(int index) {
        return incorrectAnswers[index];
    }

    public String[] getAllAnswers(){
        String[] allAnswers = new String[incorrectAnswers.length + 1];
        for(int i = 0; i < allAnswers.length; i++)
            allAnswers[i] = (i == 0 ? correctAnswer : incorrectAnswers[i-1]);
        return allAnswers;
    }

    public int getAnswerCount(){
        return incorrectAnswers.length + 1;
    }
}
