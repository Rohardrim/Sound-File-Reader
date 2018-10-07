package audio;

import additionalListSoundClass.additionalClass;
import base.SoundBase;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;


public class Player extends JPanel implements ActionListener{

    private boolean isplay = true;
    private File f;
    private Clip c;
    private static String path, songName;
    private JButton frameStart, frameStop, frameLoop, framesetSoundTable, frameRefreshTable, frameGetPath,framePervious, frameNext;
    private JMenuBar jmb;
    private JMenu jmOpen, jmStart, jmStop, jmLoop;
    private JMenuItem open, start, stop, loop;
    private JLabel soundPath;
    private JTabbedPane tabbedPane;
    private JPanel jp1;
    private JTable table;
    private DefaultTableModel model;
    private boolean reset = false;
    private int setRow;
    SoundBase sb;

    public Player(){

        setFocusable(true);
        setLayout(null);

        frameStart = new JButton("Play");
        frameStart.setBounds(80,140,60,20);
        frameStart.addActionListener(this);
        add(frameStart);
        //-----------------------------------------------------------

        frameStop = new JButton("Stop");
        frameStop.setBounds(80,200,60,20);
        frameStop.addActionListener(this);
        add(frameStop);
        //-----------------------------------------------------------

        frameLoop = new JButton("Loop");
        frameLoop.setBounds(75,170,70,20);
        frameLoop.addActionListener(this);
        add(frameLoop);
        //-----------------------------------------------------------

        framesetSoundTable = new JButton("Add to Playlist");
        framesetSoundTable.setBounds(50,350,130,20);
        framesetSoundTable.addActionListener(this);
        add(framesetSoundTable);
        //-----------------------------------------------------------

        frameRefreshTable = new JButton("Refresh Table");
        frameRefreshTable.setBounds(50,380,130,20);
        frameRefreshTable.addActionListener(this);
        add(frameRefreshTable);
        //-----------------------------------------------------------

        frameGetPath = new JButton("Select");
        frameGetPath.setBounds(60,290,100,20);
        frameGetPath.addActionListener(this);
        add(frameGetPath);
        //-----------------------------------------------------------

        framePervious = new JButton("Pervious");
        framePervious.setBounds(10,250,90,20);
        framePervious.addActionListener(this);
        add(framePervious);
        //-----------------------------------------------------------

        frameNext = new JButton("Next");
        frameNext.setBounds(110,250,90,20);
        frameNext.addActionListener(this);
        add(frameNext);

        //-----------------------------------------------------------
        soundPath = new JLabel("");
        soundPath.setBounds(20, 100,300,20);
        add(soundPath);

        //-----------------------------------------------------------
        jmb = new JMenuBar();
        jmb.setBounds(0,0,940,30);
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

        //-----------------------------------------------------------
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(210,30,700,420);

        jp1 = new JPanel();
        jp1.setLayout(new GridLayout());

        tabbedPane.addTab("PlayList",jp1);

        add(tabbedPane);

        String[] columns = {"IdSond", "SongName", "SongPath"};

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        JScrollPane sc = new JScrollPane(table);
        jp1.add(sc);

        sb = new SoundBase();
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

        if(source == framesetSoundTable)
            insertTable();

        if (source == frameRefreshTable) {
            refreshTable();
            fillTable();
        }

        if (source == frameGetPath)
            playListGetPath();

        if (source == framePervious)
            pervoiusSong(-1);

        if (source == frameNext)
            pervoiusSong(1);

        if(source == open) {
            path = getPath();
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

            /*File*/ f = new File(song);
            isplay = true;
            c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(f));
            c.start();
            Thread.sleep(c.getMicrosecondLength()/900);

        } catch (Exception ex) {

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

                                /*File*/ f = new File(song);
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

            /*File*/ f = jf.getSelectedFile();
            filePath = f.getAbsolutePath();
            songName = f.getName();
            soundPath.setText(songName);
        }

        return filePath;
    }


    public void insertTable(){
        sb.InsertSound();
    }

    public void fillTable(){
        LinkedList<additionalClass> l = sb.SelectSounds();

        for(additionalClass adc : l){

            model.addRow(new Object[] {adc.getId(),adc.getName(),adc.getPath()});

        }

        reset = true;
    }

    public void refreshTable()
    {
        if(reset)
        {
            for(int i=0; i<table.getRowCount(); i++){
                model.removeRow(i);
                i--;
            }

            System.out.println();
        }
    }

    public void  playListGetPath(){

        setRow = table.getSelectedRow();

        Object a2 = table.getValueAt(setRow,2);

        String pat = (String) a2;

        path = pat;

        Object name = table.getValueAt(setRow,1);
        soundPath.setText((String)name);

    }

    public void pervoiusSong(int id){

        if (setRow+id>=0 && setRow+id<table.getRowCount()) {

            Object iden = table.getValueAt(setRow + id, 0);

            path = sb.perNextSong((int) iden);

            Object song = table.getValueAt(setRow + id, 1);

            soundPath.setText((String) song);

            shortPlay(path);

            setRow+=id;

        }

    }





}
