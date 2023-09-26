/**
 * Author: Oscar Lee, September, 2022
 * Most of the following code was taken from youtuber RyiSnow. Link to tutorial playlist here
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */

package tile;

import main.GamePanel;
import main.ImageLoader;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    private final GamePanel gp;
    private final Tile[] tile;

    private final int[][] mapTileNum;

    private final ImageLoader imageLoader;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];

        imageLoader = new ImageLoader();

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].setImage(imageLoader.loadImage("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].setImage(imageLoader.loadImage("/tiles/brick.png"));
            tile[1].setCollision(true);

            tile[2] = new Tile();
            tile[2].setImage(imageLoader.loadImage("/tiles/water.png"));
            tile[2].setCollision(true);

            tile[3] = new Tile();
            tile[3].setImage(imageLoader.loadImage("/tiles/dirt.png"));

            tile[4] = new Tile();
            tile[4].setImage(imageLoader.loadImage("/tiles/tree.png"));
            tile[4].setCollision(true);

            tile[5] = new Tile();
            tile[5].setImage(imageLoader.loadImage("/tiles/sand.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
               String line = br.readLine();

               while(col < gp.getMaxWorldCol()) {
                   String numbers[] = line.split(" ");

                   int num = Integer.parseInt(numbers[col]);
                   mapTileNum[col][row] = num;
                   col++;
               }

               if (col == gp.getMaxWorldCol()) {
                   col = 0;
                   row++;
               }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        while(worldCol < gp.getMaxWorldCol() && worldRow < gp.getMaxWorldRow()) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();
            int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

            if (worldX + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                worldX - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                worldY + gp.getTileSize() > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                worldY - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) {
                g2.drawImage(tile[tileNum].getImage(), screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
            }

            worldCol++;

            if(worldCol == gp.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }

    public Tile[] getTile() {
        return tile;
    }
}
