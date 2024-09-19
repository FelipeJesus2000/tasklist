package com.felipe.components;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class CustonButton extends JButton {
    private static String projectURL = System.getProperty("user.dir");
    private static String pathAssets = projectURL + "\\src\\assets\\";

    public CustonButton(String text) {
        setText(text);
    }

    // public CustonButton(String text, String nameImg) {
    //     setText(text);
    //     setImage(nameImg);
    // }

    public CustonButton() {
        setText("");
        setBackground(null);
        setBorder(new EmptyBorder(0, 10, 0, 10));
    }

    public void setImage(String nameImg) {
        ImageIcon iconTasks = new ImageIcon(pathAssets + nameImg);
        setIcon(iconTasks);
    }

    public void backgroundColor(String color) {
        setBackground(Color.decode(color));
    }

    public void textColor(String color) {
        setForeground(Color.decode(color));
    }

  
}
