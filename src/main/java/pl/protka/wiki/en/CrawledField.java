package pl.protka.wiki.en;

import pl.protka.db.CrawledSource;

import java.util.Arrays;
import java.util.List;


/**
 * Created by gruszek on 11.04.15.
 */

public enum CrawledField {
    INFOBOX(Arrays.asList("{{Infobox"), Arrays.asList("{{Biogram")),
    PERSON(Arrays.asList("{{Persondata"), Arrays.asList("{{Biogram")),
    FIELDS(Arrays.asList("field"), Arrays.asList("")),
    DEATH_DATE(Arrays.asList("death_date"), Arrays.asList("")),
    DEATH_PLACE(Arrays.asList("death_place"), Arrays.asList("")),
    BIRTH_DATE(Arrays.asList("birth_date"), Arrays.asList("")),
    BIRTH_PLACE(Arrays.asList("birth_place"), Arrays.asList("")),
    INSTITUTIONS(Arrays.asList("work_institutions", "alma_mater"), Arrays.asList("")),
    CITIES(Arrays.asList("birth_place", "death_place", "residence"), Arrays.asList("")),
    COUNTRIES(Arrays.asList("birth_place", "death_place", "residence"), Arrays.asList("")),
    INSTITUTIONS_TXT(Arrays.asList("Academy", "university"), Arrays.asList("")),
    CITIES_TXT(Arrays.asList("town", "city", "village", "state"), Arrays.asList("")),
    COUNTRIES_TXT(Arrays.asList("country"), Arrays.asList("")),
    RELATION(Arrays.asList("doctoral_advisor", "doctoral_students", "notable_students"), Arrays.asList(""));

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
