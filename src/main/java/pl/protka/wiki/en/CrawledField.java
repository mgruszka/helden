package pl.protka.wiki.en;

import pl.protka.db.CrawledSource;

import java.util.Arrays;
import java.util.List;


/**
 * Created by gruszek on 11.04.15.
 */

public enum CrawledField {
    NAME(Arrays.asList("name"), Arrays.asList("imię", "imie")),
    BIRTH_DATE(Arrays.asList("birth date"), Arrays.asList("data urodzenia")),
    BIRTH_PLACE(Arrays.asList("birth place"), Arrays.asList("miejsce urodzenia"));

    private final List<String> eng;
    private final List<String> pl;

    CrawledField(List<String> eng, List<String> pl) {
        this.eng = eng;
        this.pl = pl;
    }

    public List<String> get(CrawledSource type) {
        switch (type) {
            case WIKIENG:
                return getEng();
            case WIKIPL:
                return getPl();
            default:
                return null;
        }

    }

    public List<String> getEng() {
        return eng;
    }

    public List<String> getPl() {
        return pl;
    }
}
