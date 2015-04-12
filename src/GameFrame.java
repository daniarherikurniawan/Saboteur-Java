/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Kelas frame utama game
 * @author user
 */
public class GameFrame extends JFrame {
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HEIGHT = 720;

    
    private JPanel basePanel;
    private JPanel[] gamePanel;
    private static final int numberOfPanel = 11;
    GridBagConstraints c = new GridBagConstraints();
    GamePlay gamePlay = new GamePlay();
    private JLabel JLabel;

    /**
     *  Konstruktor GameFrame
     */
    public GameFrame() {
        super("Saboteur");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        Init();
        
        basePanel.add(gamePanel[0], "MAIN MENU");
        basePanel.add(gamePanel[1], "START");
        basePanel.add(gamePanel[2], "HIGH SCORE");
        basePanel.add(gamePanel[3], "SETTINGS");
        basePanel.add(gamePanel[4], "HELP");
        basePanel.add(gamePanel[5], "ABOUT");
        basePanel.add(gamePanel[6], "GAMEPLAY");
        basePanel.add(gamePanel[7], "WIN");
        basePanel.add(gamePanel[8], "SHOW REGISTERED PLAYER");
        basePanel.add(gamePanel[9], "CHOOSE PLAYER");
        basePanel.add(gamePanel[10], "SWAP TURN");
        
        add(basePanel);
        setVisible(true);
    }
    
    private void Init() {
        basePanel = new JPanel();
        basePanel.setLayout(new CardLayout());
        gamePanel = new JPanel[numberOfPanel];
        
        gamePanel[0] = new ImagePanel("img/entrance.png");
        for (int i=1; i<6; i++) {
            gamePanel[i] = new ImagePanel("img/backgroundBoard.jpg");
        }
        gamePanel[6] = new GamePlayPanel();
        
        for (int i=7; i<numberOfPanel; i++) {
            gamePanel[i] = new ImagePanel("img/backgroundBoard.jpg");
        }
        

        InitPanel0();       //MAIN MENU
        InitPanel1();       //START
        InitPanel2();       //HIGH SCORE
        InitPanel3();       //SETTINGS
        InitPanel4();       //HELP
        InitPanel5();       //ABOUT
        InitPanel6();       //GAME PLAY
        //InitPanel7();       //WIN
        //InitPanel8();       //SHOW REGISTERED PLAYER
        //InitPanel9();       //CHOOSE PLAYER
        
        
    }
    
    /* Set Panel */
    private void InitPanel0() {     //MAIN MENU
        gamePanel[0].setLayout(new GridBagLayout());
        
        /* Panel untuk tombol - tombol di menu utama */
        final JPanel baseMenu = new JPanel();
        baseMenu.setLayout(new CardLayout());
        /* tombol - tombol menu utama */
        final JButton[] menuButton = new JButton[6];
       // ImageIcon iconMenu = new ImageIcon("img/center.png");
       // menuButton[0] = new JButton(/*"START",*/iconMenu);
       // menuButton[1] = new JButton(/*"HIGH SCORE",*/ iconMenu);
    //    menuButton[2] = new JButton(/*"SETTINGS",*/ iconMenu);
      //  menuButton[3] = new JButton(/*"HELP", */iconMenu);
   //     menuButton[4] = new JButton(/*"ABOUT",*/ iconMenu);
   //     menuButton[5] = new JButton(/*"EXIT",*/ iconMenu);
        
        menuButton[0] = new JButton("START");
        menuButton[1] = new JButton("HIGH SCORE");
        menuButton[2] = new JButton("SETTINGS");
        menuButton[3] = new JButton("HELP");
        menuButton[4] = new JButton("ABOUT");
        menuButton[5] = new JButton("EXIT");
        /*try {
            BufferedImage img = ImageIO.read(new File("img/button.png"));
            for (int i=0; i<6; i++) {
                menuButton[i].setIcon(new ImageIcon(img));
                menuButton[i].setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
                //menuButton[i].setPreferredSize(new Dimension(FRAME_WIDTH/6, FRAME_HEIGHT/11));
                baseMenu.add(menuButton[i]);
            }
        } catch(IOException e) {}*/
        for (int i=0; i<6; i++) {
            menuButton[i].setPreferredSize(new Dimension(FRAME_WIDTH/6, FRAME_HEIGHT/11));
           // menuButton[i].setPreferredSize(new Dimension(iconMenu.getIconWidth(), iconMenu.getIconHeight()));
            baseMenu.add(menuButton[i]);
        }
        
        /*JLabel[] label = new JLabel[6];
        label[0] = new JLabel("START");
        menuButton[0].setLayout(new GridBagLayout());
        menuButton[0].add(label[0], c);
        Font font = new Font("Verdana" , Font.BOLD, 18);
        label[0].setFont(font);    */   
        
        /* Listener untuk tombol - tombol menu utama */
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout)basePanel.getLayout();
                if (e.getSource() == menuButton[0]) {
                    cardLayout.show(basePanel, "START");
                }
                if (e.getSource() == menuButton[1]) {
                    cardLayout.show(basePanel, "HIGH SCORE");
                }
                if (e.getSource() == menuButton[2]) {
                    cardLayout.show(basePanel, "SETTINGS");
                }
                if (e.getSource() == menuButton[3]) {
                    cardLayout.show(basePanel, "HELP");
                }
                if (e.getSource() == menuButton[4]) {
                    cardLayout.show(basePanel, "ABOUT");
                }
                if (e.getSource() == menuButton[5]) {
                    System.exit(0);
                }
            } 
        };
        /* Menambahkan Listener ke tombol di menu utama */
        for (int i=0; i<6; i++) {
            menuButton[i].addActionListener(action);
        }
        
        JButton prev = new JButton("<<");
        JButton next = new JButton(">>");
        prev.setPreferredSize(new Dimension(FRAME_WIDTH/15, FRAME_HEIGHT/11));
        next.setPreferredSize(new Dimension(FRAME_WIDTH/15, FRAME_HEIGHT/11));
        prev.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               CardLayout cl = (CardLayout) baseMenu.getLayout();
               cl.previous(baseMenu);
           }
        });
        next.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               CardLayout cl = (CardLayout) baseMenu.getLayout();
               cl.next(baseMenu);
           }
        });
        
        /* Panel untuk pilihan menu berisi baseMenu, prev, dan next */
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridBagLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        //c.ipady = FRAME_HEIGHT/20;
        panelMenu.add(prev,c);
        
        c.gridx = 1;
        c.gridwidth = 1;
        //c.ipadx = FRAME_WIDTH/10;
        panelMenu.add(baseMenu, c);
        
        c.gridx = 12;
        c.gridwidth = 1;
        //c.ipadx = 0;
        panelMenu.add(next, c);
        
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.anchor = GridBagConstraints.SOUTH;
        c.insets = new Insets(0, 0, FRAME_HEIGHT/15, 0);
        gamePanel[0].add(panelMenu, c);
        
        
        /*c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.9;
        //c.weighty = 0.8;
        c.anchor = GridBagConstraints.NORTH;
        for (int i=0; i<5; i++) {
            c.gridx = i;
            gamePanel[0].add(menuButton[i], c);
        }
        
        c.gridy = 2;
        c.gridx = 2;
        c. anchor = GridBagConstraints.NORTH;
        gamePanel[0].add(menuButton[5],c);*/
        
    }
    private void InitPanel1() {     //START
        gamePanel[1].setLayout(new GridBagLayout());
        ResetConstraints();
        
        /* Label */
        ImagePanel g = new ImagePanel("img/title/play.png");
        g.setOpaque(false);
        c.gridx = 0;
        c.gridy = 0;
        //c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(FRAME_HEIGHT/20, 0, 0, 0);
        gamePanel[1].add(g, c);
        
        /* Pilihan Menu Play */
        //Panel
        final JPanel base = new JPanel(new CardLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 0, FRAME_HEIGHT/20));
        buttonPanel.setOpaque(false);
        JPanel addPlayerPanel = new JPanel(new GridBagLayout());
        //Atribut addPlayerPanel
        //Label
        JLabel pesan = new JLabel("Masukkan nama pemain");
        pesan.setForeground(Color.WHITE);
        //Text area
        final JTextArea ta = new JTextArea();
        final JButton addButton = new JButton("Add");
        //Button
        final JButton showPlayerButton = new JButton("SHOW REGISTERED PLAYER");
        final JButton addPlayerButton = new JButton("ADD PLAYER");
        final JButton choosePlayerButton = new JButton("CHOOSE PLAYER");
        final JButton playButton = new JButton("PLAY");
        final JButton buttonMenu = new JButton("MAIN MENU");
        
        //Ukuran button
        showPlayerButton.setPreferredSize(new Dimension(FRAME_WIDTH/5, FRAME_HEIGHT/12));
        addPlayerButton.setPreferredSize(new Dimension(FRAME_WIDTH/5, FRAME_HEIGHT/12));
        choosePlayerButton.setPreferredSize(new Dimension(FRAME_WIDTH/5, FRAME_HEIGHT/12));
        playButton.setPreferredSize(new Dimension(FRAME_WIDTH/5, FRAME_HEIGHT/12));
        buttonMenu.setPreferredSize(new Dimension(FRAME_WIDTH/5, FRAME_HEIGHT/12));
        
        ta.setPreferredSize(new Dimension(300,100));
        
        //Listener
        ActionListener action = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonMenu) {
                    CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                    gamePlay.ListOfPlayer.clear();
                    for(int i = 0 ; i < gamePlay.ListOfPlayer.size() ; i ++)
                        gamePlay.ListOfPlayer.get(i).CardsOnHand.clear();
                    cardLayout.show(basePanel, "MAIN MENU");
                }
                if (e.getSource() == addPlayerButton) {
                    CardLayout cardLayout = (CardLayout) base.getLayout();
                    cardLayout.next(base);
                }
                if (e.getSource() == showPlayerButton) {
                    CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                    InitPanel8();
                    cardLayout.show(basePanel, "SHOW REGISTERED PLAYER");
                }
                if (e.getSource() == addButton) {
                    CardLayout cardLayout = (CardLayout) base.getLayout();
                    String name = ta.getText().replace("\\s", "");
                    if(!name.isEmpty()){
                        Player myPlayer = new Player(name);
                        myPlayer.setDate(new Date().toString());
                        if(gamePlay.IsNotRegistered(name)){
                            gamePlay.ListOfRegisteredPlayer.add(myPlayer);
                            gamePlay.addPlayerToFile(myPlayer);
                        }
                    }
                    cardLayout.previous(base);
                }
                if (e.getSource() == playButton) {
                    if (gamePlay.ListOfPlayer.size() < 3) {
                        Object[] options = {"OK"};
                        int n = JOptionPane.showOptionDialog(
                        gamePanel[1], "Jumlah pemain minimal 3 orang",
                        "Warning",
                        JOptionPane.ERROR_MESSAGE,
                        JOptionPane.OK_OPTION,
                        null,
                        options,
                        options[0]);
                    } else {
                        CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                        gamePlay.setRoleAndTurnForPlayer();
                        gamePlay.aDeck.fillDeck();
                        for(Player p : gamePlay.ListOfPlayer){
                            for (int i=0;i<5;i++){
                                p.drawCard(gamePlay.aDeck.popCard());
                            }
                        }
                        // semua berawal awal dari sini
                        gamePlay.currentPlayer = gamePlay.ListOfPlayer.elementAt(0);
                        ((GamePlayPanel)gamePanel[6]).setGamePlay(gamePlay);
                        InitPanel10();
                        cardLayout.show(basePanel, "SWAP TURN");
                        //cardLayout.show(basePanel, "GAMEPLAY");
                    }
                }
                if (e.getSource() == choosePlayerButton) {
                    InitPanel9();
                    CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                    cardLayout.show(basePanel, "CHOOSE PLAYER");
                }
            }
        };
        showPlayerButton.addActionListener(action);
        addPlayerButton.addActionListener(action);
        choosePlayerButton.addActionListener(action);
        buttonMenu.addActionListener(action);
        addButton.addActionListener(action);
        playButton.addActionListener(action);
        
        //Ditambahkan
        
        buttonPanel.add(playButton);
        buttonPanel.add(showPlayerButton);
        buttonPanel.add(addPlayerButton);
        buttonPanel.add(choosePlayerButton);
        buttonPanel.add(buttonMenu);        
        
        //addPlayerPanel
        ResetConstraints();
        addPlayerPanel.setOpaque(false);
        c.gridx = 0;
        c.gridy = 0;
        addPlayerPanel.add(pesan,c);
        c.gridy = 1;
        addPlayerPanel.add(ta,c);
        c.gridy = 2;
        addPlayerPanel.add(addButton,c);
        
        //base
        base.setOpaque(false);
        base.add(buttonPanel);
        base.add(addPlayerPanel);
        //Ditambahkan
        c.gridy = 1;
        c.insets = new Insets(FRAME_HEIGHT/8, 0, 10, 0);
        gamePanel[1].add(base, c);
        
    }
    private void InitPanel2() {     //HIGH SCORE
        gamePanel[2].setLayout(new GridBagLayout());
        ResetConstraints();
        
        ImagePanel g = new ImagePanel("img/title/highscores.png");
        g.setOpaque(false);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(FRAME_HEIGHT/20, 0, 0, 0);
        gamePanel[2].add(g, c);
        
        JTextArea ta = new JTextArea();
        String myStr = new String();
        for(int i = 0 ; i < gamePlay.ListOfRegisteredPlayer.size()-1 ; i++){
        Player p1 = gamePlay.ListOfRegisteredPlayer.elementAt(i);
                for(int j = i+1 ; j <gamePlay.ListOfRegisteredPlayer.size() ; j++){
                        Player p2 = gamePlay.ListOfRegisteredPlayer.elementAt(j);
                        if(p1.getScore() < p2.getScore()){
                                gamePlay.ListOfRegisteredPlayer.removeElementAt(i);
                                gamePlay.ListOfRegisteredPlayer.add(i,p2);
                                gamePlay.ListOfRegisteredPlayer.removeElementAt(j);
                                gamePlay.ListOfRegisteredPlayer.add(j,p1);
                        }
                }
        }
        if(gamePlay.ListOfRegisteredPlayer.size() > 10 ){
                for(int i = 0 ; i < 10 ; i++){
                        Player p = new Player();
                        p = gamePlay.ListOfRegisteredPlayer.elementAt(i);
                        myStr +=((i+1)+". "+p.getPlayerName()
                                        +"  ["+p.getScore()+"]   <"+p.getDate()+">\n");
                }
        }else{
                for(int i = 0 ; i < gamePlay.ListOfRegisteredPlayer.size() ; i++){
                        Player p = new Player();
                        p = gamePlay.ListOfRegisteredPlayer.elementAt(i);
                        myStr += ((i+1)+". "+p.getPlayerName()
                                        +"  ["+p.getScore()+"]   <"+p.getDate()+">\n");
                }
        }
        ta.setOpaque(false);
        ta.setText(myStr);
        ta.setEditable(false);
        ta.setPreferredSize(new Dimension(FRAME_WIDTH/2, (int)((double)FRAME_HEIGHT * 0.6)));
        //ta.setBackground(Color.blue);
        Font font = new Font("Verdana", 1, 18);
        ta.setFont(font);
        ta.setForeground(Color.WHITE);
        c.gridy = 1;
        gamePanel[2].add(ta,c);
        
        JButton buttonMenu = new JButton("MAIN MENU");
        buttonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                cardLayout.show(basePanel, "MAIN MENU");
            }
        });
        c.gridy = 2;
        c.weighty = 1;
        c.weightx = 1;
        c.ipadx = FRAME_WIDTH/15;
        c.ipady = FRAME_HEIGHT/20;
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.insets = new Insets(0, 10, 10, 0);    //top, left, bottom, right
        gamePanel[2].add(buttonMenu, c);
    }
    private void InitPanel3() {     //SETTINGS
        gamePanel[3].setLayout(new GridBagLayout());
        ResetConstraints();
        
        ImagePanel g = new ImagePanel("img/title/settings.png");
        g.setOpaque(false);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(FRAME_HEIGHT/20, 0, 0, 0);
        gamePanel[3].add(g, c);
        
        JButton buttonMenu = new JButton("MAIN MENU");
        buttonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                cardLayout.show(basePanel, "MAIN MENU");
            }
        });
        c.gridy = 1;
        c.weighty = 1;
        c.weightx = 1;
        c.ipadx = FRAME_WIDTH/15;
        c.ipady = FRAME_HEIGHT/20;
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.insets = new Insets(0, 10, 10, 0);    //top, left, bottom, right
        gamePanel[3].add(buttonMenu, c);
    }
    private void InitPanel4() {     //HELP
        gamePanel[4].setLayout(new GridBagLayout());
        ResetConstraints();
        
        ImagePanel g = new ImagePanel("img/title/help.png");
        g.setOpaque(false);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(FRAME_HEIGHT/20, 0, 0, 0);
        gamePanel[4].add(g, c);
        
        JButton buttonMenu = new JButton("MAIN MENU");
        buttonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                cardLayout.show(basePanel, "MAIN MENU");
            }
        });
        c.gridy = 1;
        c.weighty = 1;
        c.weightx = 1;
        c.ipadx = FRAME_WIDTH/15;
        c.ipady = FRAME_HEIGHT/20;
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.insets = new Insets(0, 10, 10, 0);    //top, left, bottom, right
        gamePanel[4].add(buttonMenu, c);
    }
    private void InitPanel5() {     //ABOUT
        gamePanel[5].setLayout(new GridBagLayout());
        ResetConstraints();
        
        ImagePanel g = new ImagePanel("C:/Users/user/Desktop/tesAbout.png");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(FRAME_HEIGHT/20, 0, 0, 0);
        gamePanel[5].add(g, c);
        
        JButton buttonMenu = new JButton("MAIN MENU");
        buttonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                cardLayout.show(basePanel, "MAIN MENU");
            }
        });
        c.gridy = 1;
        c.weighty = 1;
        c.weightx = 1;
        c.ipadx = FRAME_WIDTH/15;
        c.ipady = FRAME_HEIGHT/20;
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.insets = new Insets(0, 10, 10, 0);    //top, left, bottom, right
        gamePanel[5].add(buttonMenu, c);
    }
    private void InitPanel6() {     //GAME PLAY
       ((GamePlayPanel)gamePanel[6]).giveUpButton.addActionListener(new ActionListener() {
          
          @Override
          public void actionPerformed(ActionEvent e) {
              if(gamePlay.ListOfPlayer.size()>=4){
                int IdxPrevPlayer = gamePlay.currentPlayer.getTurn();
                gamePlay.setNextPlayer(); 
                gamePlay.changeTurn(IdxPrevPlayer);
                gamePlay.ListOfPlayer.removeElementAt(IdxPrevPlayer);
                ((GamePlayPanel)gamePanel[6]).setGamePlay(gamePlay);
                CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                cardLayout.show(basePanel, "GAMEPLAY");
              }else{
                // 1 : goldminer
                  //2 saboteur
                  int IdWinner = gamePlay.whoIsTheWinner();
                  if (IdWinner!= 0) {
                    gamePlay.GivePrize(IdWinner);
                    CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                    InitPanel7(IdWinner);
                    cardLayout.show(basePanel, "WIN");
                  }else if (!gamePlay.board.useIsExistBranch()){
                      //jalan buntu
                      // saboteur menang
                      gamePlay.GivePrize(2);
                      CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                      InitPanel7(IdWinner);
                      cardLayout.show(basePanel, "WIN");
                  }
              }
          }
       });
       
        ((GamePlayPanel)gamePanel[6]).finishTurnButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!gamePlay.board.isFinished() && gamePlay.ListOfPlayer.size() >= 3){
                if(gamePlay.currentPlayer.getFinishedDraw() || 
                        (gamePlay.aDeck.NumberOfAvailableCard == 0 && gamePlay.currentPlayer.getFinishedTurn())){
                    gamePlay.setNextPlayer();
                    ((GamePlayPanel)gamePanel[6]).fieldPanel.updateClosedCard();
                    ((GamePlayPanel)gamePanel[6]).setGamePlay(gamePlay);
                    InitPanel10();
                    CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                    //cardLayout.show(basePanel, "GAMEPLAY");
                    cardLayout.show(basePanel, "SWAP TURN");
                } else {
                    Object[] options = {"OK"};
                    int n = JOptionPane.showOptionDialog(
                    ((GamePlayPanel)gamePanel[6]).panelButton, "Belum bisa mengakhiri giliran!",
                    "Warning",
                    JOptionPane.ERROR_MESSAGE,
                    JOptionPane.OK_OPTION,
                    null,
                    options,
                    options[0]);
                }
            }else{
                CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                // goldminer
                cardLayout.show(basePanel, "WIN");
            }
        }
        });
    }
    private void InitPanel7(int Id) {     //WIN
        ResetConstraints();
        gamePanel[7].setLayout(new GridBagLayout());
        JTextArea pesan = new JTextArea();
        String list = new String();
        for(int i = 0 ;  i < gamePlay.ListOfPlayer.size(); i ++){
            if(gamePlay.ListOfPlayer.get(i).getRoleId()==Id)
                 list = gamePlay.ListOfPlayer.get(i).getPlayerName() + "\t" +
                         gamePlay.ListOfPlayer.get(i).getScore()+"\n";
        }
        String Pesan = new String();
        if(Id == 2){
            // Sabot
            Pesan = "Saboteur Win\n" + list;
            
        }else{
            Pesan = "GoldMinner Win\n" + list;
            
        }
        
        pesan.setText(Pesan);
        
        JButton back = new JButton("MAIN MENU");
        back.addActionListener(new ActionListener() {
            @Override   
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout)basePanel.getLayout();
                gamePlay.ListOfPlayer.clear();
                cardLayout.show(basePanel, "MAIN MENU");
            }            
        });
        c.gridx = 0;
        c.gridy = 0;
        gamePanel[7].add(pesan,c);
        c.gridy = 1;
        gamePanel[7].add(back,c);
    }
    private void InitPanel8() {     //SHOW REGISTERED PLAYER
        ResetConstraints();
        Font font = new Font("Verdana", 1, 12);
        gamePanel[8].setLayout(new GridBagLayout());
        JTextArea playerList = new JTextArea(25,60);
        playerList.setOpaque(false);
        String myStr = new String();
        for(int i = 0 ; i < gamePlay.ListOfRegisteredPlayer.size() ; i++){
            Player p = new Player();
            p = gamePlay.ListOfRegisteredPlayer.elementAt(i);
            myStr += ((i+1)+". "+p.getPlayerName()
                            +"  ["+p.getScore()+"]   <"+p.getDate()+">\n");
        }
        playerList.setText(myStr);
        playerList.setEditable(false);
        playerList.setFont(font);
        JScrollPane scroll = new JScrollPane (playerList, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setOpaque(false);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        gamePanel[8].add(scroll,c);
        
        JButton backButton = new JButton("BACK");
        backButton.setPreferredSize(new Dimension(FRAME_WIDTH/10, FRAME_HEIGHT/20));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) basePanel.getLayout();
                cardLayout.show(basePanel, "START");
                gamePanel[8].removeAll();
            }
        });
        c.gridy = 1;
        c.anchor = GridBagConstraints.SOUTHWEST;
        gamePanel[8].add(backButton,c);
        
    }
    private void InitPanel9() {     //CHOOSE PLAYER
        ResetConstraints();
        gamePanel[9].setLayout(new GridBagLayout());
        Font font = new Font ("Verdana", 1, 12);
        /* Daftar Player */
        JTextArea playerList = new JTextArea(40,20);
        String myStr = new String();
        for(int i = 0 ; i < gamePlay.ListOfRegisteredPlayer.size() ; i++){
            Player p = new Player();
            p = gamePlay.ListOfRegisteredPlayer.elementAt(i);
            myStr += ((i+1)+". "+p.getPlayerName()+"\n");
        }
        playerList.setText(myStr);
        playerList.setEditable(false);
        playerList.setOpaque(false);
        playerList.setFont(font);
        playerList.setForeground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(playerList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                           JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.WEST;
        gamePanel[9].add(playerList,c);
        
        /* Input */
        JPanel pane = new JPanel(new GridBagLayout());
        final JTextField choosePlayer = new JTextField(20);
        JScrollPane scroll1 = new JScrollPane(choosePlayer, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                                             JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton ok = new JButton("OK");
        
        /* Label */
        JLabel pesan = new JLabel("Masukkan indeks pemain");
        pesan.setForeground(Color.WHITE);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout)basePanel.getLayout();
                cardLayout.show(basePanel, "START");
                gamePanel[9].removeAll();
            }
        });
        
        pane.setOpaque(false);
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        pane.add(pesan,c);
        
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        pane.add(scroll1, c);
        
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 0.5;
        pane.add(add,c);
        
        c.gridx = 1;
        pane.add(ok,c);
        
        c.gridx = 2;
        pane.add(remove,c);
        
        c.gridx = 1;
        c.gridy = 0;
        //c.weightx = 0.5;
        //c.weighty = 0.5;
        c.anchor = GridBagConstraints.NORTH;
        gamePanel[9].add(pane, c);
        
        /* Daftar player yang terpilih */
        final JTextArea playerChoosen = new JTextArea(40,20);
      
        
        playerChoosen.setEditable(false);
        playerChoosen.setOpaque(false);
        playerChoosen.setFont(font);
        JScrollPane scroll2 = new JScrollPane(playerChoosen, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                             JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll2.setOpaque(false);
        playerChoosen.setOpaque(false);
        playerChoosen.setForeground(Color.WHITE);
        c.fill = GridBagConstraints.CENTER;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 2;
        c.gridy = 0;
        gamePanel[9].add(playerChoosen, c);
        
        add.addActionListener(new ActionListener() { // choose
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gamePlay.ListOfPlayer.size()==0)
                    playerChoosen.setText("");
                String myLine = choosePlayer.getText().replace("\\s","").replace(" ",",").trim();
                if(!myLine.isEmpty()){
                    playerChoosen.removeAll();
                    String parts[] = myLine.split(",");
                    for (String i : parts){
                        if(Integer.parseInt(i) <= gamePlay.ListOfRegisteredPlayer.size()){
                           Player p = gamePlay.ListOfRegisteredPlayer.elementAt(Integer.parseInt(i)-1);
                           System.out.println(gamePlay.ListOfPlayer.size());
                           if(gamePlay.IsNotChoosen(p.getPlayerName())){
                                   gamePlay.ListOfPlayer.add(p);
                           }
                        }
                    }
                    String myStr = new String();
                    for(int i = 0 ; i < gamePlay.ListOfPlayer.size() ; i++){
                        Player p = new Player();
                        p = gamePlay.ListOfPlayer.elementAt(i);
                        myStr += ((i+1)+". "+p.getPlayerName()+"\n");
                    }
                   playerChoosen.setText(myStr);
                   //playerChoosen.repaint();
                }else{
                    choosePlayer.setText("");
                }
            }
        });
        
        remove.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                String myLine = choosePlayer.getText().replace("\\s","").trim();
                if(!myLine.isEmpty()){
                    playerChoosen.removeAll();
                    String parts[] = myLine.split(" ");                    
                    Collections.sort(Arrays.asList(parts),Collections.reverseOrder());
                    
                    for (String i : parts){
                        if(Integer.parseInt(i)<= gamePlay.ListOfPlayer.size())
                            gamePlay.ListOfPlayer.removeElementAt(Integer.parseInt(i)-1);
                    }
                    String myStr = new String();
                    for(int i = 0 ; i < gamePlay.ListOfPlayer.size() ; i++){
                        Player p = new Player();
                        p = gamePlay.ListOfPlayer.elementAt(i);
                        myStr += ((i+1)+". "+p.getPlayerName()+"\n");
                    }
                    playerChoosen.setText(myStr);
                   //playerChoosen.repaint();
                }
                choosePlayer.setText("");
           }
        });
        
        String mySstr = new String();
        for(int i = 0 ; i < gamePlay.ListOfPlayer.size() ; i++){
            Player p = new Player();
            p = gamePlay.ListOfPlayer.elementAt(i);
            mySstr += ((i+1)+". "+p.getPlayerName()+"\n");
         }
         playerChoosen.setText(mySstr);
    }
    
    private void InitPanel10() {    //SWAP TURN
        System.out.println(gamePlay.ListOfPlayer.size()+"  "+gamePlay.currentPlayer.getTurn());
        gamePanel[10].removeAll();
        String name = gamePlay.currentPlayer.getPlayerName();
        JLabel preparation = new JLabel();
        preparation.setText(name);
        preparation.setFont(new Font("VERDANA",1,18));
        preparation.setForeground(Color.WHITE);
        gamePanel[10].add(preparation, BorderLayout.CENTER);
        
        JButton ok = new JButton("NEXT");
        ok.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout)basePanel.getLayout();
                cardLayout.show(basePanel, "GAMEPLAY");
            }            
        });
        gamePanel[10].add(ok, BorderLayout.CENTER);
    }
    
    private void updateRegisteredPlayer() {
        
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
    
}

