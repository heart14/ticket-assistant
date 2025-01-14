package com.heart.ticket.base.utils;

import cn.hutool.core.codec.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author wfli
 * @since 2024/10/24 14:50
 */
public class DHashUtil {

    /**
     * 计算给定图片文件的dHash哈希值。
     *
     * @param file 图片文件
     * @return 哈希值，如果读取图片失败则返回-1
     */
    private static long getDHash(File file) {
        try {
            // 读取图片文件
            BufferedImage srcImage = ImageIO.read(file);

            // 创建一个新的9x8像素的缓冲图像
            BufferedImage buffImg = new BufferedImage(9, 8, BufferedImage.TYPE_INT_RGB);

            // 将原始图片缩放到9x8大小并绘制到缓冲图像上
            buffImg.getGraphics().drawImage(
                    srcImage.getScaledInstance(9, 8, Image.SCALE_SMOOTH), 0, 0, null);

            // 获取缩放后的图片宽度和高度
            int width = buffImg.getWidth();
            int height = buffImg.getHeight();

            // 创建一个数组来存储灰度像素值
            int[] grayPix = new int[width * height];
            int i = 0;

            // 遍历每个像素，将其转换为灰度值
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = buffImg.getRGB(x, y);
                    int r = rgb >> 16 & 0xff;
                    int g = rgb >> 8 & 0xff;
                    int b = rgb & 0xff;
                    // 计算灰度值
                    int gray = (r * 30 + g * 59 + b * 11) / 100;
                    grayPix[i++] = gray;
                }
            }

            // 初始化哈希值
            long hash = 0;

            // 计算每对相邻像素的灰度差值并生成哈希值
            for (i = 0; i < width * height - 1; i++) {
                long bit = grayPix[i] > grayPix[i + 1] ? 1 : 0;
                hash |= bit << i;
            }

            // 返回计算出的哈希值
            return hash;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String[] args) {
        // 图片文件路径
        // a.png和b.png图片不同
        // a.png和c.png图片完全一样
        File file1 = new File("C:\\Users\\wfli\\Downloads\\1.png");
        File file2 = new File("C:\\Users\\wfli\\Downloads\\2.png");
        File file3 = new File("C:\\Users\\wfli\\Downloads\\1.png");
        // 计算图片文件的哈希值
        long hash1 = getDHash(file1);
        long hash2 = getDHash(file2);
        long hash3 = getDHash(file3);
        System.out.println("hash a:"+hash1);
        System.out.println("hash b:"+hash2);
        System.out.println("hash c:"+hash3);
        // 计算两个哈希值之间的汉明距离
        int distance_ab = 0;
        for (int i = 0; i < 64; i++) {
            if ((hash1 >> i & 1) != (hash2 >> i & 1)) {
                distance_ab++;
            }
        }
        // 计算两个哈希值之间的汉明距离
        int distance_ac = 0;
        for (int i = 0; i < 64; i++) {
            if ((hash1 >> i & 1) != (hash3 >> i & 1)) {
                distance_ac++;
            }
        }
        // 计算两个哈希值之间的汉明距离
        int distance_bc = 0;
        for (int i = 0; i < 64; i++) {
            if ((hash2 >> i & 1) != (hash3 >> i & 1)) {
                distance_bc++;
            }
        }
        System.out.println("汉明距离 a&b: " + distance_ab);
        System.out.println("汉明距离 a&c: " + distance_ac);
        System.out.println("汉明距离 b&c: " + distance_bc);


        String encode1 = Base64.encode(file1);
        String encode2 = Base64.encode(file2);
        System.out.println("base64 a:" + encode1);
        System.out.println("base64 b:" + encode2);
    }
}