package com.felipe;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Audio {
    private static String projectURL = System.getProperty("user.dir");
    private static String pathAudio = projectURL + "\\src\\assets\\audio\\";

    public static void playAudio(String nameAudio, String message, String title) {
        String filename = pathAudio + nameAudio;
        try {
            File musicPath = new File(filename);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                JOptionPane.showConfirmDialog(null, message, title, JOptionPane.DEFAULT_OPTION);

            } else {
                System.out.println("Couldn't find Music file");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
