package com.relyme.linkOccupation.test;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelTest {

    public static void main(String[] args) {
        FileOutputStream fileOut = null;
        BufferedImage bufferImg = null;
        //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            bufferImg = ImageIO.read(new File("C:\\linkOccupation\\file\\upload\\fa10292c7eca69a6b144a234d3b7dd2b.jpg"));
            ImageIO.write(bufferImg, "jpg", byteArrayOut);

            XSSFWorkbook wb1 = new XSSFWorkbook();
            XSSFSheet sheet1 = wb1.createSheet("test picture");
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            XSSFDrawing patriarch = sheet1.createDrawingPatriarch();
            //anchor主要用于设置图片的属性

            /**
             * @param dx1 图片的左上角在开始单元格（col1,row1）中的横坐标
             * @param dy1 图片的左上角在开始单元格（col1,row1）中的纵坐标
             * @param dx2 图片的右下角在结束单元格（col2,row2）中的横坐标
             * @param dy2 图片的右下角在结束单元格（col2,row2）中的纵坐标
             * @param col1 开始单元格所处的列号, base 0, 图片左上角在开始单元格内
             * @param row1 开始单元格所处的行号, base 0, 图片左上角在开始单元格内
             * @param col2 结束单元格所处的列号, base 0, 图片右下角在结束单元格内
             * @param row2 结束单元格所处的行号, base 0, 图片右下角在结束单元格内
             */
            //XSSFClientAnchor anchor = new XSSFClientAnchor(dx1, dy1, dx2, dy2,(short) col1, row1, (short) col2, row2);
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0,(short) 1, 1, (short) 5, 2);
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            //插入图片
            patriarch.createPicture(anchor, wb1.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
            fileOut = new FileOutputStream("D:/测试Excel.xlsx");
            // 写入excel文件
            wb1.write(fileOut);
            System.out.println("----Excle文件已生成------");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fileOut != null){
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
