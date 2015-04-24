package pl.protka.wiki.en;

import pl.protka.db.CrawledSource;

import java.util.Arrays;
import java.util.List;


/**
 * Created by gruszek on 11.04.15.
 */

public enum CrawledField {
    INFOBOX(Arrays.asList("{{Infobox"), Arrays.asList("{{Biogram")),
    NAME(Arrays.asList("name"), Arrays.asList("imię", "imie")),
    FIELDS(Arrays.asList("field"), Arrays.asList("imię", "imie")),
    DEATH_DATE(Arrays.asList("death_date"), Arrays.asList("data smierci")),
    DEATH_PLACE(Arrays.asList("death_date"), Arrays.asList("data smierci")),
    BIRTH_DATE(Arrays.asList("birth_date"), Arrays.asList("data urodzenia")),
    BIRTH_PLACE(Arrays.asList("birth_place"), Arrays.asList("miejsce urodzenia")),
    INSTITUTIONS(Arrays.asList("work_institutions", "alma_mater"), Arrays.asList("miejsce urodzenia")),
    CITIES(Arrays.asList("birth_place", "death_place", "residence"), Arrays.asList("miejsce urodzenia")),
    COUNTRIES(Arrays.asList("birth_place", "death_place", "residence"), Arrays.asList("miejsce urodzenia")),
    RELATION(Arrays.asList("doctoral_advisor", "doctoral_students", "notable_students"), Arrays.asList("miejsce urodzenia"));

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
