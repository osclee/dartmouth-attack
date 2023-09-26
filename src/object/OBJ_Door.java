/**
 * Author: Oscar Lee, September, 2022
 * Most of the following code was taken from youtuber RyiSnow. Link to tutorial playlist here
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */

package object;

import main.ImageLoader;

import java.io.IOException;

public class OBJ_Door extends SuperObject {
    public OBJ_Door() {
        name = "Door";

        try {
            image = new ImageLoader().loadImage("/objects/door.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;

    }
}
