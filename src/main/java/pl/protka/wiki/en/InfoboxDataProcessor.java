package pl.protka.wiki.en;

import info.bliki.api.Page;
import info.bliki.api.User;
import pl.protka.db.CrawledSource;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pl.protka.wiki.en.CrawledField.INFOBOX;

/**
 * Created by gruszek on 24.04.15.
 */

public class InfoboxDataProcessor {

    private final CrawledSource lang;

    public InfoboxDataProcessor(CrawledSource type) {
        this.lang = type;
    }

    public String getInfoboxData(Page page) {
        String s = page.toString();
        try {
            List<String> options = INFOBOX.get(lang);
            s = s.substring(s.indexOf(options.get(0)));
            s = s.substring(0, s.indexOf("\n}}") + 3).replaceAll("  *", " ").replaceAll("\n\\|", "\n");
//            System.out.println(s + "\n\n\n");
            return s;
        }catch(StringIndexOutOfBoundsException e){
            // Gotcha!
            System.err.println("Incorrect page");
            return null;
        }
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


    public String prepareDate(String dateToProcess, String result) {
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
        if(result != ""){
            return result;
        }
        return dateToProcess;
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

    public String getFieldContent(String key, String result) {
        if(result != "" || key==null){
            return result;
        }
        key = key.replace("[", "");
        key = key.replace("]", "");
        return key;
    }

    public Set<String> getFieldContentList(String s) {
        Set<String> list = new LinkedHashSet<>();

        try {
            Matcher m = Pattern.compile("\\[\\[(.*?)\\]\\]").matcher(s);
            while (m.find()) {
                list.add(m.group(1).split("\\|")[0].split("#")[0]);
            }
        } catch (NullPointerException e) {
            // Gotcha!
            // this catch was discussed, it should stay here
            System.out.println("Empty parameter");
        }
        return list;
    }
}
