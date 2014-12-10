// Author: James Luo
// Last modified: 12/9/2014

package com.mathproblemconstructor;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.util.TypedValue;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class Math extends FragmentActivity
    implements TextToSpeech.OnInitListener
{
    private GradeLevel gl;
    private MathProblem[] problems;
    private ProblemsPagerAdapter ppa;
private TextToSpeech tts;
private TextView problem;
    private ViewPager vp;
    private int grade, numCorrect, numProb, numSkipped;
    private long startTime;
    
    private class ProblemFragment extends Fragment
    {
        private int probNum;
        @Override
        public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle b)
        {
            View v = li.inflate(R.layout.problem, vg, false);
            final Bundle args = getArguments();
            probNum = args.getInt("PROBLEM");
            ((TextView) v.findViewById(R.id.num)).setText(
                Integer.toString(probNum + 1) + ") ");
            //((TextView) v.findViewById(R.id.problem)).setText(
            //    problems[probNum].getProblem());
problem = (TextView) v.findViewById(R.id.problem);
problem.setText(problems[probNum].getProblem());
            Button submit = (Button) v.findViewById(R.id.submit);
            final EditText response =
                (EditText) v.findViewById(R.id.userResponse);
            final TextView correct = (TextView) v.findViewById(R.id.correct);
            final TextView wrong = (TextView) v.findViewById(R.id.wrong);
            submit.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    if(!response.getText().toString().isEmpty())
                    {
                        correct.setVisibility(View.INVISIBLE);
                        wrong.setVisibility(View.INVISIBLE);
                        if(problems[probNum].isCorrect(response.getText().toString()))
                            correct.setVisibility(View.VISIBLE);
                        else
                            wrong.setVisibility(View.VISIBLE);
                    }
                }
            });
int stringLength = ((TextView) v.findViewById(R.id.problem)).getText().toString().length();
if(stringLength < 10)
    ((TextView) v.findViewById(R.id.problem)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 200);
else if(stringLength < 20)
    ((TextView) v.findViewById(R.id.problem)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 175);
else if(stringLength < 30)
    ((TextView) v.findViewById(R.id.problem)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 160);
else if(stringLength < 40)
    ((TextView) v.findViewById(R.id.problem)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 140);
else if(stringLength < 50)
    ((TextView) v.findViewById(R.id.problem)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 120);
else if(stringLength < 60)
    ((TextView) v.findViewById(R.id.problem)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
else
    ((TextView) v.findViewById(R.id.problem)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 75);
/*
DisplayMetrics metrics = new DisplayMetrics();
getWindowManager().getDefaultDisplay().getMetrics(metrics);
float scale = metrics.density;
((TextView) v.findViewById(R.id.problem)).setText("density: " + scale
    + "\ndensityDpi: " + metrics.densityDpi + "\nheightPixels: " + metrics.heightPixels
    + "\nscaledDensity: " + metrics.scaledDensity + "\nwidthPixels: " + metrics.widthPixels
    + "\nxdpi: " + metrics.xdpi + "\nydpi: " + metrics.ydpi
    + "\nviewWidth: " + ((TextView) v.findViewById(R.id.problem)).getWidth());
((TextView) v.findViewById(R.id.problem)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 10*scale);
*/
// how to set text size of textview dynamically for different screens  values-ldpi
// android dynamically change text size
            return(v);
        }
    }
    private class ProblemsPagerAdapter extends FragmentStatePagerAdapter
    {
        public ProblemsPagerAdapter(FragmentManager fm) {super(fm);}
        @Override
        public Fragment getItem(int i)
        {
            Fragment f = new ProblemFragment();
            Bundle args = new Bundle();
            args.putInt("PROBLEM", i);
            f.setArguments(args);
            return(f);
        }
        @Override
        public int getCount() {return(numProb);}
        @Override
        public CharSequence getPageTitle(int position)
        {
            return("Problem " + (position + 1));
        }
    }
    
    private void goToResults()
    {
        Intent i = new Intent(getBaseContext(), Result.class);
        i.putExtra("NUMBER", numProb);
        updateNums();
        i.putExtra("CORRECT", numCorrect);
        i.putExtra("SKIPPED", numSkipped);
        i.putExtra("DURATION", System.currentTimeMillis() - startTime);
        startActivity(i);
        finish();
    }
    private boolean isLandscape()
    {
        Resources r = getResources();
        DisplayMetrics d = r.getDisplayMetrics();
        return(d.widthPixels > d.heightPixels);
    }
    private void mkToast(String s)
    {
        Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
    }


    private void speakOut()
	{
	    String text = problem.getText().toString();
	    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}


    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_math);
        Intent i = getIntent();
        grade = i.getExtras().getInt("GRADE");
        numProb = i.getExtras().getInt("NUMBER");
        switch(grade)
        {
            case 0:
                gl = new Kindergarten();
                break;
            case 1:
                gl = new FirstGrade();
                break;
            case 2:
                gl = new FourthGrade();//SecondGrade();                        // Fix later
                break;
            case 3:
                gl = new ThirdGrade();
                break;
            case 4:
                gl = new FourthGrade();
                break;
            case 5:
                gl = new FourthGrade();//FifthGrade();                         // Fix later
        }
        problems = new MathProblem[numProb];
        for(int j = 0; j < problems.length; j++)
        {
            problems[j] = new MathProblem(gl);
        }
        ppa = new ProblemsPagerAdapter(getSupportFragmentManager());
        vp = (ViewPager) findViewById(R.id.pager);
        vp.setAdapter(ppa);
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        numCorrect = 0;
        numSkipped = 0;
tts = new TextToSpeech(this, this);
        startTime = System.currentTimeMillis();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu m)
    {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.math, m);
        return(super.onCreateOptionsMenu(m));
    }
    
    @Override
	public void onDestroy()
	{
	    if(tts != null)
	    {
	        tts.stop();
	        tts.shutdown();
	    }
	    super.onDestroy();
	}
	@Override
	public void onInit(int status)
	{
	    if(status == TextToSpeech.SUCCESS)
	    {
	        int result = tts.setLanguage(Locale.US);
	        if(result == TextToSpeech.LANG_MISSING_DATA ||
	            result == TextToSpeech.LANG_NOT_SUPPORTED)
	        {
	            Toast.makeText(getBaseContext(), 
	                "This language is not supported",
	                Toast.LENGTH_SHORT).show();
	        }
	        else
	        {
	            //speakOut();
	        }
	    }
	    else
	    {
	        Toast.makeText(getBaseContext(), 
	            "Initialization failed",
	            Toast.LENGTH_SHORT).show();
	    }
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return(true);
            case R.id.action_results:
                goToResults();
                return(true);
            case R.id.action_tts:
                speakOut();
                return(true);
            default:
                return(super.onOptionsItemSelected(item));
        }
    }
    private void updateNums()
    {
        for(int i = 0; i < problems.length; i++)
        {
            if(problems[i].isCorrect()) numCorrect++;
            if(problems[i].isSkipped()) numSkipped++;
        } 
    }
}