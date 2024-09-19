package com.felipe.structure;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.felipe.App;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

public class Header extends JPanel {
    public Header(String pageName) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.decode("#5005F2"));
        this.setPreferredSize(new Dimension(App.frameWidth, 50));
        this.setName("header");

        JLabel title = new JLabel(pageName, JLabel.CENTER);
        title.setFont(new Font("arial", Font.BOLD, 18));

        title.setOpaque(true); // Torna a etiqueta opaca
        title.setBackground(null);
        title.setForeground(Color.white);

        this.add(title, BorderLayout.CENTER);
    }
}
