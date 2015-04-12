
import java.util.Collections;
import java.util.Stack;


/**
 * Realisasi dan implementasi kelas Deck 
 * @author Ramandika
 */
public class Deck {
    private Stack<Card> PileOfCard;
    public int NumberOfAvailableCard;
    private Card [][] aCard = new Card[5][9];
    
    /**
     * constructor kelas desk
     */
    public Deck(){
        PileOfCard = new Stack<Card>();
        NumberOfAvailableCard = 0;
    }

    /**
     * Method untuk mengisi deck dengan kartu dengan susunan random (dilakukan pada saat awal permainan)
     */
    public void fillDeck(){
        PileOfCard = new Stack<Card>();
        
        for (int i=1; i<=4; i++){
        	PileOfCard.addElement(new Path_Card('0', '0', '1', '1', '1', 2));
        	PileOfCard.addElement(new Path_Card('0', '1', '0', '1', '1', 4));
    	}
        for (int i=1; i<=5; i++){
        	PileOfCard.addElement(new Path_Card('0', '1', '1', '1', '1', 6));
        	PileOfCard.addElement(new Path_Card('1', '1', '1', '1', '1', 1));
            PileOfCard.addElement(new Path_Card('0', '1', '1', '0', '1', 5));
            PileOfCard.addElement(new Path_Card('1', '1', '1', '0', '1', 7));
        }
        for (int i=1; i<=3; i++)
        	PileOfCard.addElement(new Path_Card('1', '1', '0', '0', '1', 3));
        for (int i=1; i<=6; i++){
	        PileOfCard.addElement(new Action_Card(1));
        	PileOfCard.addElement(new Action_Card(3));
        }
        for (int i=1; i<=9; i++)
        	PileOfCard.addElement(new Action_Card(2));
        
        PileOfCard.addElement(new Path_Card('1', '1', '0', '0', '0', 11));
        PileOfCard.addElement(new Path_Card('0', '0', '1', '1', '0', 10));
        PileOfCard.addElement(new Path_Card('1', '1', '1', '0', '0', 15));
        PileOfCard.addElement(new Path_Card('0', '0', '0', '1', '1', 8));
        PileOfCard.addElement(new Path_Card('0', '0', '1', '1', '0', 14));
        PileOfCard.addElement(new Path_Card('1', '1', '1', '1', '0', 16));
        PileOfCard.addElement(new Path_Card('0', '1', '0', '1', '0', 13));
        PileOfCard.addElement(new Path_Card('0', '1', '1', '0', '0', 12));
        PileOfCard.addElement(new Path_Card('1', '0', '0', '0', '1', 9));
        
        Collections.shuffle(PileOfCard);
        NumberOfAvailableCard = 60;
    }
    
    /**
     * Mengambil kartu dari deck
     * @return Card
     */
    public Card popCard(){
        Card result = PileOfCard.pop();
        NumberOfAvailableCard--;
        return result;
    }
    
    /**
     * Print isi deck
     */
    public void  printDeck(){
    	
    }
    
    /**
     * Push kartu yang dibuang player ke deck
     * @param C
     */
    public void pushCard(Card C){
        PileOfCard.push(C);
        NumberOfAvailableCard++;
    }
}
