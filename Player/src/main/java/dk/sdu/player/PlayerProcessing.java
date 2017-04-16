package dk.sdu.player;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import static dk.sdu.common.data.GameKeys.*;
import dk.sdu.common.data.World;
import dk.sdu.common.data.Collision;
import dk.sdu.common.services.IProcessingService;
import static java.lang.Math.abs;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author fatihozcelik
 */
@ServiceProvider(service = IProcessingService.class)
public class PlayerProcessing implements IProcessingService {

    Collision collisionDetection = new Collision();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            //if (entity.getType().equals(PLAYER)) {
            float x = entity.getPositionX();
            float y = entity.getPositionY();
            float dt = gameData.getDelta();

//                float dx = entity.getDx();
//                float dy = entity.getDy();
            float acceleration = entity.getAcceleration();
            float deceleration = entity.getDeacceleration();
            float maxSpeed = entity.getMaxSpeed();
            float dX = entity.getDeltaX();
            float dY = entity.getDeltaY();
//                float radians = entity.getRadians();
//                float rotationSpeed = entity.getRotationSpeed();

            // Walking left
            if (gameData.getKeys().isDown(LEFT)) {
                if (abs(x - dX) / dt > maxSpeed) {
                    dX = x - (acceleration * dt);
                } else {
                    dX = x - (maxSpeed * dt);
                }
            }

            // Walking right
            if (gameData.getKeys().isDown(RIGHT)) {
                if (abs(x - dX) / dt > maxSpeed) {
                    dX = acceleration * dt + x;
                } else {
                    dX = maxSpeed * dt + x;
                }
            }
            
            
            // Jump           
            if (gameData.getKeys().isDown(UP)) {

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
                dY = y;
            }
            
            

            //}
            // Set position:
            x = dX;
            y = dY;

            //update entity
            entity.setPositionX(x);
            entity.setPositionY(y);
            entity.setDeltaX(dX);
            entity.setDeltaY(dY);
            

//            updateShape(entity);
        }
    }

//    private void updateShape(Entity entity) {
//        float[] shapex = entity.getShapeX();
//        float[] shapey = entity.getShapeY();
//        float x = entity.getPositionX();
//        float y = entity.getPositionY();
//
//        shapex[0] = (float) (x + Math.cos(1) * 8);
//        shapey[0] = (float) (y + Math.sin(1) * 8);
//
//        shapex[1] = (float) (x + Math.cos(1 - 4 * 3.1415f / 5) * 8);
//        shapey[1] = (float) (y + Math.sin(1 - 4 * 3.1145f / 5) * 8);
//
//        shapex[2] = (float) (x + Math.cos(1 + 3.1415f) * 5);
//        shapey[2] = (float) (y + Math.sin(1 + 3.1415f) * 5);
//
//        shapex[3] = (float) (x + Math.cos(1 + 4 * 3.1415f / 5) * 8);
//        shapey[3] = (float) (y + Math.sin(1 + 4 * 3.1415f / 5) * 8);
//
//        entity.setShapeX(shapex);
//        entity.setShapeY(shapey);
//    }
}
