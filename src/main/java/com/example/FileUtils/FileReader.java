package com.example.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileReader {
    public static byte[] getData(BufferedImage image){
        ArrayList<Byte> bytes = new ArrayList<>();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                //Reading each pixel
                int pixel = image.getRGB(x,y);
                Color color = new Color(pixel, true);
                int[] argb = new int[]{ color.getRed(),  color.getBlue()};
                //                                                                              3
                if(argb[0] == 0){
                    x = image.getWidth()+1;
                    y = image.getHeight()+1;
                }else {
                    bytes.add((byte) argb[1]);
                }
            }
        }
        byte[] bytesArr = new byte[bytes.size()];

        for (int i = 0; i < bytes.size(); i++) {
            bytesArr[i] = bytes.get(i);
        }
        return bytesArr;

    }
}
