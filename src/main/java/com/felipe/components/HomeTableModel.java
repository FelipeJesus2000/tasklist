package com.felipe.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class HomeTableModel extends AbstractTableModel{
    // id and daily task
    private String[] collumnNames = { "ID", "Tarefas diárias" };
    private List<Object[]> data = new ArrayList<>();
    // private List<Boolean> rowEnabled = new ArrayList<>();
 
    @Override
    public int getRowCount() {
       return data.size(); // return how many rows
    }

    @Override
    public int getColumnCount() {
        return collumnNames.length; // return how many columns
    }
    @Override
    public String getColumnName(int column) {
        return collumnNames[column]; //return name of collumn
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return  data.get(rowIndex)[columnIndex]; // get value of cell
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // block cell editable
    }

    //////non override methods
    public void addRow(Object[] rowData) {
        data.add(rowData);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    
}
