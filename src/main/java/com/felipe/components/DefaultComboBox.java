package com.felipe.components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

// Colors 
// #9C5EF2
// #9C5EF2
// #C4A2F2
// #5005F2
// #F2F2F2

public class DefaultComboBox extends JComboBox<String> {
    public DefaultComboBox(String[] arrString) {
        super(arrString);
        Border coloredBorder = new LineBorder(Color.decode("#6D0FF2"), 2, true);
        setBorder(coloredBorder);
        // setForeground(Color.BLACK);
        // setBackground(Color.decode("#C4A2F2"));

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setOpaque(true);
                setForeground(Color.BLACK);
                setBackground(Color.decode("#C4A2F2"));
            }

            @Override
            public void focusLost(FocusEvent e) {
                setOpaque(false);
                setBackground(Color.decode("#F2F2F2"));
            }
        });

        setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                CustonButton arrowButton = new CustonButton();
                arrowButton.setBackground(Color.decode("#C4A2F2")); 
                arrowButton.setBorder(BorderFactory.createLineBorder(Color.decode("#6D0FF2"), 1)); 
                arrowButton.setImage("down-arrow.png");
                return arrowButton;
            }
        });

    }

    public static void setDefault() {
        SwingUtilities.invokeLater(() -> {
            UIManager.put("ComboBox.selectionBackground", Color.decode("#5005F2"));
            UIManager.put("ComboBox.selectionForeground", Color.decode("#F2F2F2"));
        });
    }
}
