/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.player;

import dk.sdu.common.data.Entity;

/**
 *
 * @author fatihozcelik
 */
public class Player extends Entity {
    
    public Player( float positionX, float positionY, float maxSpeed, float acceleration, float deacceleration, boolean direction, int health) {
            super( positionX, positionY, maxSpeed, acceleration,deacceleration, direction, health);

       
    }
    
}
