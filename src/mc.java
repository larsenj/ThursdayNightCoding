import javax.swing.JFrame;

public class mc {

    public static void main (String[] args){
        JFrame frame = new JFrame ("Mini Constructor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new mcPanel());
        frame.pack();
        frame.setVisible(true);



    }

}
