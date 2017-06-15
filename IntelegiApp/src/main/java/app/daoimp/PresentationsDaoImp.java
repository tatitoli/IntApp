package app.daoimp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import app.dao.PresentationsDao;
import app.model.Section;
import app.sort.Presentation;
import app.sort.PresentationOperator;

public class PresentationsDaoImp implements PresentationsDao {

	@Override
	public LinkedList<PresentationOperator> readPresentations() {
		return null;
	}

	@Override
	public LinkedList<PresentationOperator> getPresentations() {
		LinkedList<PresentationOperator> operators = new LinkedList<>();
		try {
			FileInputStream file = new FileInputStream(new File("test.xls"));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(1);
			int i = 1;
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() > 0) {
					String presentationTitle = null;
					String actor = null;
					String topic = null;
					String from = null;
					String intervallum = null;
					String to = null;
					String fontos = null;
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
						case 5:
							fontos = cell.getStringCellValue();
							break;
						}
					}
					if ("Fontos".equals(fontos)) {
						operators.addFirst(
								new PresentationOperator(i, presentationTitle, actor, topic, from, to, true, 15));
					} else {
						operators
								.add(new PresentationOperator(i, presentationTitle, actor, topic, from, to, false, 15));
					}
					i++;
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
	public LinkedList<PresentationOperator> getPresentations(File input) {
		LinkedList<PresentationOperator> operators = new LinkedList<>();
		try {
			FileInputStream file = new FileInputStream(input);
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(1);
			int i = 1;
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Cell cell = null;
				Row row = rowIterator.next();
				if (row.getRowNum() > 0) {
					String presentationTitle = null;
					String actor = null;
					String topic = null;
					String from = null;
					String to = null;
					String fontos = null;
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						cell = cellIterator.next();
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
						case 5:
							fontos = cell.getStringCellValue();
							break;
						}
					}
					if ("Fontos".equals(fontos)) {
						operators.addFirst(
								new PresentationOperator(i, presentationTitle, actor, topic, from, to, true, 15));
					} else {
						operators
								.add(new PresentationOperator(i, presentationTitle, actor, topic, from, to, false, 15));
					}
					i++;
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
	public Section getSection(File input) {
		LinkedList<String> sections = new LinkedList<>();
		LinkedList<String> dates = new LinkedList<>();
		Section section = null;
		try {
			FileInputStream file = new FileInputStream(input);
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			int sectionNumber = 0;
			String from = null;
			String to = null;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() == 1) {
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (cell.getColumnIndex()) {
						case 0:
							sectionNumber = (int) cell.getNumericCellValue();
							break;
						case 1:
							from = new DataFormatter().formatCellValue(cell);
							break;
						case 2:
							to = new DataFormatter().formatCellValue(cell);
							break;
						}
					}
				} else if (row.getRowNum() > 1) {
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (cell.getStringCellValue() != null && !"".equals(cell.getStringCellValue())) {
							sections.add(cell.getStringCellValue());
						}
					}
				}

			}
			file.close();
			section = new Section(sectionNumber, from, to, sections);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return section;
	}

	@Override
	public LinkedList<PresentationOperator> getPresentationsInter(File input) {
		LinkedList<PresentationOperator> operators = new LinkedList<>();
		try {
			FileInputStream file = new FileInputStream(input);
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(1);
			int i = 1;
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Cell cell = null;
				Row row = rowIterator.next();
				if (row.getRowNum() > 0) {
					String presentationTitle = null;
					String actor = null;
					String topic = null;
					String from = null;
					String inter = null;
					String to = null;
					String fontos = null;
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						cell = cellIterator.next();
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
							inter = String.valueOf(cell.getNumericCellValue());
							break;
						case 4:
							from = new DataFormatter().formatCellValue(cell);
							break;
						case 5:
							to = new DataFormatter().formatCellValue(cell);
							break;
						}
						if (cell.getColumnIndex() == 5) {
							if ("Fontos".equals(fontos)) {
								operators.addFirst(new PresentationOperator(i, presentationTitle, actor, topic, inter,
										from, to, true, 15));
							} else {
								operators.add(new PresentationOperator(i, presentationTitle, actor, topic, inter, from,
										to, false, 15));
							}
							from = null;
							to = null;
						}
						if (cell.getColumnIndex() > 5 && cell.getColumnIndex() % 2 == 1) {
							to = new DataFormatter().formatCellValue(cell);
						}
						if (cell.getColumnIndex() > 5 && cell.getColumnIndex() % 2 == 0) {
							from = new DataFormatter().formatCellValue(cell);
						}
						if (cell.getColumnIndex() > 5 && from != null && to != null && !"".equals(from)
								&& !"".equals(to)) {
							if ("Fontos".equals(fontos)) {
								operators.addFirst(new PresentationOperator(i, presentationTitle, actor, topic, inter,
										from, to, true, 15));
							} else {
								operators.add(new PresentationOperator(i, presentationTitle, actor, topic, inter, from,
										to, false, 15));
							}
						}
					}
					i++;
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
	public LinkedList<PresentationOperator> getPresentationsNew(File input) {
		LinkedList<PresentationOperator> operators = new LinkedList<>();
		try {
			FileInputStream file = new FileInputStream(input);
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(1);
			int i = 1;
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() > 0) {
					String presentationTitle = null;
					String actor = null;
					String topic = null;
					String from = null;
					String intervallum = null;
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
							intervallum = new DataFormatter().formatCellValue(cell);
							break;
						case 4:
							from = new DataFormatter().formatCellValue(cell);
							break;

						}
					}
					operators.add(new PresentationOperator(i, presentationTitle, actor, topic, from, intervallum));
					i++;
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
	public void writePresentations(Presentation[][] table, Section section) {
		final String FILE_NAME = "EloadasExcel.xlsx";
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Elõadás");
		Object[][] datatypes = new Object[table.length][table[0].length];
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				if(table[i][j] != null){
					datatypes[i][j] = table[i][j].getPresentationTitle();
				}
			}
		}
		int rowNum = 0;
		for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            Cell cell = row.createCell(colNum++);
            cell.setCellValue((String) section.getSections().get(rowNum-1));
            for (Object field : datatype) {
                cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
