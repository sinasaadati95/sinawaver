/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sinawaver;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author V5-561G
 */
public class Screen extends JApplet implements KeyListener , MouseListener {
    
    protected Screen(){
        this.init(); 
    }
    
    
    
    public void init(){
        this.addKeyListener(this);
        this.addMouseListener(this); 
    }
    
    
    
    
    
    private BufferedImage image = null ;
    private BufferedImage result = null ;
    
    public void paint(Graphics g){
        this.requestFocus();
        super.paint(g);
        
        this.drawTable(g);
        
        if(this.result != null){
            g.drawImage(this.result, 100 , 100 , this.getWidth() - 120 , this.getHeight() - 120 , this); 
        }else{
            g.drawString("Welcome!", 110, 140);
            g.drawString("Please click here to open an image.", 110, 160);
            g.drawString("Don't forget to save your picture after editing it.", 110, 180); 
            
            g.drawString("Sina Saadati ::: Picture Waver", 110, 300);
            g.drawString("sina.saadati@aut.ac.ir", 110, 320);
            
        }
        
        
    }
    
    
    
    private void drawTable(Graphics g){
        
        Font mainFont = g.getFont();
        
        
        g.drawRect(98, 98, this.getWidth() - 119, this.getHeight() - 119 );
        if(this.result != null){
            g.fillRect(120, 120, this.getWidth() - 130, this.getHeight() - 130 );
        }
        
        ///---------------------------------------------------
        g.setFont(new Font(mainFont.getName() , 20 , 20 ));
        
        g.drawRect(100, 20, 200, 70); g.drawString("Simple wave", 110, 60);
        g.drawRect(310, 20, 200, 70); g.drawString("Advance wave", 320, 60);
        g.drawRect(520, 20, 200, 70); g.drawString("Edge Finder", 530, 60);
        
        ///---------------------------------------------------
        
        g.setFont(new Font(mainFont.getName() , 15 , 15 ));
        
        g.drawRect(10, 110, 70, 50); 
        g.drawString("a =" , 12, 130);
        g.drawString("" + this.a , 12, 150 );
        
        g.drawRect(10, 170, 70, 50); 
        g.drawString("dx =" , 12, 190);
        g.drawString("" + this.dx , 12, 210 );
        
        g.drawRect(10, 230, 70, 50); 
        g.drawString("X0 =" , 12, 250);
        g.drawString("" + this.x0 , 12, 270 );
        
        g.drawRect(10, 290, 70, 50); 
        g.drawString("MaxH =" , 12, 310);
        g.drawString("" + this.maxH , 12, 330 );
        
        
        ///---------------------------------------------------
        
        g.drawRect(10, 500 , 40 , 30 ); 
        g.drawString("Save" , 12 , 520  );
        
        g.setFont(mainFont);
        
    }
    
    
    ///*************************************************************************
    
    private void getPic(){
        JFileChooser temp = new JFileChooser() ;
        temp.showOpenDialog(this);
        File f = temp.getSelectedFile();
        try {
            this.image = ImageIO.read(f);
            this.result = ImageIO.read(f);
            this.setDefault();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Can not load the picture.");
            System.exit(000);
        }
    }
    
    
    
    private void savePic(){
        try {
            String location = JOptionPane.showInputDialog("Please enter the address you want to save the result. \nNotice: add \'.jpg\' at the end of the address.\n(Example: E:\\result.jpg)");
            ImageIO.write(this.result, "jpg", new File(location));
            JOptionPane.showMessageDialog(this, "Picture saved successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ":( \nCan NOT save the picture.");
        }
    }
    
    ///*************************************************************************
    
    
    public double x0 = 100.00 ;
    public double dx = 100.00 ;
    public double a = 100.000 ;
    
    
    
    private void execute(){
        switch(this.state){
            case 1 : 
                this.wave1();
                break;
            case 2 : 
                this.wave2();
                break;
            case 3 : 
                this.edger();
                break;
        }
        this.repaint();
    }
    
    
    
    
    public void wave1(){
        
        this.clearPic(0xffffffff);
        
        for (double y = 0; y < this.image.getHeight(); y++) {
            for (double x = 0; x < this.image.getWidth(); x++) {
                int pixel = this.image.getRGB((int)x, (int)y);
                double y2 = y + ( this.a * Math.cos( ( x + this.x0) / this.dx )) ; 
                this.set(x, y2, pixel); 
                
                {
                    if(y == 00){
                        for (int i = ((int)y2); i >= 00; i--) {
                            this.set(x, i, pixel);
                        }
                    }else if(y == (this.image.getHeight() - 1)){
                        for (int i = ((int)y2); i < this.image.getHeight(); i++) {
                            this.set(x, i, pixel);
                        }
                    }
                }
                
            }
        } 
    }
    
    
    
    
    
    
    public double maxH = 100.000 ;
    
    public void wave2(){
        
        this.clearPic(0xffffffff);
        
        double h = this.maxH ; 
        double dh = (this.maxH /  ( (double) this.result.getHeight() ) ) ; 
        
        for (double y = 0; y < this.image.getHeight(); y++) {
            for (double x = 0; x < this.image.getWidth(); x++) {
                int pixel = this.image.getRGB((int)x, (int)y);
                double y2 = y + ( h * Math.cos( ( x + this.x0) / this.dx )) ; 
                for (double i = 0.000 ; i <= (2.100 * dh); i+=0.15) {
                    this.set(x, (y2 + i), pixel);
                }
                if(y == 00){
                    for (int i = ((int)y2); i >= 0; i--) {
                        this.set(x, i, pixel);
                    }
                }
            }
            h -= dh ;
        } 
    }
    
    
    
    
    
    
    
    public void edger(){
        
        this.clearPic(0xffffffff);
        
        for (double y = 0; y < this.image.getHeight() - 1 ; y++) {
            for (double x = 0; x < this.image.getWidth() - 1 ; x++) {
                int t0 = this.image.getRGB((int)x, (int)y);
                int t1 = this.image.getRGB((int)x + 1 , (int)y);
                int dAlpha = this.qadr(this.getAlpha(t1) - this.getAlpha(t0)); 
                int dRed = this.qadr(this.getRed(t1) - this.getRed(t0));
                int dGreen = this.qadr(this.getGreen(t1) - this.getGreen(t0));
                int dBlue = this.qadr(this.getBlue(t1) - this.getBlue(t0));
                this.set(x, y, this.getRGB(dAlpha, dRed, dGreen, dBlue));
            }
        }
        
    }
    
    
    
    
    
    
    ///*************************************************************************
    
    
    
    private void clearPic(int color){
        for (int i = 0; i < this.result.getWidth(); i++) {
            for (int j = 0; j < this.result.getHeight(); j++) {
                this.result.setRGB(i, j, color);
            } 
        }
    }
    
    
    private void setDefault(){
        this.a = 100.000 ; 
        this.x0 = (this.image.getWidth() / 2.000);
        this.dx = 70.00 ; 
        ///---------------------------------------
        this.maxH = 100.000 ;
    }
    
    
    
    
    ///*************************************************************************
    
    
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_ESCAPE : 
                System.exit(000);
                break;
            case KeyEvent.VK_ENTER : 
                this.edger();this.repaint();
                break;
                
        }
    }
    
    public void keyReleased(KeyEvent e){
        
    }
    
    public void keyTyped(KeyEvent e){
        
    }
    
    
    
    
    
    
    

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() , y = e.getY();
        this.mouseEvent(x, y);
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    
    
    public int state = 1 ;
    
    private void mouseEvent(int x , int y){
        
        if(this.isInRect(x, y, 100, 20, 200, 70)){
            ///Simple Wave
            this.state = 1 ; 
            this.execute();
        }
        else if(this.isInRect(x, y, 310, 20, 200, 70)){
            ///Advance Wave
            this.state = 2 ; 
            this.execute();
        }
        else if(this.isInRect(x, y, 520, 20, 200, 70)){
            /// Edge Finder
            this.state = 3 ;
            this.execute();
        }
        
        ///---------------------------------------------------
        
        if(this.isInRect(x, y, 10, 110, 70, 50)){
            /// a 
            this.askA(); 
            this.execute();
        }
        else if(this.isInRect(x, y, 10, 170, 70, 50)){
            /// dx
            this.askDX();
            this.execute();
        }
        else if(this.isInRect(x, y, 10, 230, 70, 50)){
            /// X0
            this.askX0();
            this.execute();
        }
        else if(this.isInRect(x, y, 10, 290, 70, 50)){
            /// MaxH
            this.askMaxH();
            this.execute();
        } 
        else if(this.isInRect(x, y, 10, 500, 40, 30)){
            ///Save
            this.savePic();
        }
        
        if(this.isInRect(x, y, 100, 100, 1600, 400)){
            this.getPic();
        }
            
        
    }
    
    
    public boolean isInRect(int x , int y , int rectX , int rectY , int rectW , int rectH){
        if(     rectX <= x && x <= (rectX + rectW)
                &&
                rectY <= y && y <= (rectY + rectH) ){
            return true ; 
        }
        return false ;
    }
    
    
    
    
    private void askA(){
        try{
            this.a = Double.parseDouble(JOptionPane.showInputDialog("Please enter the value."));
        }catch(Exception ex){
            this.a = 100.00 ;
        }
    }
    
    
    private void askDX(){
        try{
            this.dx = Double.parseDouble(JOptionPane.showInputDialog("Please enter the value."));
        }catch(Exception ex){
            this.dx = 70.00 ;
        }
    }
    
    
    private void askX0(){
        try{
            this.x0 = Double.parseDouble(JOptionPane.showInputDialog("Please enter the value."));
        }catch(Exception ex){
            this.x0 = ( this.result.getWidth() / 2.000 ) ;
        }
    }
    
    
    private void askMaxH(){
        try{
            this.maxH = Double.parseDouble(JOptionPane.showInputDialog("Please enter the value."));
        }catch(Exception ex){
            this.maxH = 100.00 ;
        }
    }
    
    
    ///*************************************************************************
    
    
    public int getBlue(int rgb){
        return (rgb & 0x000000ff);
    }
    
    
    public int getGreen(int rgb){
        int temp = (rgb >> 8);
        return (temp & 0x000000ff);
    }
    
    
    public int getRed(int rgb){
        int temp = (rgb >> 16 );
        return (temp & 0x000000ff);
    }
    
    
    
    
    public int getAlpha(int rgb){
        int temp = (rgb >> 24 );
        return (temp & 0x000000ff);
    }
    
    
    
    
    
    public int getRGB(int alpha , int red , int green , int blue ){
        int r = alpha ;
        r = (r << 8);
        r = (r | red);
        r = (r << 8);
        r = (r | green);
        r = (r << 8);
        r = (r | blue);
        
        return r ;
    }
    
    
    
    public int qadr(int u){
        if(u >= 000)
            return (u);
        return (-u);
    }
    
    
    ///*************************************************************************
    
    
    
    public void set(double x , double y , int rgb){
        this.set((int)x, (int)y, rgb);
    }
    
    
    public void set(int x , int y , int rgb){
        if(x >= 00 && x < this.result.getWidth() && y >= 00 && y < this.result.getHeight()){
            this.result.setRGB(x, y, rgb);
        }
    }
    
    
}
