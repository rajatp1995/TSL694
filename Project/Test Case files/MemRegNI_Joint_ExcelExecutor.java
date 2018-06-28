package softpac_final;

import org.testng.annotations.AfterMethod;
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

public class MemRegNI_Joint_ExcelExecutor {
	int mainSheetRowCount = 0;
	WebDriver driver;
	Sheet excelSheet;
	GetByObjectAndAct getAndAct;
	HashMap<String, String> variableData = new HashMap<String, String>();
	Row row = null;
	public String filePath = null;

	String sheetName = "Report";
	String fileName = "MemRegNI_JointReport";

	String fileName_main = "Member_Registration_non_individual_joint";

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

		filePath = "test//resources//data//reports//" + fileName + ".xlsx";
		String filePath_main = "test//resources//data//" + fileName_main
				+ ".xlsx";
		new CallDDT(filePath_main);

		XSSFWorkbook workbook = new XSSFWorkbook();
		workbook.createSheet(sheetName);

		FileOutputStream fileOut = new FileOutputStream(filePath);
		workbook.write(fileOut);
		fileOut.close();
		System.out.println("Generated Excel Sheet");
	}

	int mainRowCount = 1;

	@Test(dataProvider = "DP1", dataProviderClass = CallDDT.class)
	public void testLogin(String name, String holdername1, String holdername2,
			String operator1, String operator2, String constitution,
			String docDetails, String nameOfDoc, String issueAuthority,
			String placeOfIssue, String regNum, String month, String year,
			String date, String pan, String address1, String address2,
			String villageName, String state, String pin, String landline,
			String mobNum, String email, String income, String education,
			String natureOfActivity, String activeName, String uploadPhoto,
			String activeID, String uploadSign) throws Exception {
		ArrayList<String> data = new ArrayList<String>();

		data.add(name);
		data.add(holdername1);
		data.add(holdername2);
		data.add(operator1);
		data.add(operator2);
		data.add(constitution);
		data.add(docDetails);
		data.add(nameOfDoc);
		data.add(issueAuthority);
		data.add(placeOfIssue);
		data.add(regNum);
		data.add(month);
		data.add(year);
		data.add(date);
		data.add(pan);
		data.add(address1);
		data.add(address2);
		data.add(villageName);
		data.add(state);
		data.add(pin);
		data.add(landline);
		data.add(mobNum);
		data.add(email);
		data.add(income);
		data.add(education);
		data.add(natureOfActivity);
		data.add(activeName);
		data.add(uploadPhoto);
		data.add(activeID);
		data.add(uploadSign);
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
					System.out.println(data);
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
						fileName);

				logData.clear();
				logStatus.clear();

			} else if (row.getCell(0).toString().equals("end")) {
				System.out.println("All Test Cases End");
				ExcelWriter excelWriter = new ExcelWriter();
				excelWriter.excelWriter(logData, logStatus, mainRowCount,
						fileName);
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
						fileName);
				logData.clear();
				logStatus.clear();
			}
			mainRowCount++;
		}

	}

	@AfterClass
	public void tearDown() throws IOException {

		ExcelReportWriter exrw = new ExcelReportWriter();
		exrw.excelReportWriter(fileName, mainSheetRowCount);

		driver.quit();
		java.util.Date date = new java.util.Date();
		System.out.println("\n\nExecution Log - End Time - "
				+ new Timestamp(date.getTime()));
	}

}