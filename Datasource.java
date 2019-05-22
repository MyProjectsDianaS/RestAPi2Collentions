package com.didi.model;

import java.sql.*;
import java.util.ArrayList;
//import org.postgresql.

public class Datasource {

    public static final String DB_NAME = "Buyers.db";
    public static final String CONNECTION_STRING = "jdbc:postgresql://localhost:5432/Buyers";

    public static final String TABLE_BUYER = "buyers";
    public static final String COLUMN_BUYER_ID = "id";
    public static final String COLUMN_BUYER_NAME = "buyername";
    public static final String COLUMN_BUYER_VALUE = "value";
    public static final int INDEX_BUYER_ID = 1;
    public static final int INDEX_BUYER_NAME = 2;
    public static final int INDEX_BUYER_VALUE = 3;

    public static final String TABLE_INDIVIDUALBUYER = "individualbuyers";
    public static final String COLUMN_INDIVIDUALBUYER_DATEREGISTERED = "dateregistered";
    public static final String COLUMN_INDIVIDUALBUYER_PID = "buyerpersonalid";
    public static final String COLUMN_INDIVIDUALBUYER_TRANSACTIONS = "transactions";
    public static final int INDEX_INDIVIDUALBUYER_DATEREGISTERED = 1;
    public static final int INDEX_INDIVIDUALBUYER_PID = 2;
    public static final int INDEX_INDIVIDUALBUYER_TRANSACTIONS = 3;

    public static final String TABLE_CORPORATEBUYER = "corporatebuyers";
    public static final String COLUMN_CORPORATEBUYER_ADDRESS = "address";
    public static final String COLUMN_CORPORATEBUYER_CID = "companyid";
    public static final String COLUMN_CORPORATEBUYER_TRANSACTIONS = "transactions";
    public static final int INDEX_CORPORATEBUYER_ADDRESS = 1;
    public static final int INDEX_CORPORATEBUYER_CID = 2;
    public static final int INDEX_CORPORATEBUYER_TRANSACTIONS = 3;

    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COLUMN_TRANSACTION_ID = "id";
    public static final String COLUMN_TRANSACTION_NUMBER = "transactionNumber";
    public static final String COLUMN_TRANSACTION_VALUE = "value";
    public static final String COLUMN_TRANSACTION_DESCRIPTION = "description";
    public static final int INDEX_TRANSACTION_ID = 1;
    public static final int INDEX_TRANSACTION_NUMBER = 2;
    public static final int INDEX_TRANSACTION_VALUE = 3;
    public static final int INDEX_TRANSACTION_DESCRIPTION = 4;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    public static final String INSERT_INDIVIDUALBUYER = "INSERT INTO public." + TABLE_INDIVIDUALBUYER +
            '(' + COLUMN_BUYER_ID + ", " + COLUMN_BUYER_NAME + "," +
            COLUMN_BUYER_VALUE + ", " + COLUMN_INDIVIDUALBUYER_DATEREGISTERED + ", " +
            COLUMN_INDIVIDUALBUYER_PID + ", " + COLUMN_INDIVIDUALBUYER_TRANSACTIONS +
            ") VALUES(?, ?, ?, ?, ?, ?)";
    public static final String INSERT_CORPORATEBUYER = "INSERT INTO public." + TABLE_CORPORATEBUYER +
            '(' + COLUMN_BUYER_ID + ", " + COLUMN_BUYER_NAME + ", " +
            COLUMN_BUYER_VALUE + ", " + COLUMN_CORPORATEBUYER_ADDRESS + ", " +
            COLUMN_CORPORATEBUYER_CID + ", " + COLUMN_CORPORATEBUYER_TRANSACTIONS +
            ") VALUES(?, ?, ? ,? ,?, ?)";

    public static final String INSERT_TRANSACTION = "INSERT INTO public." + TABLE_TRANSACTIONS +
            '(' + COLUMN_TRANSACTION_ID + ", \"" + COLUMN_TRANSACTION_NUMBER + "\", " +
            COLUMN_TRANSACTION_VALUE + ", " + COLUMN_TRANSACTION_DESCRIPTION +
            ") VALUES(row( ? )::objectId, ?, ?, ?)";

    public static final String QUERY_INDIVIDUALBUYERS = "SELECT " + COLUMN_BUYER_NAME + " FROM public." +
            TABLE_INDIVIDUALBUYER;

    public static final String QUERY_CORPORATEBUYERS = "SELECT " + COLUMN_BUYER_NAME + " FROM public." +
            TABLE_CORPORATEBUYER;

    public static final String QUERY_TRANSACTIONS = "SELECT " + COLUMN_TRANSACTION_VALUE + " FROM public." +
            TABLE_TRANSACTIONS + " WHERE " + COLUMN_TRANSACTION_NUMBER + " = \"";

//    public static final String QUERY_VIEW_TRANSACTION_INFO = "SELECT " + COLUMN_TRANSACTION_VALUE + ", " +
//            COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + " FROM " + TABLE_ARTIST_SONG_VIEW +
//            " WHERE " + COLUMN_SONG_TITLE + " = \"";

    private Connection conn;

    private PreparedStatement insertIntoIndividualBuyers;
    private PreparedStatement insertIntoCorporateBuyers;
    private PreparedStatement insertIntoTransactions;

    private PreparedStatement queryIndividualBuyers;
    private PreparedStatement queryCorporateBuyers;






    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, "postgres", "sarchizian");

            insertIntoIndividualBuyers = conn.prepareStatement(INSERT_INDIVIDUALBUYER, Statement.RETURN_GENERATED_KEYS);
            insertIntoCorporateBuyers = conn.prepareStatement(INSERT_CORPORATEBUYER, Statement.RETURN_GENERATED_KEYS);
            insertIntoTransactions = conn.prepareStatement(INSERT_TRANSACTION);

            queryIndividualBuyers = conn.prepareStatement(QUERY_INDIVIDUALBUYERS);
            queryCorporateBuyers = conn.prepareStatement(QUERY_CORPORATEBUYERS);


            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {

            if (insertIntoIndividualBuyers != null) {
                insertIntoIndividualBuyers.close();
            }

            if (insertIntoCorporateBuyers != null) {
                insertIntoCorporateBuyers.close();
            }

            if (insertIntoTransactions != null) {
                insertIntoTransactions.close();
            }

            if (queryIndividualBuyers != null) {
                queryIndividualBuyers.close();
            }

            if (queryCorporateBuyers != null) {
                queryCorporateBuyers.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public void queryTransactionsMetadata() {
        String sql = "SELECT * FROM " + TABLE_TRANSACTIONS;

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sql)) {

            ResultSetMetaData meta = results.getMetaData();
            int numColumns = meta.getColumnCount();
            for (int i = 1; i <= numColumns; i++) {
                System.out.format("Column %d in the transactions table is named %s\n",
                        i, meta.getColumnName(i));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }


    public int insertIndividualBuyer(String id, String buyerName, long value,
                                      Date dateRegistered, String buyerPersonalId,
                                      ArrayList<String> transactions) throws SQLException {


        ResultSet results = queryIndividualBuyers.executeQuery();
        if (results.next()) {
            return results.getInt(1);
        } else {
            // Insert the IndividualBuyer
            insertIntoIndividualBuyers.setObject(1, id);
            insertIntoIndividualBuyers.setString(2, buyerName);
            insertIntoIndividualBuyers.setLong(3, value);
            insertIntoIndividualBuyers.setDate(4, dateRegistered);
            insertIntoIndividualBuyers.setString(5, buyerPersonalId);
            insertIntoIndividualBuyers.setObject(6, transactions);

            int affectedRows = insertIntoIndividualBuyers.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert IndividualBuyer!");
            }

            ResultSet generatedKeys = insertIntoIndividualBuyers.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get id for IndividualBuyer");
            }
        }
    }

    public void insertTransaction(String id, long transactionNumber, long value, String description) {

        try {
            conn.setAutoCommit(false);

            insertIntoTransactions.setObject(1, id);
            insertIntoTransactions.setLong(2, transactionNumber);
            insertIntoTransactions.setLong(3, value);
            insertIntoTransactions.setString(4, description);
            int affectedRows = insertIntoTransactions.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
            } else {
                throw new SQLException("The transaction insert failed");
            }

        } catch (Exception e) {
            System.out.println("Insert transaction exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                conn.rollback();
            } catch (SQLException e2) {
                System.out.println("Oh boy! Things are really bad! " + e2.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }

        }
    }


}
