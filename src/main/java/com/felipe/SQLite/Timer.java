package com.felipe.SQLite;

public class Timer {
    private String time;

    public Timer(String time) {
        this.time = time;
    }
    // get string time formated
    public String getTime() {
        String time = String.format("%02d:%02d:%02d", getHours(), getMinutes(), getSecconds());
        return time;
    }
    // set this.time to formated string
    public void setTime(int hours, int minutes, int secconds) {
        String time = String.format("%02d:%02d:%02d", hours, minutes, secconds);
        System.out.println(time);
        this.time = time;
    }
    // get seccond of string 
    public int getSecconds() {
        int firstColonPosition = time.lastIndexOf(':');
        String secconds = time.substring(firstColonPosition + 1, (time.length()));
        return Integer.parseInt(secconds);
    }
    // get minutes of string 
    public int getMinutes() {
        int startPosition = time.indexOf(':') + 1;
        int endPosition = time.lastIndexOf(':');
        String minutes = time.substring(startPosition, endPosition);
        return Integer.parseInt(minutes);
    }
    // get hours of string 
    public int getHours() {
        int firstColonPosition = time.indexOf(':');
        String hours = time.substring(0, firstColonPosition);
        return Integer.parseInt(hours);
    }
    // if secconds is greater than 60, add minutes
    private void arrangeSecconds() {
        if (getSecconds() > 60) {
            int secconds = getSecconds() % 60;
            int minutes = getSecconds() / 60;
            minutes += getMinutes();
            setTime(getHours(), minutes, secconds);
        }
    }
    // if minutes is greater than 60, add hours
    private void arrangeMinutes() {
        System.out.println(getTime());
        if (getMinutes() > 60) {
            int minutes = getMinutes() % 60;
            int hours = getMinutes() / 60;
            hours += getHours();
            setTime(hours, minutes, getSecconds());
        }
    }
    //  arrange time
    public void arrangeTime() {
        arrangeSecconds();
        arrangeMinutes();
    }
    // sum timers 
    public static Timer sum(Timer t1, Timer t2) {
        int secconds = t1.getSecconds() + t2.getSecconds();
        int minutes = t1.getMinutes() + t2.getMinutes();
        int hours = t1.getHours() + t2.getHours();
        String time = String.format("%02d:%02d:%02d", hours, minutes, secconds);
        Timer timer = new Timer(time);
        timer.arrangeTime();
        return timer;
    }
    // verify if is valid timer
    public static boolean isTimer(String time) {
        // hours
        int firstColonPosition = time.indexOf(':');
        String hours = time.substring(0, firstColonPosition);
        // minutes
        int startPosition = firstColonPosition + 1;
        int endPosition = time.lastIndexOf(':');
        String minutes = time.substring(startPosition, endPosition);
        // secconds
        String secconds = time.substring(endPosition + 1, (time.length()));
        try{
            // verify if parts of string is number
            Integer.parseInt(hours);
            Integer.parseInt(minutes);
            Integer.parseInt(secconds);
        }
        catch(NumberFormatException numberError){
            // string isn't a number
            System.out.println("isn't a number");
            System.out.println("error: " + numberError);
            return false;
        }
        return true;
    }

}
