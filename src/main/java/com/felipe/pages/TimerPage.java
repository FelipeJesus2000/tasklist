package com.felipe.pages;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.felipe.App;
import com.felipe.Audio;
import com.felipe.Task;
import com.felipe.SQLite.Crud;
import com.felipe.components.DefaultButton;
import com.felipe.structure.Body;
// import com.felipe.SQLite.Timer as time;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
// import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class TimerPage extends Page {
    private static boolean isRunning = false;
    private static ImageIcon iconPause;
    private static ImageIcon iconPlay;
    private static JButton btnPlay;
    private static JLabel lblTimerNumber;
    private static Timer timer = new Timer();
    private static TimerTask timerTask;
    private static int hr;
    private static int mm;
    private static int ss;
    private static Task globalTask;
    private static Crud crud = new Crud();

    public TimerPage(Task task) {
        globalTask = task;

        // get path
        String projectURL = System.getProperty("user.dir");
        String pathImg = projectURL + "\\src\\assets\\";

        JPanel footer = new JPanel();
        // button end task;
        DefaultButton btnEnd = new DefaultButton("Encerrar Atividade");
        btnEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // pause countdown
                if(isRunning){
                    setImageTimer();
                    myTimer();
                }
                    
                // show message asking if user are really sure that him wanna leave task
                String message = "Deseja realmente encerrar a atividade? Você perderá o progresso realizado!";
                int question = JOptionPane.showConfirmDialog(null, message, "Atenção!", JOptionPane.YES_NO_OPTION);
                if (question == JOptionPane.YES_OPTION) {
                    System.out.println("User clicked Yes");
                    // Go to home page
                    App.setHomePage();
                } else if (question == JOptionPane.NO_OPTION) {
                    System.out.println("User clicked No");
                    // Still in this page
                } else {
                    System.out.println("User closed the dialog");
                    // Still in this page
                }
            }
        });
        footer.add(btnEnd);

        add(footer, BorderLayout.SOUTH);

        JPanel body = new Body();
        add(body);

        JPanel pnlText = new JPanel();
        JLabel lblTimerText = new JLabel(task.getNmQuest());
        lblTimerText.setFont(new Font("Arial", Font.PLAIN, 30));
        lblTimerText.setHorizontalTextPosition(SwingConstants.CENTER);

        JPanel pnlNumber = new JPanel();
        lblTimerNumber = new JLabel(task.getQtTime());
        lblTimerNumber.setFont(new Font("Arial", Font.PLAIN, 30));

        pnlText.add(lblTimerText, BorderLayout.CENTER);
        body.add(pnlText, BorderLayout.NORTH);

        pnlNumber.add(lblTimerNumber, BorderLayout.CENTER);
        body.add(pnlNumber, BorderLayout.SOUTH);

        // button that play and pause
        iconPause = new ImageIcon(pathImg + "pause-timer.png");
        iconPlay = new ImageIcon(pathImg + "play-timer.png");
        btnPlay = new JButton(iconPlay);
        btnPlay.setBorder(null);
        btnPlay.setBackground(null);
        btnPlay.setPreferredSize(new Dimension(50, 50));

        // create event when click btnPlay
        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setImageTimer();
                myTimer();
            }
        });
        // btnPlay.setBorder(new EmptyBorder(100, 100, 100, 100));
        body.add(btnPlay, BorderLayout.CENTER);

    }

    private void setImageTimer() {
        // verify if countdown is running
        if (isRunning) {
            // if is running, stop and change icon
            isRunning = false;
            btnPlay.setIcon(iconPlay);
        } else {
            //if isn't running, play and change icon
            isRunning = true;
            btnPlay.setIcon(iconPause);
        }
    }
    // timer logic 
    private void myTimer() {
        if (isRunning) {
            setTimeVariables();
            System.out.println("Hours: " + hr + "\nMinutes:" + mm + "\nSecconds:" + ss);

            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    timerLogic();
                }
            };

            if (ss > 00 || mm > 0 || ss > 0) {
                timer.schedule(timerTask, 1000, 1000);
            }

        }
        // if hour, minute and seccond is equal 0, stop the countdown
        if (!isRunning) {
            timer.cancel();
        }
    }

    private void timerLogic() {
        // get values of string clock and put in variables for hour, minute and seccond
        setTimeVariables();
        StringBuilder sb = new StringBuilder();
        // if seccond is greater than 0, decrease one seccond
        if (ss > 00) {
            ss--;
            sb.append(String.format("%02d:", hr));
            sb.append(String.format("%02d:", mm));
            sb.append(String.format("%02d", ss));
            lblTimerNumber.setText(sb.toString());
            return;
        }
        // if minute is greater than 0, decrease one minute and secconds are equal to 59
        if (mm > 00) {
            mm--;
            ss = 59;
            sb.append(String.format("%02d:", hr));
            sb.append(String.format("%02d:", mm));
            sb.append(String.format("%02d", ss));
            lblTimerNumber.setText(sb.toString());
            return;
        }
        // if hour is greater than 0, decrease one hour and minutes and secconds are equal to 59
        if (hr > 00) {
            hr--;
            mm = 59;
            ss = 59;
            sb.append(String.format("%02d:", hr));
            sb.append(String.format("%02d:", mm));
            sb.append(String.format("%02d", ss));
            lblTimerNumber.setText(sb.toString());
            return;
        }
        // if hour, minute and seccond is equal 0, stop timer and call method setImageTime
        if (hr == 0 && mm == 0 && ss == 0) {
            timer.cancel();
            System.out.println(lblTimerNumber.getText());
            setImageTimer();
            taskIsCompleted();
        }
    }
    // get values of string clock and put in variables for hour, minute and seccond
    private void setTimeVariables() {
        String clock = lblTimerNumber.getText();
        hr = Integer.parseInt(clock.substring(0, 2));
        mm = Integer.parseInt(clock.substring(3, 5));
        ss = Integer.parseInt(clock.substring(6, 8));
    }
    // complete task
    private void taskIsCompleted(){
        
        // play complete task audio and send message
        String message = "Bom trabalho, você completou a atividade!";
        Audio.playAudio("conclusion.wav", message, "Parabéns");
        
        try {
            // update time dedicated on this task
            com.felipe.SQLite.Timer timeDedicated = new com.felipe.SQLite.Timer(globalTask.getHrDedicated());
            com.felipe.SQLite.Timer timeQuest = new com.felipe.SQLite.Timer(globalTask.getQtTime());
            com.felipe.SQLite.Timer newTimeDedicated = com.felipe.SQLite.Timer.sum(timeDedicated, timeQuest);
            newTimeDedicated.arrangeTime();

            crud.completeTask(globalTask.getId(), newTimeDedicated.getTime());
        } catch (SQLException e) {
            String errorCode = "Código do erro: " + e.getErrorCode() + "\n";
            String errorMessage = "Ops! Não foi possivel completar a tarefa.";
            JOptionPane.showConfirmDialog(null, (errorCode + errorMessage), "Erro!!!", JOptionPane.DEFAULT_OPTION);
            System.out.println("Error: " + e.getMessage());
        }
        // back to home page 
        App.setHomePage();
        isRunning = false;
        return;
    }
}
