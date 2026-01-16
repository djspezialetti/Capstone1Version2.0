package com.pluralsight.DAO;

import com.pluralsight.Ledger;
import com.pluralsight.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.pluralsight.DAO.DataManager.connection;

public class LedgerDAO {
    private final DataSource dataSource;
    private static Ledger ledger;

    public LedgerDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        System.out.println("Loading all transactions...\n\n");

        String query = "SELECT * FROM transactions";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                    int transactionID = results.getInt("transaction_id");
                    String trans_date = results.getString("trans_date");
                    String trans_time = results.getString("trans_time");
                    String description = results.getString("description");
                    String vendor = results.getString("vendor");
                    String amountString = results.getString("amount");

                    Transaction transaction = new Transaction(transactionID,
                            trans_date, trans_time, description, vendor, amountString);
                    transactions.add(transaction);

                    return transactions;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
