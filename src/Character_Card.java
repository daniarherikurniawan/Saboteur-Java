/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Kelas Character_card menurukan kelas abstrak kartu
 * @author Ramandika
 */
public class Character_Card extends Card{
    private int id; //1=GoldMiner 2=Saboteur

     /**
     * Mengembalikan tipe character card
     * @param i
     */
    public Character_Card(int i){
        id = i;
    }
    
    /**
     * Method untuk mengeluakan tipe kartu
     * @return
     */
    @Override
    public int getType(){
        return 3;
    }
    
    /**
     * Method untuk mendapatkan id kartu
     * @return
     */
    @Override
    public int getID(){
        return id;
    }
    
    /**
     * Method untuk menampilkan kartu
     */
    @Override
    public void displayCard(){
        System.out.println("ini character card");
    }
    
}
