/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.player;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import static dk.sdu.common.data.GameKeys.*;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IProcessingService;

/**
 *
 * @author fatihozcelik
 */
public class PlayerProcessing implements IProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getType().equals(PLAYER)) {
                float x = entity.getPositionX();
                float y = entity.getPositionY();
                float dt = gameData.getDelta();
//                float dx = entity.getDx();
//                float dy = entity.getDy();
                float acceleration = entity.getAcceleration();
                float deceleration = entity.getDeacceleration();
                float maxSpeed = entity.getMaxSpeed();
//                float radians = entity.getRadians();
//                float rotationSpeed = entity.getRotationSpeed();

                // turning
                if (gameData.getKeys().isDown(LEFT)) {
                    radians += rotationSpeed * dt;
                }

                if (gameData.getKeys().isDown(RIGHT)) {
                    radians -= rotationSpeed * dt;
                }

                // accelerating            
                if (gameData.getKeys().isDown(UP)) {
                    dx += cos(radians) * acceleration * dt;
                    dy += sin(radians) * acceleration * dt;
                }

                // deceleration
                float vec = (float) sqrt(dx * dx + dy * dy);
                if (vec > 0) {
                    dx -= (dx / vec) * deceleration * dt;
                    dy -= (dy / vec) * deceleration * dt;
                }
                if (vec > maxSpeed) {
                    dx = (dx / vec) * maxSpeed;
                    dy = (dy / vec) * maxSpeed;
                }

            }

        }
    }
}
