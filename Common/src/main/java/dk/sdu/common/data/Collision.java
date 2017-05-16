/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.data;

import static dk.sdu.common.data.EntityType.MAP;
import java.awt.Rectangle;

/**
 *
 * @author Arian
 */
public class Collision {

    public void mapCollision(Entity entity, World world) {
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
        if (entity.getDeltaX() > 150 && entity.getDeltaX() < 430) {
            if (entity.getDeltaY() > 175 && entity.getDeltaY() < 240) {
                entity.setCollisionY(true);
                entity.setCollisionX(true);
            }
        }
        for (Entity otherEntity : world.getEntities()) {
            if (otherEntity.getType().equals(MAP)) {
                if (testCollision(entity, otherEntity)) {
                    System.out.println("BOOOOOOOOOO");
                    entity.setCollisionY(true);
                    entity.setCollisionX(true);
                }
            }
        }
    }

    private boolean testCollision(Entity source, Entity target) {

        Rectangle rect1 = new Rectangle((int) source.getDeltaX(), (int) source.getDeltaY(), (int) source.getWidth(), (int) source.getHeight());
        Rectangle rect2 = new Rectangle((int) target.getPositionX(), (int) target.getPositionY(), (int) target.getWidth(), (int) target.getHeight());

        boolean isCollision = false;

        if (rect1.intersects(rect2)) {
            isCollision = true;
        }

        return isCollision;
    }
}
//    World world =  new World();
//    public void entityCollision (){
//        for (Entity entity1: world.getEntities()) {
//            for(Entity entity2: world.getEntities()) {
//                if(entity2.equals(entity1)){
//                    
//                } else {
//                }
//            }
//        }
//    }
