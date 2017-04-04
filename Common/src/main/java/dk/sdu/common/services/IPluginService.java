/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.services;

import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;

/**
 *
 * @author Arian
 */

public interface IPluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}