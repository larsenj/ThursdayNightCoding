// Author: James Luo
// Last modified: 12/7/2014

package com.mathproblemconstructor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends Activity
{
	@Override
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setTitle("");
		setContentView(R.layout.activity_result);
		Intent i = getIntent();
		int numCorrect = i.getExtras().getInt("CORRECT");
		int number = i.getExtras().getInt("NUMBER");
		int numWrong = number - numCorrect;
		TextView correct = (TextView) findViewById(R.id.correct);
		TextView wrong = (TextView) findViewById(R.id.wrong);
		correct.setText(numCorrect + " out of " + number);
		wrong.setText(numWrong + " out of " + number);
	}
}