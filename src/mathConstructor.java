package com.example.Math_Constructor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class mathConstructor extends Activity {
    /**
     * Called when the activity is first created.
     */
    private boolean submitted = false;
    private TextView problemField;
    private Button submitButton;

    /* When the main menu is implemented we will need a way to use it to
     establish the level variable below */
    char level;
    String mathProb;
    int mathAnswer;
    gradeLevel grade;
    //private TextView solution;

    //based on user's "level", will instantiate an appropriate object
    switch (level) {
        case 'k':
            grade = new kindergarten();
            break;
        case '1':
            grade = new firstGrade();
            break;
        case '3':
            grade = new ThirdGrade();
            break;
        default:
            grade = new kindergarten();
            //System.out.println("That level not yet implemented");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mathProb = grade.getProblem();
        mathAnswer = grade.getAnswer();

        //get references to the text fields
        problemField = (TextView) findViewById(R.id.problemField);
        //solution = (TextView) findViewById(R.id.solution);
        problemField.setText(mathProb);

        //sets the TextWatcher for the solution
        EditText solution = (EditText) findViewById(R.id.solution);
        //solution.addTextChangedListener(solutionWatcher);

        //button
        submitButton = (Button) findViewById(R.id.submitButton);
        //submitButton.setOnClickListener(new View.OnClickListener
        submitButton.setOnClickListener(new View.OnClickListener());


    }   //end of onCreate method

    public View.OnClickListener customClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (!submitted) {
                //get the text of the solution and call it solutionNum

                int answer = ((kindergarten) getApplication()).getAnswer();
                if (solutionNum == answer)
                    problemField.setText("That is correct!");
                else
                    problemField.setText("That answer is incorrect.");
                submitted = true;
                submitButton.setText("Next");
            } else {
                problemField.setText(((kindergarten) getApplication()).getProblem());
                submitted = false;
            }


        }


        //check if an answer has been submitted yet

        //check if answer is correct

    }
}