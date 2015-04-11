package pl.protka.wiki.en;

import info.bliki.api.Page;
import info.bliki.api.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gruszek on 11.04.15.
 */

public class Crawler {
    public void start() {

        User user = loginToWikipedia();

    }


    private User loginToWikipedia() {
        User user = new User("", "", "http://en.wikipedia.org/w/api.php");
        user.login();
        return user;
    }

}
