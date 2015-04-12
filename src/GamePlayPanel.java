/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Untuk menampilkan semua panel dalam GUI
 * 
 */
public class GamePlayPanel extends ImagePanel {    
    private handCardGroup cardPanel;

    /**
     *deklarasi variable JSwing
     */
    public fieldCardGroup fieldPanel;
    private GridBagConstraints c = new GridBagConstraints();
    private JTextArea statusList;
    private JTextArea messageBox;
    public JButton giveUpButton,

    /**
     * Button untuk mengambil kartu
     */
    drawButton,

    /**
     * Button untuk memutar kartu 
     */
    rotateButton,

    /**
     *Button untuk mengakhiri giliran
     */
    finishTurnButton;

    /**
     *Panel untuk menyimpan Button-button
     */
    public JPanel panelButton;
    private int koorX, koorY;
    String tempPath = new String("img/fieldCard/forbiddenCard.png");
    private int  handIndex;
    GamePlay gamePlay = new GamePlay();
    boolean rotated;

    /**
     *untuk membuang kartu
     */
    public ImagePanel garbage;

    /**
     *konstruktor
     */
    public GamePlayPanel() {
        super("img/backgroundBoard.jpg");
        setLayout(new GridBagLayout());
        setOpaque(false);
        koorX = -1;
        koorY = -1;
        
        Init();
        ResetConstraints();
        
        rotated = false;
        
        /* Title */
        ImagePanel title = new ImagePanel("img/title/saboteur.png");
        title.setOpaque(false);
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(10,0,0,0);
        c.anchor = GridBagConstraints.NORTH;
        add(title,c);
        
        /* List status pemain */
        statusList = new JTextArea(20,20);
        statusList.setEditable(false);
        c.fill = GridBagConstraints.NONE;
        //c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 20, 0, 0);
        add(statusList,c);
        
        /* Field */
        ResetConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.CENTER;
        add(fieldPanel,c);
        
        /* Message box */
        messageBox = new JTextArea(20,25);
        messageBox.setEditable(false);
        messageBox.setOpaque(false);
        JScrollPane scroll = new JScrollPane(messageBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setOpaque(false);
        //c.fill = GridBagConstraints.VERTICAL;
        c.weighty = 0;
        c.gridy = 1;
        c.gridx = 2;
        c.insets = new Insets(0, 0, 0, 20);
        c.anchor = GridBagConstraints.EAST;
        add(messageBox,c);
        
        /* Garbage Collector */
        garbage = new ImagePanel("img/card/actioncard2.png");
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 10, 0);
        c.anchor = GridBagConstraints.CENTER;
        garbage.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
                if(handIndex != -1 && !gamePlay.currentPlayer.getFinishedTurn()){
                    geserKartu(handIndex);
                    gamePlay.currentPlayer.setFinishedTurn(true);
                    updatePlayerBox();
                }
            }
        });
        
        JLabel label = new JLabel("Graveyard");
        garbage.add(label);
        Font font = new Font("Verdana",2,14);
        label.setFont(font);
        add(garbage,c);
        
        /* Button - button */
        panelButton = new JPanel(new GridLayout(2,2));
        giveUpButton = new JButton("GIVE UP");
        drawButton = new JButton("DRAW");
        rotateButton = new JButton("ROTATE");
        finishTurnButton = new JButton("FINISH TURN");        
        
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    
                if(gamePlay.aDeck.NumberOfAvailableCard == 0){
                            gamePlay.currentPlayer.setFinishedDraw(true);
                            Object[] options = {"OK"};
                            int n = JOptionPane.showOptionDialog(
                            fieldPanel, "Kartu di deck sudah habis, anda tidak dapat draw kartu lagi", "Message",
                            JOptionPane.INFORMATION_MESSAGE,
                            JOptionPane.OK_OPTION,
                            null, options, options[0]);
                } else {
                    if(!gamePlay.currentPlayer.getFinishedDraw() && gamePlay.currentPlayer.getFinishedTurn()){
                        gamePlay.currentPlayer.setFinishedDraw(true);
                        Card crd = gamePlay.aDeck.popCard();
                        gamePlay.currentPlayer.drawCard(crd);
                        setCardOnHand(crd);
                        updatePlayerBox();
                        fieldPanel.updateClosedCard();
                    } else {
                        Object[] options = {"OK"};
                        int n = JOptionPane.showOptionDialog(
                        panelButton, "Tidak bisa draw!",
                        "Warning",
                        JOptionPane.ERROR_MESSAGE,
                        JOptionPane.OK_OPTION,
                        null,
                        options,
                        options[0]);
                    }
                }
            }
        });
        
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (handIndex != -1 && gamePlay.currentPlayer.CardsOnHand.elementAt(handIndex).getType() == 1) {
                    rotated = !rotated;
                    String pesan;
                    if (rotated) {
                        pesan = "Kartu telah diputar";
                        tempPath = "img/fieldCard/" + 
                                cardPanel.handCardPanel[handIndex].vPath.elementAt(0).substring(9, cardPanel.handCardPanel[handIndex].vPath.elementAt(0).length()-4)
                                +"Rotate.png";
                    } else {
                        pesan = "Kartu batal diputar";
                         tempPath = "img/fieldCard/" + 
                                cardPanel.handCardPanel[handIndex].vPath.elementAt(0).substring(9, cardPanel.handCardPanel[handIndex].vPath.elementAt(0).length());
                    }
                    Object[] options = {"OK"};
                    int n = JOptionPane.showOptionDialog(
                    fieldPanel, pesan, "Message",
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.OK_OPTION,
                    null, options, options[0]);
                }
               
            }
        });
        
        
        
        giveUpButton.setPreferredSize(new Dimension(120,40));
        drawButton.setPreferredSize(new Dimension(120,40));
        rotateButton.setPreferredSize(new Dimension(120,40));
        finishTurnButton.setPreferredSize(new Dimension(120,40));
        
        panelButton.add(rotateButton);
        panelButton.add(finishTurnButton);
        panelButton.add(drawButton);
        panelButton.add(giveUpButton);
        
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 2;
        add(panelButton,c);
        
        /* Hand Card */
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.SOUTH;
        c.insets = new Insets (0, 0, 10, 0);
        add(cardPanel,c);
        
        setVisible(true);
    }
    
    /**
     * Mengeset kartu di tangan pemain
     * @param crd
     */
    public void setCardOnHand(Card crd){
        int idx = gamePlay.currentPlayer.CardsOnHand.size()-1;
        int Id = crd.getID();
        int Type = crd.getType();
        String strType = new String();
        if(Type==1)
            strType = "path"+Id;
        else if(Type == 2)
            strType = "actioncard"+Id;
        try {
            cardPanel.handCardPanel[idx].card = ImageIO.read(new File("img/card/"+strType+".png"));
            cardPanel.handCardPanel[idx].cardSelect = ImageIO.read(new File("img/cardSelect/"+strType+"Select.png"));
            cardPanel.handCardPanel[idx].cardClick = ImageIO.read(new File("img/cardClick/"+strType+"Click.png"));
            cardPanel.handCardPanel[idx].vPath.clear();
            cardPanel.handCardPanel[idx].vPath.add("img/card/"+strType+".png");
            cardPanel.handCardPanel[idx].vPath.add("img/cardSelect/"+strType+"Select.png");
            cardPanel.handCardPanel[idx].vPath.add("img/cardClick/"+strType+"Click.png");
            cardPanel.handCardPanel[idx].ID = 4;
            cardPanel.handCardPanel[idx].image = cardPanel.handCardPanel[4].card;
            cardPanel.handCardPanel[idx].repaint();
        } catch (IOException ex) {
            Logger.getLogger(GamePlayPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * Menggeser kartu di tampilan gui
     * @param Idx
     */
    public void geserKartu(int Idx){
        System.out.println("mmm " + Idx);
        // ini buat cMD
        gamePlay.currentPlayer.CardsOnHand.removeElementAt(handIndex);
        gamePlay.currentPlayer.setFinishedTurn(true);
        try{
            for(int i = Idx+1 ; i < 5 ; i++){
                cardPanel.handCardPanel[i -1].card = ImageIO.read(new File(cardPanel.handCardPanel[i].vPath.elementAt(0)));                        
                cardPanel.handCardPanel[i -1].cardSelect = ImageIO.read(new File(cardPanel.handCardPanel[i].vPath.elementAt(1)));
                cardPanel.handCardPanel[i -1].cardClick = ImageIO.read(new File(cardPanel.handCardPanel[i].vPath.elementAt(2)));
                cardPanel.handCardPanel[i -1].vPath = (Vector<String>) cardPanel.handCardPanel[i].vPath.clone();
                cardPanel.handCardPanel[i -1].ID = i-1;
                cardPanel.handCardPanel[i -1].image = cardPanel.handCardPanel[i-1].card;
                cardPanel.handCardPanel[i -1].repaint();
            }
            int lastcard = 4;
            cardPanel.handCardPanel[lastcard].card = ImageIO.read(new File("img/card/blank.png"));
            cardPanel.handCardPanel[lastcard].cardSelect = ImageIO.read(new File("img/card/blank.png"));
            cardPanel.handCardPanel[lastcard].cardClick = ImageIO.read(new File("img/card/blank.png"));
            cardPanel.handCardPanel[lastcard].vPath.clear();
            cardPanel.handCardPanel[lastcard].vPath.add("img/card/blank.png");
            cardPanel.handCardPanel[lastcard].vPath.add("img/card/blank.png");
            cardPanel.handCardPanel[lastcard].vPath.add("img/card/blank.png");
            cardPanel.handCardPanel[lastcard].ID = lastcard;
            cardPanel.handCardPanel[lastcard].image = cardPanel.handCardPanel[lastcard].card;
            cardPanel.handCardPanel[lastcard].repaint();

            cardPanel.repaint();
            cardPanel.handCardPanel[handIndex].clicked = false;
            handIndex = -1;
            tempPath = "img/fieldCard/forbiddenCard.png";
        }catch (IOException e){}
    }
            
    
    
    // semua berawal dari sini

    /**
     *Mengeset/ mengasign game play sesuai engine
     * @param game
     */
        public void setGamePlay(GamePlay game){
        gamePlay = game;
        updatePlayerBox();
        cardPanel.updateCardOnHand();
    }
    
    /**
     *mengupdate info player dalam game
     */
    public void updatePlayerBox() {
        String Status = new String();
        Font font = new Font("Verdana", 1, 12);
        Status+=("No  nama\n");
        for (Player p : gamePlay.ListOfPlayer){ 
            Status+=(p.getTurn() + ".  " + p.getPlayerName() + "    [" +
                    p.getStatus()+"]\n");
        }
        handIndex = -1;
        tempPath = "img/fieldCard/forbiddenCard.png";
        statusList.setText(Status);
        statusList.setOpaque(false);
        statusList.setFont(font);
        statusList.setForeground(Color.WHITE);
        String Msg = new String();
        Msg += "Hai, "+gamePlay.currentPlayer.getPlayerName()+"\n";
        if(gamePlay.currentPlayer.getRoleId()==1){
            Msg += "Anda adalah seorang GoldMiner.\n";
        } else
            Msg += "Anda adalah seorang Saboteur.\n";
        if(gamePlay.currentPlayer.getFinishedTurn() && !gamePlay.currentPlayer.getFinishedDraw())
            Msg += "Anda belum melakukan draw kartu baru\n";
        else if (gamePlay.currentPlayer.getFinishedDraw())
            Msg += "Giliran anda sudah berakhir\n";
        else
            Msg += "Anda belum menggunakan kartu satu pun\n";
        messageBox.setText(Msg);
        messageBox.setOpaque(false);
        messageBox.setFont(font);
        messageBox.setForeground(Color.WHITE);
        //scroll.setOpaque(false);
    }
    
    
    private void Init() {
        /* cardPanel */
        //ResetConstraints();
        cardPanel = new handCardGroup();

        /* fieldPanel */
        fieldPanel = new fieldCardGroup();

    }
    
    private void ResetConstraints() {
       c.weightx = 0.0;
       c.weighty = 0.0;
       c.ipadx = 0;
       c.ipady = 0;
       c.gridheight = 1;
       c.gridwidth = 1;
       c.anchor = GridBagConstraints.CENTER;
       c.fill = GridBagConstraints.NONE;
       c.insets = new Insets(0, 0, 0, 0);
    }
    
    /**
     *Kelas yang mngatur kumpulan kartu ditangan
     */
    public class handCardGroup extends JPanel {

        /**
         *kumpulan kartu ditangan
         */
        public handCard[] handCardPanel;
        //private int click;
//1=Path_Card, 2=Action_Card, 3=Character_Card
//1=ViewMap 2=break 3=repair
//1=GoldMiner 2=Saboteur

        /**
         *konstruktor
         */
                public handCardGroup() {
            
            
        }
        
        /**
         *mengupdate kartu di tangan
         */
        public void updateCardOnHand(){
            removeAll();
            ResetConstraints();
            setLayout(new GridBagLayout());
            c.insets = new Insets(0, 5, 0, 5);
            handCardPanel = new handCard[5];
            System.out.println("nih  "+gamePlay.currentPlayer.CardsOnHand.size());
            c.gridy = 0;
            int i;
            for (i=0; i<gamePlay.currentPlayer.CardsOnHand.size(); i++) {
                    int Id = gamePlay.currentPlayer.CardsOnHand.elementAt(i).getID();
                    int Type = gamePlay.currentPlayer.CardsOnHand.elementAt(i).getType();
                    String strType = new String();
                    if(Type==1)
                        strType = "path"+Id;
                    else if(Type == 2)
                        strType = "actioncard"+Id;
                    c.gridx = i;
                    System.out.println(strType);
                    handCardPanel[i] = new handCard("img/card/"+strType+".png", "img/cardSelect/"+strType+"Select.png",
                            "img/cardClick/"+strType+"Click.png", i);
                   // handCardPanel[i].repaint();
                    add(handCardPanel[i],c);                    
            }
            for (; i<5; i++) {
                handCardPanel[i] = new handCard("img/card/blank.png", "img/card/blank.png",
                            "img/card/blank.png", i);
            }
            handIndex = -1;
            //repaint();
        }
        
        class handCard extends ImagePanel {
            int ID;
            public Vector<String> vPath = new Vector<>();
            public boolean clicked;
            public BufferedImage card, cardSelect, cardClick;
            
            public handCard(final String path, String select, String click1, final int ID) {
                super(path);
                vPath.add(path);
                vPath.add(select);
                vPath.add(click1);               
                
                try {
                    card = ImageIO.read(new File(path));
                    cardSelect = ImageIO.read(new File(select));
                    cardClick = ImageIO.read(new File(click1));
                } catch(IOException e) { 
                    System.out.println(e.getMessage());
                }
                this.ID = ID;
                clicked = false;
                addMouseListener(new MouseAdapter() {
                   @Override
                   public void mousePressed(MouseEvent e) {
                       if (e.isMetaDown()) {
                           System.out.println("Tes");
                       } else {
                            changeImage();
                            if (handIndex != ID) {
                                if (handIndex != -1) handCardPanel[handIndex].changeImage();
                                handIndex = ID;
                                rotated = false;
                                if(gamePlay.currentPlayer.CardsOnHand.elementAt(handIndex).getType() == 2 
                                        && !gamePlay.currentPlayer.getFinishedTurn()){
                                    if(gamePlay.currentPlayer.CardsOnHand.elementAt(handIndex).getID() == 2) {      //break tool
                                        Object[] options = {"Pakai", "Buang"};
                                        int n = JOptionPane.showOptionDialog(
                                        fieldPanel, "Apa yang ingin anda lakukan?", "Konfirmasi",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                        null, options, options[0]);
                                        System.out.println("Ini pilihan " + n);
                                        if (n == 0) usingBreakTool();
                                    }else if(gamePlay.currentPlayer.CardsOnHand.elementAt(handIndex).getID() == 3) {      //repair tool
                                        Object[] options = {"Pakai", "Buang"};
                                        int n = JOptionPane.showOptionDialog(
                                        fieldPanel, "Apa yang ingin anda lakukan?", "Konfirmasi",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                        null, options, options[0]);
                                        System.out.println("Ini pilihan " + n);
                                        if (n == 0) usingRepairTool();
                                    }
                                }else   //mapCard
                                    tempPath = "img/fieldCard/" + vPath.elementAt(0).substring(9, vPath.elementAt(0).length());
                            } else {
                                 handIndex = -1;
                                 rotated = false;
                                 tempPath = "img/fieldCard/forbiddenCard.png";
                            }
                            System.out.println("Klik " + handIndex);

                            //System.out.println(clicked);
                       }
                   }
                   @Override
                   public void mouseEntered(MouseEvent e) {
                       System.out.println(ID);
                       if (!clicked) {
                           image = cardSelect;
                           repaint();
                       }
                   }
                   @Override
                   public void mouseExited(MouseEvent e) {
                       if (!clicked) {
                           image = card;
                           repaint();
                       }
                   }
                });
            }

            private void usingBreakTool() {
                String urutan = JOptionPane.showInputDialog(null, "Masukkan indeks pemain:", "Break Tool",
                JOptionPane.WARNING_MESSAGE);
                if(Integer.parseInt(urutan) < gamePlay.ListOfPlayer.size()){
                    Player victim = gamePlay.ListOfPlayer.get(Integer.parseInt(urutan));
                    if (victim.getStatus().compareTo("Enable")==0 && 
                            victim.getTurn()!=gamePlay.currentPlayer.getTurn()){
                        System.out.println("Masuk");
                       geserKartu(handIndex);
                       gamePlay.ListOfPlayer.elementAt(Integer.parseInt(urutan)).setStatus("Disable");
                       gamePlay.currentPlayer.setFinishedTurn(true);
                       updatePlayerBox();
                        Object[] options = {"OK"};
                        int n = JOptionPane.showOptionDialog(
                        fieldPanel, victim.getPlayerName()+" telah didisable!",
                        "Warning",
                        JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.OK_OPTION,
                        null,
                        options,
                        options[0]);
                    }else{
                        Object[] options = {"OK"};
                        int n = JOptionPane.showOptionDialog(
                        fieldPanel, "Player tidak valid!",
                        "Warning",
                        JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.OK_OPTION,
                        null,
                        options,
                        options[0]);
                    }
                }else{
                    Object[] options = {"OK"};
                    int n = JOptionPane.showOptionDialog(
                    fieldPanel, "Player tidak valid!",
                    "Warning",
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.OK_OPTION,
                    null,
                    options,
                    options[0]);
                }
            }
            
            private void usingRepairTool() {
                String urutan = JOptionPane.showInputDialog(null, "Masukkan indeks pemain:", "Repair Tool",
                JOptionPane.WARNING_MESSAGE);
                if(Integer.parseInt(urutan) < gamePlay.ListOfPlayer.size()){
                    Player victim = gamePlay.ListOfPlayer.get(Integer.parseInt(urutan));
                    if (victim.getStatus().compareTo("Disable")==0 && 
                            victim.getTurn()!=gamePlay.currentPlayer.getTurn()){
                        System.out.println("Masuk");
                       geserKartu(handIndex);
                       gamePlay.ListOfPlayer.elementAt(Integer.parseInt(urutan)).setStatus("Enable");
                       gamePlay.currentPlayer.setFinishedTurn(true);
                       updatePlayerBox();
                        Object[] options = {"OK"};
                        int n = JOptionPane.showOptionDialog(
                        fieldPanel, victim.getPlayerName()+" telah dienable!",
                        "Warning",
                        JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.OK_OPTION,
                        null,
                        options,
                        options[0]);
                    }else{
                        Object[] options = {"OK"};
                        int n = JOptionPane.showOptionDialog(
                        fieldPanel, "Player tidak valid!",
                        "Warning",
                        JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.OK_OPTION,
                        null,
                        options,
                        options[0]);
                    }
                }else{
                    Object[] options = {"OK"};
                    int n = JOptionPane.showOptionDialog(
                    fieldPanel, "Player tidak valid!",
                    "Warning",
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.OK_OPTION,
                    null,
                    options,
                    options[0]);
                }
            }
            
            /*public void setAttribute(String path1, String path2) {
                try{
                    this.path1 = path1;
                    this.path2 = path2;
                    image = ImageIO.read(new File(path1));
                } catch(IOException e) {
                    System.out.println(e.getMessage());
                }
                repaint();
            }*/

            private void changeImage() {
                clicked = !clicked;
                image = clicked ? cardClick : card;
                setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
                repaint();
            }
        }

    }
    
    /**
     * mengatur kartu yang ada di board
     */
    public class fieldCardGroup extends JPanel {

        /**
         *array of kartu di board
         */
        public fieldCard[][] fCard = new fieldCard[5][9];
        
        /**
         *konstruktor
         */
        public fieldCardGroup() {
            setLayout(new GridBagLayout());
            ResetConstraints();
            for (int i=0; i<5; i++) {
                c.gridy = i;
                for (int j=0; j<9; j++) {
                    // empty card
                    fCard[i][j] = new fieldCard("img/fieldCard/cardEmpty.png", "img/fieldCard/path2.png", i, j);
                    c.gridx = j;
                    add(fCard[i][j],c);
                }
            }
            try{
                fCard[2][8].image = ImageIO.read(new File("img/fieldCard/startcard.png"));
                updateClosedCard();
                //fCard[2][8].image = Resize(fCard[2][8], 0.5);
            } catch (IOException e) {}
            fCard[2][8].filled = true;
            fCard[2][8].repaint();
            fCard[0][0].filled = true;
            fCard[2][0].filled = true;
            fCard[4][0].filled = true;

        }
        
        /**
         *mengupdate kondisi kartu goal yang mungkin tertutup atau terebuka
         */
        public void updateClosedCard(){
            gamePlay.board.openGoal();
            try {
                if (!gamePlay.board.GoalCondition[0]) {   
                    fCard[0][0].filled = false;
                    System.out.println("mau 1 " +gamePlay.board.MatrixOfCard[1][1].getCenter());
                    if(gamePlay.board.MatrixOfCard[1][1].getCenter() == 'G')
                        fCard[0][0].image = ImageIO.read(new File("img/fieldCard/goldcard.png"));
                    else
                        fCard[0][0].image = ImageIO.read(new File("img/fieldCard/rockcard.png"));
                } else 
                    fCard[0][0].image = ImageIO.read(new File("img/fieldCard/closedCard.png"));
                
                if (!gamePlay.board.GoalCondition[1]) { 
                    fCard[2][0].filled = false;
                    System.out.println("mau 2 " +gamePlay.board.MatrixOfCard[3][1].getCenter());
                    if(gamePlay.board.MatrixOfCard[3][1].getCenter() == 'G')
                        fCard[2][0].image = ImageIO.read(new File("img/fieldCard/goldcard.png"));
                    else
                        fCard[2][0].image = ImageIO.read(new File("img/fieldCard/rockcard.png"));
                } else 
                    fCard[2][0].image = ImageIO.read(new File("img/fieldCard/closedCard.png"));
                
                if (!gamePlay.board.GoalCondition[2]) {    
                    fCard[4][0].filled = false;
                    System.out.println("mau 3 " +gamePlay.board.MatrixOfCard[5][1].getCenter());
                    if(gamePlay.board.MatrixOfCard[5][1].getCenter() == 'G')
                        fCard[4][0].image = ImageIO.read(new File("img/fieldCard/goldcard.png"));
                    else
                        fCard[4][0].image = ImageIO.read(new File("img/fieldCard/rockcard.png"));
                } else 
                    fCard[4][0].image = ImageIO.read(new File("img/fieldCard/closedCard.png"));
                fCard[0][0].repaint();
                fCard[2][0].repaint();
                fCard[4][0].repaint();
                fCard[0][0].filled = true;
                fCard[2][0].filled = true;
                fCard[4][0].filled = true;
            } catch(IOException e) {}
              System.out.println(gamePlay.board.GoalCondition[0] + " " + gamePlay.board.GoalCondition[1] + " " + gamePlay.board.GoalCondition[2]);
        }
        
        /**
         *kelas yang mengatur gambar pada kartu di board
         */
        public class fieldCard extends ImagePanel {
            public BufferedImage cardEmpty,

            /**
             * memilih kartu
             */
            cardSelect;
            private int[] ID = new int[2];
            private boolean filled;
            
            /**
             *
             * @param empty
             * @param select
             * @param x
             * @param y
             */
            public fieldCard(String empty, String select, int x, int y) {
                super(empty);
                setOpaque(false);
                try {
                    cardEmpty = ImageIO.read(new File(empty));
                    cardSelect = ImageIO.read(new File(select));
                } catch(IOException e) {
                     System.out.println(e.getMessage());
                }
                filled = false;
                ID[0] = x;
                ID[1] = y;
                addMouseListener(new MouseAdapter() {
                   @Override
                    public void mouseEntered(MouseEvent e) {
                        if (!filled) {
                            //System.out.println("Koor " + ID[0] + "," + ID[1]);
                            try{
                                cardSelect = ImageIO.read(new File(tempPath));
                            } catch(IOException ex) {}
                            image = cardSelect;
                            repaint();
                        }
                    }

                   @Override
                   public void mousePressed(MouseEvent e) {
                       if (handIndex != -1)  {
                           koorX = ID[0]+1;
                           koorY = ID[1]+1;
                           System.out.println("super sekali "+koorX+"   "+koorY + "    "+gamePlay.currentPlayer.CardsOnHand.elementAt(handIndex).getID());
                           Card crd = gamePlay.currentPlayer.CardsOnHand.elementAt(handIndex);
                            Vector<Integer> pos = new Vector(); pos.add(0,koorX);
                                pos.add(1,koorY);
                            if(crd.getType()==1 && !gamePlay.currentPlayer.getFinishedTurn() && !gamePlay.currentPlayer.getFinishedDraw()){
                                if(rotated){
                                    ((Path_Card) crd).rotateCard();
                                    
                                }
                                if (-1 != gamePlay.board.putCardOnBoard((Path_Card) crd, pos)){
                                    filled = true;
                                    geserKartu(handIndex);
                                    updatePlayerBox();
                                    updateClosedCard();
                                    System.out.println("saya krluar");
                                }
                           } else if (gamePlay.currentPlayer.CardsOnHand.elementAt(handIndex).getID() == 1 
                                        && !gamePlay.currentPlayer.getFinishedTurn()) {
                               System.out.println("ssmau 1 " +gamePlay.board.GoalCondition[0]+ "  " +gamePlay.board.GoalCondition[1]+"  "+gamePlay.board.GoalCondition[2]);
                                System.out.println("koor " + koorX + " " + koorY);
                               try{
                                if(gamePlay.aDeck.NumberOfAvailableCard == 0){
                                    gamePlay.currentPlayer.setFinishedDraw(true);
                                }
                                if (koorY == 1 && koorX == 1 && gamePlay.board.GoalCondition[0]) {
                                    fCard[0][0].filled = false;
                                    System.out.println("ssmau 1 " +gamePlay.board.MatrixOfCard[1][1].getCenter());
                                    if(gamePlay.board.MatrixOfCard[1][1].getCenter() == 'G')
                                        fCard[0][0].image = ImageIO.read(new File("img/fieldCard/goldcard.png"));
                                    else
                                        fCard[0][0].image = ImageIO.read(new File("img/fieldCard/rockcard.png"));
                                    fCard[0][0].filled = true;
                                    fCard[0][0].repaint();
                                    gamePlay.currentPlayer.setFinishedTurn(true);
                                    geserKartu(handIndex);
                                    updatePlayerBox();
                                }
                                if (koorY == 1 && koorX == 3 && gamePlay.board.GoalCondition[1]) {
                                    fCard[2][0].filled = false;
                                    System.out.println("ssmau 1 " +gamePlay.board.MatrixOfCard[3][1].getCenter());
                                    if(gamePlay.board.MatrixOfCard[3][1].getCenter() == 'G')
                                        fCard[2][0].image = ImageIO.read(new File("img/fieldCard/goldcard.png"));
                                    else
                                        fCard[2][0].image = ImageIO.read(new File("img/fieldCard/rockcard.png"));
                                    fCard[2][0].filled = true;
                                    fCard[2][0].repaint();
                                    gamePlay.currentPlayer.setFinishedTurn(true);
                                    geserKartu(handIndex);
                                    updatePlayerBox();
                                }
                                
                                if (koorY == 1 && koorX == 5 && gamePlay.board.GoalCondition[2]) {
                                    fCard[4][0].filled = false;
                                    System.out.println("ssmau 1 " +gamePlay.board.MatrixOfCard[5][1].getCenter());
                                    if(gamePlay.board.MatrixOfCard[5][1].getCenter() == 'G')
                                        fCard[4][0].image = ImageIO.read(new File("img/fieldCard/goldcard.png"));
                                    else
                                        fCard[4][0].image = ImageIO.read(new File("img/fieldCard/rockcard.png"));
                                    fCard[4][0].filled = true;
                                    fCard[4][0].repaint();
                                    gamePlay.currentPlayer.setFinishedTurn(true);
                                    geserKartu(handIndex);
                                    updatePlayerBox();
                                }
                               }catch(IOException exe) {}
                            }
                        }
                   }
                   
                   @Override
                   public void mouseExited(MouseEvent e) {
                        if (!filled) {
                            image = cardEmpty;
                            repaint();
                        }
                   }
                });
            }
            
        }
    }
    
    
}

