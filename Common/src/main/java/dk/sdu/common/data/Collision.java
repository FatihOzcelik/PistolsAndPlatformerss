/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.data;

/**
 *
 * @author Arian
 */
public class Collision {
    public void mapCollision(Entity entity) {
        if (entity.getDeltaX() < 0 || entity.getDeltaX() > 762) {
            entity.setCollisionX(true);
        } else {
            entity.setCollisionX(false);
        }
        if (entity.getDeltaY() < 80) {
            entity.setCollisionY(true);
        } else {
            entity.setCollisionY(false);
        }
        if (entity.getDeltaX() > 150 && entity.getDeltaX() < 430){
            if(entity.getDeltaY() > 175 && entity.getDeltaY() < 240){
                entity.setCollisionY(true);
                entity.setCollisionX(true);
            }
        }
    }
    World world =  new World();
    public void entityCollision (){
        for (Entity entity1: world.getEntities()) {
            for(Entity entity2: world.getEntities()) {
                if(entity2.equals(entity1)){
                    
                } else {
                }
            }
        }
    }
}
