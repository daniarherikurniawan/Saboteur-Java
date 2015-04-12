/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nim_13512080
 */
public class Character_Card extends Card{
    private int id; //1=GoldMiner 2=Saboteur
    public Character_Card(int i){
        id = i;
    }
    
    @Override
    public int getType(){
        return 3;
    }
    
    @Override
    public int getID(){
        return id;
    }
    
    @Override
    public void displayCard(){
        System.out.println("ini character card");
    }
    
}
