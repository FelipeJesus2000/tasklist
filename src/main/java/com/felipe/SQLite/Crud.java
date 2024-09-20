package com.felipe.SQLite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import com.felipe.Audio;
import com.felipe.Dates;
import com.felipe.Task;

public class Crud {
    SQLConnection sqlConnection = new SQLConnection();

    // insert one quest
    public boolean insertQuest(Task task) {
        String sql = "INSERT INTO tb_quest (nm_quest, qt_time, nm_priority, ac_weekday, dt_last_update) values (?, ?, ?, ?, ?)";
        Connection connection = null;
        boolean rowsUpdated = false;
        try {
            connection = sqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            LocalDate localDate = LocalDate.now();

            preparedStatement.setString(1, task.getNmQuest());
            preparedStatement.setString(2, task.getQtTime());
            preparedStatement.setString(3, task.getNmPriority());
            preparedStatement.setString(4, task.getAcWeekday());
            preparedStatement.setString(5, localDate.toString());

            rowsUpdated = preparedStatement.executeUpdate() > 0;
            System.out.println("Task inserted successfully!");
            String message = "Tarefa inserida com sucesso!";
            Audio.playAudio("conclusion.wav", message, "Confirmação");
        } catch (SQLException e) {
            System.out.println("error file: Crud.java");
            System.out.println("error method: InsertQuest()");
            System.out.println(e.getMessage());
           
            String message = "Não foi possivel inserir a tarefa\n"
            + "código de erro: " + e.getErrorCode();
            Audio.playAudio("error.wav", message, "Erro!");
        } 
        sqlConnection.disconnect(connection);
        return rowsUpdated;
    }

    // select all tasks
    public List<Task> selectAllTask(String weekday) {
        String sql = "SELECT * FROM tb_quest WHERE ac_weekday = ? ORDER BY ic_complete, \n"
                + "CASE \n"
                + "WHEN nm_priority = 'alta' THEN 1 \n"
                + "WHEN nm_priority = 'média' THEN 2 \n"
                + "WHEN nm_priority = 'baixa' THEN 3 \n"
                + "ELSE 4 \n"
                + "END";

        List<Task> tasks = new ArrayList<>();
        SQLConnection sqlConnection = new SQLConnection();
        try (Connection connection = sqlConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, weekday);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nm_quest = rs.getString("nm_quest");
                String qt_time = rs.getString("qt_time");
                String nm_priority = rs.getString("nm_priority");
                String ac_weekday = rs.getString("ac_weekday");
                int ic_complete = rs.getInt("ic_complete");

                tasks.add(new Task(id, nm_quest, qt_time, nm_priority, ac_weekday, ic_complete));
            }
        } catch (SQLException e) {
            System.out.println("error file: Crud.java");
            System.out.println("method: selectAllTask()");
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    // select one task
    public Task selectTask(int id) {
        Task task = null;
        String sql = "SELECT * FROM tb_quest WHERE id = ? ";
        try (Connection connection = sqlConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String nm_quest = rs.getString("nm_quest");
                String qt_time = rs.getString("qt_time");
                String nm_priority = rs.getString("nm_priority");
                String ac_weekday = rs.getString("ac_weekday");
                int ic_complete = rs.getInt("ic_complete");
                String hr_dedicated = rs.getString("hr_dedicated");
                task = new Task(id, nm_quest, qt_time, nm_priority, ac_weekday, ic_complete, hr_dedicated);
            }
        } catch (SQLException e) {
            System.out.println("error file: Crud.java");
            System.out.println("method: selectTask(int id)");
            System.err.println(e.getMessage());
        }
        return task;
    }

    // select all task that have name like nmQuest
    public List<Task> selectTask(String nmQuest) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tb_quest WHERE nm_quest = ?";
        try (Connection connection = sqlConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nmQuest);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nm_quest = rs.getString("nm_quest");
                String qt_time = rs.getString("qt_time");
                String nm_priority = rs.getString("nm_priority");
                String ac_weekday = rs.getString("ac_weekday");
                int ic_complete = rs.getInt("ic_complete");
                String hr_dedicated = rs.getString("hr_dedicated");
                Task task = new Task(id, nm_quest, qt_time, nm_priority, ac_weekday, ic_complete, hr_dedicated);
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println("error file: Crud.java");
            System.out.println("method: selectTask(String nmQuest)");
            System.err.println(e.getMessage());
        }
        return tasks;
    }

    public boolean resetDailyTask() {
        Dates dates = new Dates();
        String today = dates.getAcWeekday().toLowerCase();
        String now = LocalDate.now().toString();
        String sql = "SELECT dt_last_update from tb_quest WHERE ac_weekday = '" + today + "' LIMIT 1";
        // select one task of current day
        try {
            ResultSet rs = select(sql);
            while (rs.next()) {
                String date = rs.getString("dt_last_update");
                if (date != null) {
                    if (date.equals(now)) {
                        System.out.println("the daily task has already been updated previously!");
                        rs.close();
                        return false;
                    }
                }
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException sqlError) {
            System.err.println("error-page: 'Crud.java'");
            System.err.println("error-function: 'resetDailyTask() part 1'");
            System.out.println("SQL error: " + sqlError.getMessage());
        }
        // if last update is before the current day, update all tasks for the current
        // day of week to ic_complete = 0
        try {
            boolean rowUpdated;
            sql = "UPDATE tb_quest " +
                    "SET dt_last_update = ?, ic_complete = 0 " +
                    "WHERE ac_weekday = ?";
            Connection connection = sqlConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, now);
            statement.setString(2, today);
            rowUpdated = statement.executeUpdate() > 0;
            System.out.println("the daily task has just been reset!");
            connection.close();
            statement.close();
            return rowUpdated;
        } catch (SQLException sqlError) {
            System.err.println("error-page: 'Crud.java'");
            System.err.println("error-method: 'resetDailyTask() part 2'");
            System.out.println("SQL error: " + sqlError.getMessage());
        }
        return false;
    }

    // select
    public ResultSet select(String query) {
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = sqlConnection.getConnection();
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            // sqlConnection.disconnect(connection);
        } catch (SQLException sqlError) {
            System.out.println("error: " + sqlError.getMessage());
        }
        return rs;
    }

    // delete quest
    public boolean deleteQuest(int id) throws SQLException {
        boolean rowDeleted = false;
        String DELETE_QUEST_SQL = "DELETE FROM tb_quest WHERE id = ?";
        try (Connection connection = sqlConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUEST_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException sqlError) {
            System.out.println("error-page: 'Crud.java'");
            System.out.println("error-method: deleteQuest()");
            System.out.println(sqlError.getMessage());
        }
        return rowDeleted;
    }

    // update table quest
    public String updateQuest(Task task) throws SQLException {
        boolean rowUpdated = false;
        String sql = "UPDATE tb_quest SET nm_quest = ?, qt_time = ?, nm_priority = ?, ac_weekday = ? WHERE id = ?";
        try (Connection connection = sqlConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, task.getNmQuest());
            statement.setString(2, task.getQtTime());
            statement.setString(3, task.getNmPriority());
            statement.setString(4, task.getAcWeekday());
            statement.setInt(5, task.getId());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException sqlError) {
            System.out.println("error-page: 'Crud.java'");
            System.out.println("error-method: updateQuest()");
            String errorMessage = sqlError.getMessage();
            String error = errorMessage.substring(0, errorMessage.indexOf("]")+1);
            System.out.println(errorMessage);
            if(error.equals("[SQLITE_CONSTRAINT_CHECK]")){
                String message = "Digite no campo prioridade alta, média ou baixa!";
                Audio.playAudio("error.wav", message, "Erro!");
            }
                
        }
        if (rowUpdated) {
            return "the task has been updated";
        }
        return "An error occurred and the task could not be updated";
    }

    // update tb_quest: ic_complete to 1(mean task complete) and hours dedicated
    public String completeTask(int id, String hr_dedicated) throws SQLException {
        boolean rowUpdated = false;
        String sqlCommand = "UPDATE tb_quest SET ic_complete = 1, hr_dedicated = ? WHERE id = ?";
        try (Connection connection = sqlConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, hr_dedicated);
            statement.setInt(2, id);
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException sqlError) {
            System.out.println("error-page: 'Crud.java'");
            System.out.println("error-method: completeTask()");
            System.out.println(sqlError.getMessage());
        }
        if (rowUpdated) {
            return "the task has been updated";
        }
        return "An error occurred and the task could not be updated";
    }

    public ArrayList<String> selectDedicatedTime(){
        ArrayList<String> stats = new ArrayList<>();;
        ResultSet rs = null; 
        Connection connection = null;
        Statement stmt = null;

        // select all time dedicated
        String dedicatedAllTask = "SELECT \n" 
        +"time(SUM(strftime('%s', hr_dedicated) - strftime('%s', '00:00:00')), 'unixepoch') AS total_time\n" 
        +"FROM \n" 
        +"tb_quest ";
        
        try{
            connection = sqlConnection.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(dedicatedAllTask);

            while(rs.next()){
                stats.add(rs.getString(1));
            }
            connection = null;
            rs = null;
            stmt = null;
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
            stats.clear();
            return stats;
        }

        // select dedicated of one task 
        String dedicatedOneTask = "SELECT \n"
        +"time(SUM(strftime('%s', hr_dedicated) - strftime('%s', '00:00:00')), 'unixepoch') AS total_time\n" 
        +"FROM \n"
        +"tb_quest\n"
        +"WHERE nm_quest = 'test'";

        try{
            connection = sqlConnection.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(dedicatedOneTask);

            while(rs.next()){
                stats.add(rs.getString(1));
            }
            connection = null;
            rs = null;
            stmt = null;
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
            stats.clear();
            return stats;
        }

        return stats;
    }

    // show tables
    // ____________________________________________________________________________________________________________________________
    public void showTables() {
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%';";
        Statement stmt = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = sqlConnection.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println("---------------------------------------------------");
            System.out.println("Tabelas no banco de dados:");
            while (rs.next()) {
                String nomeTabela = rs.getString("name");
                System.out.println(nomeTabela);
            }
            System.out.println("---------------------------------------------------");
            rs.close();
            sqlConnection.disconnect(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void sqlite(){
        String query = "ALTER TABLE tb_quest ADD CONTRAINT chk_priority CHECK(nm_priority IN ('baixa', 'média', 'alta'))";
        Connection connection = null;
        Statement smtm = null;
        try{
            connection = sqlConnection.getConnection();
            smtm = connection.createStatement();
            smtm.executeQuery(query);            
        }catch(SQLException e){
            System.out.println("Constraint:" + e.getMessage());
        }
        smtm = null;   
    }

}
