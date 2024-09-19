package com.felipe.pages;

import com.felipe.App;
import com.felipe.Audio;
import com.felipe.Task;
import com.felipe.SQLite.Crud;
import com.felipe.SQLite.Timer;
import com.felipe.components.DefaultInput;
import com.felipe.components.InputSearch;
import com.felipe.components.RoundedBorder;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StatsPage extends Page {
   public StatsPage() {

      // PANEL SEARCH
      JPanel panelSearch = new JPanel();
      panelSearch.setLayout(new FlowLayout());

      // create input button
      InputSearch inputSearch = new InputSearch(300, 20);
      panelSearch.add(inputSearch);
      // add in page
      add(panelSearch, BorderLayout.NORTH);

      // panel that shows task name and hour dedicated
      JPanel card = new JPanel();
      card.setLayout(new BoxLayout(card, BoxLayout.PAGE_AXIS));
      // set rounded border
      card.setBorder(new RoundedBorder(20));

      // row task name
      JPanel rowTaskName = new JPanel();
      
  
      ImageIcon taskIcon = new ImageIcon(App.pathAssets + "task2.png");
      JLabel lblTaskName = new JLabel(taskIcon);
      // lblTaskName.setFont(new Font("Arial", Font.PLAIN, 24));

      JLabel taskNameResult = new JLabel();
      taskNameResult.setFont(new Font("Arial", Font.PLAIN, 24));

      rowTaskName.add(lblTaskName);
      rowTaskName.add(taskNameResult);

      card.add(rowTaskName);
      // row task dedicated time
      JPanel rowDedicatedTime = new JPanel();
      ImageIcon clockIcon = new ImageIcon(App.pathAssets + "clock.png");
      JLabel lblDedicatedTime = new JLabel(clockIcon);
      lblDedicatedTime.setFont(new Font("Arial", Font.PLAIN, 22));

      JLabel dedicatedTimeResult = new JLabel();
      dedicatedTimeResult.setFont(new Font("Arial", Font.PLAIN, 22));

      rowDedicatedTime.add(lblDedicatedTime);
      rowDedicatedTime.add(dedicatedTimeResult);

      card.add(rowDedicatedTime);

      // panel results
      JPanel panelResults = new JPanel();
      panelResults.add(card, BorderLayout.NORTH);
      // add(panelResults, BorderLayout.CENTER);

      // create event when click btnSearch
      inputSearch.getButton().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // verify if is placeholder
            if (DefaultInput.contentIsPlaceholder(inputSearch.getTextField()))
               return;
            Crud crud = new Crud();
            // select all tasks that have searched name
            List<Task> tasks = crud.selectTask(inputSearch.getText());
            // if doesn't match value return
            if (tasks.size() == 0) {
               String message = "Não há nenhuma atividade com este nome\n"
               + "Verifique se digitou as letras maiúsculas e minúsculas corretamente";
               Audio.playAudio("warning.wav", message, "Ops!");
               return;
            }
            // create new timer
            Timer totalDedicatedTime = new Timer("00:00:00");
            // sum all tasks timer
            for (Task task : tasks) {
               Timer dedicatedTime = new Timer(task.getHrDedicated());
               totalDedicatedTime = Timer.sum(dedicatedTime, totalDedicatedTime);
               System.out.println(task.getNmQuest());
            }
            add(panelResults, BorderLayout.CENTER);
            totalDedicatedTime.arrangeTime();
            taskNameResult.setText(inputSearch.getText());
            dedicatedTimeResult.setText(totalDedicatedTime.getTime());
           
         }
      });
   }
}
