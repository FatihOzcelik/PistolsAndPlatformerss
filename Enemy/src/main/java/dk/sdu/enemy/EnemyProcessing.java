package dk.sdu.enemy;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import static dk.sdu.common.data.GameKeys.*;
import dk.sdu.common.data.World;
import dk.sdu.common.data.Collision;
import static dk.sdu.common.data.EntityType.ENEMY;
import dk.sdu.common.services.IProcessingService;
import org.openide.util.lookup.ServiceProvider;
import static java.lang.Math.abs;

/**
 *
 * @author fatihozcelik
 */
@ServiceProvider(service = IProcessingService.class)
public class EnemyProcessing implements IProcessingService {

    Collision collisionDetection = new Collision();
    private boolean canJump;
    float velocityY;
    float gravity = -500f;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getType().equals(ENEMY)) {
                float x = entity.getPositionX();
                float y = entity.getPositionY();
                float dt = gameData.getDelta();
                float acceleration = entity.getAcceleration();
                float deceleration = entity.getDeacceleration();
                float maxSpeed = entity.getMaxSpeed();
                float dX = entity.getDeltaX();
                float dY = entity.getDeltaY();

                //randomly changes direction
                if (Math.random() > 0.99) {
                    entity.setDirection(!entity.isDirection());
                }

                // Walking right or left depending on what the direction is set to
                if (entity.isDirection()) {
                    //making sure not to move faster than the maxSpeed
                    if (abs(x - dX) / dt > maxSpeed) {
                        dX = acceleration * dt + x;
                    } else {
                        dX = maxSpeed * dt + x;
                    }
                } else if (abs(x - dX) / dt > maxSpeed) {
                    dX = x - (acceleration * dt);
                } else {
                    dX = x - (maxSpeed * dt);
                }

                //gravity
                dY += velocityY * dt;      // Apply vertical velocity to X position
                velocityY += gravity * dt;

                // Jump randomly
                if (Math.random() > 0.99) {
                    if (canJump) {
                        velocityY = +400f;
                        canJump = false;
                    }
                }

                // Duck (not implemented  
                if (gameData.getKeys().isDown(DOWN)) {

                }

                /**
                 * Collision with other entities
                 */
                if (entity.isIsHit()) {
                    entity.setHealth(entity.getHealth() - 50);
                    entity.setIsHit(false);
                    if (entity.getHealth() <= 0) {
                        world.removeEntity(entity);
                    }
                }

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
