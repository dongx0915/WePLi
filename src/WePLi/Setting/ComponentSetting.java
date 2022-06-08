/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WePLi.Setting;

import Dto.Song.SongDto;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Donghyeon <20183188>
 */
public class ComponentSetting {

    /* 웹 이미지 url을 ImageIcon으로 변경하는 메소드 */
    public static ImageIcon imageToIcon(String url, int width, int height) {
        try {
            /* 웹 이미지 가져오기 */
            ImageIcon icon = new ImageIcon(new URL(url));

            /* 이미지 사이즈 조정 */
            return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (MalformedURLException e) {
            return null;
        }
    }
    
    public static ImageIcon cropImageToIcon(String url, int width, int height) {
        try {
            /* 웹 이미지 가져오기 */
            ImageIcon icon = new ImageIcon(new URL(url));
            ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            
            return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (MalformedURLException e) {
            return null;
        }
    }
    


    

    
    public static ImageIcon getSmallBlurImage(String url) {
        try {
            long before = System.currentTimeMillis();
            
            Color color[];
            URL imgUrl = new URL(url);
            BufferedImage input = ImageIO.read(imgUrl);

            BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);

            // Setting dimensions for the image to be processed
            int i = 0;
            int max = 400, rad = 10;
            int a1 = 0, r1 = 0, g1 = 0, b1 = 0;
            color = new Color[max];

            // Now this core section of code is responsible for
            // blurring of an image
            int x = 1, y = 1, x1, y1, ex = 5, d = 0;

            // Running nested for loops for each pixel
            // and blurring it
            for (x = rad; x < input.getHeight() - rad; x++) {
                for (y = rad; y < input.getWidth() - rad; y++) {
                    for (x1 = x - rad; x1 < x + rad; x1++) {
                        for (y1 = y - rad; y1 < y + rad; y1++) {
                            color[i++] = new Color(
                                    input.getRGB(y1, x1));
                        }
                    }
                    // Smoothing colors of image
                    i = 0;
                    for (d = 0; d < max; d++) a1 = a1 + color[d].getAlpha();

                    a1 = a1 / (max);
                    for (d = 0; d < max; d++) r1 = r1 + color[d].getRed();

                    r1 = r1 / (max);
                    for (d = 0; d < max; d++) g1 = g1 + color[d].getGreen();

                    g1 = g1 / (max);
                    for (d = 0; d < max; d++) b1 = b1 + color[d].getBlue();

                    b1 = b1 / (max);
                    int sum1 = (a1 << 24) + (r1 << 16) + (g1 << 8) + b1;

                    output.setRGB(y, x, (int) (sum1));
                }
            }
            
            ImageIcon blur = new ImageIcon(output.getSubimage(10, 10, input.getWidth() - 20, input.getHeight() - 20));
            long after = System.currentTimeMillis();
            
            System.out.println((after - before) / 1000);
            
            return new ImageIcon(blur.getImage().getScaledInstance(912, 912, Image.SCALE_SMOOTH));
        } catch (IOException ex) { ex.printStackTrace(); }
        
        return null;
    }
    
        public static ImageIcon getBigBlurImage(String url, int width, int height) {
        try {
            long before = System.currentTimeMillis();
            URL imgUrl = new URL(url);
            BufferedImage input = ImageIO.read(imgUrl);
            
            // 블러 정도 조절
            int radius = 5;
            int size = radius * 3 + 1;
            float weight = 1.0f / (size * size);
            float[] data = new float[size * size];

            for (int i = 0; i < data.length; i++) { data[i] = weight; }

            Kernel kernel = new Kernel(size, size, data);
            BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            
            ImageIcon blur = new ImageIcon(op.filter(input, null));
            
            long after = System.currentTimeMillis();
            
            System.out.println((after - before) / 1000);
            
            return new ImageIcon(blur.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (IOException ex) { ex.printStackTrace(); }
        
        return null;
    }
}
