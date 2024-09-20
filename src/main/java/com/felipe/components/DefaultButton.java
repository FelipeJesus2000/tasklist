package com.felipe.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Colors 
// #6D0FF2
// #9C5EF2
// #C4A2F2
// #5005F2
// #F2F2F2
public class DefaultButton extends CustonButton {
    public DefaultButton(String text) {
        super(text);
        backgroundColor("#5005F2");
        // setContentAreaFilled(false); // Remove o fundo padrão
        setFocusPainted(false);
        setForeground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(Color.BLACK);
                // button.setOpaque(true);
                setContentAreaFilled(true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // setContentAreaFilled(false);
                // setOpaque(false);
                setBackground(Color.decode("#5005F2"));
            }
        });
    }
}
