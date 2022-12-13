package com.francis.app;

/**
 * 自定义功率计算系数，求解悬链线系数，已知炉口高度，求其余数据
 */
public class Calculator2 {
    public double a1; // 计算系数
    public double h1; // 左侧炉口高度 定义
    public double h2; // 右侧炉口高度 定义
    public double h3; // 最低点垂下高度 求解
    public double h11; // 左侧支撑高度 求解
    public double h22; // 右侧支撑高度 求解
    public double distance1; // 左侧炉口距离 定义
    public double distance2; // 右侧炉口距离 定义
    public double length; // 两侧支撑距离 定义
    public double lengthL; // 最低点与左侧距离 求解
    public double thickness; // 带料厚度 定义
    public double width; // 带料宽度 定义
    public double internalStress; // 内应力 求解
    public String materials; // 材料 定义
    public double strengthOfExtension; // 屈服强度 求解
    public double density; // 材料密度 求解
    public double tension; // 张力 求解
    public double coefficient; // 悬垂系数 求解
    public double unitDensity; // 单位重力 求解
    public double v; // 线速度
    public double w; // 功率

    public Calculator2() {
        this.materials = "钢";
    }

    /**
     * 根据选取材料计算判断屈服强度及密度
     */
    public void selStrOfExt() {
        switch (materials) {
            case "钢" -> {
                strengthOfExtension = 200;
                density = 7850;
            }
            case "铝" -> {
                strengthOfExtension = 85;
                density = 2700;
            }
            default -> {
            }
        }
    }

    /**
     * 计算单位重力
     */
    public void calUniDen() {
        unitDensity = density * thickness / 1000 * width / 1000 * 9.8;
    }

    /**
     * 计算张力及悬垂系数及内应力
     */
    public void calTensionAndCoefficient() {
        tension = a1 * thickness * width * 9.8; // 计算张力
        coefficient = tension / unitDensity; // 计算悬垂系数
        internalStress = tension / thickness / width; // 计算内应力
    }

    /**
     * 计算最低点与左侧支撑距离，最低点高度
     */
    public void calCoe() {
        int flagL = 0, flagR = 0;
        double dl = 0.0; // 假设最低点距离支撑中心位置便宜，左为负，右为正
        double _h1 = h1 / 1000.0;
        double _h2 = h2 / 1000.0;
        double dLeft; // 最低点距离左侧炉口距离
        double dRight; // 最低点距离右侧炉口距离
        double hLeft; // 根据左侧计算的最低点距离
        double hRight; // 根据右侧计算的最低点距离
        for (int i = 0; i < 100; i++) {
            dLeft = length / 1000 / 2 - distance1 / 1000 + dl; // 最低点距离左侧炉口距离
            dRight = length / 1000 / 2 - distance2 / 1000 - dl; // 最低点距离右侧炉口距离
            hLeft = _h1 - coefficient * (Math.cosh(dLeft / coefficient) - 1); // 根据左侧计算的最低点距离
            hRight = _h2 - coefficient * (Math.cosh(dRight / coefficient) - 1); // 根据右侧计算的最低点距离
            if (Math.abs(hLeft - hRight) < 0.00001) {
                lengthL = length / 2 + dl * 1000; // 求解距离左侧距离
                h3 = hLeft * 1000; // 求解最低点高度
                break; // 跳出循环
            } else if (hLeft < hRight) {
                if (flagR >= 1) {
                    dl = dl - 1.0 / Math.pow(2.0, flagR);
                    flagR++;
                } else if (flagL >= 2) {
                    dl = dl - 1.0 / Math.pow(2.0, flagL);
                    flagL++;
                } else {
                    dl--;
                    flagL = 1;
                }
            } else {
                if (flagL >= 1) {
                    dl = dl + 1.0 / Math.pow(2.0, flagL);
                    flagL++;
                } else if (flagR >= 2) {
                    dl = dl + 1.0 / Math.pow(2.0, flagR);
                    flagR++;
                } else {
                    dl++;
                    flagR = 1;
                }
            }
        }
    }

    /**
     * 计算最低点与左侧支撑距离，最低点高度
     */
    public void calCoe1() {
        selStrOfExt();
        coefficient = a1 * 1000000 / density;
        calCoe();
    }

    /**
     * 求解左侧支撑与右侧支撑高度
     */
    public void calHigh() {
        h11 = h3 + 1000 * coefficient * (Math.cosh((lengthL) / 1000 / coefficient) - 1); // 计算左侧炉口高度
        h22 = h3 + 1000 * coefficient * (Math.cosh((length - lengthL) / 1000 / coefficient) - 1); // 计算右侧炉口高度
    }

    /**
     * 计算功率
     */
    public void calW() {
        w = v / 60 * tension / 1000; // 计算功率
    }
}
