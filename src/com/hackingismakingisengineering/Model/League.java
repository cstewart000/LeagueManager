package com.hackingismakingisengineering.Model;

import com.hackingismakingisengineering.Exceptions.MaxTeamsAllowedExceeded;
import com.hackingismakingisengineering.Exceptions.TeamFullException;
import com.hackingismakingisengineering.UI.Prompter;

import java.util.*;

/**
 * Created by helloworld on 24/07/2016.
 */
public class League {

    private static Player[] players = Players.load();
    private static TreeSet<Player> playersTreeSet;

    private static int numAvailablePlayers;
    private static int numPlayers;
    private static int expiriencedPlayers;
    private static List<Team> teams = new ArrayList<>();
    private static ArrayList<Player> availablePlayers= new ArrayList<Player>(Arrays.asList(players));


    public static final int MAX_TEAMS = (int) availablePlayers.size()/Team.MAX_PLAYERS;

    public static List<Team> getTeams() {
        Collections.sort(teams);
        return teams;
    }

    public static ArrayList<Player> getAvailablePlayers() {
        Collections.sort(availablePlayers);
        return availablePlayers;
    }

    public static void addTeam(Team team) throws MaxTeamsAllowedExceeded {
        if(teams.size()>=MAX_TEAMS){
            throw new MaxTeamsAllowedExceeded();
        }else{
            teams.add(team);
        }
    }

    public static void fillTeamsRandomly() {
        for(Team team:teams) {
            while (!team.isFull()) {
                int index = getIndexOfRandomPlayer();
                try {
                    team.addPlayer(availablePlayers.remove(index));
                } catch (TeamFullException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void sortLeague() {
        Collections.sort(availablePlayers);
    }

    public static void fillTeamsFairly() {

        int maxExpPlayerPerTeam = (int) (Statistics.getFractionExpirienced(availablePlayers)*Team.MAX_PLAYERS);


        // Add expirienced players first
        for(Team team:teams) {

            while (!team.isFull()) {


                if (Statistics.getNumExpirienced(availablePlayers) != 0) {
                    int teamExpPlayers = Statistics.getNumExpirienced(team.getPlayers());


                    int index = getIndexOfRandomExpPlayer();

                    // Check expirience
                    if (availablePlayers.get(index).isPreviousExperience() && (teamExpPlayers < maxExpPlayerPerTeam)) {
                        addPlayerToTeamByIndex(team, index);
                    }

                    if (teamExpPlayers == maxExpPlayerPerTeam) {
                        break;
                    }
                }
                else{
                    break;
                }
            }
        }


                    // Add inexpirienced players after
                    for(Team team:teams) {

                        while (!team.isFull() && availablePlayers.size()!=0) {

                            int index = getIndexOfRandomPlayer();
                            addPlayerToTeamByIndex(team, index);
                        }
                    }



    }

    private static int getIndexOfRandomExpPlayer() {
        int rndm = getIndexOfRandomPlayer();
        while(availablePlayers.get(rndm).isPreviousExperience()!=true){
            if(rndm<availablePlayers.size()-1){
                rndm++;
            }else{
                rndm = 0;
            }
        }

        return rndm;
    }

    private static void addPlayerToTeamByIndex(Team team, int index) {
        try {
            team.addPlayer(availablePlayers.get(index));
        } catch (TeamFullException e) {
            e.printStackTrace();
        }
        availablePlayers.remove(index);
    }

    private static int getIndexOfRandomPlayer() {
        Random random = new Random();
        return random.nextInt(availablePlayers.size());
    }


    // DONE (cs) core -  As an organizer who is planning teams, I should be able to see a League Balance Report for all teams
    // in the league showing a total count of experienced players vs. inexperienced players, so I can determine from a
    // high level if the teams are fairly balanced regarding previous experience.

    // DONE (cs) extra - As an administrator who is planning teams, the League Balance Report should also include a count of
    // how many players of each height are on each team.
    public static String getReport() {

        String report="";
        for(Team team: teams){
            report += "\n"+team.toString() +"\n"
                    + team.getStatisticsReport();
        }

        return report;
    }
}
