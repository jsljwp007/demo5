package com.francis.app;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * 第二窗口
 * 用于第二种计算方法，以及导出Excel表
 */
public class JFrameSecond extends JFrame {
    private static int flags = 0; // 定义转换标志位
    public static double coefficient; // 悬垂系数 求解

    public JFrameSecond(String title) {
        this.setTitle(title);
        this.setSize(1015, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BgJPanel jpanel = new BgJPanel();
        this.add(jpanel);
        placeComponent(jpanel);
        this.setVisible(true);
    }

    private void placeComponent(JPanel panel) {
        panel.setLayout(null);

        JComboBox<String> text10 = new JComboBox<String>();
        text10.addItem("钢");
        text10.addItem("铝");

        JLabel label = new JLabel("计算系数");
        label.setBounds(660, 34, 100, 25);
        label.setFont(new Font("微软雅黑", Font.BOLD, 20));
        panel.add(label);

        JTextField text13 = new JTextField(20);
        text13.setBounds(751, 34, 50, 30);
        panel.add(text13);

        JTextField text1 = new JTextField(20);
        text1.setBounds(207, 241, 50, 30);
        panel.add(text1);

        JTextField text2 = new JTextField(20);
        text2.setBounds(199, 133, 50, 30);
        panel.add(text2);

        JLabel label3 = new JLabel();
        label3.setBounds(106, 234, 50, 30);
        panel.add(label3);

        JLabel label4 = new JLabel();
        label4.setBounds(316, 330, 60, 30);
        panel.add(label4);

        JLabel label5 = new JLabel();
        label5.setBounds(481, 244, 50, 30);
        panel.add(label5);

        JTextField text6 = new JTextField(20);
        text6.setBounds(481, 371, 50, 30);
        panel.add(text6);

        JLabel label7 = new JLabel();
        label7.setBounds(852, 234, 50, 30);
        panel.add(label7);

        JTextField text8 = new JTextField(20);
        text8.setBounds(763, 133, 50, 30);
        panel.add(text8);

        JTextField text9 = new JTextField(20);
        text9.setBounds(752, 241, 50, 30);
        panel.add(text9);

        // JTextField text10 = new JTextField(22);
        text10.setBounds(478, 38, 128, 27);
        panel.add(text10);

        JTextField text11 = new JTextField(22);
        text11.setBounds(478, 72, 128, 27);
        panel.add(text11);

        JTextField text12 = new JTextField(22);
        text12.setBounds(478, 107, 128, 27);
        panel.add(text12);

        JLabel label13 = new JLabel();
        label13.setBounds(211, 420, 128, 27);
        panel.add(label13);

        JLabel label14 = new JLabel();
        label14.setBounds(211, 463, 128, 27);
        panel.add(label14);

        JLabel label15 = new JLabel();
        label15.setBounds(420, 420, 128, 27);
        panel.add(label15);

        JLabel label16 = new JLabel();
        label16.setBounds(420, 463, 128, 27);
        panel.add(label16);

        JLabel label17 = new JLabel();
        label17.setBounds(673, 420, 128, 27);
        panel.add(label17);

        JButton button3 = new JButton("计算"); // new button3
        button3.setBounds(400, 510, 200, 25);
        button3.addActionListener(e -> {
            Calculator2 cl = new Calculator2(); // new Calculate, name is cl
            cl.materials = String.valueOf(text10.getSelectedItem()); // 材料类型
            cl.width = StringToDouble(text11.getText(), "请输入正确的材料宽度尺寸");
            cl.thickness = StringToDouble(text12.getText(), "请输入正确的材料厚度尺寸");
            cl.h1 = StringToDouble(text1.getText(), "请输入正确的左侧炉口高度");
            cl.distance1 = StringToDouble(text2.getText(), "请输入正确的左侧支撑到炉口距离");
            cl.h2 = StringToDouble(text9.getText(), "请输入正确的右侧炉口高度");
            cl.distance2 = StringToDouble(text8.getText(), "请输入正确的右侧支撑到炉口距离");
            cl.length = StringToDouble(text6.getText(), "请输入正确的两侧支撑跨度");
            cl.a1 = StringToDouble(text13.getText(), "请输入正确的计算系数");
            if (flags == 8) {
                cl.selStrOfExt(); // 根据选取材料计算判断屈服强度及密度
                cl.calUniDen(); // 计算单位重力
                cl.calTensionAndCoefficient(); // 计算张力及悬垂系数及内应力
                cl.calCoe(); // 计算最低点与左侧支撑距离，最低点高度
                cl.calHigh(); // 求解左侧支撑与右侧支撑高度
                label3.setText(doubleToString(cl.h11, "mm"));
                label4.setText(doubleToString(cl.lengthL, "mm"));
                label5.setText(doubleToString(cl.h3, "mm"));
                label7.setText(doubleToString(cl.h22, "mm"));
                label13.setText(doubleToString(cl.density, "kg/m3"));
                label14.setText(doubleToString(cl.strengthOfExtension, "MPa"));
                label15.setText(doubleToString(cl.internalStress, "MPa"));
                label16.setText(doubleToString(cl.tension, "N"));
                label17.setText(doubleToString(cl.coefficient, ""));
            }
            flags = 0;
        });
        panel.add(button3);

        JButton button1 = new JButton("计算垂线");
        button1.setBounds(400, 550, 200, 25);
        button1.addActionListener(e -> {
            double distance1 = StringToDouble(text2.getText(), "请输入正确的左侧炉口到支撑距离");
            double distance2 = StringToDouble(text8.getText(), "请输入正确的右侧炉口到支撑距离");
            double h1 = StringToDouble(text1.getText(), "请输入正确的左侧炉口高度");
            double h2 = StringToDouble(text9.getText(), "请输入正确的右侧炉口高度");
            double a1 = StringToDouble(text13.getText(), "请输入正确的计算系数");
            double length = StringToDouble(text6.getText(), "请输入正确的两侧支撑跨度");
            String str = String.valueOf(text10.getSelectedItem()); // 选取材料
            String str1 = "";
            String str2 = "";
            Parameter pts = new Parameter(distance1, distance2, h1, h2, a1, length, str, str1, str2);
            if (flags == 6) {
                Thread thread = new Thread(new NewExcel(pts) {
                });
                thread.start();
            }
            flags = 0;
        });
        panel.add(button1);
        JButton button4 = new JButton("计算方法1");
        button4.setBounds(10, 10, 100, 25);
        button4.addActionListener(e -> {
            JFrameMain ajframe = new JFrameMain("垂度计算器");
            this.dispose();
            ajframe.setVisible(true);
        });
        panel.add(button4);
    }
    /** String转化为double */
    public static double StringToDouble(String str, String str1) {
        double result;
        try {
            result = Double.parseDouble(str);
            flags++;
        } catch (Exception e) {
            result = 1;
            flags = 0;
            JOptionPane.showMessageDialog(null, str1);
        }
        return result;
    }

    /** double转化为String */
    public static String doubleToString(double db, String str) {
        String result;
        try {
            result = new DecimalFormat("0.00").format(db) + str;
        } catch (Exception e) {
            result = "N/A";
        }
        return result;
    }
}
