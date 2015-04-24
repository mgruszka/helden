package pl.protka.wiki.en;

import info.bliki.api.Page;
import pl.protka.db.CrawledSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gruszek on 24.04.15.
 */

public class InfoboxDataProcessor {

    private final CrawledSource lang;

    public InfoboxDataProcessor(CrawledSource type) {
        this.lang = type;
    }

    public HashMap<String, String> getPersonData(String infoboxData) {
        HashMap<String, String> params = new HashMap<>();
        Matcher m = Pattern.compile("\\S+ = .+").matcher(infoboxData);
        String[] parts;
        while (m.find()) {
            parts = m.group().split(" = ");
            params.put(parts[0], parts[1].split(",")[0]);
        }
        return params;
    }


    public String prepareDate(String dateToProcess) {
        System.out.println("Processed date: " + dateToProcess);
        try {
            Matcher m = Pattern.compile("\\d+\\|\\d+\\|\\d+").matcher(dateToProcess);
            m.find();
            String date = m.group();
            date = date.replace('|', '-');
            return date;
        } catch (IllegalStateException e) {
            // Gotcha!
            System.err.println("Incorrect date argument in processor");
        } catch (NullPointerException e){
            // Gotcha!
            System.err.println("None date argument in infobox");
        }
        return null;
    }


    public ArrayList<String> getListOfRelations(String param) {
        System.out.println("Params to process: "+param);
        ArrayList<String> places = new ArrayList<String>();
        try {
            Matcher m = Pattern.compile("\\[\\[(.*?)\\]\\]").matcher(param);
            while (m.find()) {
                places.add(m.group(1).split("\\|")[0].split("#")[0]);
            }
        } catch (NullPointerException e) {
            // Gotcha!
            // this catch was discussed, it should stay here
            System.out.println("Empty parameter");
        }
        return places;
    }
}
