package com.felipe.components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;

// Colors 
// #6D0FF2
// #9C5EF2
// #C4A2F2
// #5005F2
// #F2F2F2

public class DefaultInput extends JTextField {
    private String placeholder = "";


    public DefaultInput(){
        setOpaque(false);

        Border coloredBorder = new LineBorder(Color.decode("#6D0FF2"), 2, true);
        setBorder(coloredBorder);

        

        addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                if(getText().equals(placeholder)){
                    setOpaque(true);
                    setText("");
                    setForeground(Color.BLACK);
                    setBackground(Color.decode("#C4A2F2")); 
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                setOpaque(false);
                if(getText().trim().equals(""))
                    
                    addPlaceholder(placeholder);
                    setBackground(Color.decode("#F2F2F2")); 
            }
        });
    }

    public void addPlaceholder(String placeholder){
        this.placeholder = placeholder;
        setText(placeholder);
        setForeground(Color.GRAY);
    }

    public static boolean contentIsPlaceholder(DefaultInput input){
        if (input.getText().equals(input.placeholder)) 
            return true;

        return false;
        
    }
}
