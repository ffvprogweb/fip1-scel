package com.fatec.scel.dd;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ManipulaExcel {
	private static XSSFSheet excelWSheet;
	private static XSSFWorkbook excelWBook;
	private static XSSFCell cell;
	private static XSSFRow row;
	private static String path;
	private static Logger logger;
	public static void setExcelFile(String path, String sheetName) throws Exception {
		try {
			// Abre o arquivo Excel
			logger = LogManager.getLogger(ManipulaExcel.class);
			logger.info("manipula excel abre arquivo path = " + path + "-" + sheetName);
			FileInputStream ExcelFile = new FileInputStream(path);
			// Acessa a planilha de dados de teste
			excelWBook = new XSSFWorkbook(ExcelFile);
			excelWSheet = excelWBook.getSheet(sheetName);
		} catch (Exception e) {
			logger.error("erro não esperado manipula excel set = " + e.getMessage());
			throw (e);
		}
	}

	public static String getCellData(int rowNum, int colNum) throws Exception {
		try {
			cell = excelWSheet.getRow(rowNum).getCell(colNum);
			String cellData = cell.getStringCellValue();
			return cellData;
		} catch (Exception e) {
			return "";
		}
	}
	public static void setCellData(String result, int rowNum, int colNum) throws Exception {
		try {
			row = excelWSheet.getRow(rowNum);
			cell = row.getCell(colNum);
			if (cell == null) {
				cell = row.createCell(colNum);
				cell.setCellValue(result);
			} else {
				cell.setCellValue(result);
			}
// Constante que define o caminho dos dados e o arquivo de dados
			FileOutputStream fileOut = new FileOutputStream(path);
			excelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			logger.error("erro no set excel data = " + e.getMessage());
			throw (e);
		}
	}
}
