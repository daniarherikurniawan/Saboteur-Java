/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nim_13512080
 */

public abstract class Card {
    
    public abstract void displayCard();
    public abstract int getType(); //1=Path_Card, 2=Action_Card, 3=Character_Card
    public abstract int getID();
}
