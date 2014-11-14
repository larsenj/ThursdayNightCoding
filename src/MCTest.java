import java.util.Scanner;

public class MCTest
{
    /*
    char level;
    String mathProb ;
    int mathAnswer;
    gradeLevel grade;
    */


    public static void main(String[] args)
    {

        char level;
        String mathProb ;
        int mathAnswer;
        gradeLevel grade;

        //dummy variable to stop scrolling so user can see test results
        String junk;
        Scanner scan = new Scanner(System.in);

        System.out.println("What level?");
        level = Character.toLowerCase(scan.next().charAt(0));


        //based on user's "level", will instantiate an appropriate object
        switch (level){
            case 'k':
                grade = new kindergarten();
                break;
            case '1':
                grade = new firstGrade();
                break;
            default:
                grade = new kindergarten();
                //System.out.println("That level not yet implemented");
        }

        for (int i = 0; i < 5; i++)
        {
            mathProb = grade.getProblem();
            mathAnswer = grade.getAnswer();

            System.out.print("Problem: ");
            System.out.println (mathProb);
            System.out.print("Answer should be:  ");
            System.out.println(mathAnswer);
        }
        /*
        System.out.println();
        System.out.println("Hit 'y' to continue.");
        junk = scan.nextLine();
        System.out.println();
        */
    }
}