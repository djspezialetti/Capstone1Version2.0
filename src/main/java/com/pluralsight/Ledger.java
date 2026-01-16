package com.pluralsight;

import com.pluralsight.DAO.DataManager;
import com.pluralsight.DAO.LedgerDAO;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.pluralsight.Utility.systemDialogue;

public class Ledger {
    public final LedgerDAO ledgerDAO;
    private static DataManager dataManager;
    private static Ledger ledgerDao;

    public Ledger(LedgerDAO ledgerDAO) {
        this.ledgerDAO = ledgerDAO;
    }

    public static void showLedger(List<Transaction> transactions, Scanner scanner) {
        boolean running = true;

        while (running) {
            systemDialogue("\t--- Ledger ---"+
                    "\n\t\tA) All"+
                    "\n\t\tD) Deposits"+
                    "\n\t\tP) Payments"+
                    "\n\t\tR) Reports"+
                    "\n\t\tH) Home");

            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A" -> display(ledgerDao.getTransactions());
                case "D" -> display(filterAmount(transactions, true));
                case "P" -> display(filterAmount(transactions, false));
                case "R" -> Reports.showReports(transactions, scanner);
                case "H" -> running = false;
            }
        }
    }

    private static List<Transaction> filterAmount(List<Transaction> list, boolean deposits) {
        return list.stream()
                .filter(t -> deposits ? t.getAmount() > 0 : t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactions() {
        return ledgerDao.getTransactions();
    }

//    public static void setDataManager(DataManager dataManager) {
//        Ledger.dataManager = dataManager;
//    }

    private static void display(List<Transaction> list) {
        list.stream()
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .forEach(System.out::println);
    }
}
