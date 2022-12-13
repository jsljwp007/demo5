package com.francis.app;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 新建容器类，继承于 Jpanel 类
 * 添加背景
 */
public class BgJPanel extends JPanel {
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 父类方法
        g.drawImage(ic.getImage(), 0, 4, 1000, 600, this); // 重绘图片
    }

    ImageIcon ic = new ImageIcon("src/main/resources/jpg/bgimg.jpg"); // 新建图片对象
}
