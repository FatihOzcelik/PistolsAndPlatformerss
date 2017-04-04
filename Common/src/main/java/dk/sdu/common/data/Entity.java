/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.data;

import java.util.UUID;
import java.io.Serializable;

/**
 *
 * @author fatihozcelik
 */
public class Entity implements Serializable{
    
    private final UUID ID = UUID.randomUUID();
    private float positionX;
    private float positionY;
    private float deltaX;
    private float deltaY;
    private float maxSpeed;
    private float acceleration;
    private float deacceleration;
    private boolean direction;
    private int health;

    public Entity(float positionX, float positionY, float maxSpeed, float acceleration, float deacceleration, boolean direction, int health) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.deacceleration = deacceleration;
        this.direction = direction;
        this.health = health;
    }

    public float getDeltaX() {
        return deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }

    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }

    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }

    public String getID() {
        return ID.toString();
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
