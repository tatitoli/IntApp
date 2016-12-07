package app.dao;

import java.util.Set;

import app.model.Section;
import app.sort.Operator;

public interface PresentationsDao {
	
	Set<Operator> readPresentations();
	
	Set<Operator> getPresentations();
	
	Section getSection();
	
	void writePresentations();
}
