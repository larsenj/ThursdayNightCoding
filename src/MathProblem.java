// Author: James Luo
// Last modified: 12/9/2014

package com.mathproblemconstructor;

public class MathProblem
{
    private String problem, userResponse;
    private boolean skipped, userCorrect;
    private int answer;
    
    public MathProblem(GradeLevel gl)
    {
        problem = gl.getProblem();
        answer = gl.getAnswer();
        skipped = true;
        userCorrect = false;
        userResponse = "";
    }
    
    public int getAnswer() {return(answer);}
    public String getPrevResponse() {return(userResponse);}
    public String getProblem() {return(problem);}
    public boolean isCorrect() {return(userCorrect);}
    public boolean isCorrect(String s)
    {
        userResponse = s;
        skipped = false;
        return(userCorrect = userResponse.equals(Integer.toString(answer)));
    }
    public boolean isSkipped() {return(skipped);}
    
}