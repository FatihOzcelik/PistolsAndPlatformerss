package dk.sdu.common.data;

import java.util.UUID;
import java.io.Serializable;

/**
 *
 * @author fatihozcelik
 */
public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();
    public float positionX;
    public float positionY;
    private float deltaX;
    private float deltaY;
    public float maxSpeed;
    public float acceleration;
    public float deacceleration;
    private boolean direction;
    public int health;
    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private String sprite;
    private boolean collisionX;
    private boolean collisionY;

    public Entity(float positionX, float positionY, float maxSpeed, float acceleration, float deacceleration, boolean direction, int health) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.deacceleration = deacceleration;
        this.direction = direction;
        this.health = health;
        deltaX = positionX + acceleration;
        deltaY = positionY;
    }

    public boolean isCollisionX() {
        return collisionX;
    }

    public boolean isCollisionY() {
        return collisionY;
    }
    
    public void setCollisionX(boolean collisionX) {
        this.collisionX = collisionX;
    }

    public void setCollisionY(boolean collisionY) {
        this.collisionY = collisionY;
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

//    public String getID() {
//        return ID.toString();
//    }
    
    public UUID getID() {
        return this.ID;
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

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public String getSprite() {
        return sprite;
    }
    
    
}
