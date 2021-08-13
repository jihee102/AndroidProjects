package com.jihee.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button goButton, againButton;
    ArrayList<Integer> answers = new ArrayList<>();
    int correctAnswer;
    TextView result,sumTextview;
    TextView count, timer;
    GridLayout gridLayout;
    int totalGameCount =0;
    int correctCount =0;
    boolean gameOne = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.resultTextview);
        count = findViewById(R.id.countTextview);
        goButton= findViewById(R.id.goButton);
        againButton = findViewById(R.id.againButton);
        timer = findViewById(R.id.timeTextview);
        gridLayout= findViewById(R.id.gridLayout);


    }
    public void gameCountDown(){
        gameOne =true;
        CountDownTimer countDownTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(millisUntilFinished/1000+"s");
            }

            @Override
            public void onFinish() {
                result.setText("Done");
                againButton.setVisibility(View.VISIBLE);
                gameOne=false;
            }
        }.start();
    }

    public void makeGame(){
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        if(gameOne){
            answers = new ArrayList<>();
            sumTextview = findViewById(R.id.sumTextview);


            Random random = new Random();

            int a = random.nextInt(21);
            int b = random.nextInt(21);
            correctAnswer = a+b;
            sumTextview.setText(a+"+"+b);

            int randomPlaceOfCorrectAnswer = random.nextInt(4);
            for (int i = 0; i <4 ; i++) {
                if(i == randomPlaceOfCorrectAnswer){
                    answers.add(a+b);
                }else{
                    int randomNr = random.nextInt(42);
                    while (randomNr == a+b){
                        randomNr = random.nextInt(42);
                    }
                    answers.add(randomNr);
                }
            }
            button1.setText(Integer.toString(answers.get(0)));
            button2.setText(Integer.toString(answers.get(1)));
            button3.setText(Integer.toString(answers.get(2)));
            button4.setText(Integer.toString(answers.get(3)));
        }



    }

    public void goClicked(View view){
        view.setVisibility(View.INVISIBLE);
        gameCountDown();
        makeGame();
        timer.setVisibility(View.VISIBLE);
        sumTextview.setVisibility(View.VISIBLE);
        count.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        totalGameCount=0;
        correctCount=0;
        count.setText(correctCount+"/"+totalGameCount);
    }

    public void userAnswerClicked(View view){
        totalGameCount++;
        Button clickedBtn = (Button) view;
        if(clickedBtn.getText().toString().equals(Integer.toString(correctAnswer))){
            correctCount++;
            result.setText("Correct!:)");

        }else{
            result.setText("Wrong:(");
        }
        count.setText(correctCount+"/"+totalGameCount);
        makeGame();
    }
}
