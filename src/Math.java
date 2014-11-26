// Author: James Luo
// Last modified: 11/25/2014

package com.mathproblemconstructor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Math extends Activity
{
    private Button next, prev;
    private EditText response;
    private GradeLevel gl;
    private MathProblem[] problems;
    private TextView correct, num, problem, wrong;
    private int grade, numCorrect, numCounter, numProb;
    private void diffQ()
    {
        hideFeedback();
        response.setText("");
    }
    private void displayProblem(int n)
    {
        problem.setText(problems[n++].getProblem());
        num.setText(n + ") ");
    }
    private void goToResult()
    {
        Intent i = new Intent(getBaseContext(), Result.class);
        i.putExtra("NUMBER", numProb);
        i.putExtra("CORRECT", numCorrect);
        startActivity(i);
        finish();
    }
    private void hideFeedback()
    {
        correct.setVisibility(View.INVISIBLE);
        wrong.setVisibility(View.INVISIBLE);
    }
	@Override
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_math);
        Intent i = getIntent();
        grade = i.getExtras().getInt("GRADE");
        numProb = i.getExtras().getInt("NUMBER");
        numCounter = 0;
        switch(grade)
        {
            case 0:
                gl = new FourthGrade();//Kindergarten();        // CORRECT LATER!
                break;
            case 1:
                gl = new FourthGrade();//FirstGrade();
                break;
            case 2:
                gl = new FourthGrade();//SecondGrade();
                break;
            case 3:
                gl = new FourthGrade();//ThirdGrade();
                break;
            case 4:
                gl = new FourthGrade();
                break;
            case 5:
                gl = new FourthGrade();//FifthGrade();
        }
        problems = new MathProblem[numProb];
        for(int j = 0; j < problems.length; j++)
        {
            problems[j] = new MathProblem(gl);
        }
        Button menu = (Button) findViewById(R.id.menu);
        Button submit = (Button) findViewById(R.id.submit);
		next = (Button) findViewById(R.id.next);
		prev = (Button) findViewById(R.id.previous);
		num = (TextView) findViewById(R.id.num);
		problem = (TextView) findViewById(R.id.problem);
		response = (EditText) findViewById(R.id.userResponse);
        displayProblem(numCounter);
        numCorrect = 0;
		menu.setOnClickListener(new View.OnClickListener()
		{
            public void onClick(View v)
            {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener()
		{
            public void onClick(View v)
            {
                if(!response.getText().toString().isEmpty())
                {
                    if(problems[numCounter].isCorrect(response.getText().toString()))
                        uRCorrect();
                    else
                        uRWrong();
                }
                else mkToast("Enter an answer first!");
            }
        });
		next.setOnClickListener(new View.OnClickListener()
		{
            public void onClick(View v)
            {
                numCounter++;
                if(numCounter < problems.length)
                {
                    displayProblem(numCounter);
                    diffQ();
                    prev.setEnabled(true);
                    if(numCounter == problems.length - 1)
                    {
                        next.setText("Finish");
                    }
                }
                else goToResult();
            }
        });
        prev.setOnClickListener(new View.OnClickListener()
		{
            public void onClick(View v)
            {
                numCounter--;
                displayProblem(numCounter);
                diffQ();
                if(numCounter == 0)
                {
                    prev.setEnabled(false);
                }                
            }
        });
        prev.setEnabled(false);
        correct = (TextView) findViewById(R.id.correct);
        wrong = (TextView) findViewById(R.id.wrong);
        hideFeedback();
	}
	private void mkToast(String s)
	{
	    Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
	}
	private void uRCorrect()
	{
	    correct.setVisibility(View.VISIBLE);
	}
	private void uRWrong()
	{
	    wrong.setVisibility(View.VISIBLE);
	}
}