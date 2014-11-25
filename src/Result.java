package com.mathproblemconstructor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Result extends Activity
{
	@Override
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_result);
		Intent i = getIntent();
		int numCorrect = i.getExtras().getInt("CORRECT");
		int number = i.getExtras().getInt("NUMBER");
		int numWrong = number - numCorrect;
		TextView correct = (TextView) findViewById(R.id.correct);
		TextView wrong = (TextView) findViewById(R.id.wrong);
		correct.setText(numCorrect + " out of " + number);
		wrong.setText(numWrong + " out of " + number);
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener()
		{
            public void onClick(View v)
            {
                finish();
            }
        });
	}
}