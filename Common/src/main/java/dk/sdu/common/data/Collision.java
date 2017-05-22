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

    //check the provided interface aginst the map
    public void mapCollision(Entity entity, World world) {
        //is the entity exiting the left or right side of the screen?
        if (entity.getDeltaX() < 0 || entity.getDeltaX() > 762) {
            entity.setCollisionX(true);
        } else {
            entity.setCollisionX(false);
        }
        //is the entity going through the ground at the bottom of the screen
        if (entity.getDeltaY() < 80) {
            entity.setCollisionY(true);
        } else {
            entity.setCollisionY(false);
        }
        //is the entity coliding with the tree in the center of the map
        if (entity.getDeltaX() > 150 && entity.getDeltaX() < 430) {
            if (entity.getDeltaY() > 175 && entity.getDeltaY() < 240) {
                entity.setCollisionY(true);
                entity.setCollisionX(true);
            }
        }
        //is the entity coliding with any of the platforms
        for (Entity otherEntity : world.getEntities()) {
            if (otherEntity.getType().equals(MAP)) {
                if (testCollision(entity, otherEntity)) {
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