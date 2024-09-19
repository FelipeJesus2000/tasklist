package com.felipe.pages;

import javax.swing.JPanel;


import java.awt.BorderLayout;
import java.awt.Color;

public class Page extends JPanel{
    
    public Page(){
        setLayout(new BorderLayout()); 
        setBackground(Color.decode("#F2F2F2"));
        // setSize(App.frameWidth, App.frameHeight);
    }
    
}