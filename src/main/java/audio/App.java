package audio;

import javax.swing.*;


/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {

        //File clip = new File(path);
        //Play(clip);

        JFrame f = new JFrame();
        Player p = new Player();
        f.setSize(690,500);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
        f.add(p);

    }
}
