package pl.protka.wiki.en;

import pl.protka.db.CrawledSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gruszek on 08.04.15.
 */

public class Main {

    public static void main(String[] args) {

        System.out.println("Starting crawler");
        List<String> listOfTiles = new ArrayList<>();
//        listOfTiles.add("Leonhard Euler");
        listOfTiles.add("Jakob Emanuel Handmann");
        try {
            Crawler crawler = new Crawler(CrawledSource.WIKIENG);
            crawler.start(listOfTiles);
        } catch (SourceNotSupportedException e) {
            // Gotcha!
            e.printStackTrace();
        }


        System.out.println("Crawling finished");


    }

}