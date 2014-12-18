// Author: James Luo
// Last modified: 12/17/2014

package com.mathproblemconstructor;

import java.util.Random;

public class FifthGrade implements GradeLevel
{
    private final int ADD = 100;
    private final int MULT = 30;
    private final int WHAT = 12;
    private int a, b, c, d, answer;
    private Random r = new Random();
    private String problem;

    private void addition()
    {
        a = genNum(ADD);
        b = genNum(ADD);
        c = genNum(ADD);
        d = a + b + c;
    }
    private String divWordProb()
    {
        String[] names = {"Josh", "Brian", "James", "Rachael",
            "Wilma", "Aaron", "Sam", "Dean", "Ally", "Tina",
            "Lisa", "Jenny", "Chris", "Jason"};
        String[] items = {"pieces of candy", "pencils", "DVDs",
            "books", "hats", "balloons", "erasers", "marbles",
            "cards"};
        multiplication();
        answer = a;
        int i = genNum(items.length);
        return(names[genNum(names.length)] + " has " + c + " " +
            items[i] + " to split up between " +
            b + " friends. How many " + items[i] +
            " does each friend get?");
    }
    private int genNum(int i) {return(r.nextInt(i));}
    public int getAnswer() {return(answer);}
    public String getProblem()
    {
        switch(genNum(WHAT))
        {
            case 0:  // a + b + c = ?
                addition();
                problem = a + " + " + b + " + " + c + " = ?";
                answer = d;
                break;
            case 1:  // a + b + ? = d
                addition();
                problem = a + " + " + b + " + ? = " + d;
                answer = c;
                break;
            case 2:  // a + ? + c = d
                addition();
                problem = a + " + ? + " + c + " = " + d;
                answer = b;
                break;
            case 3:  // ? + b + c = d
                addition();
                problem = "? + " + b + " + " + c + " = " + d;
                answer = a;
                break;
            case 4:  // a x b = ?
                multiplication();
                problem = a + " " + (char)215 + " " + b + " = ?";
                answer = c;
                break;
            case 5:  // a x ? = c
                multiplication();
                problem = a + " " + (char)215 + " ? = " + c;
                answer = b;
                break;
            case 6:  // ? x b = c
                multiplication();
                problem = "? " + (char)215 + " " + b + " = " + c;
                answer = a;
                break;
            case 7:  // c / a = ?
                multiplication();
                problem = c + " " + (char)247 + " " + a + " = ?";
                answer = b;
                break;
            case 8:  // c / ? = b
                multiplication();
                problem = c + " " + (char)247 + " ? = " + b;
                answer = a;
                break;
            case 9:  // ? / a = b
                multiplication();
                problem = "? " + (char)247 + " " + a + " = " + b;
                answer = c;
                break;
            case 10:  // Multiplication word problem
                multiplication();
                problem = "If you have " + a + " groups of objects, " +
                    "and each group contains " + b + " objects " +
                    "each, how many total objects do you have?";
                answer = c;
                break;
            case 11:  // Division word problem
                problem = divWordProb();
        }
        return(problem);
    }
    private void multiplication()
    {
        a = genNum(MULT) + 1;
        b = genNum(MULT) + 1;
        c = a * b;
    }
}