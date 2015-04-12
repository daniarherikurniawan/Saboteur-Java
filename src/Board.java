
import java.util.Random;
import java.util.Vector;
import java.util.Scanner;

/**
 *Kelas board merepresentasikan papan permainan
 * @author Ramandika
 */
public class Board {

    /**
     * Representasi papan permainan berupa matriks 2 dimensi kartu
     */
    public Path_Card[][] MatrixOfCard;     // Papan permainan

    /**
     * Representasi keadaan goalcard
     */
    public boolean[] GoalCondition;        // True jika GoalCard dalam keadaan tertutup
    private boolean foundGoal;
    private boolean foundStart;

    /**
     * representasi boolean jika cabang yg ditemukan dapat dilalui
     */
    public boolean foundbranch = false;

    /**
     *Konstruktor kelas board
     */
    public Board(){
        MatrixOfCard = new Path_Card[6][10];
        for (int i=1; i<=5; i++){
            for (int j=1; j<=9; j++){
                MatrixOfCard[i][j] = new Path_Card('0', '0', '0', '0', '0', 0); 
            }
        }
        MatrixOfCard[3][9] = Path_Card.StartCard;    //startcard
        
        Random randomGenerator = new Random();
        int X = randomGenerator.nextInt(3);
        if (X == 0) 
		{MatrixOfCard[1][1] = Path_Card.GoldCard;   //Path_Card.GoldCard
		 MatrixOfCard[3][1] = Path_Card.RockCard;   //Path_Card.RockCard
		 MatrixOfCard[5][1] = Path_Card.RockCard;
		}
	else if (X == 1) 
		{MatrixOfCard[3][1] = Path_Card.GoldCard;
		 MatrixOfCard[5][1] = Path_Card.RockCard;
		 MatrixOfCard[1][1] = Path_Card.RockCard;
		}
	else if (X == 2)
		{MatrixOfCard[5][1] = Path_Card.GoldCard;
		 MatrixOfCard[3][1] = Path_Card.RockCard;
		 MatrixOfCard[1][1] = Path_Card.RockCard;
		}
        GoalCondition = new boolean[3];
        for (int i=0; i<3; i++){
            GoalCondition[i] = true;
        }
    }
    
    /**
     *Method untuk mengintip salah satu dari tiga kartu tujuan bagi yang menjalankan 
     kartu magic jenis viewmap
     * @param i
     */
    public void viewMap(int i){
        switch (i){
            case 1:
                printCard(MatrixOfCard[1][1]); break;
            case 2:
                printCard(MatrixOfCard[3][1]); break;
            case 3:
                printCard(MatrixOfCard[5][1]); break;
        }
    }
    
    private void printCard(Path_Card P){
        if (P.CompareCard(Path_Card.GoldCard)){System.out.println("GoldCard");}
        else if (P.CompareCard(Path_Card.RockCard)){System.out.println("RockCard");}
        else{System.out.println("KampretCard");}
    }
    
    private Path_Card getCardLeftOf(Vector<Integer> Position){
        if (Position.get(1) == 1){
            return new Path_Card('0', '0', '0', '0', '0', 0);     //nilcard
        } else {
            return MatrixOfCard[Position.get(0)][Position.get(1)-1];
        }
    }
    
    private Path_Card getCardRightOf(Vector<Integer> Position){
        if (Position.get(1) == 9){
            return new Path_Card('0', '0', '0', '0', '0', 0);     //nilcard
        } else {
            return MatrixOfCard[Position.get(0)][Position.get(1)+1];
        }
    }
    
    private Path_Card getCardAboveOf(Vector<Integer> Position){
        if (Position.get(0) == 1){
            return new Path_Card('0', '0', '0', '0', '0', 0);     //nilcard
        } else {
            return MatrixOfCard[Position.get(0)-1][Position.get(1)];
        }
    }
    
    private Path_Card getCardBelowOf(Vector<Integer> Position){
        if (Position.get(0) == 5){
            return new Path_Card('0', '0', '0', '0', '0', 0);     //nilcard
        } else {
            return MatrixOfCard[Position.get(0)+1][Position.get(1)];
        }
    }
    
    /**
     *Mengecek apakah posisi kartu valid pada board atau tidak
     * @param P
     * @param Position
     * @return
     */
    public boolean validatorPosition(Path_Card P, Vector<Integer> Position){
        boolean valid = false, aboveIsValid=false, belowIsValid=false, rightIsValid=false, leftIsValid=false;
        
        if (Path_Card.NilCard.CompareCard(MatrixOfCard[Position.get(0)][Position.get(1)])){
            
            Path_Card CardAbove = getCardAboveOf(Position);
            Path_Card CardBelow = getCardBelowOf(Position);
            Path_Card CardRight = getCardRightOf(Position);
            Path_Card CardLeft = getCardLeftOf(Position);
            
            if (Path_Card.NilCard.CompareCard(CardAbove) && Path_Card.NilCard.CompareCard(CardBelow) && Path_Card.NilCard.CompareCard(CardRight) && Path_Card.NilCard.CompareCard(CardLeft)){
                valid = false;
            } else {
                if ((Position.get(1) == 2) || (Position.get(1) == 1)){
                    if (Path_Card.RockCard.CompareCard(CardLeft) || Path_Card.GoldCard.CompareCard(CardLeft)){
                        if (Path_Card.NilCard.CompareCard(CardAbove) && Path_Card.NilCard.CompareCard(CardBelow) && Path_Card.NilCard.CompareCard(CardRight)){
                            valid = false;
                        } else {
                            P.setApproved(false);
                            if (! Path_Card.NilCard.CompareCard(CardAbove)){
                                aboveIsValid = P.canBePlacedBelowOf(CardAbove);
                            } else {
                                aboveIsValid = true;
                            }

                            if (! Path_Card.NilCard.CompareCard(CardBelow)){
                                belowIsValid = P.canBePlacedAboveOf(CardBelow);
                            } else {
                                belowIsValid = true;
                            }

                            if (! Path_Card.NilCard.CompareCard(CardRight)){
                                rightIsValid = P.canBePlacedLeftOf(CardRight);
                            } else {
                                rightIsValid = true;
                            }

                            valid = aboveIsValid && belowIsValid && rightIsValid && P.getApproved();
                        }
                    } else if ((Path_Card.RockCard.CompareCard(CardAbove) || Path_Card.GoldCard.CompareCard(CardAbove)) && (Path_Card.RockCard.CompareCard(CardBelow) || Path_Card.GoldCard.CompareCard(CardBelow))){
                        valid = P.canBePlacedLeftOf(CardRight)&& P.getApproved();
                    } else if ((Position.get(1) == 2) && (CardLeft.CompareCard(Path_Card.NilCard))){
                        P.setApproved(false);
                        if (! Path_Card.NilCard.CompareCard(CardAbove)) {
                            aboveIsValid = P.canBePlacedBelowOf(CardAbove);
                        } else {
                            aboveIsValid = true;
                        }

                        if (! Path_Card.NilCard.CompareCard(CardBelow)){
                            belowIsValid = P.canBePlacedAboveOf(CardBelow);
                        } else {
                            belowIsValid = true;
                        }

                        if (! Path_Card.NilCard.CompareCard(CardRight)){
                            rightIsValid = P.canBePlacedLeftOf(CardRight);
                        } else {
                            rightIsValid = true;
                        }
                        valid = rightIsValid && belowIsValid && aboveIsValid && P.getApproved();
                    }
                } else {
                    P.setApproved(false);
                    if (! Path_Card.NilCard.CompareCard(CardAbove)) {
                        aboveIsValid = P.canBePlacedBelowOf(CardAbove);
                    } else {
                        aboveIsValid = true;
                    }
                    
                    if (! Path_Card.NilCard.CompareCard(CardBelow)){
                        belowIsValid = P.canBePlacedAboveOf(CardBelow);
                    } else {
                        belowIsValid = true;
                    }
                        
                    if (! Path_Card.NilCard.CompareCard(CardRight)){
                        rightIsValid = P.canBePlacedLeftOf(CardRight);
                    } else {
                        rightIsValid = true;
                    }
                    
                    if (! Path_Card.NilCard.CompareCard(CardLeft)) {
                        leftIsValid = P.canBePlacedRightOf(CardLeft);
                    } else {
                        leftIsValid = true;
                    }
                    
                    valid = aboveIsValid && belowIsValid && rightIsValid && leftIsValid && P.getApproved();
                }
            }
        } else {
            valid = false;
        }
        return valid;
    }
    
    /**
     * Method untuk menaruh karu pada board
     * @param P
     * @param Position
     * @return
     */
    public int putCardOnBoard(Path_Card P, Vector<Integer> Position){
        
        if (this.validatorPosition(P,Position)){
            MatrixOfCard[Position.get(0)][Position.get(1)] = P;
            for(int i = 1; i <6 ; i++ ){
                for (int j = 1; j<10; j++){
                        System.out.print("    R=" + MatrixOfCard[i][j].getRight()+"\t");
                    }
                 System.out.println();
                    for (int j = 1; j<10; j++){
                        System.out.print("    L=" + MatrixOfCard[i][j].getLeft()+"\t");
                    }

                 System.out.println();
                    for (int j = 1; j<10; j++){
                        System.out.print("    T=" + MatrixOfCard[i][j].getTop()+"\t");
                    }
                 System.out.println();
                    for (int j = 1; j<10; j++){
                        System.out.print("    B=" + MatrixOfCard[i][j].getBottom()+"\t");
                    }
                 System.out.println();
                    for (int j = 1; j<10; j++){
                        System.out.print("    C=" + MatrixOfCard[i][j].getCenter()+"\t");
                    }
                 System.out.println("\n");
             }
            return 0;
        }else{
            System.out.println("Koordinat tidak valid ");
            return -1;
        }
    }
    
   /**
     * Mencetak board pada terminal
     */
    public void PrintBoard(){
	//Kamus Lokal
	int i,j;
	String lineu = "\u2594";
	String linel = "\u258F";
	String liner = "\u2595";
	String lined = "\u2581";
	String block = "\u2588";
	String goal = "\u0047";
	String rock = "\u25CE";
	String gold = "\u2606";
	
	//Algoritma
	System.out.println();
        System.out.println();
		
	for(i=1;i<=5;i++){	  
		if (i==1) {
			System.out.println("      1      2      3      4      5      6      7      8      9");
                        System.out.println();
                        System.out.print("  ");
                        if(GoalCondition[0]){
                            System.out.print(liner + lineu + lineu + lineu + lineu + lineu + lineu);
                        } else {
                            System.out.print(liner + lineu + lineu + block + block + lineu + lineu);
                        }
                        for (j=2;j<=9;j++){
                            System.out.print(liner + lineu + lineu); 
                            if (MatrixOfCard[i][j].getTop() == '1') {
                                System.out.print(block + block);
                            } else {
                                System.out.print(lineu + lineu);
                            } 
                            System.out.print(lineu + lineu);
                        } 
                        System.out.println(linel); 
			if(GoalCondition[0]){
                            System.out.print("  " + liner + "      ");
                        } else {
                            System.out.print("  "+ liner + "  " + block + block + "   ");
                        } 
                        for (j=2;j<=9;j++) {
                            System.out.print(liner + "  ");
                            if (MatrixOfCard[i][j].getTop() == '1') {
                                System.out.print(block + block);
                            } else {
                                System.out.print("  ");
                            } 
                            System.out.print("  ");
                        } 
                        System.out.println(linel); 
			if(GoalCondition[0]){
                            System.out.print(" 1" + liner + "   " + goal + "  ");
                        } else {
                            if(MatrixOfCard[1][1].getCenter() == 'R'){
                                System.out.print(" 1" + liner + block + block + rock + " " + block + block + "");
                            } else {
                                System.out.print(" 1" + liner + block + block + gold + " " + block + block + "");
                            }
                        }
                        for (j=2;j<=9;j++) {
                            if(MatrixOfCard[i][j].getLeft() == '1'){
                                if(MatrixOfCard[i][j].getCenter() == '1'){
                                    if (MatrixOfCard[i][j].getRight() == '1'){
                                        System.out.print(liner + block + block + block + block + block + block);
                                    } else {
                                        System.out.print(liner + block + block + block + block + "  ");
                                    }
                                } else {
                                    if (MatrixOfCard[i][j].getRight() == '1'){
                                        System.out.print(liner + block + block + "  " + block + block);
                                    } else {
                                        System.out.print(liner + block + block + "    ");
                                    }
                                }
                            } else {
                                if(MatrixOfCard[i][j].getCenter() == '1'){
                                    if (MatrixOfCard[i][j].getRight() == '1'){
                                        System.out.print(liner + "  " + block + block + block + block);
                                    } else {
                                        System.out.print(liner + "  " + block + block +"  ");
                                    }
                                } else {
                                    if (MatrixOfCard[i][j].getRight() == '1') {
                                        System.out.print(liner + "    " + block + block);
                                    } else {
                                        System.out.print(liner + "      ");
                                    }
                                }
                            }
                        }
                        System.out.println(linel);
			if(GoalCondition[0]){
                            System.out.print("  " + liner + "      ");
                        } else {
                            System.out.print("  " + liner + "  "+ block + block + "  ");
                        }
                        for (j=2;j<=9;j++) {
                            System.out.print(liner + "  "); 
                            if (MatrixOfCard[i][j].getBottom() == '1') {
                                System.out.print(block + block);
                            } else {
                                System.out.print("  ");
                            } 
                            System.out.print("  ");
                        }
                        System.out.println(linel); 
			if(GoalCondition[0]){
                            System.out.print("  " + liner + lined + lined + lined + lined + lined + lined);
                        } else {
                            System.out.print("  " + liner + lined + lined + block + block + lined + lined);
                        } 
                        
                        for (j=2;j<=9;j++) {
                            System.out.print(liner + lined + lined);
                            if (MatrixOfCard[i][j].getBottom() == '1') {
                                System.out.print(block + block);
                            } else {
                                System.out.print(lined + lined);
                            }
                            System.out.print(lined + lined);
                        } 
                        System.out.println(linel);
			} else if (i==3) {
				if(GoalCondition[1]){
                                    System.out.print("  " + liner + "      ");
                                } else {
                                    System.out.print("  " + liner + "  "+ block + block + "  ");
                                } 
                                for (j=2;j<=8;j++) {
                                    System.out.print(liner + "  ");
                                    if (MatrixOfCard[i][j].getTop() == '1') {
                                        System.out.print(block + block);
                                    } else {
                                        System.out.print("  ");
                                    } 
                                    System.out.print("  ");
                                } 
                                System.out.print(liner + "  " + block + block + "  ");
                                System.out.println(linel); 
				if(GoalCondition[1]){
                                    System.out.print("  " + liner + "      ");
                                } else {
                                    System.out.print("  " + liner + "  " + block + block + "  ");
                                }
                                for (j=2;j<=8;j++) {
                                    System.out.print(liner + "  ");
                                    if (MatrixOfCard[i][j].getTop() == '1') {
                                        System.out.print(block + block);
                                    } else {
                                        System.out.print("  ");
                                    } 
                                    System.out.print("  ");
                                } 
                                System.out.print(liner + "  " + block + block + "  ");
                                System.out.println(linel); 
				if(GoalCondition[1]){
                                    System.out.print(" 3" + liner + "   " + goal + "  ");
                                } else {
                                    if(MatrixOfCard[3][1].getCenter() == 'R'){
                                        System.out.print(" 3"+ liner + block + block + rock + " " + block + block);
                                    } else {
                                        System.out.print("  "+ liner + block + block + gold + " " + block + block);
                                    }
                                }
                                for (j=2;j<=8;j++) {
                                    if(MatrixOfCard[i][j].getLeft() == '1'){
                                        if(MatrixOfCard[i][j].getCenter() == '1'){
                                            if (MatrixOfCard[i][j].getRight() == '1'){
                                                System.out.print(liner + block + block + block + block + block + block);
                                            } else {
                                                System.out.print(liner + block + block + block + block + "  ");
                                            }
                                        } else {
                                            if (MatrixOfCard[i][j].getRight() == '1'){
                                                System.out.print(liner + block + block + "  " + block + block);
                                            } else {
                                                System.out.print(liner + block + block + "    ");
                                            }
                                        }
                                    } else {
                                        if(MatrixOfCard[i][j].getCenter( )== '1') {
                                            if (MatrixOfCard[i][j].getRight() == '1'){
                                                System.out.print(liner + "  " + liner + block + block + block + block);
                                            } else {
                                                System.out.print(liner + "  " + block + block + "  ");
                                            }
                                        } else {
                                            if (MatrixOfCard[i][j].getRight() == '1') {
                                                System.out.print(liner + "    " + block + block); 
                                            } else {
                                                System.out.print(liner + "      ");
                                            }
                                        }
                                    }
                                }
                                System.out.print(liner + block + block + block + block + block + block);
                                System.out.println(linel);
				if(GoalCondition[1]){
                                    System.out.print("  " + liner + "      ");
                                } else {
                                    System.out.print("  " + liner + "  " + block + block + "  ");
                                } 
                                for (j=2;j<=8;j++) {
                                    System.out.print(liner + "  ");
                                    if (MatrixOfCard[i][j].getBottom() == '1') {
                                        System.out.print(block + block);
                                    } else {
                                        System.out.print("  ");
                                    }
                                    System.out.print("  ");
                                }
                                System.out.print(liner + "  " + block + block + "  ");
                                System.out.println(linel); 
				if(GoalCondition[1]){
                                    System.out.print("  " + liner + lined + lined + lined + lined + lined + lined);
                                } else {
                                    System.out.print("  " + liner + lined + lined + block + block + lined + lined);
                                }
                                for (j=2;j<=8;j++) {
                                    System.out.print(liner + lined + lined); 
                                    if (MatrixOfCard[i][j].getBottom() == '1') {
                                        System.out.print(block + block);
                                    } else {
                                        System.out.print(lined + lined);
                                    } 
                                    System.out.print(lined + lined);
                                }
                                System.out.print(liner + lined + lined + block + block + lined + lined);
                                System.out.println(linel);
				} else if (i==5) {
                                    if(GoalCondition[2]){
                                        System.out.print("  " + liner + "      ");
                                    } else {
                                        System.out.print("  " + liner + "  " + block + block + "  ");
                                    }
                                    for (j=2;j<=9;j++) {
                                        System.out.print(liner + "  ");
                                        if (MatrixOfCard[i][j].getTop() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print("  ");
                                        }
                                        System.out.print("  ");
                                    }
                                    System.out.println(linel); 
                                    if(GoalCondition[2]){
                                        System.out.print("  " + liner + "      ");
                                    } else {
                                        System.out.print("  " + liner + "  " + block + block + "  ");
                                    }
                                    for (j=2;j<=9;j++) {
                                        System.out.print(liner + "  ");
                                        if (MatrixOfCard[i][j].getTop() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print("  ");
                                        }
                                        System.out.print("  ");
                                    }
                                    System.out.println(linel); 
                                    if(GoalCondition[2]){
                                        System.out.print(" 5" + liner + "   " + goal + "  ");
                                    } else {
                                        if(MatrixOfCard[5][1].getCenter() == 'R'){
                                            System.out.print(" 5" + liner + block + block + rock + " " + block + block);
                                        } else {
                                            System.out.print(" 5" + liner + block + block + gold + " " + block + block);
                                        }
                                    }
                                    for (j=2;j<=9;j++) {
                                        if(MatrixOfCard[i][j].getLeft() == '1'){
                                            if(MatrixOfCard[i][j].getCenter() == '1'){
                                                if (MatrixOfCard[i][j].getRight() == '1'){
                                                    System.out.print(liner + block + block + block + block + block + block);
                                                } else {
                                                    System.out.print(liner + block + block + block + block + "  ");
                                                }
                                            } else {
                                                if (MatrixOfCard[i][j].getRight() == '1'){
                                                    System.out.print(liner + block + block + "  " + block + block);
                                                } else {
                                                    System.out.print(liner + block + block + "    ");
                                                }
                                            }
                                        } else {
                                            if(MatrixOfCard[i][j].getCenter() == '1') {
                                                if (MatrixOfCard[i][j].getRight() == '1'){
                                                    System.out.print(liner + "  " + liner + block + block + block + block);
                                                } else {
                                                    System.out.print(liner + "  " + block + block + "  ");
                                                }
                                            } else {
                                                if (MatrixOfCard[i][j].getRight() == '1') {
                                                    System.out.print(liner + "    " + block + block);
                                                } else {
                                                    System.out.print(liner + "      ");
                                                }
                                            }
                                        }
                                    }
                                    System.out.println(linel);
                                    if(GoalCondition[2]){
                                        System.out.print("  " + liner + "      ");
                                    } else {
                                        System.out.print("  " + liner + "  " + block + block + "  ");
                                    } 
                                    for (j=2;j<=9;j++) {
                                        System.out.print(""+ liner + "  ");
                                        if (MatrixOfCard[i][j].getBottom() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print("  ");
                                        }
                                        System.out.print("  ");
                                    }
                                    System.out.println(linel); 
                                    if(GoalCondition[2]){
                                        System.out.print("  " + liner + lined + lined + lined + lined + lined + lined);
                                    } else {
                                        System.out.print("  " + liner + lined + lined + block + block + lined + lined);
                                    }
                                    for (j=2;j<=9;j++) {
                                        System.out.print(liner + lined + lined); 
                                        if (MatrixOfCard[i][j].getBottom() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print(lined + lined);
                                        }
                                        System.out.print(lined + lined);
                                    }
                                    System.out.println(linel);
				} else if (i==2){
                                    System.out.print("  ");
                                    for (j=1;j<=9;j++) {
                                        System.out.print(liner + "  "); 
                                        if (MatrixOfCard[i][j].getTop() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print("  ");
                                        }
                                        System.out.print("  ");
                                    } 
                                    System.out.println(linel); 
                                    System.out.print("  ");
                                    for (j=1;j<=9;j++) {
                                        System.out.print(liner + "  "); 
                                        if (MatrixOfCard[i][j].getTop() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print("  ");
                                        }
                                        System.out.print("  ");
                                    }
                                    System.out.println(linel); 
                                    System.out.print(" 2");
                                    for (j=1;j<=9;j++) {
                                        if(MatrixOfCard[i][j].getLeft() == '1'){
                                            if(MatrixOfCard[i][j].getCenter() == '1'){
                                                if (MatrixOfCard[i][j].getRight() == '1'){
                                                    System.out.print(liner + block + block + block + block + block + block);
                                                } else {
                                                    System.out.print(liner + block + block + block + block + "  ");
                                                }
                                            } else {
                                                if (MatrixOfCard[i][j].getRight() == '1'){
                                                    System.out.print(liner + block + block + "  " + block + block);
                                                } else {
                                                    System.out.print(liner + block + block + "    ");
                                                }
                                            }
                                        } else {
                                            if(MatrixOfCard[i][j].getCenter() == '1') {
                                                if (MatrixOfCard[i][j].getRight() == '1'){
                                                    System.out.print(liner + "  " + block + block + block + block);
                                                } else {
                                                    System.out.print(liner + "  " + block + block);
                                                }
                                            } else {
                                                if (MatrixOfCard[i][j].getRight() == '1') {
                                                    System.out.print(liner + "    " + block + block);
                                                } else {
                                                    System.out.print(liner + "      ");
                                                }
                                            }
                                        }
                                    }
                                    System.out.println(linel);
                                    System.out.print("  ");
                                    for (j=1;j<=9;j++) {
                                        System.out.print(liner + "  ");
                                        if (MatrixOfCard[i][j].getBottom() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print("  ");
                                        }
                                        System.out.print("  ");
                                    }
                                    System.out.println(linel); 
                                    System.out.print("  ");
                                    for (j=1;j<=9;j++) {
                                        System.out.print(liner + lined + lined); 
                                        if (MatrixOfCard[i][j].getBottom() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print(lined + lined);
                                        }
                                        System.out.print(lined + lined);
                                    }
                                    System.out.println(linel);

				}
				else if (i==4){
					System.out.print("  ");
                                        for (j=1;j<=9;j++) {
                                            System.out.print(liner + "  ");
                                            if (MatrixOfCard[i][j].getTop() == '1') {
                                                System.out.print(block + block);
                                            } else {
                                                System.out.print("  ");
                                            } 
                                            System.out.print("  ");
                                        } System.out.println(linel); 
					System.out.print("  ");
                                        for (j=1;j<=9;j++) {
                                            System.out.print(liner + "  ");
                                            if (MatrixOfCard[i][j].getTop() == '1') {
                                                System.out.print(block + block);
                                            } else {
                                                System.out.print("  ");
                                            }
                                            System.out.print("  ");
                                        }
                                        System.out.println(linel); 
					System.out.print(" 4");
                                        for (j=1;j<=9;j++) {
                                            if(MatrixOfCard[i][j].getLeft() == '1'){
                                                if(MatrixOfCard[i][j].getCenter() == '1'){
                                                    if (MatrixOfCard[i][j].getRight() == '1'){
                                                        System.out.print(liner + block + block + block + block + block + block);
                                                    } else {
                                                        System.out.print(liner + block + block + block + block + "  ");
                                                    }
                                                } else {
                                                    if (MatrixOfCard[i][j].getRight() == '1'){
                                                        System.out.print(liner + block + block + "  " + block + block);
                                                    } else {
                                                        System.out.print(liner + block + block + "    ");
                                                    }
                                                }
                                            } else {
                                                if(MatrixOfCard[i][j].getCenter() == '1') {
                                                    if (MatrixOfCard[i][j].getRight() == '1'){
                                                        System.out.print(liner + "  " + block + block + block + block);
                                                    } else {
                                                        System.out.print(liner + "  " + block + block);
                                                    }
                                                } else {
                                                    if (MatrixOfCard[i][j].getRight() == '1') {
                                                        System.out.print(liner + "    " + block + block);
                                                    } else {
                                                        System.out.print(liner + "      ");
                                                    }
                                                }
                                            }
                                        }
                                        System.out.println(linel);
					System.out.print("  ");
                                        for (j=1;j<=9;j++) {
                                            System.out.print(liner + "  ");
                                            if (MatrixOfCard[i][j].getBottom() == '1') {
                                                System.out.print(block + block);
                                            } else {
                                                System.out.print("  ");
                                            }
                                            System.out.print("  ");
                                        }
                                        System.out.println(linel); 
					System.out.print("  ");
                                        for (j=1;j<=9;j++) {
                                            System.out.print(liner + lined + lined); 
                                            if (MatrixOfCard[i][j].getBottom() == '1') {
                                                System.out.print(block + block);
                                            } else {
                                                System.out.print(lined + lined);
                                            }
                                            System.out.print(lined + lined);
                                        }
                                        System.out.println(linel);
				}
		}
}

    /** I.S. G dalam keadaan tertutup.
    *  F.S. Mengecek apakah pemain berhasil meletakkan PathCard di sebelah GoalCard yang berisi RockCard pada Board B.
    *      Jika berhasil, maka G dalam keadaan terbuka.
    *      Jika tidak, maka G tetap dalam keadaan tertutup. **/
    public void openGoal(){
    /* I.S. G dalam keadaan tertutup.
    *  F.S. Mengecek apakah pemain berhasil meletakkan PathCard di sebelah GoalCard yang berisi RockCard pada Board B.
    *      Jika berhasil, maka G dalam keadaan terbuka.
    *      Jika tidak, maka G tetap dalam keadaan tertutup. */
	
	foundStart = false;
        if(CardOnSecondColumnFilled()){
            boolean[][] check = new boolean[6][10];
            int i, j;
            Path_Card P = new Path_Card('0', '0', '0', '0', '0', 0);

            for (i=1;i<=5;i++) {
                    for (j=1;j<=9;j++) {
                            check[i][j]=false;
                    }
            }
            Vector<Integer> position1 = new Vector<>(); position1.add(0, 1); position1.add(1, 1);
            Vector<Integer> position2 = new Vector<>(); position2.add(0, 3); position2.add(1, 1);
            Vector<Integer> position3 = new Vector<>(); position3.add(0, 5); position3.add(1, 1);
         
            this.openGoalRecc(position1, check, P); 
                if (foundStart) 
                    {GoalCondition[0] = false;
                        System.out.println(" goal 1.\n");
                    }
                System.out.println("saya suadah cek goal 1.\n");
            foundStart = false;
            for (i=1;i<=5;i++) {
                    for (j=1;j<=9;j++) {
                            check[i][j]=false;
                    }
            }
             P = new Path_Card('0', '0', '0', '0', '0', 0);
            this.openGoalRecc(position2, check, P); 
                    if (foundStart) 
                    {
                        GoalCondition[1] = false;
                        System.out.println(" goal 2.\n");
                    }
                    System.out.println("saya suadah cek goal 2.\n");
                    
            foundStart = false;
            for (i=1;i<=5;i++) {
                    for (j=1;j<=9;j++) {
                            check[i][j]=false;
                    }
            }
             P = new Path_Card('0', '0', '0', '0', '0', 0);
            this.openGoalRecc(position3, check, P); 
                if (foundStart) 
                    {
                        GoalCondition[2] = false;
                        System.out.println(" goal 3.\n");
                }
                System.out.println("saya suadah cek goal 3.\n");
            }
            
                        System.out.println(" hasilnya   "+foundStart);
        

    }
    private boolean CardOnSecondColumnFilled(){
        
        for (int i=1; i<6; i++){
            if (MatrixOfCard[i][2].getCenter() == '1'){
                return true;
            }
        }
        return false;
    }

    /** I.S. B terdefinisi
    * F.S. Mengembalikan nilai true jika kondisi akhir permainan tercapai, yaitu saat pemain 
            berhasil meletakkan PathCard di sebelah GoalCard yang merupakan GoldCard.	    
     * @return  **/	 
    public boolean isFinished(){
    /* I.S. B terdefinisi
    * F.S. Mengembalikan nilai true jika kondisi akhir permainan tercapai, yaitu saat pemain 
            berhasil meletakkan PathCard di sebelah GoalCard yang merupakan GoldCard. */
        foundGoal = false;
        if(CardOnSecondColumnFilled()){
            boolean[][] check = new boolean[6][10];
            int i, j;
            Path_Card P = new Path_Card('0', '0', '0', '0', '0', 0);

            for (i=1;i<=5;i++) {
                    for (j=1;j<=9;j++) {
                            check[i][j]=false;
                    }
            }
            Vector<Integer> position1 = new Vector<>(); position1.add(0, 1); position1.add(1, 1);
            Vector<Integer> position2 = new Vector<>(); position2.add(0, 3); position2.add(1, 1);
            Vector<Integer> position3 = new Vector<>(); position3.add(0, 5); position3.add(1, 1);
         
            if (Path_Card.GoldCard.CompareCard(MatrixOfCard[1][1]))
            {
                this.openGoalRecc(position1, check, P); 
                if (foundGoal) 
                    {GoalCondition[0] = false;
                        System.out.println(" goal 1.\n");
                    }
                System.out.println("saya suadah cek goal 1.\n");
            }
            else if (Path_Card.GoldCard.CompareCard(MatrixOfCard[3][1]) )
                {
                    this.openGoalRecc(position2, check, P); 
                    if (foundGoal) 
                    {
                        GoalCondition[1] = false;
                        System.out.println(" goal 2.\n");
                    }
                    System.out.println("saya suadah cek goal 2.\n");
                }
            else if (Path_Card.GoldCard.CompareCard(MatrixOfCard[5][1]) )
            {
                this.openGoalRecc(position3, check, P); 
                if (foundGoal) 
                    {
                        GoalCondition[2] = false;
                        System.out.println(" goal 3.\n");
                }
                System.out.println("saya suadah cek goal 3.\n");
            }
            
                        System.out.println(" hasilnya   "+foundGoal);
        }
        return foundGoal;
    }
    
    /**
     *
     * @param Position
     * @param cek
     * @param P
     */
    public void openGoalRecc(Vector<Integer> Position, boolean[][] cek, Path_Card P){
    /* Proses: Prosedur rekursif 4 arah untuk mengecek apakah ada jalan yang terbentuk dari GoalCard ke Path_Card.StartCard.
    * F.S: P akan berisi Path_Card.StartCard jika prosedur ini berhasil mencapai Path_Card.StartCard.
    *      P akan berisi Path_Card.NilCard jika prosedur ini berhenti pada kotak kosong.
    *      P akan berisi PathCard yang lain jika prosedur ini berhenti di PathCard yang merupakan kartu buntu */ 
        for(int i = 1; i <6 ; i++ ){
                    for (int j = 1; j<10; j++){
                        System.out.print( cek[i][j]+"\t");
                    }   
                 System.out.println("\n");
             }
         Scanner scana = new Scanner(System.in);
        // int i = scana.nextInt();

        cek[Position.get(0)][Position.get(1)] = true;		//sudah dicek
        if (((Position.get(0) == 3) && (Position.get(1) == 9)) || (Path_Card.NilCard.CompareCard(MatrixOfCard[Position.get(0)][Position.get(1)])) || ((MatrixOfCard[Position.get(0)][Position.get(1)]).getCenter() == '0'))
        {
                P = MatrixOfCard[Position.get(0)][Position.get(1)];
                if (Path_Card.StartCard.CompareCard(P)) 
                {   
                    foundGoal = true;
                    foundStart = true;
                }
        }
        else
        {
                if (((MatrixOfCard[Position.get(0)][Position.get(1)]).getTop() == '1') && (Position.get(0) > 1) && (! Path_Card.NilCard.CompareCard(this.getCardAboveOf(Position))) && (! cek[Position.get(0)-1][Position.get(1)])) {
                    Vector<Integer> pos = new Vector(); pos.add(0, Position.get(0)-1); pos.add(1, Position.get(1));
                    this.openGoalRecc(pos, cek, P);
                } 
                if ((! (Path_Card.StartCard.CompareCard(P))) && ((MatrixOfCard[Position.get(0)][Position.get(1)]).getRight() == '1') && (Position.get(1) < 9) && (! Path_Card.NilCard.CompareCard(this.getCardRightOf(Position))) && (! cek[Position.get(0)][Position.get(1)+1])){
                    Vector<Integer> pos = new Vector(); pos.add(0, Position.get(0)); pos.add(1, Position.get(1)+1);
                    this.openGoalRecc(pos, cek, P);
                }
                if ((! (Path_Card.StartCard.CompareCard(P))) && ((MatrixOfCard[Position.get(0)][Position.get(1)]).getBottom() == '1') && (Position.get(0) < 5) && (! Path_Card.NilCard.CompareCard(this.getCardBelowOf(Position))) && (! cek[Position.get(0) +1][Position.get(1)])) {
                    Vector<Integer> pos = new Vector(); pos.add(0, Position.get(0)+1); pos.add(1, Position.get(1));
                    this.openGoalRecc(pos, cek, P);
                }
                if ((! (Path_Card.StartCard.CompareCard(P))) && ((MatrixOfCard[Position.get(0)][Position.get(1)]).getLeft() == '1') && (Position.get(1) > 1) && (! Path_Card.NilCard.CompareCard(this.getCardLeftOf(Position))) && (! cek[Position.get(0)][Position.get(1)-1])) {
                    Vector<Integer> pos = new Vector(); pos.add(0, Position.get(0)); pos.add(1, Position.get(1)-1);
                    this.openGoalRecc(pos, cek, P);
                }
        }
        
    }
    
    public boolean useIsExistBranch(){
        Vector<Integer> pos = new Vector<Integer>();
        pos.add(3); pos.add(9);
        boolean[][] check = new boolean[6][10];
        for (int i=1;i<=5;i++) {
          for (int j=1;j<=9;j++) {
                  check[i][j]=false;
          }
        }
        isExistBranchRecc(pos, check, new Path_Card('0','0','0','0','0',0));
        return foundbranch;
                  
    }
    
    
    
    /**
     * Method untuk memeriksa apakah masih ada cabang yang bisa dilalui. 
     * Mengeluarkan true jika terdapat, false jika tidak.
     * @param Position
     * @param cek
     * @param P
     */
    public void isExistBranchRecc(Vector<Integer> Position, boolean[][] cek, Path_Card P){
        
         Scanner scana = new Scanner(System.in);
        //int i = scana.nextInt();

        cek[Position.get(0)][Position.get(1)] = true;		//sudah dicek
        for(int i = 1; i <6 ; i++ ){
                    for (int j = 1; j<10; j++){
                        System.out.print( cek[i][j]+"\t");
                    }   
                 System.out.println("\n");
             }
         System.out.println("\n");
        if ((Path_Card.NilCard.CompareCard(MatrixOfCard[Position.get(0)][Position.get(1)]))
                || ((MatrixOfCard[Position.get(0)][Position.get(1)]).getCenter() == '0') || ((MatrixOfCard[Position.get(0)][Position.get(1)]).getCenter() == 'R') || ((MatrixOfCard[Position.get(0)][Position.get(1)]).getCenter() == 'G'))
        {
                P = MatrixOfCard[Position.get(0)][Position.get(1)];
                if (Path_Card.NilCard.CompareCard(P) && (Position.get(0) <6) &&
                        (Position.get(0) > 0) && (Position.get(1) < 10) 
                        && (Position.get(1) > 0)) 
                {   
                    foundbranch = true;
                } else if (((MatrixOfCard[Position.get(0)][Position.get(1)]).getCenter() == 'R') || ((MatrixOfCard[Position.get(0)][Position.get(1)]).getCenter() == 'R')){
                    foundbranch = foundbranch || false;
                }
        }
        else
        {
            boolean masukT = false, masukR = false, masukB = false, masukL = false;
                if (((MatrixOfCard[Position.get(0)][Position.get(1)]).getTop() == '1')
                        && (Position.get(0) > 1)
                        && (! cek[Position.get(0)-1][Position.get(1)])) {
                    masukT = true;
                    Vector<Integer> pos = new Vector(); pos.add(0, Position.get(0)-1); pos.add(1, Position.get(1));
                    this.isExistBranchRecc(pos, cek, P);
                } 
                if (((MatrixOfCard[Position.get(0)][Position.get(1)]).getRight() == '1') && (Position.get(1) < 9) && (! cek[Position.get(0)][Position.get(1)+1])){
                    masukR = true;
                    Vector<Integer> pos = new Vector(); pos.add(0, Position.get(0)); pos.add(1, Position.get(1)+1);
                    this.isExistBranchRecc(pos, cek, P);
                }
                if (((MatrixOfCard[Position.get(0)][Position.get(1)]).getBottom() == '1') 
                        && (Position.get(0) < 5) && (! cek[Position.get(0) +1][Position.get(1)])) {
                    masukB = true;
                    Vector<Integer> pos = new Vector(); pos.add(0, Position.get(0)+1);
                    pos.add(1, Position.get(1));
                    this.isExistBranchRecc(pos, cek, P);
                }
                if (((MatrixOfCard[Position.get(0)][Position.get(1)]).getLeft() == '1') && (Position.get(1) > 1) && (! cek[Position.get(0)][Position.get(1)-1])) {
                    masukL = true;
                    Vector<Integer> pos = new Vector(); pos.add(0, Position.get(0)); pos.add(1, Position.get(1)-1);
                    this.isExistBranchRecc(pos, cek, P);
                }
                
                if ((masukT || masukR || masukB || masukL) == false){foundbranch = foundbranch || false;}
        }
        
    }
    
}
