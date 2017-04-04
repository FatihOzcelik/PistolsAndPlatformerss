/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.data;

/**
 *
 * @author fatihozcelik
 */
public class Entity {
    
    float positionX;
    float positionY;
    float maxSpeed;
    float acceleration;
    float deacceleration;
    boolean direction;
    int health;

    public Entity(float positionX, float positionY, float maxSpeed, float acceleration, float deacceleration, boolean direction, int health) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.deacceleration = deacceleration;
        this.direction = direction;
        this.health = health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setDeacceleration(float deacceleration) {
        this.deacceleration = deacceleration;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getDeacceleration() {
        return deacceleration;
    }

    public boolean isDirection() {
        return direction;
    }
    
    
}
