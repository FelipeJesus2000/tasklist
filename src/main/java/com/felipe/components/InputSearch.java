package com.felipe.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class InputSearch extends JPanel {
    private DefaultInput input = new DefaultInput();
    private CustonButton button = new CustonButton();

    public InputSearch(int width, int height) {
        // panel
        Border coloredBorder = new LineBorder(Color.decode("#6D0FF2"), 1, true);
        setBorder(coloredBorder);
        setSize(new Dimension(width, height));
        setLayout(new FlowLayout());

        // input
        input.addPlaceholder("Buscar...");
        input.setBackground(null);
        input.setBorder(null);
        int inputWidth = (int) Math.round(this.getWidth() * .8);
        input.setPreferredSize(new Dimension(inputWidth, this.getHeight()));

        // on focus event input
        input.addFocusListener((FocusListener) new FocusListener() {
            public void focusGained(FocusEvent e) {
                input.setOpaque(false);
                input.setBackground(Color.decode("#F2F2F2"));
            }

            public void focusLost(FocusEvent e) {
                input.setOpaque(false);
                input.setBackground(Color.decode("#F2F2F2"));
            }
        });
        add(input);

        // button
        button.setImage("search-icon.png");
        button.setBackground(null);
        button.setContentAreaFilled(false);
        int buttonWidth = (int) Math.round(this.getWidth() * .2);
        button.setPreferredSize(new Dimension(buttonWidth, this.getHeight()));
        button.setFocusPainted(false);
        // on clicked event
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(Color.BLACK);
                // button.setOpaque(true);
                button.setContentAreaFilled(true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setContentAreaFilled(false);
                button.setOpaque(false);
                button.setBackground(null);
            }
        });
        add(button);

    }

    public String getText() {
        return input.getText();
    }

    public CustonButton getButton() {
        return this.button;
    }

    public DefaultInput getTextField() {
        return this.input;
    }
}
