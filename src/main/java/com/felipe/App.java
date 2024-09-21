package com.felipe;

import javax.swing.*;
// import javax.swing.table.DefaultTableModel;

import com.felipe.SQLite.CreateDatabase;
import com.felipe.pages.ConfigPage;
import com.felipe.pages.FormAdd;
import com.felipe.pages.HomePage;
import com.felipe.pages.StatsPage;
import com.felipe.pages.TimerPage;
import com.felipe.structure.Footer;
import com.felipe.structure.Header;
import com.felipe.SQLite.Crud;
import com.felipe.components.DefaultComboBox;

import java.awt.BorderLayout;

public class App {
    public static JFrame frame;
    public static int frameWidth = 360;
    public static int frameHeight = 640;
    private static Crud crud = new Crud();
    private static String projectURL = System.getProperty("user.dir");
    public static String pathAssets = projectURL + "\\src\\assets\\";

    // Colors
    // #6D0FF2
    // #9C5EF2
    // #C4A2F2
    // #5005F2
    // #F2F2F2

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        // Create JFrame
        frame = new JFrame("Lista de Tarefas");
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set default design of comboBox
        DefaultComboBox.setDefault();

        // Create database
        new CreateDatabase();

        // reset daily task
        Crud crud = new Crud();
        crud.resetDailyTask();
        

        // show home page
        setHomePage();
        frame.setVisible(true);
    }

    public static void clearPanel(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    // show homepage
    public static void setHomePage() {
        resetFrame();
        frame.add(new Header("Atividades de Hoje"), BorderLayout.NORTH);
        frame.add(new HomePage(), BorderLayout.CENTER);
        frame.add(new Footer(), BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // show config page
    public static void setConfigPage() {
        resetFrame();
        frame.add(new Header("Configurações"), BorderLayout.NORTH);
        frame.add(new ConfigPage(), BorderLayout.CENTER);
        frame.add(new Footer(), BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // show stats page
    public static void setStatsPage() {
        resetFrame();
        frame.add(new Header("Estatisticas"), BorderLayout.NORTH);
        frame.add(new StatsPage(), BorderLayout.CENTER);
        frame.add(new Footer(), BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // show timer page
    public static void setTimerPage(int id) {
        resetFrame();
        // ConnectionSQL connectionSQL = new ConnectionSQL(); changed
        // Task task = connectionSQL.selecTask(id); changed
        Task task = crud.selectTask(id);
        frame.add(new Header("Countdown"), BorderLayout.NORTH);
        frame.add(new TimerPage(task), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // show form add task
    public static void setFormAdd() {
        resetFrame();
        frame.add(new Header("Cadastrar Tarefa"), BorderLayout.NORTH);
        frame.add(new FormAdd(), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // remove all from JFrame
    public static void resetFrame() {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

    }

    // put Header in page
    public static void setHeader(String pageName) {
        frame.add(new Header(pageName), BorderLayout.NORTH);
    }

    // put footer in page
    public static void setFooter() {
        frame.add(new Footer(), BorderLayout.SOUTH);
    }

}
