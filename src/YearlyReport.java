import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.io.File;

public class YearlyReport {
    private Map<Integer, List<Report>> monthReports;

    public YearlyReport(){
        monthReports = new HashMap<>();
    }

    private class Report {
        double amount;
        boolean is_expense;

        private Report(double amount, boolean is_expense) {
            this.amount = amount;
            this.is_expense = is_expense;
        }
    }

    public double getReportOfMonth(int monthNumber, boolean isItExpense){
        double rep = 0;
        List<Report> report = monthReports.get(monthNumber);
        for (int i  = 0; i < report.size(); i++){
            if(report.get(i).is_expense == isItExpense){
                rep = report.get(i).amount;
            }
        }
        return rep;
    }

    public void addYearlyReport(int year){
        String[] line = readFileContentsOrNull(year).split("\n");
        for(int i = 1; i < line.length; i++) {
            String[] lineContents = line[i].split(",");
            int month = Integer.parseInt(lineContents[0]);
            double amountOfMonth = Double.parseDouble(lineContents[1]);
            boolean isExpense = Boolean.parseBoolean(lineContents[2]);
            if(!(monthReports.containsKey(month))){
                monthReports.put(month, new ArrayList<>());
            }
            Report report = new Report(amountOfMonth, isExpense);
            monthReports.get(month).add(report);
        }
    }

    public void getInformationAboutReport(int yearNumber){
        System.out.println("Год - " + yearNumber);
        getProfitOfMonth();
        getAverageOfMonth();
    }

    private void getProfitOfMonth(){
        double sum = 0;
        for (int i  = 1; i <= monthReports.size(); i++){
            sum = getReportOfMonth(i, false) - getReportOfMonth(i, true);
            System.out.println("Прибыль за " + i + " месяц: " + Math.ceil(sum));
        }
    }

    private void getAverageOfMonth(){
        double sum1 = 0, sum2 = 0;
        int count = 0;
        for (int i  = 1; i <= monthReports.size(); i++){
            sum1 += getReportOfMonth(i, true);
            sum2 += getReportOfMonth(i, false);
            count++;
        }
        System.out.println("Средний расход - " + Math.ceil(sum1/count));
        System.out.println("Средний доход - " + Math.ceil(sum2/count));
    }

    private static String readFileContentsOrNull(int year) {
        String fileSeparator = File.separator;
        String path = "resources" + fileSeparator + "y." + year + ".csv";
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
}