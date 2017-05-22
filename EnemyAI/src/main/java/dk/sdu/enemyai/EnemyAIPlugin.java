/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.enemyai;

import dk.sdu.common.data.Entity;
import static dk.sdu.common.data.EntityType.ENEMY;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IPluginService;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Frank Sebastian
 */
@ServiceProvider(service = IPluginService.class)
public class EnemyAIPlugin implements IPluginService {

    Entity enemy;
    Entity enemy1;

    @Override
    public void start(GameData gameData, World world) {
        enemy = new Entity(100, 100, 100, 20, 2, true, 10000);
        enemy1 = new Entity(600, 100, 100, 20, 2, true, 150);

        enemy.setType(ENEMY);
        enemy1.setType(ENEMY);

        enemy.setSprite("enemy.png");
        enemy1.setSprite("enemy.png");

        world.addEntity(enemy);
        world.addEntity(enemy1);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy.getID().toString());
        world.removeEntity(enemy1.getID().toString());
    }
}
