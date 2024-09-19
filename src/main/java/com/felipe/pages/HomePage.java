package com.felipe.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.felipe.App;
import com.felipe.Dates;
import com.felipe.Task;
import com.felipe.SQLite.Crud;
import com.felipe.components.CustonButton;
import com.felipe.components.HomeTableModel;

public class HomePage extends Page {
    private static CustonButton btnSelectTask;
    private static JTable table;
    private static ArrayList<Integer> disableRows;
    private static Crud crud = new Crud();
    private static  Dates dates = new Dates();

    public HomePage() {
        // set title of frame
        App.frame.setTitle("Lista de tarefas");


       
        // get all tasks of current day
        List<Task> tasks = crud.selectAllTask(dates.getAcWeekday().toLowerCase());

        // Create table
        table = new JTable();

        // Create table header
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.decode("#9C5EF2"));
        tableHeader.setForeground(Color.decode("#F2F2F2"));
        tableHeader.setPreferredSize(new Dimension(App.frameWidth, (int) (App.frameHeight * 0.05)));
        tableHeader.setFont(new Font("Arial", Font.PLAIN, 20));

        // Create specific table of home table
        HomeTableModel model = new HomeTableModel();
        table.setModel(model);
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.setRowHeight(25);

        disableRows = new ArrayList<>();
        // set table content
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            model.addRow(new Object[] { task.getId(), task.getNmQuest() });
            if (task.getIcComplete() == 1)
                disableRows.add(model.getRowCount() - 1); // disable rows that task is complete
        }

        // add pane with scroll
        JScrollPane scrollPaneTable = new JScrollPane(table);
        scrollPaneTable.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));

        // add button that select task
        btnSelectTask = new CustonButton("Selecionar Atividade");
        btnSelectTask.backgroundColor("#C4A2F2");
        btnSelectTask.textColor("#000");
        btnSelectTask.setEnabled(false);


        // customize the table row, if task is complete change row background to gray
        TableCellRenderer renderer = new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component component = table.getDefaultRenderer(String.class)
                        .getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (disableRows.contains(row)) {
                    component.setBackground(Color.GRAY); // disabled color background
                } else {
                    component.setBackground(Color.WHITE); // enabled color background
                }

                return component;
            }
        };

        // add previous custimoze table code in table
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        // event when select table row
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // verify if event is called multiple times
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (disableRows.contains(selectedRow)) {
                        btnSelectTask.setEnabled(false);
                        return;
                    }
                    if (selectedRow != -1) {
                        btnSelectTask.setEnabled(true);
                    } else {
                        btnSelectTask.setEnabled(false);
                    }

                }
            }
        });

        // event when click in select button
        btnSelectTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1)
                    return;

                int id = (Integer) table.getValueAt(selectedRow, 0);
                System.out.println("ID: " + id);
                App.setTimerPage(id);
            }
        });

        // add button select in page
        add(btnSelectTask, BorderLayout.SOUTH);
        // add table in scroll pane in page
        add(scrollPaneTable);
    }
}
