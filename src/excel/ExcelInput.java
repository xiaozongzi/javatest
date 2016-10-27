package excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangzz on 2016/10/14.
 */
public class ExcelInput {
    static FileInputStream fileInputStream;
    static FileInputStream fileContentInputStream;
    static XSSFWorkbook    xwb;
    static XSSFWorkbook    xwbContent;
    static List<List<Object>> lists;
    static List<List<Object>> listsContent;
    static String excelFileName    = "E:\\skypeFile\\Android_N_0924_AllMerged(2).xlsx";
    static String excelContentFile="E:\\skypeFile\\copy\\testcase(3).xlsx";
    static String excelOutFile="E:\\skypeFile\\copy\\testcase2.xlsx";
    public static void main(String[] args) {
        try {
            initfile();
            initContentfile();
            for (int i = 0; i < listsContent.size(); i++) {

                try {
                    if (listsContent.get(i).get(9)==null||listsContent.get(i).get(9).toString().trim().equals("")){
                        continue;
                    }else {
                        findValue(i);
                    }
                } catch (Exception e) {
                    System.out.println(i);
                    e.printStackTrace();
                }
            }
            FileOutputStream outputStream=new FileOutputStream(excelOutFile);
            xwb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private static void findValue(int i) {
        for (int j = 0; j < lists.size(); j++) {
            if (listsContent.get(i).get(0).toString().trim().equals(lists.get(j).get(0).toString().trim())&&
                    listsContent.get(i).get(3).toString().trim().equals(lists.get(j).get(3).toString().trim())){


                setValue(i, j,7);
                setValue(i, j,8);
                setValue(i, j,9);
                return;
//                            xwb.getSheetAt(0).getRow(j).copyRowFrom(xwbContent.getSheetAt(0).getRow(i),new CellCopyPolicy());
            }
        }
    }

    private static void setValue(int i, int j,int cellNum) {
        XSSFCell cell = xwb.getSheetAt(0).getRow(j).getCell(cellNum);
        if (cell==null){
            cell=xwb.getSheetAt(0).getRow(j).createCell(cellNum);
        }
        cell.setCellType(CellType.STRING);
        cell.setCellValue(getRawValue(i, cellNum));
    }

    private static String getRawValue(int i, int cellNum) {
        if (xwbContent.getSheetAt(0).getRow(i).getCell(cellNum)==null)
            return "";
        return xwbContent.getSheetAt(0).getRow(i).getCell(cellNum).getRawValue();
    }

    private static List<List<Object>> read2007Excel(FileInputStream fileInputStream,XSSFWorkbook    xwb)
            throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径


        // 读取第一章表格内容
        XSSFSheet sheet   = xwb.getSheetAt(0);
        Object    value   = null;
        XSSFRow   row     = null;
        XSSFCell  cell    = null;
        int       counter = 0;
        int       rows    = 0;
        for (int i = sheet.getFirstRowNum(); counter < sheet
                .getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (i == 0) {
                rows = row.getLastCellNum();
            }
            if (row == null) {
                continue;
            } else {
                counter++;
            }
            List<Object> linked = new LinkedList<Object>();
            for (int j = row.getFirstCellNum(); j <= rows; j++) {

                cell = row.getCell(j);
                if (cell == null) {
                    linked.add("");
                    continue;
                }
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String
                // 字符
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
                DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
//                        System.out.println(i + "行" + j + " 列 is String type");
                        value = cell.getStringCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                      /*  System.out.println(i + "行" + j
                                + " 列 is Number type ; DateFormt:"
                                + cell.getCellStyle().getDataFormatString());*/
                        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                            value = df.format(cell.getNumericCellValue());
                        } else if ("General".equals(cell.getCellStyle()
                                                            .getDataFormatString())) {
                            value = nf.format(cell.getNumericCellValue());
                        } else {
                            value = sdf.format(HSSFDateUtil.getJavaDate(cell
                                                                                .getNumericCellValue()));
                        }
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
//                        System.out.println(i + "行" + j + " 列 is Boolean type");
                        value = cell.getBooleanCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_BLANK:
//                        System.out.println(i + "行" + j + " 列 is Blank type");
                        value = "";
                        break;
                    default:
//                        System.out.println(i + "行" + j + " 列 is default type");
                        value = cell.toString();
                }
                if (value == null || "".equals(value)) {
                    linked.add("");
                }
                linked.add(value);
            }
            list.add(linked);
        }
        fileInputStream.close();
        return list;
    }
    private static List<List<Object>> read2003Excel(FileInputStream fileInputStream,HSSFWorkbook xwb)
            throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径


        // 读取第一章表格内容
        HSSFSheet sheet   = xwb.getSheetAt(0);
        Object    value   = null;
        HSSFRow   row     = null;
        HSSFCell  cell    = null;
        int       counter = 0;
        int       rows    = 0;
        for (int i = sheet.getFirstRowNum(); counter < sheet
                .getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (i == 0) {
                rows = row.getLastCellNum();
            }
            if (row == null) {
                continue;
            } else {
                counter++;
            }
            List<Object> linked = new LinkedList<Object>();
            for (int j = row.getFirstCellNum(); j <= rows; j++) {

                cell = row.getCell(j);
                if (cell == null) {
                    linked.add("");
                    continue;
                }
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String
                // 字符
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
                DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
//                        System.out.println(i + "行" + j + " 列 is String type");
                        value = cell.getStringCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                      /*  System.out.println(i + "行" + j
                                + " 列 is Number type ; DateFormt:"
                                + cell.getCellStyle().getDataFormatString());*/
                        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                            value = df.format(cell.getNumericCellValue());
                        } else if ("General".equals(cell.getCellStyle()
                                                            .getDataFormatString())) {
                            value = nf.format(cell.getNumericCellValue());
                        } else {
                            value = sdf.format(HSSFDateUtil.getJavaDate(cell
                                                                                .getNumericCellValue()));
                        }
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
//                        System.out.println(i + "行" + j + " 列 is Boolean type");
                        value = cell.getBooleanCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_BLANK:
//                        System.out.println(i + "行" + j + " 列 is Blank type");
                        value = "";
                        break;
                    default:
//                        System.out.println(i + "行" + j + " 列 is default type");
                        value = cell.toString();
                }
                if (value == null || "".equals(value)) {
                    linked.add("");
                }
                linked.add(value);
            }
            list.add(linked);
        }
        fileInputStream.close();
        return list;
    }

    private static void initfile() throws IOException {
        fileInputStream = new FileInputStream(excelFileName);
        xwb = new XSSFWorkbook(fileInputStream);
       lists= read2007Excel(fileInputStream,xwb);
    }
    private static void initContentfile() throws IOException {
        fileContentInputStream = new FileInputStream(excelContentFile);
        xwbContent = new XSSFWorkbook(fileContentInputStream);
        listsContent=read2007Excel(fileContentInputStream,xwbContent);
    }

}
