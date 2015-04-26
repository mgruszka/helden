package pl.protka.wiki.en;

import info.bliki.api.Page;
import info.bliki.api.User;
import pl.protka.db.CrawledSource;
import static pl.protka.wiki.en.CrawledField.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                for (String key : INSTITUTIONS.get(lang))
                    person.institutionsInf.addAll(idt.getFieldContentList(params.get(key)));
                for (String key : CITIES.get(lang))
                    person.citiesInf.addAll(idt.getFieldContentList(params.get(key)));
                for (String key : COUNTRIES.get(lang))
                    person.countriesInf.addAll(idt.getFieldContentList(params.get(key)));
                for (String key : RELATION.get(lang))
                    person.countriesInf.addAll(idt.getFieldContentList(params.get(key)));
                person = getInfoboxLinks(infoboxData, person);
            }
            person = getPageLinks(page.toString(), person);
            System.out.print(person);
        }

    }

    private Person getInfoboxLinks(String page, Person person) {
        HashMap<String, Set> map = getLinks(page);
        person.citiesInf = map.get("city");
        person.countriesInf = map.get("country");
        person.institutionsInf = map.get("inst");
        person.relatedPeopleInf = map.get("ppl");
        return person;
    }

    private Person getPageLinks(String page, Person person) {
        HashMap<String, Set> map = getLinks(page);
        person.citiesTxt = map.get("city");
        person.countriesTxt = map.get("country");
        person.institutionsTxt = map.get("inst");
        person.relatedPeopleTxt = map.get("ppl");
        return person;
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
                    cities.add(p.getTitle());
                    System.out.println("added city: " + p.getTitle());
                    break;
                case "country":
                    countries.add(p.getTitle());
                    System.out.println("added country: " + p.getTitle());
                    break;
                case "university":
                    institutions.add(p.getTitle());
                    System.out.println("added university: " + p.getTitle());
                    break;
                case "person":
                    people.add(p.getTitle());
                    System.out.println("added person: " + p.getTitle());
                    break;
                default:
                    System.out.println("Just a link: " + p.getTitle());
            }
        }
        HashMap<String, Set> res = new HashMap<>();
        res.put("city", cities);
        res.put("country", countries);
        res.put("inst", institutions);
        res.put("ppl", people);
        return res;
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
