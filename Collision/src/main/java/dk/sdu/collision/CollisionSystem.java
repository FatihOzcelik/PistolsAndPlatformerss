package dk.sdu.collision;

import dk.sdu.common.data.Entity;
import static dk.sdu.common.data.EntityType.BULLET;
import static dk.sdu.common.data.EntityType.ENEMY;
import static dk.sdu.common.data.EntityType.ENEMYAI;
import static dk.sdu.common.data.EntityType.PLAYER;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IProcessingService;
import java.awt.Rectangle;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author fatihozcelik
 */
@ServiceProvider(service = IProcessingService.class)
public class CollisionSystem implements IProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities()) {
            for (Entity otherEntity : world.getEntities()) {
                if (otherEntity.getType() != entity.getType() && otherEntity != entity && !entity.isIsHit() && testCollision(entity, otherEntity)) {

                    //check if player collides with own bullets
                    if (entity.getType().equals(PLAYER) && otherEntity.getType().equals(BULLET)
                            || entity.getType().equals(BULLET) && otherEntity.getType().equals(PLAYER)) {
                        entity.setIsHit(false);
                        otherEntity.setIsHit(false);

                    }
                    //check if enemy collides with enemyai
                    if (entity.getType().equals(ENEMY) && otherEntity.getType().equals(ENEMYAI)
                            || entity.getType().equals(ENEMYAI) && otherEntity.getType().equals(ENEMY)) {
                        entity.setIsHit(false);
                        otherEntity.setIsHit(false);

                    } else {
                        entity.setIsHit(true);
                    }
                }
            }
        }
    }

    private boolean testCollision(Entity source, Entity target) {

        Rectangle rect1 = new Rectangle((int) source.getPositionX(), (int) source.getPositionY(), (int) source.getWidth(), (int) source.getHeight());
        Rectangle rect2 = new Rectangle((int) target.getPositionX(), (int) target.getPositionY(), (int) target.getWidth(), (int) target.getHeight());

        boolean isCollision = false;

        if (rect1.intersects(rect2)) {
            isCollision = true;
        }

        return isCollision;
    }
}
