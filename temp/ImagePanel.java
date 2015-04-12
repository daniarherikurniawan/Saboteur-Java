/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class ImagePanel extends JPanel {
    public BufferedImage image;
    
    public ImagePanel(String path) {
        //setLayout(new FlowLayout());
        try{
            image = ImageIO.read(new File(path));
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
    }
    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

}



