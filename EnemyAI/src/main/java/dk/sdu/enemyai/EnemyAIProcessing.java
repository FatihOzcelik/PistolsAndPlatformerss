package dk.sdu.enemyai;

import dk.sdu.common.data.Collision;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import static dk.sdu.common.data.GameKeys.*;
import dk.sdu.common.data.World;
import dk.sdu.common.data.Collision;
import static dk.sdu.common.data.EntityType.ENEMY;
import static dk.sdu.common.data.EntityType.PLAYER;
import dk.sdu.common.services.IProcessingService;
import static java.lang.Math.abs;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import static java.lang.Math.abs;

/**
 *
 * @author fatihozcelik
 */
@ServiceProvider(service = IProcessingService.class)
public class EnemyAIProcessing implements IProcessingService {

    Collision collisionDetection = new Collision();
    private boolean canJump;
    float velocityY;
    float gravity = -500f;
    boolean direction = true;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getType().equals(ENEMY)) {
                boolean direction = entity.isDirection();
                float x = entity.getPositionX();
                float y = entity.getPositionY();
                float dt = gameData.getDelta();
                float acceleration = entity.getAcceleration();
                float deceleration = entity.getDeacceleration();
                float maxSpeed = entity.getMaxSpeed();
                float dX = entity.getDeltaX();
                float dY = entity.getDeltaY();

                for (Entity player : world.getEntities()) {
                    if (player.getType().equals(PLAYER)) {
                        if (entity.getPositionX() < player.getPositionX() - 30) {
                            direction = true;
                        } else if (entity.getPositionX() - 30 > player.getPositionX()) {
                            direction = false;
                        }
                        entity.setDirection(direction);
                    }
                }

                // Walking right or left
                if (entity.isDirection()) {
                    if (abs(x - dX) / dt > maxSpeed) {
                        dX = acceleration * dt + x;
                    } else {
                        dX = maxSpeed * dt + x;
                    }
//                    if (entity.isIsHit()) {
//                        dX = x;
//                    }
                } else if (abs(x - dX) / dt > maxSpeed) {
                    dX = x - (acceleration * dt);
                } else {
                    dX = x - (maxSpeed * dt);
                }
//                if (entity.isIsHit()) {
//                    dX = x;
//                }

                //gravity
//            dY = ((dY - y) * -10) * dt;
                dY += velocityY * dt;      // Apply vertical velocity to X position
                velocityY += gravity * dt;

                // Jump           
                if (Math.random() > 0.99) {
                    if (canJump) {
                        velocityY = +400f;
                        canJump = false;
                    }
                }

                // Duck           
                if (gameData.getKeys().isDown(DOWN)) {

                }

                /**
                 * Collision with entities
                 */
                if (entity.isIsHit()) {
//                    System.out.println("Enemy is hit!");
                    entity.setHealth(entity.getHealth() - 50);
                    entity.setIsHit(false);
                    if (entity.getHealth() <= 0) {
                        world.removeEntity(entity);
                    }
                }

                /**
                 * Collision with map
                 */
                //save the local delta position
                entity.setDeltaX(dX);
                entity.setDeltaY(dY);

                //check if this entity colides with the map
                collisionDetection.mapCollision(entity, world);

                //save collision test results
                boolean collisionX = entity.isCollisionX();
                boolean collisionY = entity.isCollisionY();

                // react to x collision by not moving x
                if (collisionX) {
                    dX = x;
                }
                // react to y collision by not moving y
                if (collisionY) {
                    velocityY = 0;
                    dY = y;
                    canJump = collisionY;
                }

                // Set position:
                x = dX;
                y = dY;

                //update entity
                entity.setPositionX(x);
                entity.setPositionY(y);
                entity.setDeltaX(dX);
                entity.setDeltaY(dY);
            }
        }
    }
}
