package dk.sdu.player;

import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IPluginService;
import dk.sdu.common.data.Entity;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author fatihozcelik
 */
@ServiceProvider(service = IPluginService.class)
public class EntityPlugin implements IPluginService {

    Entity player;
    Entity Enemy;
    
    @Override
    public void start(GameData gameData, World world) {
        
        Enemy = new Entity (10, 100, 100, 20, 2, true, 100);
        Enemy.setSprite("C:\\Users\\Frank Sebastian\\Documents\\NetBeansProjects\\PistolsAndPlatformerss\\Core\\target\\classes\\dk\\sdu\\core\\assets\\enemy.png");
    
        world.addEntity(Enemy);
        
        
        player = new Entity(10, 100, 100, 20, 2, true, 100);
//      player.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/player.png");
//      player.setSprite("/Users/Arian/Desktop/skole/Objekt/code/PistolsAndPlatformerss/Core/target/classes/dk/sdu/core/assets/player.png");
        
        player.setSprite("\\Users\\Frank Sebastian\\Documents\\NetBeansProjects\\PistolsAndPlatformerss\\Core\\target\\classes\\dk\\sdu\\core\\assets\\player.png");
      //  player.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/player.png");
        world.addEntity(player);
    
        
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player.getID().toString());
    }

}
