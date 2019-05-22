package com.didi.model;

import java.sql.*;

public class Datasource {

    public static final String DB_NAME = "Buyers.db";
    public static final String CONNECTION_STRING = "jdbc:postgresql://localhost:5432/Buyers";

    public static final String TABLE_BUYER = "buyers";
    public static final String COLUMN_BUYER_ID = "id";
    public static final String COLUMN_BUYER_NAME = "buyerName";
    public static final String COLUMN_BUYER_VALUE = "value";


    public static final String TABLE_MAPTRANSACTIONS = "maptransactions";
    public static final String COLUMN_MAPTRANSACTIONS_ID = "id";
    public static final String COLUMN_MAPTRANSACTIONS_BIND = "bind";



    public static final String TABLE_INDIVIDUALBUYER = "individualbuyers";
    public static final String COLUMN_INDIVIDUALBUYER_DATEREGISTERED = "dateRegistered";
    public static final String COLUMN_INDIVIDUALBUYER_PID = "buyerPersonalId";
    public static final String COLUMN_INDIVIDUALBUYER_TRANSACTIONS = "transactions";


    public static final String TABLE_CORPORATEBUYER = "corporatebuyers";
    public static final String COLUMN_CORPORATEBUYER_ADDRESS = "address";
    public static final String COLUMN_CORPORATEBUYER_CID = "companyId";
    public static final String COLUMN_CORPORATEBUYER_TRANSACTIONS = "transactions";

    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COLUMN_TRANSACTION_ID = "id";
    public static final String COLUMN_TRANSACTION_NUMBER = "transactionNumber";
    public static final String COLUMN_TRANSACTION_VALUE = "value";
    public static final String COLUMN_TRANSACTION_DESCRIPTION = "description";


    public static final String INSERT_INDIVIDUALBUYER = "INSERT INTO public." + TABLE_INDIVIDUALBUYER +
            '(' + COLUMN_BUYER_ID + ", \"" + COLUMN_BUYER_NAME + "\"," +
            COLUMN_BUYER_VALUE + ", \"" + COLUMN_INDIVIDUALBUYER_DATEREGISTERED + "\", \"" +
            COLUMN_INDIVIDUALBUYER_PID + "\", " + COLUMN_INDIVIDUALBUYER_TRANSACTIONS +
            ") VALUES(uuid_generate_v4(), ?, ?, ?, ?, ?)";

    public static final String INSERT_CORPORATEBUYER = "INSERT INTO public." + TABLE_CORPORATEBUYER +
            "(\"" + COLUMN_BUYER_ID + "\", " + COLUMN_BUYER_NAME + ", " +
            COLUMN_BUYER_VALUE + ", \"" + COLUMN_CORPORATEBUYER_ADDRESS + "\", \"" +
            COLUMN_CORPORATEBUYER_CID + "\", " + COLUMN_CORPORATEBUYER_TRANSACTIONS +
            ") VALUES(uuid_generate_v4(), ?, ? ,? ,?, ?)";

    public static final String INSERT_MAPTRANSACTION = "INSERT INTO public." + TABLE_MAPTRANSACTIONS +
            '(' + COLUMN_MAPTRANSACTIONS_ID+ ", " + COLUMN_MAPTRANSACTIONS_BIND +
            ") VALUES(?, uuid_generate_v4())";

    public static final String INSERT_TRANSACTION = "INSERT INTO public." + TABLE_TRANSACTIONS +
            '(' + COLUMN_TRANSACTION_ID + ", \"" + COLUMN_TRANSACTION_NUMBER + "\", " +
            COLUMN_TRANSACTION_VALUE + ", " + COLUMN_TRANSACTION_DESCRIPTION +
            ") VALUES(uuid_generate_v4(), ?, ?, ?)";

    public static final String QUERY_INDIVIDUALBUYERS = "SELECT " + COLUMN_BUYER_NAME + " FROM public." +
            TABLE_INDIVIDUALBUYER;

    public static final String QUERY_CORPORATEBUYERS = "SELECT " + COLUMN_BUYER_NAME + " FROM public." +
            TABLE_CORPORATEBUYER;

    public static final String QUERY_TRANSACTIONS = "SELECT " + COLUMN_TRANSACTION_VALUE + " FROM public." +
            TABLE_TRANSACTIONS + " WHERE " + COLUMN_TRANSACTION_ID + " = \"";

    public static final String QUERY_MAPTRANSACTIONS = "SELECT " + COLUMN_MAPTRANSACTIONS_BIND + " FROM public." +
            TABLE_MAPTRANSACTIONS + " WHERE " + COLUMN_MAPTRANSACTIONS_ID + " = \"";



    private Connection conn;

    private PreparedStatement insertIntoIndividualBuyers;
    private PreparedStatement insertIntoCorporateBuyers;
    private PreparedStatement insertIntoTransactions;
    private PreparedStatement insertIntoMapTransactions;

    private PreparedStatement queryIndividualBuyers;
    private PreparedStatement queryCorporateBuyers;
    private PreparedStatement queryMapTransactions;


    public boolean open() {
        try {

            conn = DriverManager.getConnection(CONNECTION_STRING, "postgres", "sarchizian");

            insertIntoIndividualBuyers = conn.prepareStatement(INSERT_INDIVIDUALBUYER);
                    //, Statement.RETURN_GENERATED_KEYS);
            insertIntoCorporateBuyers = conn.prepareStatement(INSERT_CORPORATEBUYER);
                    //, Statement.RETURN_GENERATED_KEYS);
            insertIntoTransactions = conn.prepareStatement(INSERT_TRANSACTION);
            //, Statement.RETURN_GENERATED_KEYS);
            insertIntoMapTransactions = conn.prepareStatement(INSERT_MAPTRANSACTION);
            //, Statement.RETURN_GENERATED_KEYS);


            queryIndividualBuyers = conn.prepareStatement(QUERY_INDIVIDUALBUYERS);
            queryCorporateBuyers = conn.prepareStatement(QUERY_CORPORATEBUYERS);
            queryMapTransactions = conn.prepareStatement(QUERY_MAPTRANSACTIONS);


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

            if (insertIntoMapTransactions != null) {
                insertIntoMapTransactions.close();
            }

            if (queryIndividualBuyers != null) {
                queryIndividualBuyers.close();
            }

            if (queryCorporateBuyers != null) {
                queryCorporateBuyers.close();
            }

            if (queryMapTransactions != null) {
                queryMapTransactions.close();
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

    public long insertMapTransaction (long id) throws SQLException {
        queryMapTransactions.setLong(1, id);
        ResultSet results = queryMapTransactions.executeQuery();
        if (results.next()) {
            return results.getLong(1);
        } else {
            insertIntoMapTransactions.setLong(1, id);
        }

        int affectedRows = insertIntoIndividualBuyers.executeUpdate();
        if (affectedRows != 1) {
            throw new SQLException("Couldn't insert MapTransactions!");
        }
        ResultSet generatedKeys = insertIntoIndividualBuyers.getGeneratedKeys();
        // the id from the new generated IB

        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        } else {
            throw new SQLException("Couldn't get id for IndividualBuyer");
        }
    }


    public String insertIndividualBuyer(String buyerName, long value,
                                      Date dateRegistered, String buyerPersonalId,
                                      long transactions) throws SQLException {


            queryIndividualBuyers.setString(1, buyerName);

            ResultSet results = queryIndividualBuyers.executeQuery();

            if (results.next()) {
                return results.getString(1);
            } else {
                // Insert the IndividualBuyer
                insertIntoIndividualBuyers.setString(1, buyerName);
                insertIntoIndividualBuyers.setLong(2, value);
                insertIntoIndividualBuyers.setDate(3, dateRegistered);
                insertIntoIndividualBuyers.setString(4, buyerPersonalId);
                insertIntoIndividualBuyers.setLong(5, transactions);
                }

                int affectedRows = insertIntoIndividualBuyers.executeUpdate();
                if (affectedRows != 1) {
                    throw new SQLException("Couldn't insert IndividualBuyer!");
                }

                ResultSet generatedKeys = insertIntoIndividualBuyers.getGeneratedKeys(); // we retrieve

                if(transactions!=0) {
                    insertIntoMapTransactions.setLong(1, transactions);

                }
                if (generatedKeys.next()) {
                    return generatedKeys.getString(1);
                } else {
                    throw new SQLException("Couldn't get id for IndividualBuyer");
                }
            }
    }

    public void insertTransaction( long transactionNumber, long value, String description) {

        try {
            conn.setAutoCommit(false);

            insertIntoTransactions.setLong(1, transactionNumber);
            insertIntoTransactions.setLong(2, value);
            insertIntoTransactions.setString(3, description);

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
