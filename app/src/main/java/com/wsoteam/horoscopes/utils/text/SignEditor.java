package com.wsoteam.horoscopes.utils.text;

public class SignEditor {

    public static String editText(String text){
        String dropSpaces = text.replaceAll("[,.!?;:]", "$0 ").replaceAll("\\s+", " ");
        dropSpaces = dropSpaces.replace("Standout days", " \n\nStandout days");
        dropSpaces = dropSpaces.replace("Challenging days", " \nChallenging days");
        return dropSpaces;
    }
}
