package com.hackingismakingisengineering.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by helloworld on 24/07/2016.
 */
public class Statistics {
    public static String getHeightReport(ArrayList<Player> players) {

        HashMap<Integer, Integer> heightReportMap = new HashMap<>();

        for(Player player: players){
            if(heightReportMap.containsKey(player.getHeightInInches())){
                int numPlyers = heightReportMap.get(player.getHeightInInches());

                heightReportMap.replace(player.getHeightInInches(),numPlyers++);
            }else{
                heightReportMap.put(player.getHeightInInches(),1);
            }

        }

        return "Heights:\t" + heightReportMap.toString() +"\n";
    }

    public static String getExpirienceReport(ArrayList<Player> players) {

        int numExp = getNumExpirienced(players);

        return "expirienced players:\t"+numExp+"\n"+
                "inexpirienced players:\t"+(players.size()-numExp)+"\n"+
                "total players:\t"+players.size()+"\n"+
                "fraction:\t"+(float)numExp/(players.size())+"\n";

    }

    public static int getNumExpirienced(ArrayList<Player> players) {

        int numExp = 0;

        for(Player player: players){
            if(player.isPreviousExperience()){
                numExp++;
            }
        }
        return numExp;
    }

    public static int getNumInExpirienced(ArrayList<Player> players) {
        int numInExp = players.size()-getNumExpirienced(players);
        return numInExp;
    }

    public static float getFractionExpirienced(ArrayList<Player> players) {
        float fraction = (float) getNumExpirienced(players)/players.size();
        return fraction;
    }

}
