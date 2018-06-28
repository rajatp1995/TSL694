package softpac_final;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class FD_Single_Executor {

	int mainSheetRowCount = 0;
	WebDriver driver;
	Sheet excelSheet;
	GetByObjectAndAct getAndAct;
	HashMap<String, String> variableData = new HashMap<String, String>();
	Row row = null;
	public String filePath = null;

	String reportSheetName = "Report";
	String reportFileName = "Report_FD_Single";

	String fileName_main = "fixed_deposit_single";

	public HashMap<String, String> getVariableMap() {
		return variableData;
	}

	List<String[]> logData = new ArrayList<String[]>();

	public List<String[]> getLogMap() {
		return logData;
	}

	List<String[]> logStatus = new ArrayList<String[]>();

	public List<String[]> getLogStatus() {
		return logStatus;
	}

	@BeforeTest
	public void setUp() throws Exception {
		java.util.Date date = new java.util.Date();
		System.out.println("\n\nExecution Log - Start Time - "
				+ new Timestamp(date.getTime()));
		System.setProperty("webdriver.chrome.driver",
				"test\\resources\\drivers\\" + "chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		filePath = "test//resources//data//reports//" + reportFileName
				+ ".xlsx";
		String filePath_main = "test//resources//data//" + fileName_main
				+ ".xlsx";
		new CallDDT(filePath_main);

		XSSFWorkbook workbook = new XSSFWorkbook();
		workbook.createSheet(reportSheetName);

		FileOutputStream fileOut = new FileOutputStream(filePath);
		workbook.write(fileOut);
		fileOut.close();
		System.out.println("Generated Excel Sheet");
	}

	int mainRowCount = 1;

	@Test(dataProvider = "DP1", dataProviderClass = CallDDT.class, priority=0)
	public void testLogin(String nominee_name, String amount, String year,
			String month, String day) throws Exception {
		ArrayList<String> data = new ArrayList<String>();
		
		data.add(nominee_name);
		data.add(amount);
		data.add(year);
		data.add(month);
		data.add(day);
		
		

		GetByObjectAndAct getAndAct = new GetByObjectAndAct(driver);
		excelSheet = ReadExcelFileSheet.getExcelSheet("test\\resources\\data",
				fileName_main + ".xlsx", "Frameworksheet");
		mainSheetRowCount = excelSheet.getPhysicalNumberOfRows();
		int rowCount = excelSheet.getLastRowNum() - excelSheet.getFirstRowNum();
		int counter = 0;
		for (int i = 1; i < rowCount + 1; i++) {
			row = excelSheet.getRow(i);
			if (row.getCell(0).toString().length() == 0) {
				System.out.println(i);
				System.out.println(row.getCell(1).toString() + "----"
						+ row.getCell(2).toString() + "----"
						+ row.getCell(3).toString() + "----"
						+ row.getCell(4).toString() + "----"
						+ row.getCell(5).toString());
				try {

					if (row.getCell(4).toString().contains("!&")) {

						String[] tempS = { row.getCell(0).toString(),
								row.getCell(1).toString(),
								row.getCell(2).toString(),
								row.getCell(3).toString(), data.get(counter),
								row.getCell(5).toString() };
						logData.add(tempS);
						getAndAct.performAction(row.getCell(1).toString(), row
								.getCell(2).toString(), row.getCell(3)
								.toString(), data.get(counter), row.getCell(5)
								.toString());

						counter++;
					} else {

						String[] tempS = { row.getCell(0).toString(),
								row.getCell(1).toString(),
								row.getCell(2).toString(),
								row.getCell(3).toString(),
								row.getCell(4).toString(),
								row.getCell(5).toString() };
						logData.add(tempS);
						getAndAct.performAction(row.getCell(1).toString(), row
								.getCell(2).toString(), row.getCell(3)
								.toString(), row.getCell(4).toString(), row
								.getCell(5).toString());

					}
					String[] sStatus = { "success" };
					logStatus.add(sStatus);

				} catch (Exception e) {
					System.out.println("fail =" + e.getMessage());
					String[] fStatus = { "failure", e.getMessage() };
					logStatus.add(fStatus);
					for (Cell cell : row) {
						if (cell.toString().contains("!&")) {
							counter++;
						}
					}
				}

				ExcelWriter excelWriter = new ExcelWriter();
				excelWriter.excelWriter(logData, logStatus, mainRowCount,
						reportFileName);

				logData.clear();
				logStatus.clear();

			} else if (row.getCell(0).toString().equals("end")) {
				System.out.println("All Test Cases End");
				ExcelWriter excelWriter = new ExcelWriter();
				excelWriter.excelWriter(logData, logStatus, mainRowCount,
						reportFileName);
				logData.clear();
				logStatus.clear();

			} else {
				String[] tempS = { row.getCell(0).toString(),
						row.getCell(1).toString(), row.getCell(2).toString(),
						row.getCell(3).toString(), row.getCell(4).toString(),
						row.getCell(5).toString() };
				logData.add(tempS);
				String[] gsf = { "No Result Yet" };
				logStatus.add(gsf);

				ExcelWriter excelWriter = new ExcelWriter();
				excelWriter.excelWriter(logData, logStatus, mainRowCount,
						reportFileName);
				logData.clear();
				logStatus.clear();
			}
			mainRowCount++;
			
		}

	}

	@AfterTest
	public void tearDown() throws IOException {
		ExcelReportWriter exrw = new ExcelReportWriter();
		exrw.excelReportWriter(reportFileName, mainSheetRowCount);
		driver.quit();

		java.util.Date date = new java.util.Date();
		System.out.println("\n\nExecution Log - End Time - "
				+ new Timestamp(date.getTime()));
	}

}