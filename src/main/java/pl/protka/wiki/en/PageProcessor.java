package pl.protka.wiki.en;

import info.bliki.api.Connector;
import info.bliki.api.Page;
import info.bliki.api.User;
import pl.protka.db.CrawledSource;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pl.protka.wiki.en.CrawledField.*;
import static pl.protka.wiki.en.CrawledField.CITIES_TXT;

/**
 * Created by gruszek on 26.04.15.
 */

public class PageProcessor {
    private final CrawledSource lang;
    private User user;

    public PageProcessor(User user, CrawledSource type) {
        this.lang = type;
        this.user = user;
    }

    public HashMap<String, Set> getLinks(String page) {
        ArrayList<String> links = new ArrayList<>();
        Set<String> cities = new LinkedHashSet<>();
        Set<String> countries = new LinkedHashSet<>();
        Set<String> institutions = new LinkedHashSet<>();
        Set<String> people = new LinkedHashSet<>();

        try {
            Matcher m = Pattern.compile("\\[\\[(.*?)\\]\\]").matcher(page);

            while (m.find()) {
                links.add(m.group(1).split("\\|")[0]);
            }
        } catch (NullPointerException e) {
            // Gotcha!
            // its thrown when page has no links
            System.out.println("No links at page");
        }

        List<Page> listOfLinks = user.queryContent(links);
        String pageType;
        for (Page p : listOfLinks) {
            pageType = determinePageType(p);
            switch (pageType) {
                case "city":
                    cities.add(translateToEnglish(p.getTitle()));
                    System.out.println("added city: " + translateToEnglish(p.getTitle()));
                    break;
                case "country":
                    countries.add(translateToEnglish(p.getTitle()));
                    System.out.println("added country: " + translateToEnglish(p.getTitle()));
                    break;
                case "university":
                    institutions.add(translateToEnglish(p.getTitle()));
                    System.out.println("added university: " + translateToEnglish(p.getTitle()));
                    break;
                case "person":
                    people.add(translateToEnglish(p.getTitle()));
                    System.out.println("added person: " + translateToEnglish(p.getTitle()));
                    break;
                default:
                    System.out.println("Just a link: " + translateToEnglish(p.getTitle()));
            }
        }
        HashMap<String, Set> res = new HashMap<>();
        res.put("city", cities);
        res.put("country", countries);
        res.put("inst", institutions);
        res.put("ppl", people);
        return res;
    }

    private String translateToEnglish(String title) {
        if(lang!=CrawledSource.WIKIENG){
            String[] valuePairs = { "prop", "langlinks", "titles", title, "llcontinue", "9316|bar"};
            Connector connector = new Connector();
            String rawXmlResponse = connector.queryXML(user, valuePairs);
            try {
                Matcher m = Pattern.compile("<ll lang=\"als\" xml:space=\"preserve\">(.*?)</ll>").matcher(rawXmlResponse);
                title = m.group(1).split("\\|")[0];
            } catch (IllegalStateException e) {
                // Gotcha!
                System.err.println("Page without english version: "+title);
            }
        }
        return title;
    }

    public String determinePageType(Page page){
        String s = page.toString();
        try {
            if (s.contains(PERSON.get(lang).get(0))){
                return "person";
            }
            if (page.getTitle().contains(INSTITUTIONS_TXT.get(lang).get(0))){
                return "university";
            }
            s = s.substring(s.indexOf(INFOBOX.get(lang).get(0)));
            s = s.substring(0, 50).toLowerCase();
            for (String key : INSTITUTIONS_TXT.get(lang))
                if(s.contains(key))
                    return "university";
            for (String key : COUNTRIES_TXT.get(lang))
                if(s.contains(key))
                    return "country";
            for (String key : CITIES_TXT.get(lang))
                if(s.contains(key))
                    return "city";
            return "link";
        }catch(StringIndexOutOfBoundsException e){
            // Gotcha!
            System.err.println("Page without infobox");
            return "link";
        }
    }
}
