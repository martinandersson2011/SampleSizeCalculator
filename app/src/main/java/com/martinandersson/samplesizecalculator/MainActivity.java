package com.martinandersson.samplesizecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DEFAULT_PEOPLE = 5;
    private static final int DEFAULT_CHANCE = 3;
    private static final int DEFAULT_REPETITIONS = 10;
    private static final int PEOPLE_MULTIPLY = 100;
    private static final int REPETITIONS_MULTIPLY = 10;
    private static final int SEEKBAR_MAX = 99;

    private TextView mPeopleView;
    private TextView mChanceView;
    private TextView mRepetitionsView;
    private SeekBar mSeekBarPeople;
    private SeekBar mSeekBarChance;
    private SeekBar mSeekBarRepetitions;
    private Button mCalculateButton;
    private Button mResetButton;
    private TextView mResultView;

    private int mPeople;
    private int mWinPercentage;
    private int mRepetitions;

    private int mHigh = -1;
    private int mLow = -1;
    private int mDifference = -1;
    private String mResults = "";
    private Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        mPeopleView = (TextView) findViewById(R.id.people);
        mChanceView = (TextView) findViewById(R.id.chance);
        mRepetitionsView = (TextView) findViewById(R.id.repetitions);
        mSeekBarPeople = (SeekBar) findViewById(R.id.seekbar_people);
        mSeekBarChance = (SeekBar) findViewById(R.id.seekbar_chance);
        mSeekBarRepetitions = (SeekBar) findViewById(R.id.seekbar_repetitions);
        mCalculateButton = (Button) findViewById(R.id.calculate);
        mResetButton = (Button) findViewById(R.id.reset);
        mResultView = (TextView) findViewById(R.id.result);

        mPeople = DEFAULT_PEOPLE * PEOPLE_MULTIPLY;
        mWinPercentage = DEFAULT_CHANCE;
        mRepetitions = DEFAULT_REPETITIONS * REPETITIONS_MULTIPLY;
        mSeekBarPeople.setMax(SEEKBAR_MAX);
        mSeekBarChance.setMax(SEEKBAR_MAX);
        mSeekBarRepetitions.setMax(SEEKBAR_MAX);
        mSeekBarPeople.setProgress(DEFAULT_PEOPLE);
        mSeekBarChance.setProgress(DEFAULT_CHANCE);
        mSeekBarRepetitions.setProgress(DEFAULT_REPETITIONS);
        updateUI();
        updateResult();

        setListeners();

    }

    private void updateUI() {
        mPeopleView.setText("Number of people: " + mPeople);
        mChanceView.setText("Chance to win: " + mWinPercentage + "%");
        mRepetitionsView.setText("Iterations: " + mRepetitions);
    }

    private void setListeners() {
        mSeekBarPeople.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPeople = PEOPLE_MULTIPLY * (progress + 1);
                updateUI();
                reset();
            }
        });

        mSeekBarChance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mWinPercentage = (progress + 1);
                updateUI();
                reset();
            }
        });

        mSeekBarRepetitions.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRepetitions = REPETITIONS_MULTIPLY * (progress + 1);
                updateUI();
                reset();
            }
        });


        mCalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }

        });
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void calculate() {
        reset();
        for (int rep = 0; rep < mRepetitions; rep++) {
            int wins = 0;
            for (int i = 0; i < mPeople; i++) {
                if (mRandom.nextInt(100) < mWinPercentage) {
                    wins++;
                }
            }
            if (mHigh < wins) {
                mHigh = wins;
            }
            if (mLow < 0 || mLow > wins) {
                mLow = wins;
            }
            mDifference = mLow == 0 ? -1 : (100 * (mHigh - mLow) / mLow);
            mResults = String.valueOf(wins) + " " + mResults;
        }

        updateResult();
    }

    private void reset() {
        mResults = "";
        mHigh = -1;
        mLow = -1;
        mDifference = -1;
        updateResult();
    }

    private void updateResult() {
        String text = "";
        if (mHigh < 0 || mLow < 0) {
            text = "Click calculate to start";
        } else {
            text += "Low: " + mLow + "\n";
            text += "High: " + mHigh + "\n";
            text += "Difference: " + (mDifference < 0 ? "unlimited" : mDifference) + "%\n\n";
            text += mResults;
        }
        mResultView.setText(text);

    }
}
