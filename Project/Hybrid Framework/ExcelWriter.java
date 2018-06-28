package keywordDriven;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

	static Sheet sheet = null;

	public void excelWriter(List<String[]> logData, List<String[]> logStatus, int mainRowCount, String fileName)
			throws IOException {

		String filePath = "test//resources//data//reports//" + fileName + ".xlsx";
		FileInputStream inputStream = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		sheet = workbook.getSheetAt(0);

		Row row2 = sheet.createRow(0);
		
 
	    CellStyle style_border = workbook.createCellStyle();
	    style_border.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	    style_border.setBorderTop(XSSFCellStyle.BORDER_THIN);
	    style_border.setBorderRight(XSSFCellStyle.BORDER_THIN);
	    style_border.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	    
	    CellStyle style_border_desc = workbook.createCellStyle();
	    style_border_desc.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    style_border_desc.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    style_border_desc.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
	    style_border_desc.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
	    style_border_desc.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
	    style_border_desc.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
	    


	    int cellNumHead=0;
		Cell cell2; 


		String[] heading = {"TEST DESCRIPTION", "KEYWORD","OBJECT LOCATOR", "OBJECT TYPE" , "VALUE", "VARIABLE", "RESULT", "REASON(if failure)"};
		
		for (String string : heading) {
			cell2 = row2.createCell(cellNumHead);
			cell2.setCellValue(string);
			cell2.setCellStyle(style_border_desc);
			cellNumHead++;
		}


		int tempCounter = 1;
		boolean flag = true;

		int cellnum = 0;
		int rownum = mainRowCount;
		for (String[] str : logData) {
			Row row = sheet.createRow(rownum);
			System.out.println("creating row : " + rownum);
			cellnum = 0;

			for (String str1 : str) {

				Cell cell = row.createCell(cellnum++);

				cell.setCellValue(str1);
				cell.setCellStyle(style_border);
				
			}

			for (String str2 : logStatus.get(tempCounter - 1)) {

				if (str2.equals("failure")) {
					flag = false;
				}
				Cell cell = row.createCell(cellnum++);

				cell.setCellValue(str2);
				cell.setCellStyle(style_border);
			}
			rownum++;
			tempCounter++;
		}

		for (int k = 0; k < 10; k++)
			sheet.autoSizeColumn(k);

		try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
			workbook.write(outputStream);
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
