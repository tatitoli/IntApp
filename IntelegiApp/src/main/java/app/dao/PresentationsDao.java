package app.dao;

import java.io.File;
import java.util.LinkedList;

import app.model.Section;
import app.sort.PresentationOperator;

public interface PresentationsDao {
	
	LinkedList<PresentationOperator> readPresentations();
	
	LinkedList<PresentationOperator> getPresentations();
	
	void writePresentations();

	LinkedList<PresentationOperator> getPresentations(File file);
	
	LinkedList<PresentationOperator> getPresentationsInter(File file);
	
	LinkedList<PresentationOperator> getPresentationsNew(File file);

	Section getSection(File input);
}
