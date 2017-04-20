package dk.sdu.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IPluginService;
import dk.sdu.common.services.IProcessingService;
import dk.sdu.core.managers.GameInputProcessor;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author fatihozcelik
 */
public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final Lookup lookup = Lookup.getDefault();
    private World world = new World();
    private Lookup.Result<IPluginService> result;
    private final GameData gameData = new GameData();
    private List<IPluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private TiledMap map;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    SpriteBatch batch;

    @Override
    public void create() {
        cam = new OrthographicCamera(800, 600);
        cam.translate(800 / 2, 600 / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        result = lookup.lookupResult(IPluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        for (Lookup.Item<IPluginService> plugin : result.allItems()) {
            plugin.getInstance().start(gameData, world);
        }

        TmxMapLoader loader = new TmxMapLoader();

//      map = loader.load("\\Users\\Frank Sebastian\\Documents\\NetBeansProjects\\PistolsAndPlatformerss\\Core\\src\\main\\resources\\dk\\sdu\\core\\assets\\PistolsAndPlatformersMap.tmx");
        map = loader.load("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/PistolsAndPlatformersMap.tmx");

        // map = loader.load("/Users/Arian/Desktop/skole/Objekt/code/PistolsAndPlatformerss/Core/target/classes/dk/sdu/core/assets/PistolsAndPlatformersMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        batch = new SpriteBatch();

    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.update();
    }

    private void update() {
        for (IProcessingService entityProcessorService : getProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
    }

    private Collection<? extends IProcessingService> getProcessingServices() {
        return lookup.lookupAll(IProcessingService.class);
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();

        tiledMapRenderer.setView(cam);

        tiledMapRenderer.render();
        update();
        draw();
    }

    private Sprites makeSprite(Entity e) {
        Sprites sprites = new Sprites(new Texture(e.getSprite()), e.getID());
        sprites.setX(e.getPositionX());
        sprites.setY(e.getPositionY());

        return sprites;
    }

    private void draw() {
        Sprites sprites = null;
        batch.begin();

        for (Entity entity : world.getEntities()) {
                sprites = makeSprite(entity);

            if(entity.isDirection()){
                    sprites.flip(false, false);
                } else {
                    sprites.flip(true, false);
                }
                sprites.draw(batch);
        }

        batch.end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        map.dispose();
        tiledMapRenderer.dispose();
    }

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

            Collection<? extends IPluginService> updated = result.allInstances();

            for (IPluginService us : updated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    us.start(gameData, world);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IPluginService gs : gamePlugins) {
                if (!updated.contains(gs)) {
                    gs.stop(gameData, world);
                    gamePlugins.remove(gs);
                }
            }
        }

    };

}
