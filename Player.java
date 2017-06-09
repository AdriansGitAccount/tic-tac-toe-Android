package mad.game.app.playerapp6;

import java.util.ArrayList;

/**
 * Created by adrian on 07/02/2017.
 */

public class Player {

    private int id =0;
    private String name;
    private int wins;
    private int losses;
    private int ties;

    private ArrayList<Player> allPlayers;


    public Player(){    }

    public Player(int id, String name, int wins, int losses, int ties) {
        this.id = id;
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWin(int win) {
        this.wins = win;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayers(ArrayList<Player> players){ allPlayers = players;}
    public Player getPlayer(int index) {
        return allPlayers.get(index);
    }


    public String toString(){
        return "Player Id "+ getId() + " Name: " + getName() +" | Wins: "+ getWins() +" | Losses:  "+ getLosses() +" | Ties: "+ getTies();
    }
}
