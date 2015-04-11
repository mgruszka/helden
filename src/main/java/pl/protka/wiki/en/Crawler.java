package pl.protka.wiki.en;

import info.bliki.api.Page;
import info.bliki.api.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gruszek on 11.04.15.
 */

class Crawler {
    public void start() {

        List<String> listOfTiles = new ArrayList<>();
        listOfTiles.add("Leonhard Euler");
        User user = loginToWikipedia();
        List<Page> pages = user.queryContent(listOfTiles);

        for(Page page : pages){
            System.out.print(page.toString());
        }

    }


    private User loginToWikipedia() {
        User user = new User("", "", "http://en.wikipedia.org/w/api.php");
        user.login();
        return user;
    }

}
