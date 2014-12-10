// Author: James Luo
// Last modified: 12/8/2014

package com.mathproblemconstructor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Locale;

public class Result extends Activity
{
    private String calcDuration(long l)
    {
        int seconds = ((int) l) / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return(String.format(Locale.ENGLISH,
            "%d minutes and %02d seconds", minutes, seconds));
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
		setTitle("");
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
		TextView correct = (TextView) findViewById(R.id.correct);
		TextView grade = (TextView) findViewById(R.id.letterGrade);
		TextView wrong = (TextView) findViewById(R.id.wrong);
		TextView skipped = (TextView) findViewById(R.id.skipped);
		TextView time = (TextView) findViewById(R.id.time);
		correct.setText(numCorrect + " out of " + number);
		grade.setText(letter);
		wrong.setText(numWrong + " out of " + number);
		skipped.setText(numSkipped + " out of " + number);
		time.setText(calcDuration(duration));
	}
}