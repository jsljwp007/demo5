package com.francis.app;

/**
 * 根据悬链线系数，悬链线最低点高度以及距离悬链线最低点的距离，求解此点高度
 */
public class Calculator3 {
    public double h3; // 悬链最低点高度
    public double coefficient; // 悬链线系数
    public double distance; // 需要计算的位置距离悬垂最低点的位置
    public double high; // 悬链线高度

    /**
     * 根据悬链线距离左侧距离计算悬链线此位置高度
     */
    public void CalHigh() {
        high = coefficient * (Math.cosh(distance / 1000.0 / coefficient) - 1) * 1000.0 + h3; // 计算此位置悬链线高度
    }
}
