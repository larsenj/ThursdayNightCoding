/*
All grade level classes will implement this interface. This allows the specific
grade level needed by the program to be created dynamically based on user input.

This interface is designed so that each grade level class must have at least two
functions:
    1.getProblem() which will return a string containing the math problem
    2.getAnswer() which will return the number that is the answer to the
    problem.
*/

package com.mathproblemconstructor;

public interface GradeLevel {
    public String getProblem();
    public int getAnswer();
}
