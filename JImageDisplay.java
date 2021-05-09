package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends javax.swing.JComponent {
    private BufferedImage bufferedImage;

    JImageDisplay(int width, int height){
        bufferedImage = new BufferedImage(width,height,bufferedImage.TYPE_INT_RGB);
        this.setPreferredSize(new Dimension(width,height));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
    }

    public void clearImage(){
        for(int i = 0; i < bufferedImage.getHeight(); i++){
            for(int j = 0; j < bufferedImage.getWidth(); j++){
                bufferedImage.setRGB(i,j,0);
            }
        }
    }
    public void drawPixel(int x, int y, int rgbColor){
        bufferedImage.setRGB(x,y,rgbColor);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
