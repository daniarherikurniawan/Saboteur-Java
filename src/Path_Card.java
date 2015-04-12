/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Kelas yang menangani segala macam penggunaan PathCard
 * @author nim_13512080
 */


public class Path_Card extends Card {

    /**
     * Kartu kosong, untuk inisialisasi board di awal permainan
     */
    public static Path_Card NilCard = new Path_Card('0', '0', '0', '0', '0', 0);

    /**
     * Kartu start
     */
    public static Path_Card StartCard = new Path_Card('1', '1', '1', '1', '1', 17);

    /**
     * goalCard emas
     */
    public static Path_Card GoldCard = new Path_Card('1', '1', '1', '1', 'G', 18);

    /**
     * goalCard batu
     */
    public static Path_Card RockCard = new Path_Card('1', '1', '1', '1', 'R', 19);
    private char top;
    private char right;
    private char bottom;
    private char left;
    private char center;
    private int id;
    private boolean approved;

    /**
     * Konstruktor berparameter
     * @param c
     * @param c0
     * @param c1
     * @param c2
     * @param c3
     * @param i
     */
    public Path_Card(char c, char c0, char c1, char c2, char c3, int i) {
        right = c;
        left = c0;
        bottom = c1;
        top = c2;
        center = c3;
        id = i;
    }
    
    /**
     * Setter atribut approved
     * @param bool
     */
    public void setApproved(boolean bool){
        approved = bool;
    }
    
    /**
     * Getter atribut approved
     * @return boolean
     */
    public boolean getApproved(){
        return approved;
    }
    
    /**
     * Getter atribut Type
     * @return 1
     */
    @Override
    public int getType(){
        return 1;
    }
    
    /**
     * Getter atribut ID
     * @return id
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
        System.out.println("Ini pathcard");
    }
    
    /**
     * Getter atribut Right
     * @return right
     */
    public char getRight(){
        return right;
    }
    
    /**
     * Getter atribut Left
     * @return left
     */
    public char getLeft(){
        return left;
    }
    
    /**
     * Getter atribut Top
     * @return top
     */
    public char getTop(){
        return top;
    }
    
    /**
     * Getter atribut Bottom
     * @return bottom
     */
    public char getBottom(){
        return bottom;
    }
    
    /**
     * Getter atribut Center
     * @return center
     */
    public char getCenter(){
        return center;
    }
    
    /**
     * Method untuk memeriksa apakah kartu ini bisa diletakkan di kanan kartu C
     * @param C
     * @return boolean
     */
    public boolean canBePlacedRightOf(Path_Card C){
        if((this.left=='1' && C.getRight()=='1')||(this.left=='0' && C.getRight()=='0') ){
            if(this.left=='1' && C.getRight()=='1')
                approved = true;
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Method untuk memeriksa apakah kartu ini bisa diletakkan di kiri kartu C
     * @param C
     * @return boolean
     */
    public boolean canBePlacedLeftOf(Path_Card C){
        if((this.right=='1' && C.getLeft()=='1')||(this.right=='0' && C.getLeft()=='0') ){
            if(this.right=='1' && C.getLeft()=='1')
                approved = true;
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Method untuk memeriksa apakah kartu ini bisa diletakkan di atas kartu C
     * @param C
     * @return boolean
     */
    public boolean canBePlacedAboveOf(Path_Card C){
        if((this.bottom=='1' && C.getTop()=='1')||(this.bottom=='0' && C.getTop()=='0') ){
             if(this.bottom=='1' && C.getTop()=='1')
                approved = true;
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Method untuk memeriksa apakah kartu ini bisa diletakkan di bawah kartu C
     * @param C
     * @return boolean
     */
    public boolean canBePlacedBelowOf(Path_Card C){
        if((this.top=='1' && C.getBottom()=='1')||(this.top=='0' && C.getBottom()=='0') ){
            if(this.top=='1' && C.getBottom()=='1')
                approved = true;
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Membandingkan dua kartu, apakah sama atau tidak
     * @param C
     * @return boolean
     */
    public boolean CompareCard(Path_Card C){
        return ((this.top==C.getTop())&&(this.bottom==C.getBottom())&&(this.right==C.getRight())&&(this.left==C.getLeft())&&(this.center==C.getCenter())&&(this.id==C.getID()));
    }
    
    /**
     * Method untuk memutar kartu sebesar 180 derajat
     */
    public void rotateCard(){
        char temp = right;
        right = left;
        left = temp;
        temp = top;
        top = bottom;
        bottom = temp;
    }
}

