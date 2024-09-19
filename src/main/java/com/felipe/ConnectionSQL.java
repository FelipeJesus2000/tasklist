// package com.felipe;

// import java.sql.Statement;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.sql.CallableStatement;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// public class ConnectionSQL {
//     private Connection connection = null;
//     String url = "jdbc:mariadb://localhost:3306/db_tasks";
//     String user = "root";
//     String password = "root";

//     private static final String INSERT_QUEST_SQL = "INSERT INTO tb_quest (nm_quest, qt_time, nm_priority, ac_weekday) values (?, ?, ?, ?)";
//     private static final String SELECT_QUEST_SQL = "SELECT * FROM tb_quest WHERE id = ?";
//     private static final String SELECT_ALL_QUESTS_SQL = "SELECT * FROM tb_quest WHERE ac_weekday = ?";
//     private static final String DELETE_QUEST_SQL = "DELETE FROM tb_quest WHERE id = ?";
//     private static final String UPDATE_QUEST_SQL = "UPDATE tb_quest SET nm_quest = ?, qt_time = ?, nm_priority = ?, ac_weekday = ? WHERE id = ?";

//     public Connection connect() {
//         connection = null;
//         try {
//             connection = DriverManager.getConnection(url, user, password);
//         } catch (Exception e) {
//             System.err.println("Falha ao conectar ao MySQL");
//             System.err.println(e.getMessage());
//         }
//         System.out.println("conectou!!!");
//         return connection;
//     }

//     public boolean disconnect() {
//         try {
//             if (this.connection.isClosed() == false) {
//                 this.connection.close();
//             }
//         } catch (SQLException e) {
//             System.err.println(e.getMessage());
//             return false;
//         }
//         System.out.println("desconectou!!!");
//         return true;
//     }

//     public String insertQuest(Task task) throws SQLException {
//         try (Connection connection = connect();
//                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUEST_SQL)) {
            
//             preparedStatement.setString(1, task.getNmQuest());
//             preparedStatement.setString(2, task.getQtTime());
//             preparedStatement.setString(3, task.getNmPriority());
//             preparedStatement.setString(4, task.getAcWeekday());
//             preparedStatement.executeUpdate();
//             return "Tarefa Inserida com sucesso!";
//         } catch (SQLException e) {
//             System.err.println(e);
//             return "Não foi possivel inserir a tarefa!";
//         }
//     }

//     public Task selecTask(int id) {
//         Task task = null;
//         try (Connection connection = connect();
//                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUEST_SQL)) {
//             preparedStatement.setInt(1, id);
//             ResultSet rs = preparedStatement.executeQuery();
//             while (rs.next()) {
//                 String nm_quest = rs.getString("nm_quest");
//                 String qt_time = rs.getString("qt_time");
//                 String nm_priority = rs.getString("nm_priority");
//                 String ac_weekday = rs.getString("ac_weekday");
//                 int ic_complete = rs.getInt("ic_complete");
//                 task = new Task(id, nm_quest, qt_time, nm_priority, ac_weekday, ic_complete);
//             }
//         } catch (SQLException e) {
//             System.out.println("An error occurred and the task could not be selected");
//             System.err.println(e);
//         }
//         return task;
//     }

//     public List<Task> selecAllTask(String weekday) {
//         List<Task> tasks = new ArrayList<>();
//         try (Connection connection = connect();
//                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUESTS_SQL)) {
//             preparedStatement.setString(1, weekday);
//             ResultSet rs = preparedStatement.executeQuery();
//             while (rs.next()) {
//                 int id = rs.getInt("id");
//                 String nm_quest = rs.getString("nm_quest");
//                 String qt_time = rs.getString("qt_time");
//                 String nm_priority = rs.getString("nm_priority");
//                 String ac_weekday = rs.getString("ac_weekday");
//                 int ic_complete = rs.getInt("ic_complete");

//                 tasks.add(new Task(id, nm_quest, qt_time, nm_priority, ac_weekday, ic_complete));
//             }

//         } catch (SQLException e) {
//             System.out.println("An error occurred and the task could not be selected");
//             System.err.println(e);
//         }
//         return tasks;
//     }

//     public ResultSet select(String query) {
//         ResultSet rs = null;
//         try {
//             Connection connection = connect();
//             Statement stmt = connection.createStatement();
//             rs = stmt.executeQuery(query);
//         } catch (SQLException sqlError) {
//             System.out.println("error: " + sqlError.getMessage());
//         }
//         return rs;
//     }

//     public boolean deleteQuest(int id) throws SQLException {
//         boolean rowDeleted;
//         try (Connection connection = connect();
//                 PreparedStatement statement = connection.prepareStatement(DELETE_QUEST_SQL)) {
//             statement.setInt(1, id);
//             rowDeleted = statement.executeUpdate() > 0;
//         }
//         return rowDeleted;
//     }

//     public String updateQuest(Task task) throws SQLException {
//         boolean rowUpdated;
//         try (Connection connection = connect();
//                 PreparedStatement statement = connection.prepareStatement(UPDATE_QUEST_SQL)) {
//             statement.setString(1, task.getNmQuest());
//             statement.setString(2, task.getQtTime());
//             statement.setString(3, task.getNmPriority());
//             statement.setString(4, task.getAcWeekday());
//             statement.setInt(5, task.getId());
//             rowUpdated = statement.executeUpdate() > 0;
//         }
//         if (rowUpdated) {
//             return "the task has been updated";
//         }
//         return "An error occurred and the task could not be updated";
//     }

//     public String completeTask(int id) throws SQLException {
//         boolean rowUpdated;
//         String sqlCommand = "UPDATE tb_quest SET ic_complete = 1 WHERE id = ?";
//         try (Connection connection = connect();
//                 PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
//             statement.setInt(1, id);
//             rowUpdated = statement.executeUpdate() > 0;
//         }
//         if (rowUpdated) {
//             return "the task has been updated";
//         }
//         return "An error occurred and the task could not be updated";
//     }

//     public boolean resetDailyTask() {
//         try {
//             Dates dates = new Dates();
//             String today = dates.getAcWeekday().toLowerCase();
//             String query = "SELECT dt_last_update from tb_quest WHERE ac_weekday = '" + today + "' LIMIT 1";
//             ResultSet rs = select(query);
//             while (rs.next()) {
//                 String date = rs.getString("dt_last_update");
//                 if (date != null) {
//                     String now = LocalDate.now().toString();
//                     if (date.equals(now)) {
//                         System.out.println("As String são iguais");
//                         System.out.println("Já foi atualizado!");
//                     } else {
//                         String callSQL = "{CALL resetDailyTask()}";
//                         CallableStatement callableStatement = connection.prepareCall(callSQL);
//                         callableStatement.execute();
//                         System.out.println("Procedure 'resetDailyTask()' has been executed!");
//                     }
//                 } else {
//                     String callSQL = "{CALL resetDailyTask()}";
//                     CallableStatement callableStatement = connection.prepareCall(callSQL);
//                     callableStatement.execute();
//                     System.out.println("Procedure 'resetDailyTask()' has been executed!");
//                 }
//             }
//         } catch (

//         SQLException sqlError) {
//             System.out.println("SQL error: " + sqlError.getMessage());
//         }
//         return false;
//     }
// }
