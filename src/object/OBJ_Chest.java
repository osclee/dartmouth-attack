/**
 * Author: Oscar Lee, September, 2022
 * Most of the following code was taken from youtuber RyiSnow. Link to tutorial playlist here
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */

package object;

import main.ImageLoader;

import java.io.IOException;

public class OBJ_Chest extends SuperObject{
    public OBJ_Chest() {
        name = "Chest";

        try {
            image = new ImageLoader().loadImage("/objects/chest.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
