package base;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import additionalListSoundClass.additionalClass;

public class SoundBase {

    private Connection con;
    private Statement stat;
    private DefaultTableModel model;

    public SoundBase(){

        try {

            Class.forName("org.sqlite.JDBC");

        } catch (ClassNotFoundException e) {
            System.out.println("Can not load controler: "+e.getMessage());
        }

        try {

            con = DriverManager.getConnection("jdbc:sqlite:soundBase.db");
            stat = con.createStatement();

        } catch (SQLException e2) {
            System.out.println("Can not connect to base: "+e2.getMessage());
        }

        CreateTable();

    }

    public boolean CreateTable(){

        String soundTable = "create table if not exists soundBase(idsound Integer primary key autoincrement, soundName varchar(40), soundPath varchar(60))";

        try {
            stat.execute(soundTable);
        } catch (SQLException e) {
            System.out.println("Can not create table: "+e.getMessage());
            return false;
        }
        return true;
    }

//    public int getTableSize(){
//
//        int border = 0;
//
//        try {
//            ResultSet rs = stat.executeQuery("select count(1) from soundBase");
//
//            while (rs.next()){
//                border+=1;
//            }
//
//        }catch (SQLException e){
//            System.out.println(e.getMessage());
//     }
//
//     return border;
//    }
    //------------------------------------------------------------------

    public boolean InsertSound(){

        JFileChooser jf = new JFileChooser();
        File f = null;

        if(jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            f = jf.getSelectedFile();

            String name, path;

            name = f.getName();
            path = f.getAbsolutePath();

            System.out.println(name + " " + path);

            //System.out.println(getTableSize());

            try {

                PreparedStatement ps = con.prepareStatement("insert into soundBase values(NULL, ?, ?)");
                ps.setString(1, name);
                ps.setString(2, path);
                ps.execute();

            } catch (SQLException e) {
                System.out.println("Can not insert sound: " + e.getMessage());
                return false;

            }

        }
            return true;
    }
    //-----------------------------------------------------------------------------


    public LinkedList<additionalClass> SelectSounds(){

        LinkedList<additionalClass> l = new LinkedList<>();
        int id;
        String name, path;

        try {

            ResultSet rs = stat.executeQuery("select * from soundBase");

            while(rs.next()){

                id = rs.getInt("idsound");
                name = rs.getString("soundName");
                path = rs.getString("soundPath");
                l.add(new additionalClass(id,name,path));
            }

        } catch (SQLException e) {
            System.out.println("Can not display users: "+e.getMessage());
        }

        return l;

    }

    public String perNextSong(int id){

        String perPath = "";


            try {
                ResultSet rs = stat.executeQuery("select soundPath from soundBase where idsound = " + id);

                perPath = rs.getString("soundPath");

            } catch (SQLException e) {
                System.out.println(e.getMessage() + "wyjatek metody perviousSong");
            }
        return perPath;
    }
}



























