/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.core.main;

import dk.sdu.common.data.Entity;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Frank Sebastian
 */
public class Enemy extends Entity {

    Enemy(float positionX, float positionY) {
       super(positionX, positionY);
        
    }
    public void update(){
        
    }
    
    public void draw(Graphics2D g2d){
        g2d.drawImage(getEnemyImg, positionX, positionY, null);
        
    }
    
    public Image getEnemyImg(){
       ImageIcon ic = new ImageIcon("\\Users\\Frank Sebastian\\Documents\\NetBeansProjects\\PistolsAndPlatformerss\\Core\\target\\classes\\dk\\sdu\\core\\assets\\enemy.png");
       return ic.getImage();
    }
     
}
