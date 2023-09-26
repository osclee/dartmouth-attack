/**
 * Author: Oscar Lee, September, 2022
 * Most of the following code was taken from youtuber RyiSnow. Link to tutorial playlist here
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */

package main;

import entity.Player;
import object.SuperObject;
import tile.Tile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    private final int originalTileSize = 16;
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    // world settings
    private final int maxWorldCol = 50;
    private final int maxWorldRow = 50;
    private final int worldWidth = tileSize * maxWorldCol;
    private final int worldHeight = tileSize * maxWorldRow;


    // fps
    private final int fps = 60;

    private TileManager tileManager;
    private final KeyHandler keyH;
    private Sound sound;
    private Thread gameThread;
    private CollisionChecker collisionChecker;
    private AssetSetter assetSetter;
    private final Player player;
    private SuperObject[] obj; // size the array is the amount of objects in the game at a certain time

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        keyH = new KeyHandler();
        this.addKeyListener(keyH);
        this.setFocusable(true);

        sound = new Sound();

        assetSetter = new AssetSetter(this);
        obj = new SuperObject[10];

        tileManager = new TileManager(this);
        collisionChecker = new CollisionChecker(this);
        player = new Player(this, keyH);
    }

    public void setupGame() {
        assetSetter.setObject();

        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        tileManager.draw(g2);

        for (var superObj : obj) {
            if (superObj != null) {
                superObj.draw(g2, this);
            }
        }

        player.draw(g2);
        g2.dispose();
    }

    public void playMusic (int index) {
        sound.setFile(index);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSoundEffect(int index) {
        sound.setFile(index);
        sound.play();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public Player getPlayer() {
        return player;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public SuperObject[] getObj() {
        return obj;
    }
}
