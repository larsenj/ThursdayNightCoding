import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mcPanel extends JPanel {

    private JButton startButton;
    private JButton submitButton;
    private JLabel label;
    private JTextField solution;
    private boolean submitted = false;
    private kindergarten k = new kindergarten();

    //Constructor. Sets default size, background color, buttons, and shows the
    //first problem.
    public mcPanel() {
        setLayout(new FlowLayout());

        //This is where the problem is displayed. Will also have the text
        //saying if the answer is correct or not.
        label = new JLabel("");
        label.setFont(new Font("Serif", Font.BOLD, 24));
        add(label);

        //This is the textbox where the user writes their answer
        solution = new JTextField(2);
        add(solution);

        //The "submit" button will also be the "go again?" button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new submitListener());
        add(submitButton);

        setBackground(Color.gray);
        setPreferredSize(new Dimension(500, 500));
        kindergarten k = new kindergarten();
        //creates the first problem
        label.setText(k.getProblem());


    }

    private class submitListener implements ActionListener{

        /*
        The "submit" button checks if an answer has already been submitted. If
        none yet, then it will see if the answer in the textbox matches the one
        calculated by the kindergarten class and change the label appropriately
        and change the text on the button to "Go again?". If an answer has
        already been submitted then the button changes it's text to "submit"
        and generates a new math problem.
         */
        public void actionPerformed(ActionEvent submitAnswer){

            //check if an answer has been submitted yet
            if (!submitted) {
                String text = solution.getText();
                int solutionNum = Integer.parseInt(text);
                int answer = k.getAnswer();
                //check if the user's answer is correct
                if (solutionNum == answer)
                    label.setText("Right-o kiddo!");
                else
                    label.setText("D'oh! The answer was " + answer);
                submitted = true;
                submitButton.setText("Go again?");
            }
            else {
                label.setText(k.getProblem());
                submitted = false;
            }
        }
    } //end of submitListener class
}  //end of mcPanel class
