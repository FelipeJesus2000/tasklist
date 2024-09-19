package com.felipe.structure;

import java.awt.Color;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.felipe.App;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Footer extends JPanel{
    public Footer(){
        String projectURL = System.getProperty("user.dir");
        String pathImg = projectURL + "\\src\\assets\\";

        // style 
        setBounds(0, 542, App.frameWidth, 59);
        setOpaque(true);
        setBackground(Color.decode("#6D0FF2"));
        setForeground(Color.WHITE);
        setBorder(new EmptyBorder(10,10,10,10));
        setName("footer");
        int marginX = 17;

        // btn tasks
        ImageIcon iconTasks = new ImageIcon(pathImg + "task.png");
        JButton btnTasks = new JButton(iconTasks);
        btnTasks.setBackground(null);
        btnTasks.setBorder(new EmptyBorder(0,marginX,0,marginX));
        
        btnTasks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.setHomePage();
            }
        });

        // btn config
        ImageIcon iconConfig = new ImageIcon(pathImg + "config.png");
        JButton btnConfig = new JButton(iconConfig);
        btnConfig.setBackground(null);
        btnConfig.setBorder(new EmptyBorder(0,marginX,0,marginX));
        //btnConfig.setBorder(null);
        btnConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.setConfigPage(); 
            }
        });

        // btn stats
        ImageIcon iconStats = new ImageIcon(pathImg + "stats.png");
        JButton btnStats = new JButton(iconStats);
        btnStats.setBackground(null);
        btnStats.setBorder(new EmptyBorder(0,marginX,0,marginX));
        //btnStats.setBorder(null);
        btnStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.setStatsPage();
            }
        });

        // btn timer 
        // ImageIcon iconTimer = new ImageIcon(pathImg + "timer.png");
        // JButton btnTimer = new JButton(iconTimer);
        // btnTimer.setBackground(null);
        // btnTimer.setBorder(new EmptyBorder(0,marginX,0,marginX));
        // btnTimer.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         App.setTimerPage();
        //     }
        // });
      
        // add buttons
        add(btnTasks);
        add(btnConfig);
        add(btnStats);
        // add(btnTimer);

        
    }
}
