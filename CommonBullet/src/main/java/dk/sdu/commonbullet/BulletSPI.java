package dk.sdu.commonbullet;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;

/**
 *
 * @author fatihozcelik
 */
public interface BulletSPI {
    
    Entity createBullet(GameData gameData, Entity e);
    
}
