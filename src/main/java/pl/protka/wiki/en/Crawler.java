package pl.protka.wiki.en;

import info.bliki.api.Page;
import info.bliki.api.User;
import pl.protka.db.CrawledSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gruszek on 11.04.15.
 */

class Crawler {
    private final CrawledSource type;

    public Crawler(CrawledSource type) {
        this.type = type;

    }

    public void start() {

        List<String> listOfTiles = new ArrayList<>();
        listOfTiles.add("Leonhard Euler");
        System.out.print(CrawledField.NAME.get(type));
        User user = loginToWikipedia();
        List<Page> pages = user.queryContent(listOfTiles);

        for(Page page : pages){
//            System.out.print(page.toString());
        }

    }


    private User loginToWikipedia() {
        User user = new User("", "", "http://en.wikipedia.org/w/api.php");
        user.login();
        return user;
    }

}
