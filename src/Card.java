/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
  *Realisasi kelas abstract Card yang akan diimplementasi oleh
  *1=Path_card
  *2=Action_card
   *3-Character_card
 */

public abstract class Card {
    
    /**
     * method menampilkan kartu
     */
    public abstract void displayCard();

    /**
     * Mendapatkan tipe kartu
     * @return 1=Path_Card, 2=Action_Card, 3=Character_Card
     */
    public abstract int getType(); 
    
    /**
     * mendapatkan id kartu
     * @return
     */
    public abstract int getID();
}
