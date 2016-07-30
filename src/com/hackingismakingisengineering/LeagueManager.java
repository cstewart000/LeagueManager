package com.hackingismakingisengineering;

import com.hackingismakingisengineering.Exceptions.MaxTeamsAllowedExceeded;
import com.hackingismakingisengineering.Exceptions.TeamFullException;
import com.hackingismakingisengineering.Model.League;
import com.hackingismakingisengineering.Model.Player;
import com.hackingismakingisengineering.Model.Team;
import com.hackingismakingisengineering.UI.InputType;
import com.hackingismakingisengineering.UI.Menu;
import com.hackingismakingisengineering.UI.Prompter;
import com.sun.tools.doclets.internal.toolkit.util.DocFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class LeagueManager{

    public static void main(String[] args) {

        // DONE (CS) core - As an organizer, I should be presented with a menu item that allows me to create a new team, so that I can build the season.
        showRootMenu();

        // DONE (CS) core - As an organizer adding or removing a player to a chosen team, I should be prompted with an alphabetically ordered list of players along with their stats, so that I can quickly locate the player and take action.
        // DONE (CS) - As an organizer planning teams, I should be able to view a report of a chosen team grouped by height, so that I can determine if teams are fair.

        // DONE (CS) - As a coach of a team, I should be able to print out a roster of all the players on my team, so that I can plan appropriately.

        // DONE (CS) - As an administrator, I shouldn't be able to add more teams than there are available players so
            // that teams can be arranged appropriately.
        // DONE (CS) -   As an administrator, I should only have the option of adding players that are not on existing
            // teams, so that I don't accidentally put the player on two teams.

    }

    private static void showRootMenu() {
        Menu rootMenu = new Menu(
                                "Main menu",
                                new String[]{
                                        "c - create team",
                                        "g - generate teams",
                                        "fr - fill teams (randomly)",
                                        "ff - fill teams (fairly)",
                                        "s - select team",
                                        "l - league balance report"},
                                InputType.CHAR_OPTION);

        String response = Prompter.printForString(rootMenu.toString());

        switch (response){
            case "c":
                showCreateTeamInstructions();
                showRootMenu();
                break;
            case "g":
                try {
                    generateTeams();
                } catch (MaxTeamsAllowedExceeded maxTeamsAllowedExceeded) {
                    maxTeamsAllowedExceeded.printStackTrace();
                }
                showRootMenu();
                break;
            case "fr":
                fillTeamsRandomly();
                showRootMenu();
                break;
            case "ff":
                fillTeamsFairly();
                showRootMenu();
                break;
            case "s":
                // DONE (CS) -   As an organizer adding or removing a player to a yet to be chosen team, I should be
                // prompted with an alphabetically ordered list of teams to choose from, so that I can quickly locate the team and avoid typos.
                int selectedTeam = Prompter.printForInt(showTeams());
                showTeamMenu(League.getTeams().get(selectedTeam));
                //showRootMenu();
                break;
            case "l":
                Prompter.print(League.getReport());
                showRootMenu();
                break;
        }
    }

    private static void showTeamMenu(Team team) {

        Menu teamMenu = new Menu(
                team.getTeamName()+"Team Menu",
                new String[]{
                        "a - add player",
                        //"l - list available players",
                        "t - list players in team",
                        "m - main menu",
                        "s - team stats",
                        "r - remove player"},
                InputType.CHAR_OPTION);

        String response = Prompter.printForString(teamMenu.toString());

        switch (response) {
            // DONE (CS) core - As an organizer, I should be presented with a menu item that allows me to add players to a
            // team, so that I can build fair teams.
            case "a":
                int selectedPlayerIndex = Prompter.printForInt(listAvailiblePlayers());

                try {
                    Player selectedPlayer = League.getAvailablePlayers().remove(selectedPlayerIndex);
                    team.addPlayer(selectedPlayer);
                } catch (TeamFullException e) {
                    e.printStackTrace();
                }
                showTeamMenu(team);
                break;
            //case "l":
            //    break;
            case "t":
                Prompter.print(listTeamPlayers(team));
                showTeamMenu(team);
                break;
            case "m":
                showRootMenu();
                break;
            case "s":
                Prompter.print(team.getStatisticsReport());
                showTeamMenu(team);
                break;

            // TODO core -   As an organizer, I should be presented with a menu item that allows me to remove players
            // from a team, so that I can attempt to produce more fair teams.
            case "r":
                int selectedPlayer2 = Prompter.printForInt(listTeamPlayers(team));
                League.getAvailablePlayers().add(team.getPlayers().remove(selectedPlayer2));
                showTeamMenu(team);
                break;
        }
    }

    private static String showTeams() {

        // TODO: Clean up this mess!!!!
        List<String> teamsAsArray= new ArrayList<>();

        for(Team team: League.getTeams()){
            teamsAsArray.add(team.getTeamName());
        }

        String[] teamStringArray = new String[teamsAsArray.size()];

        for(int i=0; i<teamsAsArray.size();i++){
            teamStringArray[i]=teamsAsArray.get(i);
        }

        Menu teamList = new Menu("Teams", teamStringArray, InputType.INDEX);
        return teamList.toString();
    }

    private static String listTeamPlayers(Team team) {

        ArrayList<Player> teamPlayers = team.getPlayers();
        ArrayList<String> teamPlayersAsString = new ArrayList<>();

        for(Player player: teamPlayers){
            teamPlayersAsString.add(player.toString());
        }

        Menu playerList = new Menu("Players", teamPlayersAsString.toArray(), InputType.INDEX);
        return playerList.toString();
    }

    private static String listAvailiblePlayers() {

        League.sortLeague();
        ArrayList<Player> availablePlayers = League.getAvailablePlayers();
        ArrayList<String> availablePlayersAsString = new ArrayList<>();

        for(Player player: availablePlayers){
            availablePlayersAsString.add(player.toString());
        }
        Menu availablePlayerList = new Menu("Players", availablePlayersAsString.toArray(), InputType.INDEX);

        return availablePlayerList.toString();
    }

    private static void fillTeamsFairly() {
        League.fillTeamsFairly();
    }

    private static void fillTeamsRandomly() {
        League.fillTeamsRandomly();
    }

    private static void generateTeams() throws MaxTeamsAllowedExceeded {

        Team team1 = new Team("Scotland","Scott Scottington");
        Team team2 = new Team("Machester","John Smithington");
        Team team3 = new Team("Real Madrid","Pablo Perez");

        League.addTeam(team1);
        League.addTeam(team2);
        League.addTeam(team3);
    }

    private static void showCreateTeamInstructions() {
        // DONE (CS) core -   Required fields are team name and coach.
        String teamName = Prompter.printForString("Enter team name:\t");
        String coachName = Prompter.printForString("Enter coach name:\t");

        Team team = new Team(teamName,coachName);
        try {
            League.addTeam(team);
        } catch (MaxTeamsAllowedExceeded maxTeamsAllowedExceeded) {
            maxTeamsAllowedExceeded.printStackTrace();
        }
    }
}
