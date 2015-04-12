/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nim_13512080
 */

class Path_Card extends Card {
    public static Path_Card NilCard = new Path_Card('0', '0', '0', '0', '0', 0);
    public static Path_Card StartCard = new Path_Card('1', '1', '1', '1', '1', 17);
    public static Path_Card GoldCard = new Path_Card('1', '1', '1', '1', 'G', 18);
    public static Path_Card RockCard = new Path_Card('1', '1', '1', '1', 'R', 19);
    private char top;
    private char right;
    private char bottom;
    private char left;
    private char center;
    private int id;
    private boolean approved;
    public Path_Card(char c, char c0, char c1, char c2, char c3, int i) {
        right = c;
        left = c0;
        bottom = c1;
        top = c2;
        center = c3;
        id = i;
    }
    
    public void setApproved(boolean bool){
        approved = bool;
    }
    
    public boolean getApproved(){
        return approved;
    }
    
    @Override
    public int getType(){
        return 1;
    }
    
    @Override
    public int getID(){
        return id;
    }
    
    @Override
    public void displayCard(){
        System.out.println("Ini pathcard");
    }
    
    public char getRight(){
        return right;
    }
    
    public char getLeft(){
        return left;
    }
    
    public char getTop(){
        return top;
    }
    
    public char getBottom(){
        return bottom;
    }
    
    public char getCenter(){
        return center;
    }
    
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
    
    public boolean CompareCard(Path_Card C){
        return ((this.top==C.getTop())&&(this.bottom==C.getBottom())&&(this.right==C.getRight())&&(this.left==C.getLeft())&&(this.center==C.getCenter())&&(this.id==C.getID()));
    }
    
    public void rotateCard(){
        char temp = right;
        right = left;
        left = temp;
        temp = top;
        top = bottom;
        bottom = temp;
    }
}

