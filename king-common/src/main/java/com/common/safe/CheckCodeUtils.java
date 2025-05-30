package com.common.safe;

import java.awt.*;
import java.util.Random;

/**
 * 验证码图片生成器
 *
 * @author admin
 */
public class CheckCodeUtils {
    /**
     * 验证码图片的宽度
     */
    private int width = 60;
    /**
     * 验证码图片的高度
     */
    private int height = 25;
    /**
     * 随机数
     */
    private final Random random = new Random();


    public int getWidth() {
        return width;
    }


    public void setWidth(int width) {
        this.width = width;
    }


    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 生成随机颜色
     *
     * @param fc 前景色
     * @param bc 背景色
     * @return Color对象，此Color对象是RGB形式的
     */
    public Color getRandomColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 绘制干扰线
     *
     * @param g    Graphics2D对象，用来绘制图像
     * @param nums 干扰线的条数
     */
    public void drawRandomLines(Graphics2D g, int nums) {
        g.setColor(this.getRandomColor(160, 200));
        for (int i = 0; i < nums; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(12);
            int y2 = random.nextInt(12);
            g.drawLine(x1, y1, x1 + x2, y1 + y2);
        }
    }

    /**
     * 获取随机字符串，
     * 此函数可以产生由大小写字母，数字组成的字符串
     *
     * @param length 随机字符串的长度
     * @return 随机字符串
     */
    public String drawRandomString(int length, Graphics2D g) {
        StringBuilder stub = new StringBuilder();
        String temp = "";
        for (int i = 0; i < length; i++) {
            temp = String.valueOf(random.nextInt(10));
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(temp, 13 * i + 6, 16);
            stub.append(temp);
        }
        return stub.toString();
    }
}
