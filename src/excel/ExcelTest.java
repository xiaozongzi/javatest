package excel;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.plaf.ColorUIResource;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangzz on 2016/9/28.
 */
public class ExcelTest {
    static List<List<Object>> lists;
    static String findStirng = "packages;apps;Settings;res;values;strings.xml";
    static String excelFileName = "E:\\skypeFile\\copy\\Android_N_0924_AllMerged.xlsx";
    static String excelFileOutName = "E:\\skypeFile\\copy\\Android_N_0924_AllMerged2.xlsx";
    static String wordFilePath = "F:\\phoneScreen\\sound\\word\\新建文件夹";
    static String appName = "sound";
    static String pictureName;
    static boolean resetPictureName = true;
    static FileInputStream fileInputStream;

    static XSSFWorkbook xwb;
    static XSSFWorkbook xwbDO;
    public static void main(String[] args) {
        File file = new File(excelFileName);
        try {
            lists = read2007Excel(file);
            removeOther();
        } catch (IOException e) {
            e.printStackTrace();
        }
//       doc("F:\\chorme\\金山手机助手截图20160928 092046.png.docx");

        try {
            fileOutputStream = new FileOutputStream(excelFileOutName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        docFile();
        setColor();
        try {

            xwbDO.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private static void setColor() {
        for (int i = 0; i < lists.size(); i++) {
            XSSFRow row = sheetDO.getRow(i);
            String stringCellValue = row.getCell(9).getStringCellValue();
            if (!isStringNull(stringCellValue)){
                set(row,true);
            }else {
                set(row,false);
            }
        }
    }

    static XSSFSheet sheet;
    static XSSFSheet sheetDO;
    static int lastRowNum =0;
    private static void removeOther() {
        List<List<Object>> tampList = new ArrayList<List<Object>>();
        for (int z = 0; z < lists.size(); z++) {
            String compare = ((String) lists.get(z).get(0));
            if (compare.contains(findStirng)) {
                tampList.add(lists.get(z));
                if (xwbDO==null){
                    xwbDO=new XSSFWorkbook();
                    sheetDO=xwbDO.createSheet(xwb.getSheetAt(0).getSheetName());
                }

                XSSFRow row = sheetDO.createRow(lastRowNum);

                for (int i = 0; i < 11; i++) {
                    XSSFCell cell = row.createCell(i);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(String.valueOf(lists.get(z).get(i)));

                }
                lastRowNum++;
            } else {
                if (sheet == null)
                    sheet = xwb.getSheetAt(0);

                sheet.removeRow(sheet.getRow(z));

//                sheet.rem
            }
        }
        lists = tampList;

    }

    private static void docFile() {
        File file = new File(wordFilePath);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (isDoc(files[i])) {
                        doc(files[i]);
                    }
                }
            }
        }
    }

    private static boolean isDoc(File f) {

        String fileName = f.getName();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (prefix.equals("docx") || prefix.equals("doc")) {
            return true;
        }
        return false;
    }
    private static boolean setPath;
    private static void doc(File url) {
        resetPictureName = true;
        System.out.println(url.getName());

        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(url));
            if (document != null) {
                List<XWPFParagraph> paragraphs = document.getParagraphs();
                for (int i = 0; i < paragraphs.size(); i++) {
                    List<XWPFRun> runs = paragraphs.get(i).getRuns();
                    for (int j = 0; j < runs.size(); j++) {
                        String fontFamily = runs.get(j).getText(0);
                       /* CTEmpty[] crArray = runs.get(j).getCTR().getC();
                        for (int k = 0; k < crArray.length; k++) {
                            crArray[k].
                        }*/

                        try {

                            int f= Integer.parseInt(fontFamily);
                            setPath=false;
                        } catch (NumberFormatException e) {
//                            e.printStackTrace();
                            if (!setPath){
                                path="";
                            }
                            setPath=true;
                            path+=fontFamily;
                            continue;
                        }
                        if (fontFamily != null && !fontFamily.equals("")) {
                            if (lists != null) {
                                for (int z = 0; z < lists.size(); z++) {
//                                    String compare = ((String) lists.get(z).get(0));
//                                    if (compare.contains(findStirng)) {
                                    String ss = (String) lists.get(z).get(5);

//                                            changePictureName(url.getName());
                                        writeExcel(z, url.getName(),ss,z,fontFamily);

//                                    }


                                }
                            }
                        }
                    }
                }

            }
           /* WordExtractor extractor = new WordExtractor(new FileInputStream("F:\\chorme\\金山手机助手截图20160928 092046.png.docx"));
            String text = extractor.getText();*/
          /*  HWPFDocument document=new HWPFDocument(new FileInputStream("F:\\chorme\\金山手机助手截图20160928 092046.png.docx"));
            String s=document.getText().toString();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isContains(String fontFamily, String ss) {
        String contentstirng = fontFamily.toLowerCase().trim();
        String needContent = ss.toLowerCase().trim();
        try {
            int contentstirng1 = Integer.parseInt(contentstirng);
            String substring = needContent.substring(needContent.indexOf("#")+1, needContent.lastIndexOf("#"));
            return substring.equals(contentstirng);
        } catch (NumberFormatException e) {
            e.printStackTrace();

            return false;
        }

       /* if (contentstirng.contains(needContent)) {
            *//*if (contentstirng.equals(needContent))
                return true;
            int StartPos = contentstirng.indexOf(needContent);
            *//*
            String[] split = contentstirng.split(needContent);
            int size = split.length;
            switch (size) {
                case 0:
                    return true;
                case 1:
                    if (contentstirng.startsWith(needContent)) {
                        if (split[0].startsWith(" ") || split[0].equals(""))
                            return true;
                        else return false;
                    } else {
                        if (split[0].endsWith(" ") || split[0].equals(""))
                            return true;
                        else return false;
                    }

                case 2:
                    if ((split[0].endsWith(" ") || split[0].equals("")) && (split[1].startsWith(" ") || split[1].equals("")))
                        return true;
                    else return false;
                default:
                    return false;
            }
//            return true;
        } else
            return false;*/
    }

    private static void changePictureName(String name) {

    }

    static FileOutputStream fileOutputStream;
    private static String path;
    private static void writeExcel(int rowNum, String fileName,String ss,int z,String fontFamily) {
        boolean isReturen;
        XSSFRow row = sheetDO.getRow(rowNum);
        if (isContains(fontFamily, ss)) {

        try {
         /*   FileInputStream fileInputStream = new FileInputStream(excelFileName);
            xwb = new XSSFWorkbook(fileInputStream);*/

            // 读取第一章表格内容


            XSSFCell cell = row.getCell(10);
            if (cell == null) {
                cell = row.createCell(10);

            }
            String stringCellValue = cell.getStringCellValue();
            if (!isStringNull(stringCellValue)){
                return;
            }
            String rawValue = row.getCell(3).getStringCellValue();
            if (resetPictureName) {
                pictureName = appName + "_" + rawValue;
                resetPictureName = false;
            }

            cell.setCellType(CellType.STRING);
            cell.setCellValue(pictureName + ".png");


            XSSFCell cell1 = row.getCell(9);
            if (cell1 == null) {
                cell1 = row.createCell(9);

            }

            cell1.setCellType(CellType.STRING);
            cell1.setCellValue(path);
//            set(row,true);
            System.out.println(fileName+ "//" + ss + "//" + "pos=" + (z + 1)+"//pictureName="+pictureName+".png");

        } catch (Exception e) {
            e.printStackTrace();
        }
        }/*else {
            set(row,false);
        }*/


    }

    private static boolean isStringNull(String stringCellValue) {
        return stringCellValue==null||stringCellValue.trim().equals("");
    }

    private static void set(XSSFRow row,boolean find) {
        XSSFCellStyle rowStyle;
        rowStyle = xwbDO.createCellStyle();
        rowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        rowStyle.setFillForegroundColor(new XSSFColor(find?new ColorUIResource(255, 249, 45):new ColorUIResource(255, 0, 0)));

        rowStyle.setFillBackgroundColor(new XSSFColor(find?new ColorUIResource(255, 249, 45):new ColorUIResource(255, 0, 0)));
        for (int i = 0; i < 11; i++) {
            if (row.getCell(i) != null)
                row.getCell(i).setCellStyle(rowStyle);
        }
          /*  row.getCell(0).setCellStyle(rowStyle);
//            rowStyle.setcolo*/
        row.setRowStyle(rowStyle);
    }

    private static String getPath(String fileName) {


     /*   if (fileName.contains("-")) {
            fileName = fileName.replace("-", "->");
            fileName = fileName.substring(0, fileName.indexOf("."));
        } else {
            fileName = fileName.substring(0, fileName.indexOf("."));
        }*/
        if (fileName.contains("(") && fileName.contains(")")) {
            String substring = fileName.substring(fileName.lastIndexOf("(") + 1, fileName.lastIndexOf(")"));
            String total = "(" + substring + ")";
            if (fileName.endsWith(total)) {
                try {
                    Integer.parseInt(substring);
                    fileName = fileName.replace(total, "");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileName;

    }



    private static List<List<Object>> read2007Excel(File file)
            throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        fileInputStream = new FileInputStream(file);
        xwb = new XSSFWorkbook(fileInputStream);

        // 读取第一章表格内容
        XSSFSheet sheet = xwb.getSheetAt(0);
        Object value = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        int counter = 0;
        int rows = 0;
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

}
