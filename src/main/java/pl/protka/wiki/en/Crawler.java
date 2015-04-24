package pl.protka.wiki.en;

import info.bliki.api.Page;
import info.bliki.api.User;
import pl.protka.db.CrawledSource;
import static pl.protka.wiki.en.CrawledField.*;

import java.util.ArrayList;
import java.util.HashMap;
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
        InfoboxDataProcessor idt = new InfoboxDataProcessor(lang);
        HashMap<String, String> params;

        for(Page page : pages){
            Person person = new Person();
            String infoboxData = idt.getInfoboxData(page);
            person.name = page.getTitle();
            if(infoboxData != null) {
                params = idt.getPersonData(infoboxData);
                System.out.print(infoboxData);
                for (String dateKey : BIRTH_DATE.get(lang))
                    person.birthDate = idt.prepareDate(params.get(dateKey), person.birthDate);
                for (String dateKey : DEATH_DATE.get(lang))
                    person.deathDate = idt.prepareDate(params.get(dateKey), person.deathDate);
                for (String key : BIRTH_PLACE.get(lang))
                    person.birthPlace = idt.getFieldContent(params.get(key), person.birthPlace);
                for (String key : DEATH_PLACE.get(lang))
                    person.deathPlace = idt.getFieldContent(params.get(key), person.deathPlace);
                for (String key : FIELDS.get(lang))
                    person.fields = idt.getFieldContent(params.get(key), person.fields);
                System.out.print(person);
            }
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
