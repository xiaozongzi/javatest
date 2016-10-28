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
    static String[] findStirng       = {
            "vendor;qcom;proprietary;csm;res;values;strings",
//            "packages;providers;ContactsProvider;res;values;strings",
//            "frameworks;base;packages;PrintRecommendationService;res;values;strings",
    };

    static String excelFileName    = "E:\\skypeFile\\copy\\Android_N_0924_AllMerged(2).xlsx";
    static String excelFileOutName = "E:\\skypeFile\\copy\\Android_N_0924_AllMerged2.xlsx";
    static String wordFilePath     = "F:\\phoneScreen\\pdf\\result\\新建文件夹";
    static String appName          = "battery";
    static String pictureName;
    static boolean resetPictureName = true;
    static FileInputStream fileInputStream;
    static XSSFWorkbook    xwb;
    static XSSFWorkbook    xwbDO;
    static XSSFSheet       sheet;
    static int lastRowNum = 0;
    static FileOutputStream fileOutputStream;
    private static String filePath       = "F:\\phoneScreen\\security\\picture";
    private static String filePathNo     = "F:\\phoneScreen\\security\\pictureNo";
    private static String foundPath      = "F:\\phoneScreen\\security\\found";
    private static String foundPathNo    = "F:\\phoneScreen\\security\\foundNo";
    private static String notfoundPath   = "F:\\phoneScreen\\security\\notfound";
    private static String notfoundPathNo = "F:\\phoneScreen\\security\\notfoundNo";
    private static XSSFSheet sheetDO;
    private static boolean   setPath;
    private static String    path;

    private static int count = 0;
    private static List<String> foundList;
    private static int columeNum=11;
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
        } catch (Exception e) {
            e.printStackTrace();

        }
        notFoundFile();
    }

    private static List<List<Object>> read2007Excel(File file)
            throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        fileInputStream = new FileInputStream(file);
        xwb = new XSSFWorkbook(fileInputStream);

        // 读取第一章表格内容
        XSSFSheet sheet   = xwb.getSheetAt(0);
        Object    value   = null;
        XSSFRow   row     = null;
        XSSFCell  cell    = null;
        int       counter = 0;
        int       rows    = columeNum;
        for (int i = sheet.getFirstRowNum(); counter < sheet
                .getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
          /*  if (i == 0) {
                rows = row.getLastCellNum();
            }*/
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
                DecimalFormat nf = new DecimalFormat("0");// 格式化数字
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

    private static void removeOther() {
        List<List<Object>> tampList = new ArrayList<List<Object>>();
        for (int z = 0; z < lists.size(); z++) {
            String compare = ((String) lists.get(z).get(0));
            if (isContains(compare)) {
                tampList.add(lists.get(z));
                if (xwbDO == null) {
                    xwbDO = new XSSFWorkbook();
                    sheetDO = xwbDO.createSheet(xwb.getSheetAt(0).getSheetName());
                }

                XSSFRow row = sheetDO.createRow(lastRowNum);

                for (int i = 0; i < columeNum; i++) {

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

    private static boolean isContains(String compare) {
       /* for (int i = 0; i < findStirng.length; i++) {
            if (compare.contains(findStirng[i]))
                return true;
        }
        return false;*/
       return true;
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

    private static void setColor() {
        for (int i = 0; i < lists.size(); i++) {
            XSSFRow row             = sheetDO.getRow(i);
            String  stringCellValue = row.getCell(9).getStringCellValue();
            if (!isStringNull(stringCellValue)) {
                set(row, true);
            } else {
                set(row, false);
            }
        }
    }

    private static void notFoundFile() {
        setNotFoundFile(filePathNo, notfoundPathNo);
        setNotFoundFile(filePath, notfoundPath);
    }

    private static boolean isDoc(File f) {

        String fileName = f.getName();
        String prefix   = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (prefix.equals("docx") || prefix.equals("doc")) {
            return true;
        }
        return false;
    }

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

                            int f = Integer.parseInt(fontFamily);
                            setPath = false;
                        } catch (NumberFormatException e) {
//                            e.printStackTrace();
                            if (!setPath) {
                                path = "";
                            }
                            setPath = true;
                            resetPictureName = true;
                            path += fontFamily;
                            continue;
                        }
                        if (fontFamily != null && !fontFamily.equals("")) {
                            if (lists != null) {
                                for (int z = 0; z < lists.size(); z++) {
//                                    String compare = ((String) lists.get(z).get(0));
//                                    if (compare.contains(findStirng)) {
                                    String ss = (String) lists.get(z).get(5);

//                                            changePictureName(url.getName());
                                    writeExcel(z, url.getName(), ss, z, fontFamily);

//                                    }


                                }
                            }
                        }
                    }
                }

            }
           /* WordExtractor extractor = new WordExtractor(new FileInputStream("F:\\chorme\\金山手机助手截图20160928
           092046.png.docx"));
            String text = extractor.getText();*/
          /*  HWPFDocument document=new HWPFDocument(new FileInputStream("F:\\chorme\\金山手机助手截图20160928 092046.png
          .docx"));
            String s=document.getText().toString();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isStringNull(String stringCellValue) {
        return stringCellValue == null || stringCellValue.trim().equals("");
    }

    private static void set(XSSFRow row, boolean find) {
        XSSFCellStyle rowStyle;
        rowStyle = xwbDO.createCellStyle();
        rowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        rowStyle.setFillForegroundColor(new XSSFColor(find ? new ColorUIResource(255, 249, 45) : new ColorUIResource
                (255, 0, 0)));

        rowStyle.setFillBackgroundColor(new XSSFColor(find ? new ColorUIResource(255, 249, 45) : new ColorUIResource
                (255, 0, 0)));
        for (int i = 0; i < columeNum; i++) {
            if (row.getCell(i) != null)
                row.getCell(i).setCellStyle(rowStyle);
        }
          /*  row.getCell(0).setCellStyle(rowStyle);
//            rowStyle.setcolo*/
        row.setRowStyle(rowStyle);
    }

    private static void setNotFoundFile(String inPaht, String outPaht) {
        File   file  = new File(inPaht);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            setNotFondFile(files[i], outPaht);
        }
    }

    private static void writeExcel(int rowNum, String fileName, String ss, int z, String fontFamily) {
        boolean isReturen;
        XSSFRow row = sheetDO.getRow(rowNum);
        if (isContains(fontFamily, ss)) {

            try {
         /*   FileInputStream fileInputStream = new FileInputStream(excelFileName);
            xwb = new XSSFWorkbook(fileInputStream);*/

                // 读取第一章表格内容


                if (setPNGName(row)) return;


                XSSFCell cell1 = row.getCell(9);
                if (cell1 == null) {
                    cell1 = row.createCell(9);

                }

                cell1.setCellType(CellType.STRING);
                cell1.setCellValue(getPath(path));
                copyFile();
//            set(row,true);
                count++;
//            System.out.println(fileName+ "//" + ss + "//" + "pos=" + (z + 1)+"//pictureName="+pictureName+"
// .png"+"//count"+count);
                System.out.println("//path=" + path + "//count" + count);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }/*else {
            set(row,false);
        }*/


    }

    private static void setNotFondFile(File file, String outFile) {
        for (int i = 0; i < foundList.size(); i++) {
            if (file.getName().equals(foundList.get(i))) {
                return;
            }
        }
        try {
            int byteread = 0;
            int bytesum  = 0;
            outFile += "\\" + file.getName();
            FileInputStream  inputStream  = new FileInputStream(file);
            FileOutputStream outputStream = new FileOutputStream(outFile);
            byte[]           buffer       = new byte[1024];

            while ((byteread = inputStream.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小

                outputStream.write(buffer, 0, byteread);
            }
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isContains(String fontFamily, String ss) {
        String contentstirng = fontFamily.toLowerCase().trim();
        String needContent   = ss.toLowerCase().trim();
        try {
            int    contentstirng1 = Integer.parseInt(contentstirng);
            String substring      = needContent.substring(needContent.indexOf("#") + 1, needContent.lastIndexOf("#"));
            return substring.equals(contentstirng);
        } catch (Exception e) {
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
                    if ((split[0].endsWith(" ") || split[0].equals("")) && (split[1].startsWith(" ") || split[1]
                    .equals("")))
                        return true;
                    else return false;
                default:
                    return false;
            }
//            return true;
        } else
            return false;*/
    }

    private static boolean setPNGName(XSSFRow row) {
        XSSFCell cell = row.getCell(8);
        if (cell == null) {
            cell = row.createCell(8);

        }
        String stringCellValue = cell.getStringCellValue();
        if (!isStringNull(stringCellValue)) {
            return true;
        }
        String rawValue = row.getCell(0).getStringCellValue();
        if (resetPictureName) {
            pictureName = rawValue + row.getCell(3).getStringCellValue();
            resetPictureName = false;
        }
        cell.setCellType(CellType.STRING);
        cell.setCellValue(pictureName);
        return false;
    }

    private static String getPath(String fileName) {


        if (fileName.contains("-")) {
            fileName = fileName.replace("-", "->");
//            fileName = fileName.substring(0, fileName.indexOf("."));
        } else {
//            fileName = fileName.substring(0, fileName.indexOf("."));
        }
        if (fileName.contains("(") && fileName.contains(")")) {
            String substring = fileName.substring(fileName.lastIndexOf("(") + 1, fileName.lastIndexOf(")"));
            String total     = "(" + substring + ")";
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

    private static void copyFile() {
        File FilePath   = new File(filePath);
        File FilePathNo = new File(filePathNo);
//        File FoundPath =new File(foundPath);
//        File FoundPathNo =new File(foundPathNo);
        copy(FilePath, foundPath);
        copy(FilePathNo, foundPathNo);

    }

    private static void copy(File filePath, String path) {
        File[] files = filePath.listFiles();
        for (int i = 0; i < files.length; i++) {
            copyToFile(files[i], path);
        }
    }

    private static void copyToFile(File file, String foundPath) {
        if (foundList == null) {
            foundList = new ArrayList<String>();
        }
        String trim = file.getName().toLowerCase().trim();
        trim = trim.replace(".png", "").trim();
        if (trim.equals(path.toLowerCase().trim())) {
            try {
                int bytesum  = 0;
                int byteread = 0;
                foundPath = foundPath + "\\" + pictureName + ".png";

                FileInputStream inputStream = new FileInputStream(file);
                if (!foundList.contains(file.getName())) {
                    foundList.add(file.getName());
                }
                FileOutputStream outputStream = new FileOutputStream(foundPath);
                byte[]           buffer       = new byte[1024];

                while ((byteread = inputStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小

                    outputStream.write(buffer, 0, byteread);
                }
                inputStream.close();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {


        }
    }

    private static void changePictureName(String name) {

    }

}
