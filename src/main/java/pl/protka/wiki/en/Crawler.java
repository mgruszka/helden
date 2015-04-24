package pl.protka.wiki.en;

import info.bliki.api.Page;
import info.bliki.api.User;
import pl.protka.db.CrawledSource;
import static pl.protka.wiki.en.CrawledField.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gruszek on 11.04.15.
 */

class Crawler {
    private final CrawledSource lang;
    private User user;

    public Crawler(CrawledSource type) throws SourceNotSupportedException {
        this.lang = type;
        this.user = loginToWikipedia(lang);
    }

    public void start(List<String> listOfTiles){

        List<Page> pages = user.queryContent(listOfTiles);

        for(Page page : pages){
            String infobox = getInfoboxData(page);
            System.out.print(infobox);
        }

    }

    private String getInfoboxData(Page page) {
        String s = page.toString();
        try {
            List<String> options = INFOBOX.get(lang);
            s = s.substring(s.indexOf(options.get(0)));
            s = s.substring(0, s.indexOf("\n}}") + 3).replaceAll("  *", " ").replaceAll("\n\\|", "\n");
            System.out.println(s + "\n\n\n");
            return s;
        }catch(StringIndexOutOfBoundsException e){
            // Gotcha!
            System.err.println("Incorrect page");
            return null;
        }
    }


    private User loginToWikipedia(CrawledSource lang) throws SourceNotSupportedException {
        String apiUrl;
        switch (lang) {
            case WIKIENG: apiUrl = "http://en.wikipedia.org/w/api.php";
                break;
            case WIKIPL: apiUrl = "http://pl.wikipedia.org/w/api.php";
                break;
            default:
                throw new SourceNotSupportedException();
        }
        User user = new User("", "", apiUrl);
        user.login();
        return user;
    }

}
