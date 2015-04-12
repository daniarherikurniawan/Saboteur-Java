
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Kelas panel untuk menampung gambar-gambar
 * @author user
 */
public class ImagePanel extends JPanel {

    /**
     * BufferedImage untuk menampung image
     */
    public BufferedImage image;
    
    /**
     * Konstruktor berparameter kelas ImagePanel; Set layout sesuai dengan ukuran
     * @param path String berisi path file image
     */
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



