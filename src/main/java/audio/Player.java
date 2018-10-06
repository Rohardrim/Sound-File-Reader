package audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class Player extends JPanel implements ActionListener{

    private String[] f2; //to posluzy nam jako plylista!!!!
    private boolean isplay = true;
    private Clip c;
    private static String path;
    private String getPath = "";
    private JButton frameStart, frameStop, frameLoop;
    private JMenuBar jmb;
    private JMenu jmOpen, jmStart, jmStop, jmLoop;
    private JMenuItem open, start, stop, loop;
    private JLabel soundPath;
    private JTabbedPane tabbedPane;
    private JPanel jp1,jp2;
    private JTable table;

    public Player(){

        //f = new File(path);

        setFocusable(true);
        setLayout(null);

        frameStart = new JButton("Play");
        frameStart.setBounds(50,170,60,20);
        frameStart.addActionListener(this);
        add(frameStart);
        //-----------------------------------------------------------

        frameStop = new JButton("Stop");
        frameStop.setBounds(80,200,60,20);
        frameStop.addActionListener(this);
        add(frameStop);
        //-----------------------------------------------------------

        frameLoop = new JButton("Loop");
        frameLoop.setBounds(120,170,80,20);
        frameLoop.addActionListener(this);
        add(frameLoop);
        //-----------------------------------------------------------

        soundPath = new JLabel("");
        soundPath.setBounds(20, 100,300,20);
        add(soundPath);

        //-----------------------------------------------------------
        jmb = new JMenuBar();
        jmb.setBounds(0,0,495,30);
        add(jmb);
        //-----------------------------------------------------------

        jmOpen = new JMenu("Open");
        jmb.add(jmOpen);

        open = new JMenuItem("Open");
        open.addActionListener(this);
        jmOpen.add(open);
        //-----------------------------------------------------------

        jmStart = new JMenu("Play");
        jmb.add(jmStart);

        start = new JMenuItem("Play");
        start.addActionListener(this);
        jmStart.add(start);
        //-----------------------------------------------------------

        jmStop = new JMenu("Stop");
        jmb.add(jmStop);

        stop = new JMenuItem("Stop");
        stop.addActionListener(this);
        jmStop.add(stop);
        //-----------------------------------------------------------

        jmLoop = new JMenu("Loop");
        jmb.add(jmLoop);

        loop = new JMenuItem("Loop");
        loop.addActionListener(this);
        jmLoop.add(loop);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10,30,450,420);

        jp1 = new JPanel();
        jp2 = new JPanel();
        tabbedPane.addTab("First",jp1);
        tabbedPane.addTab("Second",jp2);

        jp1.setLayout(null);
        jp1.add(frameStart);
        jp1.add(frameStop);
        jp1.add(frameLoop);
        jp1.add(soundPath);

        jp2.setLayout(new GridLayout());

        add(tabbedPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if(source == frameStart)
            shortPlay(path);

        if(source == frameStop)
            isplay = false;

        if(source == frameLoop)
            Play(path);

        if(source == open) {
            path = getPath();
            soundPath.setText(path);
        }

        if(source == start)
            shortPlay(path);


        if(source == stop)
            isplay = false;

        if(source == loop)
            Play(path);
    }

    public void shortPlay(final String song){

        try {

            File f = new File(song);
            isplay = true;
            c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(f));
            c.start();
            getPath =  f.getAbsolutePath();
            soundPath.setText(getPath);
            Thread.sleep(c.getMicrosecondLength()/900);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void Play(final String song){

        try {

//           getPath =  f.getAbsolutePath();
//            soundPath.setText(getPath);
            isplay = true;

                Thread t = new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        do {
                            try {

                                File f = new File(song);
                                c = AudioSystem.getClip();
                                c.open(AudioSystem.getAudioInputStream(f));
                                c.start();
                                Thread.sleep(c.getMicrosecondLength()/900);


                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }while (isplay);

                    }
                  }
                );
                t.start();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String getPath(){

        String filePath = "";

        JFileChooser jf = new JFileChooser();

        if(jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){

            File f = jf.getSelectedFile();
            filePath = f.getAbsolutePath();
        }

        return filePath;
    }

    public void setTable(){

        String[] columns = {"PlayList"};

        Object[][] data = {{"nama"}};

        DefaultTableModel model = new DefaultTableModel(data, columns);

        table = new JTable(model);
    }






}
