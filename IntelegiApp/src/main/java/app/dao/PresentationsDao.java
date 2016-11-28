package app.dao;

import java.util.Set;

import app.sort.Operator;

public interface PresentationsDao {
	
	Set<Operator> readPresentations();
	
	void writePresentations();
}
