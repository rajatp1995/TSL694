package keywordDriven;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import com.google.common.collect.Lists;

public class CallDDT {

	// for fetching the filepath of keyword(s) excel file
	static String filePath = null;

	public CallDDT(String filepath) {
		this.filePath = filepath;
	}

	@DataProvider(name = "DP1")
	public static Object[][] createData2() {
		Object[][] retObjArr = getExcelData(filePath, "Frameworksheet");
		return (retObjArr);
	}

	public static Object[][] getExcelData(String fileName, String sheetName) {

		// for operating with numeric values from excel sheet
		DataFormatter fmt = new DataFormatter();
		Workbook wb = null;

		// flag used for counting the number of all data driven cases in test
		// case
		int flagDDTCases = 0;
		try {
			File file = new File(fileName);
			FileInputStream fs = new FileInputStream(file);
			// check for xls or xlsx file
			if (fileName.substring(fileName.indexOf(".")).equals(".xlsx")) {
				wb = new XSSFWorkbook(fs);
			} else if (fileName.substring(fileName.indexOf(".")).equals(".xls")) {
				wb = new HSSFWorkbook(fs);
			}

			Sheet sh = wb.getSheet(sheetName);

			int totalNoOfRows = sh.getPhysicalNumberOfRows();
			List<String> dataList = new ArrayList<String>();

			// Traverse through the sheet

			// traverse all rows starting from index 1 (row at index 0 contains
			// the row-heading)
			for (int i = 1; i <= totalNoOfRows - 1; i++) {
				Row row = sh.getRow(i);

				// traverse every cell in the row
				for (Cell cell : row) {

					// if the traversed cell contains the special chars "!&",
					// then it requires DDT
					if (cell.getStringCellValue().contains("!&")) {

						// incrementing the flag
						flagDDTCases++;

						// cell index 6 contains the variable reference to DDT
						// fields
						// fetch the string and store it in another variable
						String variableRefLoc = row.getCell(5).getStringCellValue();

						// fetch the substring after special chars "!&" and open
						// that sheet for performing DDT
						String newTempFile = cell.getStringCellValue().substring(2).trim();
						Sheet sh2 = wb.getSheet(newTempFile);

						// init new sheet row traverse pointer to 0
						int newRowNum = 0;

						// Traverse through the DDT file
						// Increment the DDT file row number
						Row rowNew = sh2.getRow(newRowNum++);
						int rowsNewCount = sh2.getPhysicalNumberOfRows();
						// Traverse through each cell in the row
						for (Cell cell2 : rowNew) {
							// find the object locator
							if (cell2.getStringCellValue().equalsIgnoreCase(variableRefLoc)) {
								// get the cell number that contains the data
								int dataCellNum = cell2.getColumnIndex();

								// for each ddt test case, fetch the data
								for (int k = 0; k < rowsNewCount - 1; k++) {
									rowNew = sh2.getRow(newRowNum++);
									Cell cell3 = rowNew.getCell(dataCellNum);
									String tempString = fmt.formatCellValue(cell3);
									System.out.println(tempString);
									// add it in the list
									dataList.add(tempString);

								} // ddt test cases end
							} // end if condition for reference variable locator
						} // end cell traverse of DDT file

					} // end condition check for special chars
				} // end cell traverse for Main file
			} // end row traverse for Main file

			// convert the list into object array

			// Using Google Guava library to partition the list

			// divide the list into partitions of flagDDTCases
			// i.e. if there are 3 DDT cases, then divide list into 3 partitions
			// as the list contains all the data

			// create a list of lists and store the divided lists
			/*float x = (dataList.size() / flagDDTCases);*/
			double size = dataList.size();
			double flag = flagDDTCases;
			
			
			
			List<List<String>> listOfDataLists = Lists.partition(dataList, (int)Math.ceil(size/flag));

			// get number of elements in one list , i.e. the number of data for
			// one column in DDT
			int elementsInDDTColumn = listOfDataLists.get(0).size();

			// new List for storing
			List<String> sortedList = new ArrayList<>();
			String tempString = null;

			// Dividing groups of Data into sets from same indices
			// index 1 of each group in one set, index 2 in one set and so on .
			// . .
			for (int i = 0; i < elementsInDDTColumn; i++) {
				for (int j = 0; j < listOfDataLists.size(); j++) {
					tempString = listOfDataLists.get(j).get(i);
					sortedList.add(tempString);
				}
			}

			// Using Google Guava library, partition the list again
			List<List<String>> finalList = Lists.partition(sortedList, flagDDTCases);

			// This line converts the list into string array as the @Test
			// receives an object array as parameter for DDT
			String[][] stringArray = finalList.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
			System.out.println(stringArray.length+ "      hello");
			System.out.println(stringArray[0].length+ "      hi");

			return stringArray;

		} catch (Exception e) {
			System.out.println("error in getExcelData() " + e.getMessage());
		}
		return null;
	}

}