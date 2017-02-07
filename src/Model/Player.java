/*
 * Author: Juan Luis Su�rez D�az
 * July, 2016
 * No More Dropbox MSN
 */
package Model;

/**
 *
 * @author Juan Luis
 */
public class Player {
    private String name;
    
    private String team;
    
    private String pos;
    
    private int goals;
    
    private int asists;
    
    private int encajados;
    
    private int yellowCards;
    
    private int redCards;
    
    private int points;

    public Player() {
    }

    public Player(String name, String team, String pos, int goals, int asists, int encajados, int yellowCards, int redCards, int points) {
        this.name = name;
        this.team = team;
        this.pos = pos;
        this.goals = goals;
        this.asists = asists;
        this.encajados = encajados;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.points = points;
    }
    
    public void set(String name, String team, String pos, int goals, int asists, int encajados, int yellowCards, int redCards, int points){
        this.name = name;
        this.team = team;
        this.pos = pos;
        this.goals = goals;
        this.asists = asists;
        this.encajados = encajados;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.points = points;
    }

    public int getAsists() {
        return asists;
    }

    public int getEncajados() {
        return encajados;
    }

    public int getGoals() {
        return goals;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public String getPos() {
        return pos;
    }

    public int getRedCards() {
        return redCards;
    }

    public String getTeam() {
        return team;
    }

    public int getYellowCards() {
        return yellowCards;
    }
    
    
}
