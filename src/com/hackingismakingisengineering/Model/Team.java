package com.hackingismakingisengineering.Model;

import com.hackingismakingisengineering.Exceptions.TeamFullException;

import java.util.ArrayList;

/**
 * Created by helloworld on 24/07/2016.
 */
public class Team implements Comparable<Team> {

    public static final int MAX_PLAYERS = 11;
    private String teamName;
    private String coachName;
    private ArrayList<Player> players = new ArrayList<>();

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }



    public Team(String teamName, String coachName) {
        this.teamName = teamName;
        this.coachName = coachName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    // TODO extra -   As an administrator, my team report should show an average experience level so that we can try
    // to manually balance fairness.
    public String getStatisticsReport() {


        String heightReport = Statistics.getHeightReport(players);
        String expirienceReport = Statistics.getExpirienceReport(players);

        return heightReport+expirienceReport;

    }

    public void addPlayer(Player selectedPlayer) throws TeamFullException {
        if(isFull()) {
            throw new TeamFullException();
        }else{
            players.add(selectedPlayer);
        }
    }

    @Override
    public String toString() {
        return "Team:\t" + teamName +
                "\tCoach:\t"+ coachName;
    }

    public boolean isFull() {
        if(players.size()>=MAX_PLAYERS){
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Team other) {
        return this.getTeamName().compareTo(other.toString());
    }


}
