package pl.protka.presentation;

import pl.protka.db.DatabaseDriver;

import java.util.List;

/**
 * Created by gruszek on 24.05.15.
 */

public class CorrelationCounter {
    private static final int RELATED_IN_TEXT = 0;

    static final DatabaseDriver dbdriver = DatabaseDriver.getInstance();
    public int correlate(String right, String left) {
        int result = 0;
//
//        if(dbdriver.isBoxRelation(right, left)){
//            return 100;
//        }
//        if(dbdriver.isTextRelation(right, left)){
//            result += RELATED_IN_TEXT;
//        }
//
//        List<String> leftTextCities = dbdriver.getCitiesTextForPerson(left);
//        List<String> rightTextCities = dbdriver.getCitiesTextForPerson(right);
//
//        List<String> leftTextCountries = dbdriver.getCountryTextForPerson(left);
//        List<String> rightTextCountries = dbdriver.getCountryTextForPerson(right);
//
//        List<String> leftTextUnis = dbdriver.getUniTextForPerson(left);
//        List<String> rightTextUnis = dbdriver.getUniTextForPerson(right);
//
//        List<String> leftBoxCities = dbdriver.getCitiesBoxForPerson(left);
//        List<String> rightBoxCities = dbdriver.getCitiesBoxForPerson(right);
//
//        List<String> leftBoxCountries = dbdriver.getCountryBoxForPerson(left);
//        List<String> rightBoxCountries = dbdriver.getCountryBoxForPerson(right);
//
//        List<String> leftBoxUnis = dbdriver.getUniBoxForPerson(left);
//        List<String> rightBoxUnis = dbdriver.getUniBoxForPerson(right);


        return result;
    }
}
