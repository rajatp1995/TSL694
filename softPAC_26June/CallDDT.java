package softPAC;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

public class CallDDT {

	@DataProvider(name = "DP1")
	public static Object[][] createData2() {
		Object[][] retObjArr = getExcelData("test/resources/data/Member_Registration_NI.xlsx", "Frameworksheet");
		return (retObjArr);
	}

	public static Object[][] getExcelData(String fileName, String sheetName) {

		String[][] arrayExcelData = null;
		DataFormatter fmt = new DataFormatter();
		Workbook wb = null;
		List<Object[]> superman = new ArrayList<>();
		Object[][] matrix = null;
		List<String> lsls = new ArrayList<>();
		List<String> lsls2 = new ArrayList<>();
		int flag = 0;
		try {
			File file = new File(fileName);
			FileInputStream fs = new FileInputStream(file);
			// .xls
			if (fileName.substring(fileName.indexOf(".")).equals(".xlsx")) {
				wb = new XSSFWorkbook(fs);
			} else if (fileName.substring(fileName.indexOf(".")).equals(".xls")) {
				wb = new HSSFWorkbook(fs);
			}

			Sheet sh = wb.getSheet(sheetName);

			int totalNoOfRows = sh.getPhysicalNumberOfRows();
			int totalNoOfCols = sh.getRow(0).getPhysicalNumberOfCells();
			List<String> lsnew = new ArrayList<String>();
			List<Integer> lsnewI = new ArrayList<Integer>();

			arrayExcelData = new String[totalNoOfRows - 1][totalNoOfCols];
			for (int i = 1; i <= totalNoOfRows - 1; i++) {
				Row row = sh.getRow(i);
				for (Cell cell : row) {
					if (cell.getStringCellValue().contains("!&")) {
						flag++;

						String objectLoc = row.getCell(5).getStringCellValue();
						System.out.println("Object Locator: " + objectLoc);
						String newTempFile = cell.getStringCellValue().substring(2).trim();
						System.out.println("New Temp File: " + newTempFile);
						Sheet sh2 = wb.getSheet(newTempFile);
						// init new sheet row traverse pointer to 0
						int newRowNum = 0;
						Row rowNew = sh2.getRow(newRowNum++);
						int rowsNewCount = sh2.getPhysicalNumberOfRows();
						// find the object locator
						for (Cell cell2 : rowNew) {
							if (cell2.getStringCellValue().equals(objectLoc)) {
								int temoCellNum = cell2.getColumnIndex();

								for (int k = 0; k < rowsNewCount - 1; k++) {
									rowNew = sh2.getRow(newRowNum++);
									Cell cell3 = rowNew.getCell(temoCellNum);
									String tempString = fmt.formatCellValue(cell3);
									
									// start
									
/*									if (cell3.getCellType() == Cell.CELL_TYPE_NUMERIC) {

										tempString = cell3.getStringCellValue();
									}
									
					                if(cell3.getCellType()==Cell.CELL_TYPE_NUMERIC){

					                	tempString = String.valueOf(cell3.getNumericCellValue());
					                }*/
										
									//end
									System.out.println(tempString);
									lsnew.add(tempString);
								}
							}
						}
					}
				}
			}
			Object[] array = lsnew.toArray();
			System.out.println(lsnew + "  " + flag);
			List<List<String>> lsx = Lists.partition(lsnew, lsnew.size() / flag);
			System.out.println(lsx);
			int elements = lsx.get(0).size();

			List<String> fArray = new ArrayList<>();
			String tempEle = null;

			// Dividing groups of Data
			for (int i = 0; i < elements; i++) {
				for (int j = 0; j < lsx.size(); j++) {
					tempEle = lsx.get(j).get(i);
					fArray.add(tempEle);
				}
			}

			List<List<String>> lsxFinal = Lists.partition(fArray, flag);
			System.out.println(lsxFinal);
			Object[][] ironMan = new Object[lsxFinal.size()][];
			for (int i = 0; i < lsxFinal.size(); i++) {
				List<String> row = lsxFinal.get(i);
				array[i] = row.toArray(new Object[row.size()]);
			}
			String[][] stringArray = lsxFinal.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
			return stringArray;

		} catch (Exception e) {
			System.out.println("error in getExcelData() " + e.getMessage());
		}
		return gamechanger(matrix, lsls.size(), lsls2.size());
	}

	private static Object[][] gamechanger(Object[][] table, int cols, int rows) {

		Object[][] transposedMatrix = new Object[cols][rows];

		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				transposedMatrix[i][j] = table[j][i];
			}
		}
		return transposedMatrix;
	}

	public static Object[][] chunkArray(Object[] array, int chunkSize) {
		int numOfChunks = chunkSize;
		Object[][] output = new Object[numOfChunks][];

		for (int i = 0; i < numOfChunks; ++i) {
			int start = i * chunkSize;
			int length = Math.min(array.length - start, chunkSize);

			Object[] temp = new Object[length];
			System.arraycopy(array, start, temp, 0, length);
			output[i] = temp;
		}
		return output;
	}

	public static Object[][] trasposeMatrix(Object[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;

		Object[][] trasposedMatrix = new Object[n][m];

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				trasposedMatrix[y][x] = matrix[x][y];
			}
		}
		return trasposedMatrix;
	}

}
