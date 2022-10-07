import java.util.Scanner;

public class Main {
    private static final int MONTH_PASS = 3;
    private static final int CURRENT_YEAR = 2021;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        boolean isReadMonth = false;
        YearlyReport yearlyReport = new YearlyReport();
        boolean isReadYear = false;

        while(true) {
            printMenu();
            int userInput = sc.nextInt();
            if(userInput == 1){                 // считать все месячные отчеты
                if(isReadMonth == true){
                    System.out.println("Данные по месяцам уже были считаны.");
                } else{
                    for(int i = 1; i <= MONTH_PASS; i++) {
                        monthlyReport.addMonthlyReport(i);
                    }
                }
                isReadMonth = true;
            } else if (userInput == 2) {        // считать годовой отчёт
                if(isReadYear == true){
                    System.out.println("Данные за год уже были считаны.");
                } else {
                    yearlyReport.addYearlyReport(CURRENT_YEAR);
                }
                isReadYear = true;
            } else if (userInput == 3) {        //сверка месячных отчетов
                if(isReadMonth  == true && isReadYear == true) {
                    compareMonthsReport(monthlyReport, yearlyReport);
                } else{
                    System.out.println("Данных об указанном месяце нет.");
                }
            } else if(userInput == 4){          // вывод информации по месяцам
                for (int i = 1; i <= MONTH_PASS; i++) {
                    System.out.println(getNameOfMonth(i));
                    monthlyReport.getMaxProfitOrExpense(i);
                    System.out.println();
                }
            } else if(userInput == 5){          // отчет за год
                if(isReadMonth  == true && isReadYear == true) {
                    yearlyReport.getInformationAboutReport(CURRENT_YEAR);
                } else {
                    System.out.println("Отчет не был загружен.");
                }
            } else if (userInput == 0) {
                System.out.println("Программа завершена.");
                break;
            } else{
                System.out.println("Такой команды нет!");
            }
            System.out.println();
        }
    }

    public static void printMenu(){
        System.out.println("1. Считать все месячные отчёты");
        System.out.println("2. Считать годовой отчёт");
        System.out.println("3. Сверить отчёты");
        System.out.println("4. Вывести информацию о всех месячных отчётах");
        System.out.println("5. Вывести информацию о годовом отчёте");
        System.out.println("0. Выйти из программы.");
    }
    private static String getNameOfMonth(int monthNumber){
        String name = "";

        switch (monthNumber){
            case 1:
                name = "Январь";
                break;
            case 2:
                name = "Февраль";
                break;
            case 3:
                name = "Март";
                break;
            default:
                name = "Такого месяца нет";
        }
        return name;
    }
    private static void compareMonthsReport(MonthlyReport monthlyReport, YearlyReport yearlyReport){
        for (int i = 1; i <= MONTH_PASS; i++) {

            double profitM = monthlyReport.getMonthProfitOrExpense(i, false);
            double profitY = yearlyReport.getReportOfMonth(i, false);
            double expenseM = monthlyReport.getMonthProfitOrExpense(i, true);
            double expenseY = yearlyReport.getReportOfMonth(i, true);

            if(profitM == profitY && expenseM == expenseY){
                System.out.println("Ошибки данных за месяц " +
                        getNameOfMonth(i) + " не обнаружено. Операция завершена");
            }  else {
                System.out.println("Обнаружена ошибка при сверке данных за месяц - " + getNameOfMonth(i));
            }
        }
    }
}

