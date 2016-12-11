package app.daoimp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import app.dao.PresentationsDao;
import app.model.Section;
import app.sort.Operator;
import app.sort.PresentationOperator;

public class PresentationsDaoImp implements PresentationsDao {

	@Override
	public Set<Operator> readPresentations() {
		return null;
	}

	@Override
	public void writePresentations() {

	}
	
	@Override
	public Set<Operator> getPresentations() {
		Set<Operator> operators = new HashSet<>();
		try {
			FileInputStream file = new FileInputStream(new File("test.xls"));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(1);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() > 0) {
					String presentationTitle  = null;
					String actor = null;
					String topic = null;
					String from = null;
					String to = null;
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (cell.getColumnIndex()) {
						case 0:
							presentationTitle = cell.getStringCellValue();
							break;
						case 1:
							actor = cell.getStringCellValue();
							break;
						case 2:
							topic = cell.getStringCellValue();
							break;
						case 3:
							from = new DataFormatter().formatCellValue(cell);
							break;
						case 4:
							to = new DataFormatter().formatCellValue(cell);
							break;
						}
					}
					operators.add(new PresentationOperator(presentationTitle, actor, topic, from, to, false,15));
				}
			}
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return operators;
	}

	@Override
	public Set<Operator> getPresentations(File input) {
		Set<Operator> operators = new HashSet<>();
		try {
			FileInputStream file = new FileInputStream(input);
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(1);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() > 0) {
					String presentationTitle  = null;
					String actor = null;
					String topic = null;
					String from = null;
					String to = null;
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (cell.getColumnIndex()) {
						case 0:
							presentationTitle = cell.getStringCellValue();
							break;
						case 1:
							actor = cell.getStringCellValue();
							break;
						case 2:
							topic = cell.getStringCellValue();
							break;
						case 3:
							from = new DataFormatter().formatCellValue(cell);
							break;
						case 4:
							to = new DataFormatter().formatCellValue(cell);
							break;
						}
					}
					operators.add(new PresentationOperator(presentationTitle, actor, topic, from, to, false,15));
				}
			}
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return operators;
	}

	@Override
	public Section getSection() {
		// TODO Auto-generated method stub
		return null;
	}
}
