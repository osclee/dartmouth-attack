/**
 * Author: Oscar Lee, September, 2022
 * Most of the following code was taken from youtuber RyiSnow. Link to tutorial playlist here
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */

package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {

    private String path;

    public ImageLoader() {}

    public ImageLoader(String path) {
        this.path = path;
    }

    /*public BufferedImage loadImage(String path, String fileName) throws IOException {
        return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + fileName)));
    }*/

    public BufferedImage loadImage(String fullPath) throws IOException {
        return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(fullPath)));
    }
}
