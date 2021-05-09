package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class FractalExplorer extends JFrame {
    private int size;
    private JImageDisplay imageDisplay; // отображает фрактал
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(400);
        fractalExplorer.creatAndShowGUI();
        fractalExplorer.drawFractal();
    }


    FractalExplorer(int size){
        this.size = size;
        range = new Rectangle2D.Double();
        fractal = new Mandelbrot();
        fractal.getInitialRange(range);
        imageDisplay = new JImageDisplay(size,size);
    }

    private void creatAndShowGUI(){
        Mandelbrot mandelbrot = new Mandelbrot();
        Tricorn tricorn = new Tricorn();
        BurningShip burningShip = new BurningShip();

        JFrame frame = new JFrame("Fractal");
        JButton resetButton = new JButton("Reset");
        JButton saveButton = new JButton("Save Image");
        JLabel label = new JLabel("Fractal:");
        JComboBox comboBox = new JComboBox();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        comboBox.addItem(mandelbrot); //для того, чтобы добавить реализации вашего генератора фракталов
        comboBox.addItem(tricorn); //для того, чтобы добавить реализации вашего генератора фракталов
        comboBox.addItem(burningShip); //для того, чтобы добавить реализации вашего генератора фракталов

        resetButton.setActionCommand("reset");
        saveButton.setActionCommand("save");

        EventHandler eventHandler = new EventHandler();
        MouseHandler mouseHandler = new MouseHandler();

        imageDisplay.addMouseListener(mouseHandler);
        resetButton.addActionListener(eventHandler);
        saveButton.addActionListener(eventHandler);
        comboBox.addActionListener(eventHandler);

        panel1.add(label);
        panel1.add(comboBox);
        panel2.add(saveButton);
        panel2.add(resetButton);

        frame.add(imageDisplay,BorderLayout.CENTER);
        frame.add(panel1,BorderLayout.NORTH);
        frame.add(panel2,BorderLayout.SOUTH);
        frame.setSize(size,size + 100);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                double xCoord = FractalGenerator.getCoord(range.x,range.x + range.width, size, i);
                double yCoord = FractalGenerator.getCoord(range.y,range.y + range.height, size, j);
                int iter = fractal.numIterations(xCoord,yCoord);
                if (iter == -1)imageDisplay.drawPixel(i,j,0);
                else {
                    float hue = 0.7f + (float)iter / 200f;
                    imageDisplay.drawPixel(i,j,Color.HSBtoRGB(hue,1f,1f));
                }
            }
        }
        imageDisplay.repaint();
    }

    private class EventHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object object = e.getSource();
            if(object instanceof JComboBox){
                fractal = (FractalGenerator) ((JComboBox) object).getSelectedItem();
                fractal.getInitialRange(range);
                drawFractal();
            }
            else if(object instanceof JButton) {
                JButton button = (JButton) object;
                if(button.getActionCommand().equals("reset")){
                    fractal.getInitialRange(range);
                    drawFractal();
                }
                else if (button.getActionCommand().equals("save")){
                    JFileChooser fileChooser = new JFileChooser();
                    FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                    fileChooser.setFileFilter(filter);
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    if(fileChooser.showSaveDialog(button.getParent())
                            != JFileChooser.APPROVE_OPTION)return;
                    try {
                        ImageIO.write(imageDisplay.getBufferedImage(),"png",
                                fileChooser.getSelectedFile());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(button.getParent(), ex.getMessage(),
                                "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private class MouseHandler extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, size, x);
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, size, y);
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

}
