package com.felipe.SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class CreateDatabase {
    public CreateDatabase() {
        String url = "jdbc:sqlite:db_tasks.db";
        // code create tb_weekday
        String sqlTBWeekday = "CREATE TABLE IF NOT EXISTS tb_weekday(\n"
                + "ac_weekday TEXT NOT NULL PRIMARY KEY, \n"
                + "nm_weekday TEXT NOT NULL);";

        // code create tb_quest
        String sqlTBQuest = "CREATE TABLE IF NOT EXISTS tb_quest ( \n"
                + "id INTEGER NOT NULL PRIMARY KEY, \n"
                + "nm_quest TEXT NOT NULL, \n"
                + "qt_time TEXT NOT NULL, \n"
                + "nm_priority TEXT NOT NULL, \n"
                + "hr_dedicated TEXT DEFAULT '00:00:00', \n"
                + "ic_complete INTEGER NOT NULL DEFAULT 0, \n"
                + "ac_weekday TEXT NOT NULL REFERENCES tb_weekday(ac_weekday), \n"
                + "dt_last_update TEXT,"
                + "CONSTRAINT chk_priority CHECK(nm_priority IN ('baixa', 'média', 'alta')))";

        // execute code
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sqlTBWeekday);
            stmt.execute(sqlTBQuest);
            System.out.println("Tables created!");
        } catch (SQLException exception) {
            System.out.println(exception);
        }
        // code insert days of week
        String weekdayInserts = "insert into tb_weekday (ac_weekday, nm_weekday) values (?, ?)";

        Map<String, String> weekdays = new HashMap<>();
        weekdays.put("sun", "Sunday");
        weekdays.put("mon", "Monday");
        weekdays.put("tue", "Tuesday");
        weekdays.put("wed", "Wednesday");
        weekdays.put("thu", "Thursday");
        weekdays.put("fri", "Friday");
        weekdays.put("sat", "Saturday");

        SQLConnection sqlConnection = new SQLConnection();
        Connection connection = null;
        // execute insert
        try {
            connection = sqlConnection.getConnection();

            for (Map.Entry<String, String> entry : weekdays.entrySet()) {
                PreparedStatement preparedStatement = connection.prepareStatement(weekdayInserts);
                preparedStatement.setString(1, entry.getKey());
                preparedStatement.setString(2, entry.getValue());
                preparedStatement.execute();
                System.out.printf("%s, %s were successfully inserted! \n", entry.getKey(), entry.getValue());
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        sqlConnection.disconnect(connection);
    }
}
