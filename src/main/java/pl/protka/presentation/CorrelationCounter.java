package pl.protka.presentation;

import pl.protka.db.DatabaseDriver;

import java.util.List;

/**
 * Created by gruszek on 24.05.15.
 */

public class CorrelationCounter {
    private static final double RELATED_IN_TEXT = 0.1;
    private static final double TEXT_CITIES = 0.08;
    private static final double TEXT_COUNTRIES = 0.05;
    private static final double TEXT_UNIS = 0.12;
    private static final double BOX_CITIES = 0.2;
    private static final double BOX_COUNTRIES = 0.15;
    private static final double BOX_UNIS = 0.3;

    private static final double MAX_T_CI = 11;
    private static final double MAX_T_CO = 16;
    private static final double MAX_T_UN = 17;
    private static final double MAX_B_CI = 6;
    private static final double MAX_B_CO = 16;
    private static final double MAX_B_UN = 13;

    static final DatabaseDriver dbdriver = DatabaseDriver.getInstance();
    public double correlate(String right, String left) {
        double result = 0;

        if(dbdriver.isBoxRelation(right, left)){
            return 100;
        }

        List<String> leftTextCities = dbdriver.getCitiesTextForPerson(left);
        List<String> rightTextCities = dbdriver.getCitiesTextForPerson(right);

        List<String> leftTextCountries = dbdriver.getCountryTextForPerson(left);
        List<String> rightTextCountries = dbdriver.getCountryTextForPerson(right);

        List<String> leftTextUnis = dbdriver.getUniTextForPerson(left);
        List<String> rightTextUnis = dbdriver.getUniTextForPerson(right);

        List<String> leftBoxCities = dbdriver.getCitiesBoxForPerson(left);
        List<String> rightBoxCities = dbdriver.getCitiesBoxForPerson(right);

        List<String> leftBoxCountries = dbdriver.getCountryBoxForPerson(left);
        List<String> rightBoxCountries = dbdriver.getCountryBoxForPerson(right);

        List<String> leftBoxUnis = dbdriver.getUniBoxForPerson(left);
        List<String> rightBoxUnis = dbdriver.getUniBoxForPerson(right);

        leftTextCities.retainAll(rightTextCities);
        int cities_text = leftTextCities.size();
        
        leftTextCountries.retainAll(rightTextCountries);
        int countries_text = leftTextCountries.size();
        
        leftTextUnis.retainAll(rightTextUnis);
        int unis_text = leftTextUnis.size();

        leftBoxCities.retainAll(rightBoxCities);
        int cities_box = leftBoxCities.size();

        leftBoxCountries.retainAll(rightBoxCountries);
        int countries_box = leftBoxCountries.size();

        leftBoxUnis.retainAll(rightBoxUnis);
        int unis_box = leftBoxUnis.size();
        
        result = (TEXT_CITIES * (cities_text/MAX_T_CI)) + (TEXT_COUNTRIES * (countries_text/MAX_T_CO)) + (TEXT_UNIS * (unis_text/MAX_T_UN)) +
                (BOX_CITIES * (cities_box/MAX_B_CI)) + (BOX_COUNTRIES * (countries_box/MAX_B_CO)) + (BOX_UNIS * (unis_box/MAX_B_UN));

        if(dbdriver.isTextRelation(right, left)){
            result += RELATED_IN_TEXT;
        }
        
        return result*100;
    }
}
