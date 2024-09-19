package com.felipe.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


import com.felipe.App;
import com.felipe.Audio;
import com.felipe.Dates;
import com.felipe.Task;
import com.felipe.SQLite.Crud;
import com.felipe.components.CustonButton;
import com.felipe.components.DefaultComboBox;

public class ConfigPage extends Page {
    private static DefaultComboBox dayComboBox;
    private static JTable table;
    private static JPanel panelCarousel;
    private static DefaultTableModel model;
    private static JPanel panelButtons;
    private static Crud crud = new Crud();
    private static  Dates dates = new Dates();

    public ConfigPage() {
        // set title 
        App.frame.setTitle("Configurações");

        String[] daysOfWeek = {
                "Segunda-feira", "Terça-feira",
                "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"
        };

        // config panel and comboBox
        panelCarousel = new JPanel();
        dayComboBox = new DefaultComboBox(daysOfWeek);
        panelCarousel.add(dayComboBox);
        dayComboBox.setSelectedIndex(dates.getWeekdayIndex());

        // when change value of JComboBox change table values to selected day of week
        dayComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) dayComboBox.getSelectedItem();
                // Handle the selected item change here (e.g., display a message)
                System.out.println("Selected value: " + selectedValue);
          
                String acWeekday = dates.getAcWeekday(selectedValue);
                System.out.println(acWeekday);
                showTable(acWeekday);
            }
        });

        // add panel with comboBox in page
        add(panelCarousel, BorderLayout.NORTH);

        // --------------------------------------------------------------------------
        // Panel with buttons
        // --------------------------------------------------------------------------
        panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout());
        panelButtons.setBackground(Color.decode("#C4A2F2"));

        // buttons 
        CustonButton btnAdd = new CustonButton();
        CustonButton btnUpdate = new CustonButton();
        CustonButton btnDelete = new CustonButton();

        // set images in buttons 
        btnAdd.setImage("add.png");
        btnUpdate.setImage("update.png");
        btnDelete.setImage("delete.png");

        // add buttons in page
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnAdd);

        // configure  action delete when click in btnDelete 
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow == -1){
                    String message = "Selecione a  tarefa que deseja excluir";
                    Audio.playAudio("warning.wav", message, "Atenção!");
                    return;
                }
                int id = (Integer) table.getValueAt(selectedRow, 0);
                // delete task and reload page 
                try {
                  
                    String message = "Deseja realmente excluir esta tarefa?";
                    int answer = JOptionPane.showConfirmDialog(null, message, "Excluir", JOptionPane.YES_NO_OPTION);
                    if(answer == JOptionPane.NO_OPTION)
                        return;
                    crud.deleteQuest(id);
                    App.setConfigPage(); 
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // configure  action update when click in btnUpdate 
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow == -1){
                    String message = "Selecione a  tarefa que deseja alterar";
                    Audio.playAudio("warning.wav", message, "Atenção!");
                    return;
                }
                String message = "Deseja realmente alterar esta tarefa?";
                int answer = JOptionPane.showConfirmDialog(null, message, "Alterar", JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.NO_OPTION)
                    return;

                
                table.editCellAt(0, 0);
                table.clearSelection();

                int id = (Integer) table.getValueAt(selectedRow, 0);
                String nmTask = (String) table.getValueAt(selectedRow, 1);
                String time = (String) table.getValueAt(selectedRow, 2);
                String priority = (String) table.getValueAt(selectedRow, 3);

                String selectedWeek = (String) dayComboBox.getSelectedItem();

                Task task = new Task(id, nmTask, time, priority,
                        dates.getAcWeekday(selectedWeek), 0);

                System.out.println(task.getNmQuest());
                
                task.update();
            }
        });

        // configure action open formAdd when click in button btnAdd
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("button add has clicked");
                App.setFormAdd();
            }
        });

        // show table with all day of week tasks
        showTable(dates.getAcWeekday());

    }

    private void showTable(String acWeekday) {
        // clear page 
        App.clearPanel(this);

        // add panel with comboBox
        add(panelCarousel, BorderLayout.NORTH);

        // create table
        table = new JTable();

        // configure if cell is editable
        model = new DefaultTableModel() {
            private int notEditableColumn = 0;
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex != notEditableColumn;
            }

        };
        // set model in table
        table.setModel(model);

        // customize table header
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.decode("#9C5EF2"));
        tableHeader.setForeground(Color.decode("#F2F2F2"));
        tableHeader.setPreferredSize(new Dimension(App.frameWidth, (int)(App.frameHeight * 0.10)));
        tableHeader.setFont(new Font("Arial", Font.PLAIN, 15));

        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.setRowHeight(40);

        // table collumns
        model.addColumn("ID");
        model.addColumn("Atividade");
        model.addColumn("Tempo");
        model.addColumn("Prioridade");

        
        // get tasks
        List<Task> tasks = crud.selectAllTask(acWeekday.toLowerCase());

        // set table content
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            model.addRow(new Object[] { task.getId(), task.getNmQuest(),
                    task.getQtTime(), task.getNmPriority() });
        }

        // put table in scrollPane
        JScrollPane scrollPaneTable = new JScrollPane(table);

        // add table to page
        add(scrollPaneTable, BorderLayout.CENTER);

        // add buttons
        add(panelButtons, BorderLayout.SOUTH);
    }
}
