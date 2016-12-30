package app.dao;

import java.io.File;
import java.util.Set;

import app.model.Section;
import app.sort.PresentationOperator;

public interface PresentationsDao {
	
	Set<PresentationOperator> readPresentations();
	
	Set<PresentationOperator> getPresentations();
	
	void writePresentations();

	Set<PresentationOperator> getPresentations(File file);

	Section getSection(File input);
}
