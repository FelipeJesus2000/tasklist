package com.felipe.structure;

import javax.swing.JPanel;

import com.felipe.App;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class Body extends JPanel {
    public Body(){
        setPreferredSize(new Dimension(App.frameWidth, 50));
        setBackground(Color.decode("#F2F2F2"));
        setLayout(new BorderLayout());
    }
}