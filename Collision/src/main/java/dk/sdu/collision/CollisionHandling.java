/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.collision;

import dk.sdu.common.data.World;
import dk.sdu.common.data.Entity;

/**
 *
 * @author Arian
 */
public class CollisionHandling  {
    public void mapCollision(World world){
        for(Entity entity : world.getEntities()){
            if(entity.getDeltaX() < 100){
                entity.setCollisionX(true);
            } else {
                entity.setCollisionX(false);
            }
            if(entity.getDeltaY() < 100){
                entity.setCollisionY(true);
            } else {
                entity.setCollisionY(false);
            }
        }
    }
}
