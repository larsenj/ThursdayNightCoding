// Author: James Luo
// Last Modified: 12/18/2014

package com.mathproblemconstructor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends Activity
{
    private Button button;
    private EditText et;
    private final int MAXPROB = 30;  // Number of problems a user can construct.
    private int grade, numProb;
    
    private void mkToast(String s)
	{
	    Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onCreate(Bundle b)
	{
        super.onCreate(b);
        setTitle("");  // This is so there is no name next to the icon.
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
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener()
		{
            public void onClick(View v)
            {
                if(!et.getText().toString().isEmpty())
                {
                    updateNumProb();
                    if(numProb <= 0)
                        mkToast("Number should be more than 0");
                    else if(numProb > MAXPROB)
                        mkToast("Number should be no more than " + MAXPROB);
                    else
                    {
                        Intent i = new Intent(getBaseContext(), Math.class);
                        i.putExtra("GRADE", grade);
                        i.putExtra("NUMBER", numProb);
                        startActivity(i);
                    }
                }
                else mkToast("Enter the number of problems you want first!");
            }
        });
        setupTextSize();
    }
    private void setupTextSize()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float scale = metrics.density;
        TextView tvt = (TextView) findViewById(R.id.tvt);
        TextView tvs = (TextView) findViewById(R.id.tvs);
        TextView tvp = (TextView) findViewById(R.id.tvp);
        RadioButton gk = (RadioButton) findViewById(R.id.gradek);
        RadioButton g1 = (RadioButton) findViewById(R.id.grade1);
        RadioButton g2 = (RadioButton) findViewById(R.id.grade2);
        RadioButton g3 = (RadioButton) findViewById(R.id.grade3);
        RadioButton g4 = (RadioButton) findViewById(R.id.grade4);
        RadioButton g5 = (RadioButton) findViewById(R.id.grade5);
        float bigSize = 22*scale;
        float midSize = 18*scale;
        float size = 14*scale;
        tvt.setTextSize(TypedValue.COMPLEX_UNIT_SP, bigSize);
        tvs.setTextSize(TypedValue.COMPLEX_UNIT_SP, midSize);
        tvp.setTextSize(TypedValue.COMPLEX_UNIT_SP, midSize);
        gk.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        g1.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        g2.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        g3.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        g4.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        g5.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        et.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }
	private void updateNumProb()
	{
	    numProb = Integer.parseInt(et.getText().toString());
	}
}