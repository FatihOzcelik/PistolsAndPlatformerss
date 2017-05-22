package dk.sdu.player;

import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IPluginService;
import dk.sdu.common.data.Entity;
import static dk.sdu.common.data.EntityType.PLAYER;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author fatihozcelik
 */
@ServiceProvider(service = IPluginService.class)
public class EntityPlugin implements IPluginService {

    Entity player;

    @Override
    public void start(GameData gameData, World world) {
        player = new Entity(10, 100, 100, 20, 2, true, 100);
        player.setType(PLAYER);
        player.setSprite("player.png");

        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player.getID().toString());
    }

}
