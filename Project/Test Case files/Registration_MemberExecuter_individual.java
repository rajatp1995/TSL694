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

public class Registration_MemberExecuter_individual {

	int mainSheetRowCount = 0;
	WebDriver driver;
	Sheet excelSheet;
	GetByObjectAndAct getAndAct;
	HashMap<String, String> variableData = new HashMap<String, String>();
	Row row = null;
	public String filePath = null;

	String reportSheetName = "Report";
	String reportFileName = "registration_members_report";

	String fileName_main = "registration_members";

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

	/*
	 *		

	 * */
	@Test(dataProvider = "DP1", dataProviderClass = CallDDT.class)
	public void testLogin(String first_name, String middle_name,
			String last_name, String first_name1, String middle_name1,
			String last_name1, String first_name2, String middle_name2,
			String last_name2, String month, String year, String land_acre,
			String nation, String father_name, String mother_name,
			String pan_no, String amt, String voter_id, String dl,
			String passport_no, String aadhar_no, String address_first,
			String address_second, String state_name, String pincode,
			String landline_no, String mobile_no, String email_id,
			String income_money, String first_name_photo,
			String location_drive, String first_name_sign,
			String location_drive_2) throws Exception {
		ArrayList<String> data = new ArrayList<String>();

		data.add(first_name);
		data.add(middle_name);
		data.add(last_name);
		data.add(first_name1);
		data.add(middle_name1);
		data.add(last_name1);
		data.add(first_name2);
		data.add(middle_name2);
		data.add(last_name2);
		data.add(month);
		data.add(year);
		data.add(land_acre);
		data.add(nation);
		data.add(father_name);
		data.add(mother_name);
		data.add(pan_no);
		data.add(amt);
		data.add(voter_id);
		data.add(dl);
		data.add(passport_no);

		data.add(aadhar_no);
		data.add(address_first);

		data.add(address_second);
		data.add(state_name);
		data.add(pincode);
		data.add(landline_no);
		data.add(mobile_no);
		data.add(email_id);
		data.add(income_money);
		data.add(first_name_photo);
		data.add(location_drive);
		data.add(first_name_sign);
		data.add(location_drive_2);

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

	@AfterClass
	public void tearDown() throws IOException {

		ExcelReportWriter exrw = new ExcelReportWriter();
		exrw.excelReportWriter(reportFileName, mainSheetRowCount);

		driver.quit();
		java.util.Date date = new java.util.Date();
		System.out.println("\n\nExecution Log - End Time - "
				+ new Timestamp(date.getTime()));
	}

}