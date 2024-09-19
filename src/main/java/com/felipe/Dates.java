package com.felipe;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Dates {
    private LocalDate today = LocalDate.now();
    private DayOfWeek dayOfWeek = today.getDayOfWeek();
    // private DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
    
    

    public String getAcWeekday(){
        return dayOfWeek.toString().substring(0, 3);
    }

    public String geeWeekday(){
        return dayOfWeek.toString();
    }
    public String getWeekday(String acWeekday){
        switch (acWeekday) {
            case "mon":
                return "Monday";
            case "tue":
                return "Tuesday";
            case "wed":
                return "Wednesday";
            case "thu":
                return "Thursday";
            case "fri":
                return "Friday";
            case "sat":
                return "Saturday";
            case "sun":
                return "Sunday";
            default:
                return "Invalid abbreviation";
        }
    }
    public String getWeekdayPT(String acWeekday){
        switch (acWeekday) {
            case "mon":
                return "Segunda-feira";
            case "tue":
                return "Terça-feira";
            case "wed":
                return "Quarta-feira";
            case "thu":
                return "Quinta-feira";
            case "fri":
                return "Sexta-feira";
            case "sat":
                return "Sábado";
            case "sun":
                return "Domingo";
            default:
                return "Abreviação inválida";
        }
    }
    public String getAcWeekday(String fullDayName) {
        switch (fullDayName) {
          case "Segunda-feira":
            return "mon";
          case "Terça-feira":
            return "tue";
          case "Quarta-feira":
            return "wed";
          case "Quinta-feira":
            return "thu";
          case "Sexta-feira":
            return "fri";
          case "Sábado":
            return "sat";
          case "Domingo":
            return "sun";
          default:
            return "Dia inválido";
        }
      }
    public int getWeekdayIndex(){
        // System.out.println("número dia da semana: " + dayOfWeek.getValue());
        return dayOfWeek.getValue() - 1;
    }

}
