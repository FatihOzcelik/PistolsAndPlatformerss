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
    Entity enemy1;
//    Entity enemy2;
//    Entity enemy3;
//    Entity enemy4;
//    Entity enemy5;
//    Entity enemy6;
//    Entity enemy7;
//    Entity enemy8;
//    Entity enemy9;

    @Override
    public void start(GameData gameData, World world) {
        enemy = new Entity(100, 100, 100, 20, 2, true, 10000);
        enemy1 = new Entity(600, 100, 100, 20, 2, true, 150);
        enemy.setWidth(48);
        enemy.setHeight(45);
//        enemy2 = new Entity(100, 100, 100, 20, 2, true, 100);
//        enemy3 = new Entity(100, 100, 100, 20, 2, true, 800);
//        enemy4 = new Entity(100, 100, 100, 20, 2, true, 100);
//        enemy5 = new Entity(100, 100, 100, 20, 2, true, 100);
//        enemy6 = new Entity(100, 100, 100, 20, 2, true, 100);
//        enemy7 = new Entity(100, 100, 100, 20, 2, true, 100);
//        enemy8 = new Entity(100, 100, 100, 20, 2, true, 100);
//        enemy9 = new Entity(100, 100, 100, 20, 2, true, 100);
        enemy.setType(ENEMY);
        enemy1.setType(ENEMY);
//        enemy2.setType(ENEMY);
//        enemy3.setType(ENEMY);
//        enemy4.setType(ENEMY);
//        enemy5.setType(ENEMY);
//        enemy6.setType(ENEMY);
//        enemy7.setType(ENEMY);
//        enemy8.setType(ENEMY);
//        enemy9.setType(ENEMY);


      enemy.setSprite("/Users/Arian/Desktop/skole/Objekt/code/PistolsAndPlatformerss/Core/target/classes/dk/sdu/core/assets/enemy.png");
      enemy1.setSprite("/Users/Arian/Desktop/skole/Objekt/code/PistolsAndPlatformerss/Core/target/classes/dk/sdu/core/assets/enemy.png");
//      enemy.setSprite("\\Users\\Frank Sebastian\\Documents\\NetBeansProjects\\PistolsAndPlatformerss\\Core\\target\\classes\\dk\\sdu\\core\\assets\\enemy.png");
//        enemy.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/enemy.png");
//        enemy1.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/enemy.png");
//        enemy2.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/enemy.png");
//        enemy3.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/enemy.png");
//        enemy4.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/enemy.png");
//        enemy5.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/enemy.png");
//        enemy6.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/enemy.png");
//        enemy7.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/enemy.png");
//        enemy8.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/enemy.png");
//        enemy9.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/enemy.png");
        

        world.addEntity(enemy);
        world.addEntity(enemy1);
//        world.addEntity(enemy2);
//        world.addEntity(enemy3);
//        world.addEntity(enemy4);
//        world.addEntity(enemy5);
//        world.addEntity(enemy6);
//        world.addEntity(enemy7);
//        world.addEntity(enemy8);
//        world.addEntity(enemy9);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy.getID().toString());
        world.removeEntity(enemy1.getID().toString());
//        world.removeEntity(enemy2.getID().toString());
//        world.removeEntity(enemy3.getID().toString());
//        world.removeEntity(enemy4.getID().toString());
//        world.removeEntity(enemy5.getID().toString());
//        world.removeEntity(enemy6.getID().toString());
//        world.removeEntity(enemy7.getID().toString());
//        world.removeEntity(enemy8.getID().toString());
//        world.removeEntity(enemy9.getID().toString());
    }
}
