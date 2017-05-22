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

    //takes two inputs which control the number of platforms per layer and the number of layers.
    public void generateMap(int numberOfPlatforms, int numberOfLayers, GameData gameData, World world) {

        //calculates how many points need to be set (a start and an end for each platform +  a height)
        int NumberOfPointsToPlace = (numberOfPlatforms * 2) - 2;

        //calculates ho far should be between each layer
        int layerDistance = 355 / numberOfLayers - 10;

        //for each layer that needs to be made
        for (int i = 0; i < numberOfLayers; i++) {

            //for each point that needs to be set in each layer
            for (int j = 0; j <= NumberOfPointsToPlace; j++) {

                //sets the first integer in each layer to be the height of the layers
                if (j == 0) {
                    height = ((layerDistance) * (i + 1) - layerDistance / 7 + newNumber.nextInt(layerDistance / 7)) + 100;
                    platformPlacements[i][j] = height;

                    //otherwise it sets the position of the beginning and end of each platform in the layers
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
                }
            }
        }
    }

    public int[][] getPlatformPlacements() {
        return platformPlacements;
    }

    @Override
    //generates the map with 3 layers and 3 platforms per level
    public void start(GameData gameData, World world) {
        generateMap(3, 3, gameData, world);
    }

    @Override
    public void stop(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
