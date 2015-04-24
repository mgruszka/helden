package pl.protka.wiki.en;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by gruszek on 24.04.15.
 */
public class Person {
    String name = "";
    String birthDate = "";
    String deathDate = "";
    String birthPlace = "";
    String deathPlace = "";
    String fields = "";

    Set<String> citiesInf = new LinkedHashSet<>();
    Set<String> citiesTxt = new LinkedHashSet<>();
    Set<String> countriesInf = new LinkedHashSet<>();
    Set<String> countriesTxt = new LinkedHashSet<>();
    Set<String> institutionsInf = new LinkedHashSet<>();
    Set<String> institutionsTxt = new LinkedHashSet<>();
    Set<String> relatedPeopleInf = new LinkedHashSet<>();
    Set<String> relatedPeopleTxt = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", deathDate='" + deathDate + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", deathPlace='" + deathPlace + '\'' +
                ", fields='" + fields + '\'' +
                ", citiesInf=" + citiesInf +
                ", citiesTxt=" + citiesTxt +
                ", countriesInf=" + countriesInf +
                ", countriesTxt=" + countriesTxt +
                ", institutionsInf=" + institutionsInf +
                ", institutionsTxt=" + institutionsTxt +
                ", relatedPeopleInf=" + relatedPeopleInf +
                ", relatedPeopleTxt=" + relatedPeopleTxt +
                '}';
    }
}
