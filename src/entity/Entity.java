/**
 * Author: Oscar Lee, September, 2022
 * Most of the following code was taken from youtuber RyiSnow. Link to tutorial playlist here
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */

package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    protected int worldX, worldY;
    protected int speed;
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected Direction direction;

    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    protected Rectangle solidArea;
    protected int solidAreaDefaultX, solidAreaDefaultY;
    protected boolean collisionOn = false;

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }


}
