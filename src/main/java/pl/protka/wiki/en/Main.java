package pl.protka.wiki.en;

import pl.protka.db.CrawledSource;
import pl.protka.db.DatabaseDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gruszek on 08.04.15.
 */

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting crawler");
        DatabaseDriver dbdriver = DatabaseDriver.getInstance();
        List<String> listOfTiles;

//        StartingMathematicans sm = new StartingMathematicans();
//        sm.prepareStartingDb();
//        listOfTiles.add("Albert Einstein");

        List<String> list = dbdriver.getPeopleNotCrowled(CrawledSource.WIKIENG);

        while(list.size()>0) {
            try {
                Crawler crawler = new Crawler(CrawledSource.WIKIENG);
                listOfTiles = list;
                crawler.start(listOfTiles);
            } catch (SourceNotSupportedException e) {
                // Gotcha!
                e.printStackTrace();
            }
            list = dbdriver.getPeopleNotCrowled(CrawledSource.WIKIENG);
        }


        list = dbdriver.getPeopleNotCrowled(CrawledSource.WIKIPL);
        while(list.size()>0) {
            try {
                Crawler crawler = new Crawler(CrawledSource.WIKIPL);
                listOfTiles = list;
                crawler.start(listOfTiles);
            } catch (SourceNotSupportedException e) {
                // Gotcha!
                e.printStackTrace();
            }
            list = dbdriver.getPeopleNotCrowled(CrawledSource.WIKIPL);
        }


        System.out.println("Crawling finished");


    }

}