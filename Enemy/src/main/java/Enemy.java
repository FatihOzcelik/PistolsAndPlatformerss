
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Frank Sebastian
 */
public class Enemy  {
    
    private float positionX, positionY;
    
    public Enemy(float positionX, float positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public void update(){
        
    }
   
    
    public Image getEnemyImg(){
       ImageIcon ic = new ImageIcon("\\Users\\Frank Sebastian\\Documents\\NetBeansProjects\\PistolsAndPlatformerss\\Core\\target\\classes\\dk\\sdu\\core\\assets\\enemy.png");
       return ic.getImage();
    }
      
}
