package pl.protka.presentation;

import pl.protka.db.DatabaseDriver;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gruszek on 24.05.15.
 */
public class PeopleList {
    static final DatabaseDriver dbdriver = DatabaseDriver.getInstance();
//    static final List<String> res = new ArrayList<>(dbdriver.getPeople().values());

    public String getDropdown(){
        String options = "";
        List<String> res = new ArrayList<>(dbdriver.getPeople().values());
        for(String name : res){
            options += "<option value=\""+name+"\">"+name+"</option>";
        }
        String select = "<select name=\"left\">"+options+"</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name=\"right\">"+options+"</select>";
        System.out.print("zrobione");
        return select;
    }
}
