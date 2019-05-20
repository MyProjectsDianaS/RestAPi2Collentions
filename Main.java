package com.didi;

import com.didi.model.Datasource;
import com.didi.model.ObjectId;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Datasource datasource = new Datasource();
        if(!datasource.open()){
            System.out.println("Can't open datasource");
            return;
        }

//        List<Artist> artists = datasource.queryArtists(5);
//        if(artists == null) {
//            System.out.println("No artists!");
//            return;
//        }
//
//        for(Artist artist : artists) {
//            System.out.println("ID = " + artist.getId() + ", Name = " + artist.getName());
//        }
//
//        List<String> albumsForArtist =
//                datasource.queryAlbumsForArtists("Pink Floyd", Datasource.ORDER_BY_DESC );
//
//        for (String album: albumsForArtist) {
//            System.out.println(album);
//        }

        ObjectId id = new ObjectId();
        id.setObjectIdValue("TCB2555696");
        datasource.insertTransaction(id, 58623694, 2001, "wedding gift");
        datasource.close();

    }
}
