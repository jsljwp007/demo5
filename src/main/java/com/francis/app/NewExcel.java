package com.francis.app;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 新建Excel，继承于Runnable接口
 */
public class NewExcel implements Runnable {
    public Parameter pts;

    public NewExcel(Object obj) {
        this.pts = (Parameter) obj;
        run();
    }

    @Override
    public void run() {
        Calculator2 calculator = new Calculator2();
        calculator.materials = pts.str;
        calculator.distance1 = pts.db;
        calculator.distance2 = pts.db1;
        calculator.h1 = pts.db2;
        calculator.h2 = pts.db3;
        calculator.a1 = pts.db4;
        calculator.length = pts.db5;
        /* 计算悬垂线形状 */
        calculator.calCoe1(); // 计算悬垂线形状

        pts.str1 = JFrameMain.doubleToString(calculator.lengthL, "mm"); // 返回下垂最低点到左侧支撑距离
        pts.str2 = JFrameMain.doubleToString(calculator.coefficient, ""); // 悬垂系数

        int iLeft = (int) (calculator.lengthL / 1000);
        int iRight = (int) ((calculator.length - calculator.lengthL) / 1000);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet ws = wb.createSheet("计算结果");

        Row titleRow = ws.createRow(0);
        String[] titleName = { "序号", "与左侧支撑距离", "高度" };
        for (int i = 0; i < titleName.length; i++) {
            Cell titleCells = titleRow.createCell(i);
            titleCells.setCellValue(titleName[i]);
        }

        Calculator3 ccr = new Calculator3();

        for (int i = 0; i < (iLeft + iRight + 1); i++) {
            double distanceL = calculator.lengthL - 1000 * (iLeft - i);
            ccr.distance = 1000 * Math.abs(iLeft - i);
            ccr.coefficient = calculator.coefficient;
            ccr.h3 = calculator.h3;
            ccr.CalHigh();
            double high = ccr.high;
            Row row = ws.createRow(i + 1);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(i + 1);
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(distanceL);
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(high);
        }
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("计算结果.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
