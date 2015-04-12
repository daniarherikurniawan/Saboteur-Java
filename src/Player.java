
import java.util.Vector;

/**
 * Kelas Player; 10 atribut; 23 methods
 * @author icha
 */
public class Player {
    private Character_Card Role;
    private String Status = new String("Undefined");

    /**
     * Vector untuk menampung semua kartu yang ada di tangan seorang Player
     */
    public  Vector<Card> CardsOnHand = new Vector<Card>();
    private int Turn;
    private int Score;
    private String Name = new String("Undefined");
    private String RegisteredDate = new String();
    private boolean finishedTurn ;
    private boolean finishedDraw ;
    private int idxRemovedCard;

    /**
     * Konstruktor tanpa parameter untuk kelas Player
     */
    public Player(){
        
    }
    
    /**
     * Konstruktor kelas Player dengan parameter String yang merupakan nama Player baru
     * @param playerName
     */
    public Player(String playerName){
    	Name = playerName;
        finishedTurn = false;
        finishedDraw = false;
        Score = 0;
        Status = "Enable";
        idxRemovedCard = -1;
    }
    
    /**
     * Getter atribut Score
     * @return Score
     */
    public int getScore(){
    	return Score;
    }
    
    /**
     * Method untuk mengatur atribut Status Player
     * @param S
     */
    public void setStatus(String S)
    {
        Status = S;
    }
    
    /** 
     * Method untuk menampilkan status seorang Player; menampilkan Role dan apakah Player tsb sudah menggunakan kartu atau men-Draw kartu
     */
    public void showStatus(){
        System.out.println("Hai, "+Name);
        if(Role.getID()==1)
            System.out.println("Anda adalah seorang GoldMiner");
        else
            System.out.println("Anda adalah seorang Saboteur");
        if(finishedTurn)
            System.out.println("Anda belum mendraw kartu");
        else
            System.out.println("Anda belum menggunakan satu kartu pun");
    }
    
   /**
     * Method untuk mengatur atribut Turn seorang Player
     * @param t
     */
    public void setTurn(int t){
        Turn = t;
    }
    
    /**
     * getter atribut Turn
     * @return Turn
     */
    public int getTurn(){
        return Turn;
    }
    
    /**
     * getter atribut finishedTurn
     * @return boolean
     */
    public boolean getFinishedTurn(){
        return finishedTurn;
    }
        
    /**
     * setter atribut finishedTurn
     * @param myBool
     */
    public void setFinishedTurn(boolean myBool){
        finishedTurn = myBool;
    }

    /**
     * getter atribut finishedDraw
     * @return boolean
     */
    public boolean getFinishedDraw(){
        return finishedDraw;
    }
        
    /**
     * setter atribut finishedDraw
     * @param myBool
     */
    public void setFinishedDraw(boolean myBool){
        finishedDraw = myBool;
    }
    
    /**
     * setter atribut Score
     * @param s
     */
    public void setScore(int s){
        Score = s;
    }
    
    /**
     * setter atribut Date
     * @param d
     */
    public void setDate(String d){
    	RegisteredDate = d;        
    }
    
    /**
     * getter atribut Date
     * @return string
     */
    public String getDate(){
        return RegisteredDate.toString();        
    }
    
    /**
     * getter atribut Name
     * @return name
     */
    public String getPlayerName(){
    	return Name;
    }
    
    /**
     * getter jumlah kartu yang dimiliki seorang Player
     * @return nCard
     */
    public int getNCardonHand(){
        return CardsOnHand.size();
    }
    
     /**
     * setter untuk atribut Role
     * @param id
     */
    public void setRole(int id){
    	Role = new Character_Card(id);
    }
    
    /**
     * getter id Role
     * @return idrole
     */
    public int getRoleId(){
    	return Role.getID();
    }
    
    /**
     * Method yang menangani proses draw card yang dilakukan oleh Player
     * @param c
     */
    public void drawCard(Card c)
    {
        if(idxRemovedCard == -1)
            CardsOnHand.add(c);
        else{
            CardsOnHand.add(idxRemovedCard, c);
        }
    }
    
   /**
     * Setter atribut idxRemovedCard
     * @param Idx
     */
    public void setIdxRemovedCard(int Idx){
        idxRemovedCard = Idx;
    }
    
    /**
     * method yang menangani proses discard card yang dilakukan oleh Player
     * @param index
     * @return int
     */
    public Card disCard(int index){
        idxRemovedCard = index;
        return CardsOnHand.remove(index);
    }
    
    /**
     * getter atribut Status
     * @return status
     */
    public String getStatus(){
        return Status;
    }
    
    /**
     * Method untuk mencetak semua kartu yang dipegang oleh Player
     */
    public void printCardsOnHand(){     // Mencetak semua kartu di tangan
        String lineu = "\u2594";
	String linel = "\u258F";
	String liner = "\u2595";
	String lined = "\u2581";
	String block = "\u2588";
        System.out.print("\n  ");
        for (int i=0; i<CardsOnHand.size(); i++){
            System.out.print("   " + i + "    ");
        }
        System.out.println();
        System.out.print("  ");
        for (int i=0; i<CardsOnHand.size(); i++){
            System.out.print(liner + lineu + lineu);
            if (CardsOnHand.get(i).getType() == 1){
                if (((Path_Card) CardsOnHand.get(i)).getTop() == '1') {
                    System.out.print(block + block);
                } else {
                    System.out.print(lineu + lineu);
                } 
                System.out.print(lineu + lineu + linel);
            } else {
                System.out.print(lineu + lineu);
                System.out.print(lineu + lineu + linel);
            }
        }
        System.out.println();
        System.out.print("  ");
        for (int i=0; i<CardsOnHand.size(); i++){
            if (CardsOnHand.get(i).getType() == 1){
                System.out.print(liner + "  ");
                if (((Path_Card) CardsOnHand.get(i)).getTop() == '1') {
                    System.out.print(block + block);
                } else {
                    System.out.print("  ");
                } 
                System.out.print("  " + linel);
            } else {
                if (((Action_Card) CardsOnHand.get(i)).getID() == 1){
                    System.out.print(liner + " ");
                    System.out.print("VIEW");
                    System.out.print(" " + linel);
                } else if (((Action_Card) CardsOnHand.get(i)).getID() == 2){
                    System.out.print(liner + "");
                    System.out.print("BREAK");
                    System.out.print(" " + linel);
                } else {
                    System.out.print(liner + "");
                    System.out.print("REPAIR");
                    System.out.print("" + linel);
                }
            }
        }
        System.out.println();
        System.out.print("  ");
        for (int i=0; i<CardsOnHand.size(); i++){
            if (CardsOnHand.get(i).getType() == 1){
                if(((Path_Card) CardsOnHand.get(i)).getLeft() == '1'){
                    if(((Path_Card) CardsOnHand.get(i)).getCenter() == '1'){
                        if (((Path_Card) CardsOnHand.get(i)).getRight() == '1'){
                            System.out.print(liner + block + block + block + block + block + block + linel);
                        } else {
                            System.out.print(liner + block + block + block + block + "  " + linel);
                        }
                    } else {
                        if (((Path_Card) CardsOnHand.get(i)).getRight() == '1'){
                            System.out.print(liner + block + block + "  " + block + block + linel);
                        } else {
                            System.out.print(liner + block + block + "    " + linel);
                        }
                    }
                } else {
                    if(((Path_Card) CardsOnHand.get(i)).getCenter() == '1'){
                        if (((Path_Card) CardsOnHand.get(i)).getRight() == '1'){
                            System.out.print(liner + "  " + block + block + block + block + linel);
                        } else {
                            System.out.print(liner + "  " + block + block + "  " + linel);
                        }
                    } else {
                        if (((Path_Card) CardsOnHand.get(i)).getRight() == '1') {
                            System.out.print(liner + "    " + block + block + linel);
                        } else {
                            System.out.print(liner + "      " + linel);
                        }
                    }
                }    
            } else {
                if (((Action_Card) CardsOnHand.get(i)).getID() == 1){
                    System.out.print(liner + " ");
                    System.out.print("MAP");
                    System.out.print("  " + linel);
                } else if (((Action_Card) CardsOnHand.get(i)).getID() == 2){
                    System.out.print(liner + " ");
                    System.out.print("TOOL");
                    System.out.print(" " + linel);
                } else {
                    System.out.print(liner + " ");
                    System.out.print("TOOL");
                    System.out.print(" " + linel);
                }
            }
        }
        System.out.println();
        System.out.print("  ");
        for (int i=0; i<CardsOnHand.size(); i++){
            if (CardsOnHand.get(i).getType() == 1){
                System.out.print(liner + "  "); 
                if (((Path_Card) CardsOnHand.get(i)).getBottom() == '1') {
                    System.out.print(block + block);
                } else {
                    System.out.print("  ");
                } 
                System.out.print("  " + linel);
            } else {
                System.out.print(liner + "  "); 
                System.out.print("  ");
                System.out.print("  " + linel);
            }
        }
        System.out.println();
        System.out.print("  ");
        for (int i=0; i<CardsOnHand.size(); i++){
            if (CardsOnHand.get(i).getType() == 1){
                System.out.print(liner + lined + lined);
                if (((Path_Card) CardsOnHand.get(i)).getBottom() == '1') {
                    System.out.print(block + block);
                } else {
                    System.out.print(lined + lined);
                }
                System.out.print(lined + lined + linel);
            } else {
                System.out.print(liner + lined + lined);
                System.out.print(lined + lined);
                System.out.print(lined + lined + linel);
            }
        }
        System.out.println();
    }
}
