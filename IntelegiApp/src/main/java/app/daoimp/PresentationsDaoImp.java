package app.daoimp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

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
	public Section getSection(File input) {
		LinkedList<String> sections = new LinkedList<>();
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
					String shortFrom = null;
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
					}if("Délután".equals(from)){
						shortFrom = "DU";
					}else if("Délelõtt".equals(from)){
						shortFrom = "DE";
					}else{
						shortFrom = "BAR";
					}
					operators.add(new PresentationOperator(i, presentationTitle, actor, topic, shortFrom, intervallum, null, null));
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
	public void writePresentationsXls(Map<String, LinkedList<Presentation>> finalTable, Section section) {
		final String FILE_NAME = "EloadasExcel.xls";
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet  sheet = workbook.createSheet("Elõadás");
		Map<String, LinkedList<Presentation>> tmpMap = new HashMap<>();
		for (Map.Entry<String, LinkedList<Presentation>> map : finalTable.entrySet()) {
			if(!tmpMap.containsKey(map.getKey().substring(0, map.getKey().length()-2)) && map.getKey().contains("DE")){
				tmpMap.put(map.getKey().substring(0, map.getKey().length()-2), map.getValue());
			}
		}
		for (Map.Entry<String, LinkedList<Presentation>> map : finalTable.entrySet()) {
			if(tmpMap.containsKey(map.getKey().substring(0, map.getKey().length()-2)) && map.getKey().contains("DU")){
				LinkedList<Presentation> tmpListOld = tmpMap.get(map.getKey().substring(0, map.getKey().length()-2));
				LinkedList<Presentation> tmpList = map.getValue();
				for (Presentation presentation : tmpList) {
					tmpListOld.add(presentation);
				}
				tmpMap.put(map.getKey().substring(0, map.getKey().length()-2), tmpListOld);
			}
		}
		int rowNum = 0;
		Row row = sheet.createRow(rowNum++);
		int colNum = 0;
        Cell cell = row.createCell(colNum++);
        cell.setCellValue((String) "Elõadás kezdete");
        cell = row.createCell(colNum++);
        cell.setCellValue((String) "Elõadó");
        cell = row.createCell(colNum++);
        cell.setCellValue((String) "Elõadás címe");
		for (Map.Entry<String, LinkedList<Presentation>> map : tmpMap.entrySet()) {
			row = sheet.createRow(rowNum++);
          colNum = 0;
          cell = row.createCell(colNum++);
          cell.setCellValue((String) map.getKey());
          row = sheet.createRow(rowNum++);
          colNum = 0;
          for (Presentation field : map.getValue()) {
              cell = row.createCell(colNum++);
              cell.setCellValue((String) field.getFromTime());
              cell = row.createCell(colNum++);
              cell.setCellValue((String) field.getActor());
              cell = row.createCell(colNum++);
              cell.setCellValue((String) field.getPresentationTitle());
              if(colNum>= 3){
            	  row = sheet.createRow(rowNum++);
            	  colNum=0;
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
	
	@Override
	public void writePresentationsXlsx(Map<String, LinkedList<Presentation>> finalTable, Section section) {
		final String FILE_NAME = "EloadasExcel.xlsx";
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Elõadás");
		Map<String, LinkedList<Presentation>> tmpMap = new HashMap<>();
		for (Map.Entry<String, LinkedList<Presentation>> map : finalTable.entrySet()) {
			if(!tmpMap.containsKey(map.getKey().substring(0, map.getKey().length()-2)) && map.getKey().contains("DE")){
				tmpMap.put(map.getKey().substring(0, map.getKey().length()-2), map.getValue());
			}
		}
		for (Map.Entry<String, LinkedList<Presentation>> map : finalTable.entrySet()) {
			if(tmpMap.containsKey(map.getKey().substring(0, map.getKey().length()-2)) && map.getKey().contains("DU")){
				LinkedList<Presentation> tmpListOld = tmpMap.get(map.getKey().substring(0, map.getKey().length()-2));
				LinkedList<Presentation> tmpList = map.getValue();
				for (Presentation presentation : tmpList) {
					tmpListOld.add(presentation);
				}
				tmpMap.put(map.getKey().substring(0, map.getKey().length()-2), tmpListOld);
			}
		}
		int rowNum = 0;
		Row row = sheet.createRow(rowNum++);
		int colNum = 0;
        Cell cell = row.createCell(colNum++);
        cell.setCellValue((String) "Elõadás kezdete");
        cell = row.createCell(colNum++);
        cell.setCellValue((String) "Elõadó");
        cell = row.createCell(colNum++);
        cell.setCellValue((String) "Elõadás címe");
		for (Map.Entry<String, LinkedList<Presentation>> map : tmpMap.entrySet()) {
			row = sheet.createRow(rowNum++);
          colNum = 0;
          cell = row.createCell(colNum++);
          cell.setCellValue((String) map.getKey());
          row = sheet.createRow(rowNum++);
          colNum = 0;
          for (Presentation field : map.getValue()) {
              cell = row.createCell(colNum++);
              cell.setCellValue((String) field.getFromTime());
              cell = row.createCell(colNum++);
              cell.setCellValue((String) field.getActor());
              cell = row.createCell(colNum++);
              cell.setCellValue((String) field.getPresentationTitle());
              if(colNum>= 3){
            	  row = sheet.createRow(rowNum++);
            	  colNum=0;
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
