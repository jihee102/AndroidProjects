package com.jihee.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView timerTextView;
    Button goButton;
    long countdownMinutes;
    long countdownSeconds;
    boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        timerTextView = findViewById(R.id.textView);
        goButton = findViewById(R.id.button);

        seekBar.setMax(600);
        seekBar.setProgress(30);
        timerTextView.setText("00:30");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void updateTime(long secondsLeft){
        countdownMinutes = secondsLeft/60;
        countdownSeconds = secondsLeft - countdownMinutes*60;
        String secondString = Long.toString(countdownSeconds);
        if(secondString.equals("0")){
            secondString = "00";
        }else if(countdownSeconds < 10){
            secondString = "0"+countdownSeconds;
        }else{
            secondString = ""+countdownSeconds;
        }

        timerTextView.setText(countdownMinutes+":"+secondString);
    }

    public void ticking(View view){
        if(counterIsActive){
            seekBar.setProgress(30);
            timerTextView.setText("00:30");
            seekBar.setEnabled(true);
            countDownTimer.cancel();
            counterIsActive=false;
            goButton.setText("GO!");
        }else {
            counterIsActive=true;
            seekBar.setEnabled(false);
            goButton.setText("STOP!");
             countDownTimer = new CountDownTimer(seekBar.getProgress()*1000, 1000){
                public void onTick(long millisecondsUntilDone){
                    updateTime(millisecondsUntilDone/1000);
                }
                public void onFinish(){
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                    mediaPlayer.start();
                }
            }.start();
        }

    }
}
