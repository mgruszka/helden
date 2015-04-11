package pl.protka.wiki.en;

import pl.protka.db.CrawledSource;

/**
 * Created by gruszek on 08.04.15.
 */

public class Main {

    public static void main(String[] args) {

        System.out.println("Starting crawler");

        Crawler crawler = new Crawler(CrawledSource.WIKIPL);
        crawler.start();

        System.out.println("Crawling finished");


    }

}