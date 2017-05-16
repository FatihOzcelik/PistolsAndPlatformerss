package dk.sdu.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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

    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final Lookup lookup = Lookup.getDefault();
    private World world = new World();
    private Lookup.Result<IPluginService> result;
    private final GameData gameData = new GameData();
    private List<IPluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private TiledMap map;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
//    private Box2DDebugRenderer b2dr;
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 600;
    private SpriteBatch batch;
    private HUD hud;
    private int oldValue;
    private Screen gameOverScreen;
    private boolean isPlayerDead;

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
//        map = loader.load("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss - loadunload/Core/src/main/resources/dk/sdu/core/assets/PistolsAndPlatformersMap.tmx");
//         map = loader.load("/Users/Arian/Desktop/skole/Objekt/code/PistolsAndPlatformerss/Core/target/classes/dk/sdu/core/assets/PistolsAndPlatformersMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        batch = new SpriteBatch();

        // Box2DDebuggin lines     
//        world = new World(new Vector2(0, 0), true);
//        b2dr = new Box2DDebugRenderer(); // graphical representaion of fixtures and bodies inside the Box2D world.
//
//        BodyDef bDef = new BodyDef();
//        PolygonShape shape = new PolygonShape();
//        FixtureDef fDef = new FixtureDef();
//        Body body;
//
//        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//
//            bDef.type = BodyDef.BodyType.StaticBody;
//            bDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
//
//            body = world.createBody(bDef);
//
//            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
//            fDef.shape = shape;
//            body.createFixture(fDef);
//
//        }
        hud = new HUD(batch);

        oldValue = world.getEntities().size();

//        gameOverScreen = new GameOverScreen();
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
        for (IProcessingService entityProcessorService : getProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

//        for (Entity entity : world.getEntities()) {
//            System.out.println("entityhealth for : " + entity.getType() + ": " + entity.getHealth());
//        }
//        System.out.println("world.getEntities.size: " + world.getEntities().size());
        //Checks if an enemy is removed from the world. If so, 100 is added to the score.
        int newValue = world.getEntities().size();
        for (Entity entity : world.getEntities()) {
            if (oldValue > newValue && entity.getType().equals(ENEMY)) {
                hud.addScore(100);
                oldValue = newValue;
            }

            //If the entity has 0 health and it is the player, then the game is over
            if (entity.getHealth() < 1 && entity.getType().equals(PLAYER)) {
                System.out.println("Game Over");
                isPlayerDead = true; //this will trigger the renderer to draw the gameover screen
            }
        }

        //check the timer, and if it is 0, then the game is over
        if (hud.getWorldTimer() == 0) {
            isPlayerDead = true; //this will trigger the renderer to draw the gameover screen
        }

//        System.out.println("hud.getWorldTimer: " + hud.getWorldTimer());
        //        for (Entity entity : world.getEntities()) {
        //            //Checks if an entity has 0 health and if so, the entity is removed
        //            if (entity.getHealth() == 0) {
        //                world.removeEntity(entity);
        //                if (!entity.getType().equals(BULLET)) {
        //                    //adds 100 points to the score for each killed entity
        //                    //(if the killed (removed) entity was not a bullet)
        //                    hud.addScore(100);
        //                }
        //            }
        //
        //            //If the entity has 0 health and it is the player, then the game is over
        //            if (entity.getHealth() == 0 && entity.getType().equals(PLAYER)) {
        //                System.out.println("Game Over");
        //                
        //            }
        //
        //        }
        {
            for (Entity entity : world.getEntities()) {
                //This is for updating player health displayed on HUD
                if (entity.getType().equals(PLAYER)) {
                    hud.setHealth((int) entity.getHealth());
                }
            }
        }

        hud.update(gameData.getDelta());
//        world.step(1/60f, 6, 2);
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

        //renders Box2DDebugLines
//        b2dr.render(world, cam.combined);
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOverScreen != null && isPlayerDead) {
            gameOverScreen.render(Gdx.graphics.getDeltaTime());
            removeAllEntities();
        }

        update();
        draw();
    }

    private Sprites makeSprite(Entity e, float x, float y) {
        Sprites sprites = new Sprites(new Texture(e.getSprite()), e.getID());
        sprites.setX(x);
        sprites.setY(y);

        if (!e.getType().equals(MAP) && !e.getType().equals(BULLET)) {
            e.setHeight(sprites.getTexture().getHeight());
            e.setWidth(sprites.getTexture().getWidth());
        }

        return sprites;
    }

    private void draw() {
        Sprites sprites = null;
        batch.begin();
        Random platformNumb = new Random();

        for (Entity entity : world.getEntities()) {
            if (entity.getType().equals(MAP)) {
                int numbPlatWidth = (int) (entity.getWidth() / 40) - 1;
                entity.setSprite(entity.getSprite().substring(0, entity.getSprite().lastIndexOf("/") + 1) + "edgeStart.png");
                sprites = makeSprite(entity, entity.getPositionX(), entity.getPositionY());
                sprites.draw(batch);
                for (int i = 1; i < numbPlatWidth; i++) {
                    entity.setSprite(entity.getSprite().substring(0, entity.getSprite().lastIndexOf("/") + 1) + "platform" + i % 4 + ".png");
                    sprites = makeSprite(entity, entity.getPositionX() + (i * 40), entity.getPositionY());
                    sprites.draw(batch);
                }
                entity.setSprite(entity.getSprite().substring(0, entity.getSprite().lastIndexOf("/") + 1) + "edgeEnd.png");
                sprites = makeSprite(entity, entity.getPositionX() + numbPlatWidth * 40, entity.getPositionY());
                sprites.draw(batch);
            } else {
                sprites = makeSprite(entity, entity.getPositionX(), entity.getPositionY());

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
