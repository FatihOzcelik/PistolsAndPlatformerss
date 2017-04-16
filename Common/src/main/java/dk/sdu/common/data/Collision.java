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
        if (entity.getDeltaX() < 10) {
            entity.setCollisionX(true);
        } else {
            entity.setCollisionX(false);
        }
        if (entity.getDeltaY() < 80) {
            entity.setCollisionY(true);
        } else {
            entity.setCollisionY(false);
        }
    }
}
