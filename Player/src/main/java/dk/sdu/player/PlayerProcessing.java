package dk.sdu.player;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import static dk.sdu.common.data.GameKeys.*;
import dk.sdu.common.data.World;
import dk.sdu.common.data.Collision;
import static dk.sdu.common.data.EntityType.PLAYER;
import dk.sdu.common.services.IProcessingService;
import dk.sdu.commonbullet.Bullet;
import dk.sdu.commonbullet.BulletSPI;
import static java.lang.Math.abs;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author fatihozcelik
 */
@ServiceProvider(service = IProcessingService.class)
public class PlayerProcessing implements IProcessingService {

    Collision collisionDetection = new Collision();
    private boolean canJump;
    float velocityY;
    float gravity = -500f;
    private float CD;
    private boolean canShoot;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getType().equals(PLAYER)) {
                float x = entity.getPositionX();
                float y = entity.getPositionY();
                float dt = gameData.getDelta();
                float acceleration = entity.getAcceleration();
                float deceleration = entity.getDeacceleration();
                float maxSpeed = entity.getMaxSpeed();
                float dX = entity.getDeltaX();
                float dY = entity.getDeltaY();

                // Walking left
                if (gameData.getKeys().isDown(LEFT)) {
                    entity.setDirection(false);
                    if (abs(x - dX) / dt > maxSpeed) {
                        dX = x - (acceleration * dt);
                    } else {
                        dX = x - (maxSpeed * dt);
                    }
                }

                // Walking right
                if (gameData.getKeys().isDown(RIGHT)) {
                    entity.setDirection(true);
                    if (abs(x - dX) / dt > maxSpeed) {
                        dX = acceleration * dt + x;
                    } else {
                        dX = maxSpeed * dt + x;
                    }
                }

                //Shooting                
                weaponCD(gameData);
                BulletSPI bulletProvider = Lookup.getDefault().lookup(BulletSPI.class);
                if (gameData.getKeys().isDown(SPACE) && canShoot && bulletProvider != null) {
                    if (world.getEntities(Bullet.class).size() < 6) {   //allow only 6 bullets at a time
                        Entity bullet = bulletProvider.createBullet(gameData, entity);
                        world.addEntity(bullet);
                        canShoot = false;
                        CD = 0.2f;
                    }
                }

                //gravity
//            dY = ((dY - y) * -10) * dt;
                dY += velocityY * dt;      // Apply vertical velocity to X position
                velocityY += gravity * dt;

                // Jump           
                if (gameData.getKeys().isDown(UP)) {
                    if (canJump) {
                        velocityY = +400f;
                        canJump = false;
                    }
                }

                // Duck           
                if (gameData.getKeys().isDown(DOWN)) {

                }

                //save the local delta position
                entity.setDeltaX(dX);
                entity.setDeltaY(dY);

                //check if this entity colides with the map
                collisionDetection.mapCollision(entity);

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

    private void weaponCD(GameData gameData) {
        if (CD > 0) {
            CD -= gameData.getDelta();
        } else {
            canShoot = true;
        }
    }
}
