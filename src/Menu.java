// Author: James Luo
// Last Modified: 11/24/2014

package com.mathproblemconstructor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Menu extends Activity
{
    private EditText et;
    private final int MAXPROB = 60;
    private int grade, numProb;
	@Override
	protected void onCreate(Bundle b)
	{
        super.onCreate(b);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
		grade = 0;
		numProb = 15;
		RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
		    public void onCheckedChanged(RadioGroup rg, int id)
		    {
		        switch(id)
		        {
		            case R.id.gradek: grade = 0; break;
		            case R.id.grade1: grade = 1; break;
		            case R.id.grade2: grade = 2; break;
		            case R.id.grade3: grade = 3; break;
		            case R.id.grade4: grade = 4; break;
		            case R.id.grade5: grade = 5;
		        }
		    }
		});
		et = (EditText) findViewById(R.id.et);
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener()
		{
            public void onClick(View v)
            {
                if(!et.getText().toString().isEmpty())
                {
                    updateNumProb();
                    if(numProb <= MAXPROB)
                    {
                        Intent i = new Intent(getBaseContext(), Math.class);
                        i.putExtra("GRADE", grade);
                        i.putExtra("NUMBER", numProb);
                        startActivity(i);
                    }
                    else mkToast("Number should be no more than " + MAXPROB);
                }
                else mkToast("Enter the number of problems you want first!");
            }
        });
	}
	private void mkToast(String s)
	{
	    Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
	}
	private void updateNumProb()
	{
	    numProb = Integer.parseInt(et.getText().toString());
	}
}