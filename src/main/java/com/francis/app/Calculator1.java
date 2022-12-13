package com.francis.app;

/**
 * 计算器1，根据两端支撑高度与悬垂最低点高度计算其它数据
 */
public class Calculator1 {
    public double h1; // 左侧材料高度 定义
    public double h2; // 右侧材料高度 定义
    public double h3; // 最低点垂下高度 定义
    public double h11; // 左侧炉口高度 求解
    public double h22; // 右侧炉口高度 求解
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

    public Calculator1() {

    }

    public Calculator1(String arg0) {
        this.materials = arg0;
        selStrOfExt();
    }

    public Calculator1(double arg0, double arg1, double arg2, double arg3) {
        this.h1 = arg0;
        this.h2 = arg1;
        this.h3 = arg2;
        this.length = arg3;
        calCoef();
    }

    /** 根据选取材料计算判断屈服强度及密度 */
    public void selStrOfExt() {
        switch (materials) {
            case "钢":
                strengthOfExtension = 200;
                density = 7850;
                break;
            case "铝":
                strengthOfExtension = 85;
                density = 2700;
                break;
            default:
                break;
        }
    }

    /** 计算单位重力 */
    public void calUniDen() {
        unitDensity = density * thickness / 1000 * width / 1000 * 9.8;
    }

    /** 求解张力，悬垂系数与最低点与左侧支撑距离 */
    public void calCoef() {
        double _coefficientS = 2000.0, dlS = 10.0;
        double _coefficient = _coefficientS, dl = dlS;
        double _h1 = 0.0, _h2 = 0.0, _h3 = 0.0, _dh1 = 0.0, _dh2 = 0.0;
        double dh = 0.0, dl1 = 0.0, dl2 = 0.0;
        _h1 = h1 / 1000.0; // 左侧高度
        _h2 = h2 / 1000.0; // 右侧高度
        _h3 = h3 / 1000.0; // 最低点高度
        _dh1 = _h1 - _h3; // 左侧与最低点高度差
        _dh2 = _h2 - _h3; // 右侧与最低点高度差
        if (_h1 < _h2) { // 当左侧比右侧低时；
            for (int i = 0; i < 100; i++) {
                dl1 = length / 1000.0 / 2.0 - dl; // 最低点与左侧距离
                dl2 = length / 1000.0 - dl1; // 最低点与右侧距离
                for (int j = 0; j < 100; j++) {
                    dh = _coefficient * (Math.cosh(dl1 / _coefficient) - 1); // 根据虚拟的系数计算的高度差，以左侧进行虚拟计算
                    if (Math.abs(dh - _dh1) < 0.00001) {
                        break;
                    } else {
                        if (dh < _dh1) {
                            _coefficient = _coefficient - _coefficientS / 2.0 / Math.pow(2.0, (double) j);
                        } else
                            _coefficient = _coefficient + _coefficientS / 2.0 / Math.pow(2.0, (double) j);
                    }
                }
                if (Math.abs(_coefficient * (Math.cosh(dl2 / _coefficient) - 1) - _dh2) < 0.000001) {
                    break;
                } else if (i < 99) {
                    if (_coefficient * (Math.cosh(dl2 / _coefficient) - 1) > _dh2) {
                        dl = dl - dlS / 2.0 / Math.pow(2.0, (double) i);
                    } else
                        dl = dl + dlS / 2.0 / Math.pow(2.0, (double) i);
                    _coefficient = _coefficientS;
                }
            }
            lengthL = length / 2.0 - dl * 1000.0; // 将计算到左侧的距离赋值到左侧距离
        } else { // 当左侧比右侧高或者相等时；
            for (int i = 0; i < 100; i++) {
                dl1 = length / 1000.0 / 2.0 + dl; // 最低点与左侧距离
                dl2 = length / 1000.0 - dl1; // 最低点与右侧距离
                for (int j = 0; j < 100; j++) {
                    dh = _coefficient * (Math.cosh(dl1 / _coefficient) - 1); // 根据虚拟的系数计算的高度差，以左侧进行虚拟计算
                    if (Math.abs(dh - _dh1) < 0.00001) {
                        break;
                    } else {
                        if (dh < _dh1) {
                            _coefficient = _coefficient - _coefficientS / 2.0 / Math.pow(2.0, (double) j);
                        } else
                            _coefficient = _coefficient + _coefficientS / 2.0 / Math.pow(2.0, (double) j);
                    }
                }
                if (Math.abs(_coefficient * (Math.cosh(dl2 / _coefficient) - 1) - _dh2) < 0.000001) {
                    break;
                } else if (i < 99) {
                    if (_coefficient * (Math.cosh(dl2 / _coefficient) - 1) > _dh2) {
                        dl = dl + dlS / 2.0 / Math.pow(2.0, (double) i);
                    } else
                        dl = dl - dlS / 2.0 / Math.pow(2.0, (double) i);
                    _coefficient = _coefficientS;
                }
            }
            lengthL = length / 2.0 + dl * 1000.0; // 将计算到左侧的距离赋值到左侧距离
        }
        coefficient = _coefficient; // 将计算系数赋值到系数
    }

    /** 求解张力 */
    public void calTension() {
        tension = coefficient * unitDensity;
    }

    /** 求解内应力 */
    public void calIntStr() {
        internalStress = tension / thickness / width;
    }

    /** 求解左侧炉口高度与右侧炉口高度 */
    public void calHigh() {
        h11 = h3 + 1000 * coefficient * (Math.cosh((lengthL - distance1) / 1000 / coefficient) - 1); // 计算左侧炉口高度
        h22 = h3 + 1000 * coefficient * (Math.cosh((length - lengthL - distance2) / 1000 / coefficient) - 1); // 计算右侧炉口高度
    }
}
