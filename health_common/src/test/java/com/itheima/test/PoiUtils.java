package com.itheima.test;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author: 汪诚
 * @Date: 2020/2/10 21:47
 */
public class PoiUtils {

    @Test
    public void test01() throws IOException {

        XSSFWorkbook xssfWorkbook = null;

        try {
            //获取工作簿
            xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File("G:\\poi.xlsx")));
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);

            //获取最后一行索引
            int lastRowNum = sheetAt.getLastRowNum();

            for (int i = 0; i <=lastRowNum ; i++) {
                XSSFRow row = sheetAt.getRow(i);
                short lastCellNum = row.getLastCellNum();
                for (int j = 0; j < lastCellNum; j++) {
                    XSSFCell cell = row.getCell(j);
                    System.out.println(cell.getStringCellValue());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(xssfWorkbook != null){
                xssfWorkbook.close();
            }
        }
    }
}
