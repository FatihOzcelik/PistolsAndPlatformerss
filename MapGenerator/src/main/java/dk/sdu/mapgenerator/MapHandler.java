/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mapgenerator;

import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import java.util.Random;
import dk.sdu.common.services.IPluginService;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.EntityType;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Arian
 */
@ServiceProvider(service = IPluginService.class)
public class MapHandler implements IPluginService {

    Random newNumber = new Random();
    int[][] platformPlacements = new int[8][32];
    int height;
    int lateralPoint;
    List<Entity> platformList = new ArrayList<>();

    public void generateMap(int numberOfPlatforms, int numberOfLayers, GameData gameData, World world) {
//        platformList.clear();
        int NumberOfPointsToPlace = (numberOfPlatforms * 2) - 2;
        int layerDistance = 355 / numberOfLayers - 10;
        for (int i = 0; i < numberOfLayers; i++) {
            for (int j = 0; j <= NumberOfPointsToPlace; j++) {
                if (j == 0) {
                    height = ((layerDistance) * (i + 1) - layerDistance / 7 + newNumber.nextInt(layerDistance / 7)) + 100;
                    platformPlacements[i][j] = height;
                } else {

                    lateralPoint = (800 / NumberOfPointsToPlace) * (j - 1);

                    if (j % 2 == 1) {
                        lateralPoint += newNumber.nextInt(70);
                    } else {
                        lateralPoint -= newNumber.nextInt(70);
                    }

                    platformPlacements[i][j] = lateralPoint;
                    Entity platform = new Entity(lateralPoint, height, 0, 0, 0, true, 9999999);
                    platform.setHeight(20);
                    platform.setWidth((float) (newNumber.nextInt(4) + 2) * 40);
                    
                    platform.setType(EntityType.MAP);
                    platformList.add(platform);

                    world.addEntity(platform);

                    platform.setSprite("mapSprites/");
//                    platform.setSprite("/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/Core/src/main/resources/dk/sdu/core/assets/mapSprites/");
//                    platform.setSprite("/Users/Frank Sebastian/Documents/NetBeansProjects/PistolsAndPlatformerss/Core/target/classes/dk/sdu/core/assets/mapSprites/");
                }
            }
        }
    }

    public int[][] getPlatformPlacements() {
        return platformPlacements;
    }

    @Override
    public void start(GameData gameData, World world) {
        generateMap(3, 3, gameData, world);
    }

    @Override
    public void stop(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}