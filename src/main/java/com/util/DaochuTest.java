package com.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DaochuTest {


    /**
     *
     * @Title: WriteExcel
     * @Description: 执行导出Excel操作
     * @param
     * @return boolean
     * @throws
     */
    public static boolean WriteExcel(List<Map> list, String[] excelHeader, String[] mapKey,String titleName, HttpServletResponse response) {

//        String excelFile = "D:/bigData.xlsx";
        // 内存中只创建100个对象，写临时文件，当超过100条，就将内存中不用的对象释放。
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        Sheet sheet = null; // 工作表对象
        Row nRow = null; // 行对象
        Cell nCell = null; // 列对象

        try {
            //TODO 执行时的时间
            long startTime = System.currentTimeMillis();
            System.out.println("开始执行时间 : " + startTime / 1000 + "m");

            int rowNo = 0; // 总行号
            int pageRowNo = 0; // 页行号

            while ((list.size()-rowNo)>0) {
                // 打印300000条后切换到下个工作表，可根据需要自行拓展，2百万，3百万...数据一样操作，只要不超过1048576就可以
                if (rowNo % 300000 == 0) {
                    sheet = wb.createSheet("我的第" + (rowNo / 300000 + 1) + "个工作簿");// 建立新的sheet对象
                    sheet = wb.getSheetAt(rowNo / 300000); // 动态指定当前的工作表
                    pageRowNo = 1; // 每当新建了工作表就将当前工作表的行号重置为1
                    //定义表头
                    nRow = sheet.createRow(0);
                    for(int i=0;i<excelHeader.length;i++){
                        //标题的列
                        Cell cel0 = nRow.createCell(i);
                        //标题列的写入
                        cel0.setCellValue(excelHeader[i]);
                        //每列长度自适应
                        sheet.autoSizeColumn(i);
                    }
                }
                rowNo++;
                nRow = sheet.createRow(pageRowNo++); // 新建行对象

                // 打印每行，每行有6列数据 rsmd.getColumnCount()==6 --- 列属性的个数
                for (int i = 0; i < mapKey.length; i++) {
                    nCell = nRow.createCell(i);
                    nCell.setCellValue(list.get(i+1)+"");
                }
                if (rowNo % 10000 == 0) {
                    System.out.println("row no: " + rowNo);
                }
            }

            long finishedTime = System.currentTimeMillis(); // 处理完成时间
            System.out.println("数据读取完成耗时 : " + (finishedTime - startTime) / 1000 + "m");

            //定义表格名称
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = sdf.format(new Date()) + titleName;
            response.setContentType("application/vnd.ms-excel");
            response.setHeader( "Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" )+".xlsx" );

            OutputStream outputStream = null;
            try {
                outputStream = response.getOutputStream();
                wb.write(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            long stopTime = System.currentTimeMillis(); // 写文件时间
            System.out.println("数据写入Excel表格中耗时 : " + (stopTime - startTime) / 1000 + "m");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
