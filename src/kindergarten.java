/*
 CCSS.MATH.CONTENT.K.OA.A.1
 Represent addition and subtraction with objects, fingers, mental images, drawings1, sounds (e.g., claps), acting out
 situations, verbal explanations, expressions, or equations.
 CCSS.MATH.CONTENT.K.OA.A.2
 Solve addition and subtraction word problems, and add and subtract within 10, e.g., by using objects or drawings to
 represent the problem.
 CCSS.MATH.CONTENT.K.OA.A.3
 Decompose numbers less than or equal to 10 into pairs in more than one way, e.g., by using objects or drawings, and
 record each decomposition by a drawing or equation (e.g., 5 = 2 + 3 and 5 = 4 + 1).
 CCSS.MATH.CONTENT.K.OA.A.4
 For any number from 1 to 9, find the number that makes 10 when added to the given number, e.g., by using objects or
 drawings, and record the answer with a drawing or equation.
 CCSS.MATH.CONTENT.K.OA.A.5
 Fluently add and subtract within 5.
 */

import java.util.Random;

public class kindergarten implements gradeLevel{
    private int firstDigit, secondDigit, answer;
    private Random randNum = new Random();

    public int kProblem(){

        firstDigit = randNum.nextInt(11);
        secondDigit = randNum.nextInt(11-firstDigit);
        return randNum.nextInt(2);
    }

    public void kAddition(int first, int second){
        answer = first + second;
    }

    public void kSubtraction(int first, int second){
        answer = first - second;
    }

    public String getProblem() {
        int operation = kProblem();

        if (operation == 0) {
            kAddition(firstDigit, secondDigit);
            return (firstDigit + " + " + secondDigit + " = ?");
        }
        else {
            //checks to make sure subtraction doesn't come out negative
            while (firstDigit < secondDigit)
                secondDigit = randNum.nextInt(10);
            kSubtraction(firstDigit, secondDigit);
            return (firstDigit + " - " + secondDigit + " = ?");
        }

    }


    public int getAnswer(){
        return answer;
    }
} //end of kindergarten class
