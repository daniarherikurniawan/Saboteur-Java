import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;


public class GamePlay {
    public Vector<Player> ListOfPlayer = new Vector<Player>();
    public Vector<Player> ListOfRegisteredPlayer = new Vector<Player>();
    private static Scanner scan = new Scanner(System.in);
    Deck aDeck = new Deck();
    Board board = new Board();
    Player currentPlayer = new Player();
    
    public GamePlay(){
        try{
            loadFromFile("Player.in");
        }catch(Exception e){
            System.out.println("sapi");
            try {
                PrintWriter writer = new PrintWriter("Player.in", "UTF-8");
                writer.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    public void loadFromFile (String fileName) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            Player player = new Player(parts[0]);
            player.setDate(parts[2]);
            player.setScore(Integer.parseInt(parts[1]));
            ListOfRegisteredPlayer.add(player);
        }
    }
    
    public void addPlayerToFile(Player myPlayer){
        FileWriter fileWritter;
        try {
            fileWritter = new FileWriter("Player.in",true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(myPlayer.getPlayerName()+";"+myPlayer.getScore()+";"+myPlayer.getDate()+"\n");
            bufferWritter.flush();
            bufferWritter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addPlayer() {
        System.out.print("Enter your name : ");
        String whiteSpace = scan.nextLine();
        String playerName = scan.nextLine();
        Player myPlayer = new Player(playerName);
        myPlayer.setDate(new Date().toString());
        if(IsNotRegistered(playerName)){
            ListOfRegisteredPlayer.add(myPlayer);
            addPlayerToFile(myPlayer);
        }else
            printMessage("Sorry your name already registered!");
    }
    
    public boolean IsNotRegistered(String Name){
        for (int i = 0 ; i <ListOfRegisteredPlayer.size() ; i++)
            if(Name.compareTo(ListOfRegisteredPlayer.elementAt(i).getPlayerName()) == 0)
                return false;
        return true;
    }

    public boolean IsNotChoosen(String Name){
        for (int i = 0 ; i <ListOfPlayer.size() ; i++)
            if(Name.compareTo(ListOfPlayer.elementAt(i).getPlayerName()) == 0)
                return false;
        return true;
    }
    
    public void clearScreen(){
        for (int a = 0 ; a <2 ; a++)
            System.out.println();
    }
    public void showMenuCMD(){
        clearScreen();
        System.out.println("==== Menu Utama ====");
        System.out.println(
                "1. Start"+
                "\n2. Highscores"+
                "\n3. Setting"+
                "\n4. Help"+
                "\n5. About"+
                "\n6. Exit");
        System.out.print("Input your index menu : ");
    }
    public void backToPrevMenu(){
        System.out.println("Enter to continue !");
        String whiteSpace = scan.nextLine();
        whiteSpace = scan.nextLine();
    }
    public void showRegisteredPlayer(){
        System.out.println("Number of registered player is "+ ListOfRegisteredPlayer.size() );
        for(int i = 0 ; i < ListOfRegisteredPlayer.size() ; i++){
            Player p = new Player();
            p = ListOfRegisteredPlayer.elementAt(i);
            System.out.println((i+1)+". "+p.getPlayerName()
                    +"  ["+p.getScore()+"]   <"+p.getDate()+">");
        }
    }
    public void setRoleAndTurnForPlayer(){
        Vector<Integer> myIdCharacter = new Vector();
        int i;
        for(i = 0 ; i <ListOfPlayer.size(); i++){
            ListOfPlayer.get(i).setTurn(i);
                        if(i < ListOfPlayer.size()/2)
                myIdCharacter.add(2);// add id for saboteur
            else 
                myIdCharacter.add(1);// add id for goldminer
                }
        System.out.println(i);
        Collections.shuffle(myIdCharacter);
        for (i = 0 ; i < ListOfPlayer.size(); i++)
            ListOfPlayer.elementAt(i).setRole(myIdCharacter.elementAt(i));
    }
    
    public void play(){
        setRoleAndTurnForPlayer();
        //printRolePlayers();
        aDeck.fillDeck();
        for(Player p : ListOfPlayer){
            for (int i=0;i<5;i++){
                p.drawCard(aDeck.popCard());
            }
        }
        gameLoop();
    }
        
        public void showMenuGame(){
            System.out.println("1. Draw card");
            System.out.println("2. Choose card");
            System.out.println("3. Tampilkan urutan dan status player");
            System.out.println("4. Tampilkan status player");
            System.out.println("5. keluar");
            System.out.print("Pilihan menu: ");
            
        }
        
        public void setNextPlayer(){
            do{
                if(currentPlayer.getTurn()==ListOfPlayer.size()-1){
                     currentPlayer = ListOfPlayer.get(0);
                }else
                    currentPlayer = ListOfPlayer.get(currentPlayer.getTurn()+1);
            }while(currentPlayer.getStatus().compareTo("Disable")==0);
            currentPlayer.setFinishedTurn(false);
            currentPlayer.setFinishedDraw(false);
        }
        
        public void gameLoop(){
            boolean finish=false;
            currentPlayer = ListOfPlayer.elementAt(0);
            
            while(!finish){
                board.PrintBoard();
                System.out.println("\nYour turn : "+currentPlayer.getPlayerName());
                currentPlayer.printCardsOnHand();
                showMenuGame();
                int menu = scan.nextInt();
                switch (menu){
                    case 1 ://draw Card 
                            processMenuDrawCard();
                            break;
                    case 2 : // Choose Card
                             processMenuChooseCard();
                             break;
                    case 3 : ShowPlayers();
                             break;
                    case 4 : currentPlayer.showStatus();
                             backToPrevMenu();
                             break;
                    case 5 : int IdxPrevPlayer = currentPlayer.getTurn();
                             setNextPlayer(); 
                             changeTurn(IdxPrevPlayer);
                             ListOfPlayer.removeElementAt(IdxPrevPlayer);
                             break;
                    default :printMessage("default : input is  not valid!");
                }
                finish = (board.isFinished() || ListOfPlayer.size() < 3);
                if(currentPlayer.getFinishedDraw() && menu!=5)
                    setNextPlayer();
            }
            ListOfPlayer.clear();
        }
        
        public void changeTurn(int idx)
        {
            for (int i = idx+1 ; i < ListOfPlayer.size() ; i++){
                ListOfPlayer.get(i).setTurn(i-1);
            }
        }        
        public void processMenuChooseCard(){
            if(!currentPlayer.getFinishedTurn()){
                System.out.print("Masukkan indeks kartu : ");
                int idx = scan.nextInt();
                Card crd = currentPlayer.disCard(idx);
                System.out.print("1. Buang kartu \n2. Gunakan Kartu"+
                        "\nMasukkan pilihan Anda : ");
                try{
                    idx = scan.nextInt();
                    if(idx==1){
                        currentPlayer.setFinishedTurn(true);
                        printMessage("Anda telah membuang kartu tersebut");
                        currentPlayer.setIdxRemovedCard(-1);
                    }else if (idx==2){
                        usingCard(crd);
                    }else{
                         printMessage("input Anda tidak valid! serius woii");
                    }
                }catch(Exception e){
                    printMessage("input Anda tidak valid!");
                }
                
           }else
               printMessage("Anda telah memakai kartu pada giliran ini");
        }
        
        public void processMenuDrawCard(){
             if(currentPlayer.getFinishedTurn()){
                Card c = aDeck.popCard();
                try{
                   currentPlayer.drawCard(c);
                   currentPlayer.printCardsOnHand();
                   System.out.println("wes sukses ngedraw, hahahah\nEnter to change nextplayer !");
                   currentPlayer.setFinishedTurn(true);
                }catch(Exception e){
                    printMessage("kartu habis, hahahah");
                }
                currentPlayer.setFinishedDraw(true);
                backToPrevMenu();
            }else
                printMessage("Anda belum menggunakan kartu, hahahah");
        }
        
        public void printMessage(String msg){
            System.out.println(msg);
            backToPrevMenu();
        }
        
        public void ShowPlayers(){
            System.out.println("Urutan\tnama");
            for (Player p : ListOfPlayer){ 
                System.out.println(p.getTurn() + "\t" + p.getPlayerName() + "   " +
                        p.getStatus());
            }
            backToPrevMenu();
        }
    
        public void usingCard(Card crd){
            if (crd.getType()==1){
                //pathcard
                System.out.print("Apakah Anda mau memutar kartu? (y/n) ");
                if ("y".equals(scan.next())){((Path_Card) crd).rotateCard();}
                System.out.print("Masukkan koordinat x<spasi>y : ");
                Vector<Integer> pos = new Vector(); pos.add(0,scan.nextInt());
                pos.add(1,scan.nextInt());
                if(-1==board.putCardOnBoard((Path_Card) crd, pos)){
                    currentPlayer.drawCard(crd);
                    backToPrevMenu();
                }else
                    currentPlayer.setFinishedTurn(true);
                currentPlayer.setIdxRemovedCard(-1);
            } else {
                //actioncard
                int id = crd.getID();
                switch (id) {
                    case 1 : //viewmap
                             usingViewMap(crd);
                             break;
                    case 2 : //break
                             usingBreakTool(crd);
                             break;
                    case 3 : //repair
                             usingRepairTool(crd);
                             break;
                    default : 
                }
                currentPlayer.setIdxRemovedCard(-1);
            }
        }
        
        public void usingViewMap(Card crd){
            System.out.print("Masukkan pilihan goalcard: ");
            int urutan = scan.nextInt();
            if(urutan>=1 && urutan<=3){
               board.viewMap(urutan);
               currentPlayer.setFinishedTurn(true);
               backToPrevMenu();
            }else{
                currentPlayer.drawCard(crd);
                printMessage("index tidak valid!");
            }
        }
        
        public void usingBreakTool(Card crd){
            System.out.print("Masukkan urutan pemain: ");
            int urutan = scan.nextInt();
            Player victim = ListOfPlayer.get(urutan);
            if (victim.getStatus().compareTo("Enable")==0 && 
                    victim.getTurn()!=currentPlayer.getTurn()){
               victim.setStatus("Disable");
               currentPlayer.setFinishedTurn(true);
               printMessage("Player telah berstatus disable");
            }else{
                currentPlayer.drawCard(crd);
                printMessage("Player yang anda  jadikan target tidak valid ");
            }
        }
        
        public void usingRepairTool(Card crd){
            System.out.print("Masukkan urutan pemain: ");
            int urutan = scan.nextInt();
            Player victim = ListOfPlayer.get(urutan);
            if (victim.getStatus().compareTo("Disable")==0 && 
                    victim.getTurn()!=currentPlayer.getTurn()){
               victim.setStatus("Enable");
               currentPlayer.setFinishedTurn(true);
               printMessage("Player telah berstatus enable");
            }else{
                currentPlayer.drawCard(crd);
                printMessage("Player yang anda  jadikan target tidak valid ");
            }
        }
        
    public void printRolePlayers(){
        // just for debuging
        for(int i = 0 ; i < ListOfPlayer.size() ; i++)
            System.out.println((i+1)+". "+ListOfPlayer.elementAt(i).getPlayerName()+
                    "   role : "+ListOfPlayer.elementAt(i).getRoleId());
    }
    
    public void runMenu(int indexMenu){
        switch(indexMenu){
            case 1 : preparationPlay(); break;
            case 2 : highScores(); break;
            case 3 : setting(); break;
            case 4 : help(); break;
            case 5 : about(); break;
            case 6 : //exit; 
                break;
            default: printMessage("Sorry, your input is invalid!");
        }
    }
    
    public void preparationPlay(){
        boolean readyToPlay = false;
        int menu = 0;
        while(!readyToPlay && menu != 5){
            System.out.print("\n\n1. Show registered player"+
                    "\n2. Add player"+
                    "\n3. Choose player"+
                    "\n4. Play"+
                    "\n5. Back"
                    + "\nInput your index menu : ");
            menu = scan.nextInt();
            switch(menu){
                case 1 : System.out.println("\n");
                         showRegisteredPlayer(); 
                         backToPrevMenu();break;
                case 2 : addPlayer(); break;
                case 3 : choosePlayer(); break;
                case 4 :
                         if(ListOfPlayer.size()>=3){
                            readyToPlay = true;
                            play();
                         }else{
                             printMessage("Sorry, please choose again!"+
                                        "\nminimum player is three player");
                             ListOfPlayer.clear();
                         }
            }
        }
        ListOfPlayer.clear();
    }
        
    
    public void choosePlayer(){
        System.out.println("\nMasukkan indexs pemain yang akan bermain (minimal 3)!");
        showRegisteredPlayer();
        System.out.print("Indeks pemain dipisahkan spasi : ");
        String line = scan.nextLine();
        line = scan.nextLine();
        String idx[] = line.split(" ");
        for(String i : idx){
            ListOfPlayer.add(ListOfRegisteredPlayer.elementAt(Integer.parseInt(i)-1));
        }
    }
    
    public void highScores(){
        for(int i = 0 ; i < ListOfRegisteredPlayer.size()-1 ; i++){
            Player p1 = ListOfRegisteredPlayer.elementAt(i);
            for(int j = i+1 ; j <ListOfRegisteredPlayer.size() ; j++){
                Player p2 = ListOfRegisteredPlayer.elementAt(j);
                if(p1.getScore() < p2.getScore()){
                    ListOfRegisteredPlayer.removeElementAt(i);
                    ListOfRegisteredPlayer.add(i,p2);
                    ListOfRegisteredPlayer.removeElementAt(j);
                    ListOfRegisteredPlayer.add(j,p1);
                }
            }
        }
        if(ListOfRegisteredPlayer.size() > 10 ){
            for(int i = 0 ; i < 10 ; i++){
                Player p = new Player();
                p = ListOfRegisteredPlayer.elementAt(i);
                System.out.println((i+1)+". "+p.getPlayerName()
                        +"  ["+p.getScore()+"]   <"+p.getDate()+">");
            }
        }else{
            for(int i = 0 ; i < ListOfRegisteredPlayer.size() ; i++){
                Player p = new Player();
                p = ListOfRegisteredPlayer.elementAt(i);
                System.out.println((i+1)+". "+p.getPlayerName()
                        +"  ["+p.getScore()+"]   <"+p.getDate()+">");
            }
        }
        backToPrevMenu();
    }

    public void help(){
        printMessage("Jika anda kebingungan silahkan hubungi 089619177393 [Daniar]");
    }
    public void about(){
        printMessage("Game ini di kerjakan oleh : Daniar, Hayyu, Icha, Khaidzir, dan Ramandika");
    }
    public void setting(){
        
    }
    
    public void start() throws FileNotFoundException, IOException{
        //FileReader fr = new FileReader("player.in");
//                loadFromExFile (fr);
                //addPlayer
        int indexMenu = 0;
        while(indexMenu!= 6){
            showMenuCMD();
                try {
                    indexMenu = scan.nextInt();
                    runMenu(indexMenu);
                }catch(Exception e){
                    printMessage("Sorry, your input in main menu is invalid!");
                }
            }
        System.out.print("==== Game closed ====");
    }
}
