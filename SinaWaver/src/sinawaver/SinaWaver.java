/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sinawaver;

import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 *
 * @author V5-561G
 */
public class SinaWaver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JFrame jf = new JFrame();
        jf.setTitle("Sina Saadati ::: Waver ");
        JApplet ja = new Screen();
        jf.getContentPane().add(ja);
        jf.pack();
        jf.setSize(1700, 600);
        jf.setVisible(true);
        
        
    }
    
}
