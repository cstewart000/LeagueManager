package com.hackingismakingisengineering.UI;

import java.util.Arrays;

/**
 * Created by helloworld on 24/07/2016.
 */
public class Menu {

    private String menuTitle;
    private Object[] options;
    private Enum<InputType> type;

    public Menu(String menuTitle, Object[] options, Enum<InputType> type) {
    //public Menu(String menuTitle, String[] options ) {
        this.menuTitle = menuTitle;
        this.options = options;
        this.type = type;
    }


    @Override
    public String toString() {
        String header=  "\n\n----"+menuTitle+"----\n"+
                "_______________________\n";

        String body = "";

        int index= 0;

        for(Object option:options){

            if(this.type==InputType.INDEX) {
                index++;
                body = body + index + ".- "+option.toString()+"\n";
            }else{
                body = body +option.toString()+"\n";
            }

        }

        String footer = "======================\n";

        return header + body +footer;

    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public Object[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

//    public Enum<InputType> getType() {
//        return type;
//    }
//
//    public void setType(Enum<InputType> type) {
//        this.type = type;
//    }
}
