package com.didi;

import com.didi.model.Datasource;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        Datasource datasource = new Datasource();
        if(!datasource.open()){
            System.out.println("Can't open datasource");
            return;
        }


        datasource.queryTransactionsMetadata();
//        datasource.insertTransaction(
//                58623692, 215, "new iron");

        java.util.Date myDate = new java.util.Date("10/10/2019");
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
        datasource.insertIndividualBuyer("Anna McMannon", 25365698,
                sqlDate, "anna.mcmannon", 255669);

        datasource.close();

    }
}
