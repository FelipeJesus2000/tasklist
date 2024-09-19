CREATE DATABASE db_tasks;
USE db_tasks;

CREATE TABLE tb_weekday(
    ac_weekday CHAR(3) NOT NULL PRIMARY KEY,
    nm_weekday VARCHAR(9) NOT NULL
);

CREATE TABLE tb_quest (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nm_quest VARCHAR(30) NOT NULL,
    qt_time TIME NOT NULL,
    nm_priority VARCHAR(5) NOT NULL,
    ic_complete BIT NOT NULL DEFAULT 0,
    ac_weekday CHAR(3) not null REFERENCES tb_weekday(ac_weekday),
    dt_last_update DATE
);

insert into tb_weekday (nm_weekday, ac_weekday) values
("Sunday", "sun"),
("Monday", "mon"),
("Tuesday", "tue"),
("Wednesday", "wed"),
("Thursday ", "thu"),
("Friday ", "fri"),
("Saturday ", "sat");


INSERT INTO tb_quest (nm_quest, qt_time, nm_priority, ac_weekday) VALUES
("Python", "01:40:00", "alta", "mon");

INSERT INTO tb_quest (nm_quest, qt_time, nm_priority, ac_weekday) VALUES
("Big Data", "00:50:00", "média", "mon");

INSERT INTO tb_quest (nm_quest, qt_time, nm_priority, ac_weekday) VALUES
("Python", "01:40:00", "média", "tue"),
("Java", "01:40:00", "média", "wed"),
("Java", "01:40:00", "média", "thu"),
("Kotlin", "01:40:00", "média", "fri"),
("Kotlin", "01:40:00", "média", "sat");

SELECT dt_last_update from tb_quest WHERE ac_weekday = tue;

UPDATE `db_tasks`.`tb_quest` SET `dt_last_update`='2024-01-18' WHERE  `id`=38;
UPDATE `db_tasks`.`tb_quest` SET `dt_last_update`='2024-01-18' WHERE  `id`=3;


ALTER TABLE tb_quest
	ADD COLUMN ic_complete BIT NOT NULL DEFAULT 0;
	
ALTER TABLE tb_quest 
	ADD COLUMN dt_last_update DATE;

SELECT * FROM tb_quest;

CALL resetDailyTask();
  
SELECT weekday_initials;

DELIMITER $$

CREATE PROCEDURE resetDailyTask()
BEGIN
DECLARE weekday_initials VARCHAR(3);
  
  SET weekday_initials = SUBSTRING(
    CASE
      WHEN DAYOFWEEK(CURDATE()) = 1 THEN 'Sunday'
      WHEN DAYOFWEEK(CURDATE()) = 2 THEN 'Monday'
      WHEN DAYOFWEEK(CURDATE()) = 3 THEN 'Tuesday'
      WHEN DAYOFWEEK(CURDATE()) = 4 THEN 'Wednesday'
      WHEN DAYOFWEEK(CURDATE()) = 5 THEN 'Thursday'
      WHEN DAYOFWEEK(CURDATE()) = 6 THEN 'Friday'
      WHEN DAYOFWEEK(CURDATE()) = 6 THEN 'Saturday'
    END,
    1,
    3
  );
  
  UPDATE `db_tasks`.`tb_quest` 
  SET `dt_last_update` = CURDATE(), `ic_complete` = 0 
  WHERE `ac_weekday` = weekday_initials;
END$$

DELIMITER;

DROP PROCEDURE IF EXISTS resetDailyTask;

SHOW PROCEDURE STATUS;
