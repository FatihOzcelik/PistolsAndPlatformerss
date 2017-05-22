package dk.sdu.weapon;

import dk.sdu.common.data.Entity;
import static dk.sdu.common.data.EntityType.BULLET;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IProcessingService;
import dk.sdu.commonbullet.Bullet;
import dk.sdu.commonbullet.BulletSPI;
import org.openide.util.lookup.ServiceProvider;
import static java.lang.Math.abs;

/**
 *
 * @author fatihozcelik
 */
@ServiceProvider(service = IProcessingService.class)
public class WeaponProcessing implements IProcessingService, BulletSPI {

    private boolean canShoot = true;

    @Override
    public void process(GameData gameData, World world) {

        //updates all the bullets in the game
        for (Entity bullet : world.getEntities(Bullet.class)) {
            moveBullet(gameData, bullet);

            if (bullet.getExpiration() > 0) {
                bullet.reduceExpiration(gameData.getDelta());
            } else {
                world.removeEntity(bullet.getID().toString());
            }
        }
    }

    private void moveBullet(GameData gameData, Entity bullet) {
        float x = bullet.getPositionX();
        float y = bullet.getPositionY();
        float dt = gameData.getDelta();
        float dx = bullet.getDeltaX();
        float dy = bullet.getDeltaY();
        float radians = bullet.getRadians();
        float speed = 350;
        float maxSpeed = bullet.getMaxSpeed();
        float acceleration = bullet.getAcceleration();

        // Bullet going right
        if (bullet.isDirection()) {             //checks if bullet direction is true (right)
            if (abs(x - dx) / dt > maxSpeed) {
                dx = acceleration * dt + x;
            } else {
                dx = maxSpeed * dt + x;
            }
        }

        // Bullet going left
        if (!bullet.isDirection()) {             //checks if player direction is not true (left)
            if (abs(x - dx) / dt > maxSpeed) {
                dx = x - (acceleration * dt);
            } else {
                dx = x - (maxSpeed * dt);
            }
        }

        if (bullet.isIsHit()) {
            bullet.setExpiration(0);
            bullet.setIsHit(false);
        }

        // Set position:
        x = dx;
        y = dy;

        //update bullet
        bullet.setPositionX(x);
        bullet.setPositionY(y);
        bullet.setDeltaX(dx);
        bullet.setDeltaY(dy);
    }

    @Override
    public Entity createBullet(GameData gameData, Entity e) {
        float x = e.getPositionX();
        float y = e.getPositionY();
        float dt = gameData.getDelta();
        float dx = e.getDeltaX();
        float dy = e.getDeltaY();

        Entity bullet = new Bullet(0, 0, 300, 30, 5, true, 100);
        bullet.setWidth(30);
        bullet.setHeight(16);
        bullet.setType(BULLET);
        bullet.setSprite("bullet.png");
        bullet.setDeltaX(dx + 15);
        bullet.setDeltaY(dy + 15);

        bullet.setExpiration(3);
        bullet.setPositionY(y + 15);

        // Bullet going right
        if (!e.isDirection()) {                 //checks if player direction is not true (left)
            bullet.setDirection(false);         //sets bullet direction to false (left)
            bullet.setPositionX(x - 17);
        }

        // Bullet going left
        if (e.isDirection()) {                  //checks if player direction is true (right)
            bullet.setDirection(true);          //sets bullet direction to true (right)
            bullet.setPositionX(x + 17);
        }

        return bullet;
    }
}
