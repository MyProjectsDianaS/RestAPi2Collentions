package com.didi;

import com.didi.model.Datasource;
import com.didi.model.ObjectId;

import java.sql.Date;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        Datasource datasource = new Datasource();
        if(!datasource.open()){
            System.out.println("Can't open datasource");
            return;
        }

//        List<String> albumsForArtist =
//                datasource.queryAlbumsForArtists("Pink Floyd", Datasource.ORDER_BY_DESC );
//
//        for (String album: albumsForArtist) {
//            System.out.println(album);
//        }

        datasource.queryTransactionsMetadata();
//        datasource.insertTransaction(new ObjectId("TCB2555696").toString(),
//                58623694, 2001, "wedding gift");

        ArrayList<String> tr =  new ArrayList<>();
        tr.add(new ObjectId("TIB203596387").toString());
        tr.add(new ObjectId("TIB203594000").toString());
        tr.add(new ObjectId("TIB203594005").toString());
        datasource.insertIndividualBuyer(new  ObjectId("IB03").toString(),
                "Dorin Vasile", 500, new Date(2019,03,21) ,
                "dorin.vasile89", tr);
        datasource.close();

    }
}
