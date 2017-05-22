package dk.sdu.enemyai;

import dk.sdu.common.data.Entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Frank Sebastian
 */
public class EnemyAI extends Entity {

    public EnemyAI(float positionX, float positionY, float maxSpeed, float acceleration, float deacceleration, boolean direction, int health) {
        super(positionX, positionY, maxSpeed, acceleration, deacceleration, direction, health);

    }

}
