package keywordDriven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFileSheet {

	// method takes three parameters
	// 1. file path
	// 2. file name
	// 3. sheet name in the excel file
	// returns the sheet from the given excel file
	@SuppressWarnings("resource")
	static public Sheet getExcelSheet(String filePath, String fileName, String sheetName) throws IOException {
		
		// Create a object of File class to open xlsx file
		File file = new File(filePath + "\\" + fileName);
		
		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);
		
		Workbook keywordWorkbook = null;
		// Find the file extension by spliting file name in substing and getting
		// only extension name
		
		String fileExtension = fileName.substring(fileName.indexOf("."));
		// Check condition if the file is xlsx file
		
		if (fileExtension.equals(".xlsx")) {
			// If it is xlsx file then create object of XSSFWorkbook class
			keywordWorkbook = new XSSFWorkbook(inputStream);
		}
		
		// Check condition if the file is xls file
		else if (fileExtension.equals(".xls")) {
			// If it is xls file then create object of XSSFWorkbook class
			keywordWorkbook = new HSSFWorkbook(inputStream);
		}
		
		// Read sheet inside the workbook by its name
		Sheet keyWordSheet = keywordWorkbook.getSheet(sheetName);
		return keyWordSheet;
	}
}