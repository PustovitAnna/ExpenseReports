import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.io.File;

public class MonthlyReport {
    private Map<Integer, List<Expense>> monthlyExpenses;

    public MonthlyReport(){
        monthlyExpenses = new HashMap<>();
    }

    public void addMonthlyReport(int monthNumber){
        String text = readFileContentsOrNull(monthNumber);
        String[] line = text.split("\n");
        if(!(monthlyExpenses.containsKey(monthNumber))){
            monthlyExpenses.put(monthNumber, new ArrayList<>());
        }
        for(int i = 1; i < line.length; i++) {
            String[] lineContents = line[i].split(",");
            String nameOfExpense = lineContents[0];
            boolean isExpense = Boolean.parseBoolean(lineContents[1]);
            int countOfExpense = Integer.parseInt(lineContents[2]);
            double priceOfCount = Double.parseDouble(lineContents[3]);
            Expense temp = new Expense(nameOfExpense, isExpense,
                    countOfExpense, priceOfCount);
            monthlyExpenses.get(monthNumber).add(temp);
        }
    }

    public double getMonthProfitOrExpense(int monthNumber, boolean isItExpense){
        double sum = 0;
        if(!(monthlyExpenses.containsKey(monthNumber))) {
            System.out.println("Данных об указанном месяце нет.");
            return sum;
        }
        List<Expense> exp = monthlyExpenses.get(monthNumber);
        for (int i  = 0; i < exp.size(); i++){
            if(exp.get(i).is_expense == isItExpense){
                sum += exp.get(i).quantity * exp.get(i).sum_of_one;
            }
        }
        return sum;
    }

    public void getMaxProfitOrExpense(int monthNumber){
        double maxProfit = 0;
        double maxExpense = 0;
        String supply1 = "";
        String supply2 = "";

        if(!(monthlyExpenses.containsKey(monthNumber))) {
            System.out.println("Данных об указанном месяце нет.");
            return;
        }
        List<Expense> exp = monthlyExpenses.get(monthNumber);
        for (int i  = 0; i < exp.size(); i++){
            double sum1 = 0;
            if(exp.get(i).is_expense == false){
                sum1 = exp.get(i).quantity * exp.get(i).sum_of_one;
                if(maxProfit < sum1){
                    maxProfit = sum1;
                    supply1 = exp.get(i).item_name;
                }
            }
            double sum2 = 0;
            if(exp.get(i).is_expense == true){
                sum2 = exp.get(i).quantity * exp.get(i).sum_of_one;
                if(maxExpense < sum2){
                    maxExpense = sum2;
                    supply2 = exp.get(i).item_name;
                }
            }
        }
        System.out.println("Самый прибыльный товар в текущем месяце: " + supply1);
        System.out.println("Прибыль: " + Math.ceil(maxProfit) + " рублей");

        System.out.println("Самая большая трата в текущем месяце: " + supply2);
        System.out.println("Размер траты: " + Math.ceil(maxExpense) + " рублей");

    }

    private static String readFileContentsOrNull(int monthNumber) {
        String fileSeparator = File.separator;
        String path = ("resources" + fileSeparator + "m.20210" + monthNumber + ".csv");
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    private class Expense {
        String item_name;
        boolean is_expense;
        int quantity;
        double sum_of_one;

        private Expense(String item_name, boolean is_expense, int quantity, double sum_of_one){
            this.item_name = item_name;
            this.is_expense = is_expense;
            this.quantity = quantity;
            this.sum_of_one = sum_of_one;
        }
    }
}
