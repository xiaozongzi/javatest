package excel;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangzz on 2016/9/28.
 */
public class ExcelTest {
    public static void main(String[] args) {
       /* File file=new File("E:\\skypeFile\\copy\\Android_N_0924_AllMerged.xlsx");
        try {
            List<List<Object>> lists=read2007Excel(file);
            if (lists != null) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
       doc();
    }
    private static void doc(){
        try {
            XWPFDocument document= new XWPFDocument(new FileInputStream("F:\\chorme\\金山手机助手截图20160928 092046.png.docx"));
            if (document!=null){
                List<XWPFParagraph> paragraphs = document.getParagraphs();

            }
           /* WordExtractor extractor = new WordExtractor(new FileInputStream("F:\\chorme\\金山手机助手截图20160928 092046.png.docx"));
            String text = extractor.getText();*/
          /*  HWPFDocument document=new HWPFDocument(new FileInputStream("F:\\chorme\\金山手机助手截图20160928 092046.png.docx"));
            String s=document.getText().toString();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static List<List<Object>> read2007Excel(File file)
            throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));

        // 读取第一章表格内容
        XSSFSheet sheet = xwb.getSheetAt(0);
        Object value = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        int counter = 0;
         int rows=0;
        for (int i = sheet.getFirstRowNum(); counter < sheet
                .getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (i==0){
                rows=row.getLastCellNum();
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
                        System.out.println(i + "行" + j + " 列 is String type");
                        value = cell.getStringCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        System.out.println(i + "行" + j
                                + " 列 is Number type ; DateFormt:"
                                + cell.getCellStyle().getDataFormatString());
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
                        System.out.println(i + "行" + j + " 列 is Boolean type");
                        value = cell.getBooleanCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_BLANK:
                        System.out.println(i + "行" + j + " 列 is Blank type");
                        value = "";
                        break;
                    default:
                        System.out.println(i + "行" + j + " 列 is default type");
                        value = cell.toString();
                }
                if (value == null || "".equals(value)) {
                    linked.add("");
                }
                linked.add(value);
            }
            list.add(linked);
        }
        return list;
    }

}
