import java.util.*;

public class ThirdGrade implements gradeLevel
{
    private int n1, n2, n3, answer;
    private Random randNum = new Random();

    public int problem()
    {
        return randNum.nextInt(10);
    }

    public void multiplication()
    {
        n1 = randNum.nextInt(10) + 1;
        n2 = randNum.nextInt(10) + 1;
        n3 = n1 * n2;
    }

    public String wordProblems()
    {
        String[] names = {"Josh", "Brian", "James", "Rachael", "Wilma", "Aaron", "Sam", "Dean", "Ally", "Tina"};
        String[] divisibleThings = {"slices of pizza", "pieces of pie", "pieces of candy", "pencils", "new DVDs", "books", "funny hats", "party favors", "balloons"};
        multiplication();
        int nameIndex = randNum.nextInt(10);
        int thingIndex = randNum.nextInt(9);
        String words = names[nameIndex] + " has " + n3 + " " + divisibleThings[thingIndex];
        words = words + " to split up between " + n2 + " friends. How many " + divisibleThings[thingIndex] + " does each friend get?";
        answer = n1;
        return words;
    }

    public String getProblem()
    {
        int operation = problem();

        if (operation == 0)
        {
            multiplication();
            answer = n1;
            return ("? x " + n2 + " = " + n3);
        }

        if (operation == 1)
        {
            multiplication();
            answer = n2;
            return (n1 + " x " + "? = " + n3);
        }

        if (operation == 2)
        {
            multiplication();
            answer = n3;
            return (n1 + " x " + n2 + " = ? ");
        }

        if (operation == 3)
        {
            multiplication();
            answer = n1;
            String words = "If " + n1 + " x " + n2 + " = " + n3;
            words = words + ", then " + n3 + " / " + n2 + " = ?";
            return words;
        }

        if (operation == 4)
        {
            multiplication();
            answer = n2;
            String words = "If " + n1 + " x " + n2 + " = " + n3;
            words = words + ", then " + n3 + " / " + n1 + " = ?";
            return words;
        }

        if (operation == 5)
        {
            multiplication();
            answer = n1;
            return (n3 + " / " + n2 + " = ? ");
        }

        if (operation == 6)
        {
            multiplication();
            answer = n2;
            return (n3 + " / " + n1 + " = ? ");
        }

        if (operation == 7)
        {
            return (wordProblems());
        }

        if (operation == 8)
        {
            multiplication();
            String words = "If you have " + n1 + " groups of objects, and each group contains " + n2;
            words = words + " objects each, how many total objects do you have?";
            answer = n3;
            return words;
        }

        assert (operation == 9);
        multiplication();
        int n4 = randNum.nextInt(11);
        String words = n1 + " x " + n2 + " + " + n4 + " = ? ";
        answer = n3 + n4;
        return words;
    }

    public int getAnswer()
    {
        return answer;
    }
}
