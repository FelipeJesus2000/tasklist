package com.felipe;

import java.sql.SQLException;

import com.felipe.SQLite.Crud;

public class Task {
    private int id;
    private String nm_quest;
    private String qt_time;
    private String nm_priority;
    private String ac_weekday;
    private int ic_complete;
    private String hr_dedicated = null;

    // public Task(int id, String nm_quest, String qt_time, String nm_priority, String ac_weekday) {
    //     this.id = id;
    //     this.nm_quest = nm_quest;
    //     this.qt_time = qt_time;
    //     this.nm_priority = nm_priority;
    //     this.ac_weekday = ac_weekday;
    // }
    public Task(int id, String nm_quest, String qt_time, String nm_priority, String ac_weekday, int ic_complete){
        this.id = id;
        this.nm_quest = nm_quest;
        this.qt_time = qt_time;
        this.nm_priority = nm_priority;
        this.ac_weekday = ac_weekday;
        this.ic_complete = ic_complete;
    }
    public Task(int id, String nm_quest, String qt_time, String nm_priority, String ac_weekday, int ic_complete, String hr_dedicated){
        this.id = id;
        this.nm_quest = nm_quest;
        this.qt_time = qt_time;
        this.nm_priority = nm_priority;
        this.ac_weekday = ac_weekday;
        this.ic_complete = ic_complete;
        this.hr_dedicated = hr_dedicated;
    }
    public Task(){
        this(-1, "", "", "", "", 0, "");
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNmQuest() {
        return nm_quest;
    }

    public String getQtTime() {
        return qt_time;
    }

    public String getNmPriority() {
        return nm_priority;
    }

    public String getAcWeekday() {
        return ac_weekday;
    }
    public int getIcComplete() {
        return ic_complete;
    }
    public String getHrDedicated(){
        return hr_dedicated;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNmQuest(String nmQuest) {
        this.nm_quest = nmQuest;
    }

    public void setQtTime(String qtTime) {
        this.qt_time = qtTime;
    }

    public void setNmPriority(String nmPriority) {
        this.nm_priority = nmPriority;
    }

    public void setAcWeekday(String acWeekday) {
        this.ac_weekday = acWeekday;
    }
    public void setIcComplete(int icComplete) {
        this.ic_complete = icComplete;
    }
    public void setHrDedicated(String hrDedicated){
        this.hr_dedicated = hrDedicated;
    }

    public void update(){
        try{
            Crud crud = new Crud();
            crud.updateQuest(this);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public boolean insert(){
        boolean rowsUpdated = false;
        try{
            Crud crud = new Crud();
            rowsUpdated = crud.insertQuest(this);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return rowsUpdated;
    }
    public void delete(){
        try{
            Crud crud = new Crud();
            crud.deleteQuest(getId());
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

