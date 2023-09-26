/**
 * Author: Oscar Lee, September, 2022
 * Most of the following code was taken from youtuber RyiSnow. Link to tutorial playlist here
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */

package main;

import entity.Entity;
import object.SuperObject;

import java.awt.*;

public class CollisionChecker {

    private final GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // need to check left x, right x, top y, and bottom y for collision triangle
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX/gp.getTileSize();
        int entityRightCol = entityRightWorldX/gp.getTileSize();
        int entityTopRow = entityTopWorldY/gp.getTileSize();
        int entityBottomRow = entityBottomWorldY/gp.getTileSize();

        int tileNum1, tileNum2;

        switch(entity.getDirection()) {
            case DOWN -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];
                if(gp.getTileManager().getTile()[tileNum1].isCollision() || gp.getTileManager().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case UP -> {
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
                if(gp.getTileManager().getTile()[tileNum1].isCollision() || gp.getTileManager().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case RIGHT -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];
                if(gp.getTileManager().getTile()[tileNum1].isCollision() || gp.getTileManager().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case LEFT -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
                if(gp.getTileManager().getTile()[tileNum1].isCollision() || gp.getTileManager().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        int loopIndex = 0;
        for (SuperObject superObj : gp.getObj()) {
            if (superObj != null) {
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                superObj.getSolidArea().x = superObj.getWorldX() + superObj.getSolidArea().x;
                superObj.getSolidArea().y = superObj.getWorldY() + superObj.getSolidArea().y;

                switch(entity.getDirection()) {
                    case DOWN -> {
                        entity.getSolidArea().y += entity.getSpeed();
                        if (entity.getSolidArea().intersects(superObj.getSolidArea())) {
                            if (superObj.isCollision()) {
                                entity.setCollisionOn(true);
                            }
                            if (player) {
                                index = loopIndex;
                            }
                        }
                    }
                    case UP -> {
                        entity.getSolidArea().y -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(superObj.getSolidArea())) {
                            if (superObj.isCollision()) {
                                entity.setCollisionOn(true);
                            }
                            if (player) {
                                index = loopIndex;
                            }
                        }
                    }
                    case RIGHT -> {
                        entity.getSolidArea().x += entity.getSpeed();
                        if (entity.getSolidArea().intersects(superObj.getSolidArea())) {
                            if (superObj.isCollision()) {
                                entity.setCollisionOn(true);
                            }
                            if (player) {
                                index = loopIndex;
                            }
                        }
                    }
                    case LEFT -> {
                        entity.getSolidArea().x -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(superObj.getSolidArea())) {
                            if (superObj.isCollision()) {
                                entity.setCollisionOn(true);
                            }
                            if (player) {
                                index = loopIndex;
                            }
                        }
                    }
                }

                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                superObj.getSolidArea().x = superObj.getSolidAreaDefaultX();
                superObj.getSolidArea().y = superObj.getSolidAreaDefaultY();

            }
            loopIndex++;
        }

        return index;
    }

}
