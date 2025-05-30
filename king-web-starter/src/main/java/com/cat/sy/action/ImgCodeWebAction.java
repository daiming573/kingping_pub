package com.cat.sy.action;

import com.cat.sy.action.base.WebBaseAction;
import com.common.anno.LoginNeed;
import com.common.safe.CheckCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * 广告图片查询
 */
@Slf4j
@Controller
@RequestMapping("imgCode")
@Scope("prototype")
public class ImgCodeWebAction extends WebBaseAction {

    @LoginNeed(false)
    @RequestMapping(value = "getImg")
    public void getImg() {
        try {
            // 设置不缓存图片
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "No-cache");
            response.setDateHeader("Expires", 0);
            // 指定生成的相应图片
            response.setContentType("image/jpeg");
            CheckCodeUtils idCode = new CheckCodeUtils();
            BufferedImage image = new BufferedImage(idCode.getWidth(), idCode.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            // 定义字体样式
            Font myFont = new Font("Times New Roman", Font.PLAIN, 18);
            // 设置字体
            g.setFont(myFont);

            g.setColor(idCode.getRandomColor(200, 250));
            // 绘制背景
            g.fillRect(0, 0, idCode.getWidth(), idCode.getHeight());
            g.setColor(idCode.getRandomColor(180, 200));
            idCode.drawRandomLines(g, 160);
            String randCode = idCode.drawRandomString(4, g);
            log.info("randCode:" + randCode);
            request.getSession().setAttribute("randCode", randCode);
            g.dispose();
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
