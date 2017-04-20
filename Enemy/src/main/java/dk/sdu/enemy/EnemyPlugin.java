/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.enemy;

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
public class EnemyPlugin implements IPluginService {

    Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = new Entity(100, 100, 100, 20, 2, true, 100);
        enemy.setType(ENEMY);
      enemy.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/enemy.png");
//      enemy.setSprite("/Users/Arian/Desktop/skole/Objekt/code/PistolsAndPlatformerss/Core/target/classes/dk/sdu/core/assets/enemy.png");

//        enemy.setSprite("\\Users\\Frank Sebastian\\Documents\\NetBeansProjects\\PistolsAndPlatformerss\\Core\\target\\classes\\dk\\sdu\\core\\assets\\enemy.png");
        world.addEntity(enemy);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy.getID().toString());
    }
}
