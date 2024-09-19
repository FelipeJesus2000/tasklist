package com.felipe.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import com.felipe.App;
import com.felipe.Dates;
import com.felipe.Task;
import com.felipe.SQLite.Timer;
import com.felipe.components.DefaultButton;
import com.felipe.components.DefaultComboBox;
import com.felipe.components.DefaultInput;

public class FormAdd extends Page {
    private static DefaultInput txtNmTask;
    private static JFormattedTextField txtTime;
    private static DefaultComboBox txtPriority;
    private static DefaultComboBox txtWeekday;

    public FormAdd() {
        App.frame.setTitle("Adicionar atividade");
        int txtHeight = 30, txtWidth = 200;

        // label task name
        JLabel lblNmTask = new JLabel("Atividade");
        // input task name
        txtNmTask = new DefaultInput();
        txtNmTask.setPreferredSize(new Dimension(txtWidth, txtHeight));
        txtNmTask.addPlaceholder("nome da atividade");
        // panel label and input task name
        JPanel pnlNameTask = new JPanel();
        // add in panel: label and input task name
        pnlNameTask.add(lblNmTask);
        pnlNameTask.add(txtNmTask);

        // Label for time
        JLabel lblTime = new JLabel("Tempo (HH:MM:SS)");
        // create mask format: 00:00:00
        MaskFormatter timeFormatter = null;
        try {
            timeFormatter = new MaskFormatter("##:##:##");
            timeFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            System.err.println("Error: " + e.getMessage());
        }
        // put mask in input time
        txtTime = new JFormattedTextField(timeFormatter);
        txtTime.setColumns(8);
        txtTime.setPreferredSize(new Dimension(txtWidth, txtHeight));
        txtTime.setOpaque(false);

        Border coloredBorder = new LineBorder(Color.decode("#6D0FF2"), 2, true);
        txtTime.setBorder(coloredBorder);

        txtTime.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txtTime.setOpaque(true);
                txtTime.setForeground(Color.BLACK);
                txtTime.setBackground(Color.decode("#C4A2F2"));
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtTime.setOpaque(false);
                txtTime.setBackground(Color.decode("#F2F2F2"));
            }
        });

        // panel for time
        JPanel pnlTime = new JPanel();
        // add in panel label and input of time
        pnlTime.add(lblTime);
        pnlTime.add(txtTime);

        // priorities
        String[] priorities = { "baixa", "média", "alta" };
        // label priorities
        JLabel lblPriority = new JLabel("Prioridade");
        // instance comboBox and add array with priorities inside
        txtPriority = new DefaultComboBox(priorities);

        // set size comboBox
        txtPriority.setPreferredSize(new Dimension(txtWidth, txtHeight));

        // create panel for priority
        JPanel pnlPriotiry = new JPanel();
        // add label and comboBox inside panel
        pnlPriotiry.add(lblPriority);
        pnlPriotiry.add(txtPriority);

        // day of week
        String[] daysOfWeek = {
                "Segunda-feira", "Terça-feira",
                "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"
        };

        // create label for days of week;
        JLabel lblWeekday = new JLabel("Dia da semana");
        // instance comboBox and add array with days of week
        txtWeekday = new DefaultComboBox(daysOfWeek);
        // set size comboBox days of week
        txtWeekday.setPreferredSize(new Dimension(txtWidth, txtHeight));
        Dates dates = new Dates();
        // set selected index default current day
        txtWeekday.setSelectedIndex(dates.getWeekdayIndex());

        // create panel days of week
        JPanel pnlWeekday = new JPanel();
        // add label and comboBox in panel days of week
        pnlWeekday.add(lblWeekday);
        pnlWeekday.add(txtWeekday);

        // panel with all labels and fields
        JPanel fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));

        // add panels with fields in panel fields
        fields.add(pnlNameTask);
        fields.add(pnlTime);
        fields.add(pnlPriotiry);
        fields.add(pnlWeekday);
        // add panel fields in page
        add(fields, BorderLayout.CENTER);

        // create panel buttons
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout());

        // create button add task
        JButton btnAdd = new DefaultButton("Adicionar");
        // create event when click btn add
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // validate values
                if (!validateForm())
                    return;
                // get values of fields
                String name, time, priority, weekday;
                name = txtNmTask.getText();
                time = txtTime.getText();
                priority = (String) txtPriority.getSelectedItem();
                weekday = (String) txtWeekday.getSelectedItem();
                // transform value of weekday comboBox to format in database
                String acWeekday = dates.getAcWeekday(weekday);
                Task task = new Task(0, name, time, priority, acWeekday, 0);
                // insert task in database
                task.insert();
                txtNmTask.setText("");
                txtTime.setText("");
            }
        });

        // create button btnBack
        JButton btnBack = new DefaultButton("Voltar");
        // add event back to config page when click in btnBack
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.setConfigPage();
            }
        });

        // add buttons in panel buttons
        buttons.add(btnAdd, BorderLayout.EAST);
        buttons.add(btnBack, BorderLayout.WEST);

        // set size panelButtons
        buttons.setPreferredSize(new Dimension(getWidth(), 50));
        // remove panel buttons border
        buttons.setBorder(new EmptyBorder(5, 5, 5, 5));

        // add panel buttons in page
        add(buttons, BorderLayout.SOUTH);
    }

    private boolean validateForm() {
        // string that stacks errors
        String error = "";
        String name = txtNmTask.getText();

        // verify if name task is empty
        if (name.trim().isEmpty() || DefaultInput.contentIsPlaceholder(txtNmTask)) {
            error += "O campo nome está vazio! \n";
        }

        String time = txtTime.getText();
        // verify if time task is empty
        if (time.trim().isEmpty() || DefaultInput.contentIsPlaceholder(txtNmTask))
            error += "O campo tempo está vazio! \n";
        // verify if time is number
        if (!Timer.isTimer(time))
            error += "Digite uma hora válida no campo tempo\n";
        if(time.equals("00:00:00"))
            error += "Digite uma hora válida";

        if (!error.isEmpty()) {
            // show message if exists error
            JOptionPane.showMessageDialog(null, error, "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // if does not exists errors, return true
        return true;
    }
}
