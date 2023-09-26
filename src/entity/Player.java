/**
 * Author: Oscar Lee, September, 2022
 * Most of the following code was taken from youtuber RyiSnow. Link to tutorial playlist here
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */

package entity;

import main.GamePanel;
import main.ImageLoader;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    private final GamePanel gp;
    private final KeyHandler keyH;
    private final ImageLoader imageLoader;

    private final int screenX, screenY;

    private int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = (gp.getScreenWidth() / 2) - (gp.getTileSize() / 2);
        screenY = (gp.getScreenHeight() / 2) - (gp.getTileSize() / 2);

        solidArea = new Rectangle(12, 20, 24, 24); // This is the collision box. 48
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        imageLoader = new ImageLoader();

        setDefaultValues();
        getPlayerImage();
    }

    private void setDefaultValues() {
        worldX = gp.getTileSize() * 23;
        worldY = gp.getTileSize() * 21;
        speed = 4;
        direction = Direction.DOWN;
    }

    private void getPlayerImage() {

        try {
            up1 = imageLoader.loadImage("/player/player_up_1.png");
            up2 = imageLoader.loadImage("/player/player_up_2.png");
            down1 = imageLoader.loadImage("/player/player_down_1.png");
            down2 = imageLoader.loadImage("/player/player_down_2.png");
            left1 = imageLoader.loadImage("/player/player_left_1.png");
            left2 = imageLoader.loadImage("/player/player_left_2.png");
            right1 = imageLoader.loadImage("/player/player_right_1.png");
            right2 = imageLoader.loadImage("/player/player_right_2.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.keyPressed()) {
            if (keyH.isUpPressed()) {
                direction = Direction.UP;
            } else if (keyH.isDownPressed()) {
                direction = Direction.DOWN;
            } else if (keyH.isRightPressed()) {
                direction = Direction.RIGHT;
            } else if (keyH.isLeftPressed()) {
                direction = Direction.LEFT;
            }

            // check tile collision
            collisionOn = false;
            gp.getCollisionChecker().checkTile(this);

            // check object collision
            int objIndex = gp.getCollisionChecker().checkObject(this, true);
            pickUpObject(objIndex);

            // if collision is false, player can move
            if (!collisionOn) {
                switch (direction) {
                    case DOWN -> {
                        worldY += speed;
                    }
                    case UP -> {
                        worldY -= speed;
                    }
                    case RIGHT -> {
                        worldX += speed;
                    }
                    case LEFT -> {
                        worldX -= speed;
                    }
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void pickUpObject(int index) {
        if (index != 999) {

            String objectName = gp.getObj()[index].getName();

            switch (objectName) {
                case "Key" -> {
                    hasKey++;
                    gp.getObj()[index] = null; // let garbage handler deal with this
                    gp.playSoundEffect(1);
                    System.out.println("key count: " + hasKey);
                }
                case "Door" -> {
                    if (hasKey > 0) {
                        gp.playSoundEffect(3);
                        gp.getObj()[index] = null;
                        hasKey--;
                    }
                    System.out.println("key count: " + hasKey);
                }
                case "Boots" -> {
                    gp.playSoundEffect(2);
                    speed += 2;
                    gp.getObj()[index] = null;
                }
            }


        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch (direction) {
            case UP:
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case DOWN:
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case LEFT:
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case RIGHT:
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
            default:
                throw new IllegalArgumentException();
        }

        g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}
