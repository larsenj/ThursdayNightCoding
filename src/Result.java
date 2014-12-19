// Author: James Luo
// Last modified: 12/18/2014

package com.mathproblemconstructor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;
import java.util.Locale;

public class Result extends Activity
{
    private TextView correct, grade, skipped, time, title, wrong;

    private String calcDuration(long l)
    {
        int seconds = ((int) l) / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return(String.format(Locale.ENGLISH, "Time spent: %d minutes and %02d seconds", minutes, seconds));
    }
    private String getLetterGrade(double d)
    {
        if(d >= 97.0)
		    return(getString(R.string.gradeAPlus));
		else if(d >= 93.0)
		    return(getString(R.string.gradeA));
		else if(d >= 90.0)
		    return(getString(R.string.gradeAMinus));
		else if(d >= 87.0)
		    return(getString(R.string.gradeBPlus));
		else if(d >= 83.0)
		    return(getString(R.string.gradeB));
		else if(d >= 80.0)
		    return(getString(R.string.gradeBMinus));
		else if(d >= 77.0)
		    return(getString(R.string.gradeCPlus));
		else if(d >= 73.0)
		    return(getString(R.string.gradeC));
		else if(d >= 70.0)
		    return(getString(R.string.gradeCMinus));
		else if(d >= 60.0)
		    return(getString(R.string.gradeD));
		else
		    return(getString(R.string.gradeF));
    }
	@Override
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setTitle(getResources().getString(R.string.app_name));
		setContentView(R.layout.activity_result);
		Intent i = getIntent();
		int numCorrect = i.getExtras().getInt("CORRECT");
		int number = i.getExtras().getInt("NUMBER");
		int numSkipped = i.getExtras().getInt("SKIPPED");
		long duration = i.getExtras().getLong("DURATION");
		int numWrong = number - numCorrect;
		String letter = "";
		double score = numCorrect / (double) number * 100;
		letter = ((int) score) + "% " + getLetterGrade(score);
		title = (TextView) findViewById(R.id.results);
		correct = (TextView) findViewById(R.id.correct);
		grade = (TextView) findViewById(R.id.letterGrade);
		wrong = (TextView) findViewById(R.id.wrong);
		skipped = (TextView) findViewById(R.id.skipped);
		time = (TextView) findViewById(R.id.time);
		correct.setText("Correct: " + numCorrect + " out of " + number);
		grade.setText(letter);
		wrong.setText("Incorrect: " + numWrong + " out of " + number);
		skipped.setText("Unanswered: " + numSkipped + " out of " + number);
		time.setText(calcDuration(duration));
		setupTextSize();
	}
	private void setupTextSize()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float scale = metrics.density;
        float bigSize = 22*scale*2;
        float midSize = 18*scale*2;
        float size = 14*scale*2;
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, bigSize);
        grade.setTextSize(TypedValue.COMPLEX_UNIT_SP, midSize);
        correct.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        wrong.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        skipped.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        time.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }
}