package com.mathproblemconstructor;

import java.util.Random;

public class FirstGrade implements GradeLevel {

    private int firstDigit, secondDigit, thirdDigit, answer;
    private Random randNum = new Random();

    public int Problem(){

        //generation of random numbers ensures the sum is no more then 20
        firstDigit = randNum.nextInt(21);
        secondDigit = randNum.nextInt(21-firstDigit);
        thirdDigit = randNum.nextInt(21-firstDigit-secondDigit);

        //returning a digit from 0 to 3 allows for 4 possibilities for the 
        //operation
        return randNum.nextInt(4);
    }

    public void Addition(int first, int second, int third){
        answer = first + second + third;
    }

    public void Addition(int first, int second){
        answer = first + second ;
    }

    public void Subtraction(int first, int second, int third){
        answer = first - second - third;
    }

    public void Subtraction(int first, int second){
        answer = first - second ;
    }

    public String getProblem() {
        int operation = Problem();

        //3 number addition
        if (operation == 0) {
            Addition(firstDigit, secondDigit, thirdDigit);
            return (firstDigit + " + " + secondDigit + " + " + thirdDigit +
                    " = ?");
        }

        //2 number addition
        else if (operation ==1)  {
            Addition(firstDigit, secondDigit);
            return (firstDigit + " + " + secondDigit + " = ?");
        }

        //2 number subtraction
        else if (operation == 2) {
            //checks to make sure subtraction doesn't come out negative
            while (firstDigit < secondDigit)
                secondDigit = randNum.nextInt(21);
            Subtraction(firstDigit, secondDigit);
            return (firstDigit + " - " + secondDigit + " = ?");
        }

        //3 number subtraction
        else {
            //check to make sure problem doesn't come out negative
            while (firstDigit - secondDigit - thirdDigit < 0) {
                secondDigit = randNum.nextInt(21-firstDigit);
                 thirdDigit = randNum.nextInt(21-firstDigit-secondDigit);
            }
            Subtraction(firstDigit, secondDigit, thirdDigit);
            return (firstDigit + " - " + secondDigit + " - " + thirdDigit +
                    " = ?");
        }

    }   //end of getProblem method

    public int getAnswer(){
        return answer;
    }
} //end of firstGrade class
