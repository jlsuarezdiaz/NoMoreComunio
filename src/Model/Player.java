/*
 * Author: Juan Luis Su�rez D�az
 * July, 2016
 * No More Dropbox MSN
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author Juan Luis
 */
public class Player implements Serializable{
    private int code;
    
    private String name;
    
    private String team;
    
    private String pos;
    
    private String vendedor;
    
    private int precioMin;
    
    private int valor;
    
    private int goals;
    
    private int asists;
    
    private int encajados;
    
    private int yellowCards;
    
    private int redCards;
    
    private int points;

    public Player() {
    }

    public Player(int code, String name, String team, String pos, String vendedor, int precioMin, int valor, int goals, int asists, int encajados, int yellowCards, int redCards, int points) {
        this.code = code;
        this.name = name;
        this.team = team;
        this.pos = pos;
        this.vendedor = vendedor;
        this.precioMin = precioMin;
        this.valor = valor;
        this.goals = goals;
        this.asists = asists;
        this.encajados = encajados;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.points = points;
    }
    
    public void set(int code, String name, String team, String pos, String vendedor, int precioMin, int valor, int goals, int asists, int encajados, int yellowCards, int redCards, int points){
        this.code = code;
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

    public String getVendedor() {
        return vendedor;
    }

    public int getValor() {
        return valor;
    }

    public int getPrecioMin() {
        return precioMin;
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
    
    public int getCode(){
        return code;
    }
}
