package app.dao;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;

import app.model.Section;
import app.sort.Presentation;
import app.sort.PresentationOperator;

public interface PresentationsDao {
	
	LinkedList<PresentationOperator> readPresentations();
	
	void writePresentationsXls(Map<String, LinkedList<Presentation>> table, Section section);
	
	void writePresentationsXlsx(Map<String, LinkedList<Presentation>> table, Section section);
	
	LinkedList<PresentationOperator> getPresentationsInter(File file);
	
	LinkedList<PresentationOperator> getPresentationsNew(File file);

	Section getSection(File input);
	
}
