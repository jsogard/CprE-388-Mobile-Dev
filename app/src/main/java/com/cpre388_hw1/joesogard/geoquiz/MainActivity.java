package com.cpre388_hw1.joesogard.geoquiz;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

import android.support.design.widget.Snackbar;

public class MainActivity extends AppCompatActivity {


    private MultipleChoiceQuestion[] questions;
    private int questionNumber;
    private int attempts;
    private final int attemptsReset = 2;

    private Button[] answerButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll = (LinearLayout) findViewById(R.id.answersHolder);
        answerButtons = new Button[5];
        for(int i = 0; i < 5; i++)
            answerButtons[i] = (Button)ll.getChildAt(i);

        questions = new MultipleChoiceQuestion[] {
                new MultipleChoiceQuestion("Which country has the most coastline?", "Canada", "Australia", "Russia", "China"),
                new MultipleChoiceQuestion("How many continents are there?", "7", "5", "8", "12"),
                new MultipleChoiceQuestion("Penguins can be found in...", "All are true", "Antarctica", "Africa", "Zoos"),
                new MultipleChoiceQuestion("How many Koreas are there?", "2", "1", "0", "4", "3"),
                new MultipleChoiceQuestion("Which country has the most Popes per square mile?", "Vatican City", "Italy", "Spain", "San Marino", "Turkey"),
                new MultipleChoiceQuestion("Minnesota is the land of ____ lakes", "10,000", "millions of", "several", "97,400", "the"),
                new MultipleChoiceQuestion("What is the Northernmost Contiguous U.S. State?", "Minnesota", "Maine", "Alaska", "Hawaii", "Washington")
        };


        setContentView(R.layout.activity_main);
    }

    /**
     * starts the game
     * @param view
     */
    public void startGame(View view){
        questionNumber = 0;
        questions = shuffle(questions);
        attempts = attemptsReset;
        displayQuestion();
    }

    /**
     * displays current question
     * sets question text
     * sets answers text, visibility, onclick appropriately
     */
    private void displayQuestion(){
        //attempts = attemptsReset;
        MultipleChoiceQuestion currentQuestion = questions[questionNumber];

        TextView questionText = (TextView) findViewById(R.id.question);
        questionText.setText(currentQuestion.getQuestion());


        Button btn;

        String[] answers = shuffle(currentQuestion.getAllAnswers());
        int i;
        for(i = 0; i < currentQuestion.getAnswerCount(); i++){
            btn = answerButtons[i];
            btn.setText(answers[i]);
            btn.setVisibility(View.VISIBLE);
            if(answers[i].equals(currentQuestion.getCorrectAnswer()))
                btn.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){correctAnswer();}
                });
            else
                btn.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){wrongAnswer();}
                });
        }
        for(; i < 5; i++){
            answerButtons[i].setVisibility(View.INVISIBLE);
        }
    }


    /**
     * informs user of correctness
     */
    public void correctAnswer(){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.question), "Correct!", Snackbar.LENGTH_SHORT);
        snackbar.show();

        showNextButton();
    }

    /**
     * decrements attmepts
     * informs user hes incorrect
     * offers user to try again
     */
    public void wrongAnswer(){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.question), "Incorrect!", Snackbar.LENGTH_SHORT);

        attempts--;
        if(attempts <= 0) {
            snackbar.show();
            gameOver(false);
            return;
        }

        showNextButton();
        snackbar.setAction("Try again?", new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                displayQuestion();
            }
        });
        snackbar.show();
    }



    /**
     * after a question is answered, shows button to take to next question
     */
    private void showNextButton(){

        answerButtons[0].setText("Next Question");
        answerButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNumber++;
                if(questionNumber < questions.length)
                    displayQuestion();
                else gameOver(true);
            }
        });
        for(int i = 1; i < 5; i++)
            answerButtons[i].setVisibility(View.INVISIBLE);
    }



    /**
     * game over logic
     * @param winner whether player won or lost
     */
    private void gameOver(boolean winner){
        TextView text = (TextView) findViewById(R.id.question);
        text.setText(winner ? "You Win!!" : "You are a loser.");

        Button b = answerButtons[0];
        b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){startGame(null);}
        });
        b.setText("Play Again?");
        for(int i = 1; i < 5; i++)
            answerButtons[i].setVisibility(View.INVISIBLE);
    }


    private <T> T[] shuffle(T[] arr){
        Random r = new Random();
        int j;
        T temp;
        for(int i = 0; i < arr.length; i++){
            j = r.nextInt(arr.length);
            temp = arr[j];
            arr[j] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

}
