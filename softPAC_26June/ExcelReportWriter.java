package softPAC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReportWriter {
	
	
	
	public void excelReportWriter(String fileName, int mainRowCount)
			throws IOException {
		
		System.out.println("Welcome"+ fileName+ mainRowCount);
		
		boolean flag = true;
		
		String filePath = "test//resources//data//" + fileName + ".xlsx" ;
		FileInputStream inputStream = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		
		CellStyle style_Pass = workbook.createCellStyle();
		style_Pass.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		style_Pass.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style_Pass.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
		style_Pass.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
		style_Pass.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
		style_Pass.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
		
		CellStyle style_Fail = workbook.createCellStyle();
		style_Fail.setFillForegroundColor(IndexedColors.RED.getIndex());
		style_Fail.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style_Fail.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
		style_Fail.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
		style_Fail.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
		style_Fail.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
		
		CellStyle style_TestCase = workbook.createCellStyle();
		style_TestCase.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
		style_TestCase.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style_TestCase.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
		style_TestCase.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
		style_TestCase.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
		style_TestCase.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);

		int resultCellNum = 0;
		int rowNum = 0;
		Row firstRow = sheet.getRow(rowNum);
		for(int i =0; i< firstRow.getPhysicalNumberOfCells(); i++){
			if (firstRow.getCell(i).toString().equalsIgnoreCase("RESULT")) {
				resultCellNum = i;
			}
		}
		rowNum++;
		// main logic
		for(int i = 0; i<(sheet.getPhysicalNumberOfRows()/mainRowCount)+1; i++){
			flag = true;
			Row newR = sheet.getRow(rowNum);
			
				if(newR.getCell(resultCellNum).getStringCellValue().contains("No")){
					for(int j =0; j<mainRowCount-2; j++){
						Row newRX = sheet.getRow(++rowNum);
						if(newRX.getCell(resultCellNum).getStringCellValue().equalsIgnoreCase("failure")){
							flag = false;
						}
					}
					if(!flag){
						newR.getCell(resultCellNum).setCellValue("FAIL");
						newR.getCell(resultCellNum).setCellStyle(style_Fail);
						//newR.getCell(--resultCellNum).setCellStyle(style_Fail);
					} else {
						newR.getCell(resultCellNum).setCellValue("PASS");
						newR.getCell(resultCellNum).setCellStyle(style_Pass);
						//newR.getCell(--resultCellNum).setCellStyle(style_Fail);
					}
					
					/*for(int o = 0; o < resultCellNum; i++){
						newR.getCell(o).setCellStyle(style_Fail);
					}
					*/
				}
			System.out.println("current pointer at rownum: "+rowNum);
			++rowNum;
			
			/*for(int o = 0; o < resultCellNum; i++){
				newR.getCell(o).setCellStyle(style_Fail);
			}*/
			
		}
		
		/*for(int o = 0; o < resultCellNum; i++){
			newR.getCell(o).setCellStyle(style_Fail);
		}*/
		//sheet.getPhysicalNumberOfRows();

		//int rowStart = 1;
		for(int i = 1; i<sheet.getPhysicalNumberOfRows()-2; i++){
			Row rowD = sheet.getRow(i);
			if (rowD.getCell(0).getStringCellValue().length()!=0) {
				System.out.println(resultCellNum+ "heheheheheheheh");
				for(int o = 0; o < resultCellNum; o++){
					rowD.getCell(o).setCellStyle(style_TestCase);
					System.out.println("Damn");
				}
				
			}
		}
		
		
		
		try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
			workbook.write(outputStream);
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}