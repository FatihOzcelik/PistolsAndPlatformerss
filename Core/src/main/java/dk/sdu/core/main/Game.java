package dk.sdu.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import static dk.sdu.common.data.EntityType.BULLET;
import static dk.sdu.common.data.EntityType.ENEMY;
import static dk.sdu.common.data.EntityType.MAP;
import static dk.sdu.common.data.EntityType.PLAYER;
import dk.sdu.core.scenes.HUD;
import dk.sdu.core.screens.GameOverScreen;
import java.util.Random;

/**
 *
 * @author fatihozcelik
 */
public class Game implements ApplicationListener {

    private final String pathToAssets = "/Users/Arian/Desktop/skole/Objekt/code/PistolsAndPlatformerss/Core/target/classes/dk/sdu/core/assets/";
//    private final String pathToAssets = "/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/";
//    private final String pathToAssets = "\\Users\\Frank Sebastian\\Documents\\NetBeansProjects\\PistolsAndPlatformerss\\Core\\src\\main\\resources\\dk\\sdu\\core\\assets\\";
    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final Lookup lookup = Lookup.getDefault();
    private World world = new World();
    private Lookup.Result<IPluginService> result;
    private final GameData gameData = new GameData();
    private List<IPluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private TiledMap map;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 600;
    private SpriteBatch batch;
    private HUD hud;
    private int oldValue;
    private Screen gameOverScreen;
    private boolean isPlayerDead;

    @Override
    //Generates all the basics of the display window
    public void create() {
        cam = new OrthographicCamera(800, 600);
        cam.translate(800 / 2, 600 / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        result = lookup.lookupResult(IPluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        //finds all instances of the IPluginServices and starts them
        for (Lookup.Item<IPluginService> plugin : result.allItems()) {
            plugin.getInstance().start(gameData, world);
        }

        TmxMapLoader loader = new TmxMapLoader();

        //loads the map from the map file in assets
        map = loader.load(pathToAssets + "PistolsAndPlatformersMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        batch = new SpriteBatch();
        hud = new HUD(batch);

        oldValue = world.getEntities().size();

        gameOverScreen = new GameOverScreen();

    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.update();

        if (gameOverScreen != null) {
            gameOverScreen.resize(width, height);
        }
    }

    private void removeAllEntities() {
        for (Entity entity : world.getEntities()) {
            world.removeEntity(entity);
        }

    }

    private void update() {
        //processes all instances of IProcessingService
        for (IProcessingService entityProcessorService : getProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        /*
         *defines the size of entities (exept the platforms) to be the same as the sprite size
         *meaning that all the entities are the same size as they are displayed to be
         *this way the collision can be calculated with the actual size of entities in the screen
         *and the look of entities can be changed without messing up the collision size
         */
        int newValue = world.getEntities().size();
        for (Entity entity : world.getEntities()) {
            if (oldValue > newValue && entity.getType().equals(ENEMY)) {
                hud.addScore(100);
                oldValue = newValue;
            }

            //if the player dies, the game ends
            if (entity.getHealth() < 1 && entity.getType().equals(PLAYER)) {
                System.out.println("Game Over");
                isPlayerDead = true; //this will trigger the renderer to draw the gameover screen
            }
        }

        //kills the player if the time runs out
        if (hud.getWorldTimer() == 0) {
            isPlayerDead = true; //this will end the game
        }

        for (Entity entity : world.getEntities()) {
            //This is for updating player health displayed on HUD
            if (entity.getType().equals(PLAYER)) {
                hud.setHealth((int) entity.getHealth());
            }
        }

        hud.update(gameData.getDelta());
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

        //renders the game map
        tiledMapRenderer.render();

        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        //renderes the game over screen when the game is over (and removes all the entities)
        if (gameOverScreen != null && isPlayerDead) {
            gameOverScreen.render(Gdx.graphics.getDeltaTime());
            removeAllEntities();
        }

        update();
        draw();
    }

    private Sprites makeSprite(Entity e, float x, float y) {
        /*
         *sets the sprite positions to be the positions of the entity meaning we draw the sprite
         *wherever the entity it coresponds to is.
         */
        Sprites sprites = new Sprites(new Texture(pathToAssets + e.getSprite()), e.getID());
        sprites.setX(x);
        sprites.setY(y);

        //keeps the entity the same size as the sprite, even if the sprite is changed midgame
        if (!e.getType().equals(MAP) && !e.getType().equals(BULLET)) {
            e.setHeight(sprites.getTexture().getHeight());
            e.setWidth(sprites.getTexture().getWidth());
        }

        return sprites;
    }

    private void draw() {
        Sprites sprites;
        batch.begin();
        Random platformNumb = new Random();

        //iterates over every entity and draws them in their correct position
        for (Entity entity : world.getEntities()) {

            //for the platforms it draws each platform in smaller (40pixel width) parts
            if (entity.getType().equals(MAP)) {

                //first the beginning of the platform is drawn
                int numbPlatWidth = (int) (entity.getWidth() / 40) - 1;
                entity.setSprite(entity.getSprite().substring(0, entity.getSprite().lastIndexOf("/") + 1) + "edgeStart.png");
                sprites = makeSprite(entity, entity.getPositionX(), entity.getPositionY());
                sprites.draw(batch);

                //depending on the width of the platform, width/40 (pixels) platform parts are drawn
                for (int i = 1; i < numbPlatWidth; i++) {
                    entity.setSprite(entity.getSprite().substring(0, entity.getSprite().lastIndexOf("/") + 1) + "platform" + i % 4 + ".png");
                    sprites = makeSprite(entity, entity.getPositionX() + (i * 40), entity.getPositionY());
                    sprites.draw(batch);
                }

                //the platform end is drawn
                entity.setSprite(entity.getSprite().substring(0, entity.getSprite().lastIndexOf("/") + 1) + "edgeEnd.png");
                sprites = makeSprite(entity, entity.getPositionX() + numbPlatWidth * 40, entity.getPositionY());
                sprites.draw(batch);

                //for none platform entities they are simply drawn at their position
            } else {
                sprites = makeSprite(entity, entity.getPositionX(), entity.getPositionY());

                //sets the sprites to face the same way as the entity is moving
                if (entity.isDirection()) {
                    sprites.flip(false, false);
                } else {
                    sprites.flip(true, false);
                }
                sprites.draw(batch);
            }
        }

        batch.end();
    }

    @Override
    public void pause() {
        if (gameOverScreen != null) {
            gameOverScreen.pause();
        }
    }

    @Override
    public void resume() {
        if (gameOverScreen != null) {
            gameOverScreen.resume();
        }
    }

    @Override
    public void dispose() {
        map.dispose();
        tiledMapRenderer.dispose();

        if (gameOverScreen != null) {
            gameOverScreen.hide();
        }
    }

    public void setScreen(Screen screen) {
        if (this.gameOverScreen != null) {
            this.gameOverScreen.hide();
        }
        this.gameOverScreen = screen;
        if (this.gameOverScreen != null) {
            this.gameOverScreen.show();
            this.gameOverScreen.resize(Gdx.graphics.getWidth(),
                    Gdx.graphics.getHeight());
        }
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
