package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    TextView questionTextView;
    TextView scoreCardTextView;
    TextView rightOrWrongTextView;
    Button goButton;
    Button playAgainButton;

    CountDownTimer countDownTimer;

    GridLayout numbersGridLayout;

    GridLayout topHeaderLayout;

    int result;

    int correctAnswerCount = 0;
    int numberOfQuestionsAttempted = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextview);
        questionTextView = findViewById(R.id.questionTextView);
        scoreCardTextView = findViewById(R.id.scoreTextView);
        rightOrWrongTextView = findViewById(R.id.resultTextView);

        playAgainButton = findViewById(R.id.playAgainButton);
        goButton = findViewById(R.id.startButton);
        numbersGridLayout = (GridLayout) findViewById(R.id.numbersLayout);
        topHeaderLayout = (GridLayout) findViewById(R.id.topHeaderLayout);
    }

    public void startGame(View view) {
        goButton.setVisibility(View.INVISIBLE);
        setVisibilityForLayouts(View.VISIBLE);
        startTimer();
        setQuestionOnTextView();
    }

    public void playAgain(View view) {
        startTimer();
        correctAnswerCount = 0;
        numberOfQuestionsAttempted = 0;
        scoreCardTextView.setText("0/0");
        rightOrWrongTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        enableOrDisableGridLayout(numbersGridLayout, true);
    }

    public void resultSelected(View view) {
        Button selectedButton = (Button) view;
        String clickedResult = selectedButton.getText().toString();
        if (clickedResult.equals(Integer.toString(result))) {
            rightOrWrongTextView.setText("Correct!");
            correctAnswerCount++;
        } else {
            rightOrWrongTextView.setText("Wrong!");
            numberOfQuestionsAttempted++;
        }
        scoreCardTextView.setText(correctAnswerCount + "/" + numberOfQuestionsAttempted);
        rightOrWrongTextView.setVisibility(View.VISIBLE);
        setQuestionOnTextView();
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                long remaining = millisUntilFinished / 1000;
                timerTextView.setText((int) remaining + "s");
            }

            @Override
            public void onFinish() {
                rightOrWrongTextView.setText("Done!");
                rightOrWrongTextView.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
                enableOrDisableGridLayout(numbersGridLayout, false);
            }
        };
        countDownTimer.start();
    }

    public void enableOrDisableGridLayout(GridLayout layout, boolean value) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            view.setEnabled(value);
        }
    }

    public void setQuestionOnTextView() {
        Random random = new Random();
        int firstNumber = random.nextInt(10);
        int secondNumber = random.nextInt(20);
        result = firstNumber + secondNumber;
        ArrayList<Integer> numberLayoutIntegers = new ArrayList<Integer>();
        putAnswerInLayoutIntegers(numberLayoutIntegers);
        questionTextView.setText(firstNumber + " + " + secondNumber);
    }

    public void putAnswerInLayoutIntegers(ArrayList<Integer> numberLayoutIntegers) {
        Random random = new Random();
        int locationOfCorrectAnswer = random.nextInt(4);
        if (!numberLayoutIntegers.isEmpty()) {
            numberLayoutIntegers.clear();
        }
        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                numberLayoutIntegers.add(result);
            } else {
                int number = (int) (Math.random() * 10) * 3;
                numberLayoutIntegers.add(number);
            }
        }

        Set<Integer> set = new HashSet<>(numberLayoutIntegers);

        if (set.size() < numberLayoutIntegers.size()) {
            /* There are duplicates */
            putAnswerInLayoutIntegers(numberLayoutIntegers);
        }
        setValuesToGridLayout(numberLayoutIntegers);
    }

    public void setValuesToGridLayout(ArrayList<Integer> numberLayoutIntegers) {
        for (int i = 0; i < numbersGridLayout.getChildCount(); i++) {
            Button eachButton = (Button) numbersGridLayout.getChildAt(i);
            eachButton.setText(numberLayoutIntegers.get(i).toString());
        }
    }

    public void setVisibilityForLayouts(int value) {
        setVisibilityForGridLayout(topHeaderLayout, value);
        setVisibilityForGridLayout(numbersGridLayout, value);
    }

    @SuppressLint("NewApi")
    public void setVisibilityForGridLayout(GridLayout layout, int value) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            view.setVisibility(value);
        }
    }

}
