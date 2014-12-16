import java.util.*;

// Constructs problems of the second grade level, primarily representing more advanced
// addition and subtraction (up to 100).  

public class SecondGrade implements gradeLevel
{
    private int n1, n2, n3, answer;
    private Random randNum = new Random();

    public int problem()
    {
        return randNum.nextInt(10);
    }

    public void addition()
    {
        n1 = randNum.nextInt(50) + 1;
        n2 = randNum.nextInt(50) + 1;
        n3 = n1 + n2;
    }
 
    public void additionWithoutOne()
    {
        n1 = randNum.nextInt(49) + 1;
        n2 = randNum.nextInt(49) + 1;
        n3 = n1 + n2;
    }

    public void additionWithinTwenty()
    {
        n1 = randNum.nextInt(10) + 1;
        n2 = randNum.nextInt(10) + 1;
        n3 = n1 + n2;
    }

    public String getProblem()
    {
        int operation = problem();
 
        if (operation == 0)
        {
            addition();
            answer = n3;
            return (n1 + " + " + n2 + " = ? ");
        }

        if (operation == 1)
        {
            int odd = -1;
            int even = -1;
            additionWithinTwenty();
            if (n1 %2 == 0)
                even = n1;
            else
                odd = n1;
            if (even != -1)
                odd = 19 - n1;
            else
                even = 20 - n1;
            assert (even != -1 && odd != -1);
            answer = even;
            int[] array = {even, odd};
            int order = randNum.nextInt(1);
            int otherNum;
            if (array[order] == even)
                otherNum = odd;
            else
                otherNum = even;
            return ("Of the two numbers " + array[order] + " and " + otherNum + ", which one is even?");
        }

        if (operation == 2)
        {
            int odd = -1;
            int even = -1;
            additionWithinTwenty();
            if (n1 %2 == 0)
                even = n1;
            else
                odd = n1;
            if (even != -1)
                odd = 19 - n1;
            else
                even = 20 - n1;
            assert (even != -1 && odd != -1);
            answer = odd;
            int[] array = {even, odd};
            int order = randNum.nextInt(1);
            int otherNum;
            if (array[order] == even)
                otherNum = odd;
            else
                otherNum = even;
            return ("Of the two numbers " + array[order] + " and " + otherNum + ", which one is odd?");
        }

        if (operation == 3)
        {
            String[] names = {"Violet", "Diane", "Svetlana", "Ruth", "Raymond", "Aaron", "Elijah", "Fletcher"};
            String[] things = {"Pokemon cards", "hats", "cookies", "posters", "potato chips"};
            int nameIndex = randNum.nextInt(8);
            int thingIndex = randNum.nextInt(5);
            String pronoun = "her";
            String pronoun2 = "she";
            if (nameIndex > 3)
            {
                pronoun = "him";
                pronoun2 = "he";
            }
            additionWithoutOne();
            String words = names[nameIndex] + " has " + n1 + " " + things[thingIndex] + ". ";
            words = words + "If you give " + pronoun + " " + n2 + " more, how many total does ";
            words = words + pronoun2 + " have? ";
            answer = n3;
            return words;
        }

        if (operation == 4)
        {
            additionWithoutOne();
            String word = "objects";
            if (n3 == 1)
                word = "object";
            String words = "If you have " + n3 + " " + word + " and give " + n2;
            words = words + " away, how many total objects do you have left?";
            answer = n1;
            return words;
        }
        if (operation == 5)
        {
            additionWithinTwenty();
            answer = n1;
            return ("? + " + n2 + " = " + n3);
        }

        if (operation == 6)
        {
            additionWithinTwenty();
            answer = n2;
            return (n1 + " + ? = " + n3);
        }

        if (operation == 7)
        {
            additionWithinTwenty();
            answer = n1;
            return (n3 + " - " + n2 + " = ?");
        }

        if (operation == 8)
        {
            int num = randNum.nextInt(19) + 1;
            int num2 = 20 - num;
            answer = num2;
            return ("20 - " + num + " = ? ");
        }

        assert (operation == 9);
        
        int number = randNum.nextInt(100) + 1;
        if (number % 2 != 0)
            number = number - 1;
        int number2 = number / 2;
        answer = number2;
        return ("The number " + number + " is equal to what + what?");
    }

    public int getAnswer()
    {
        return answer;
    }
}
            
