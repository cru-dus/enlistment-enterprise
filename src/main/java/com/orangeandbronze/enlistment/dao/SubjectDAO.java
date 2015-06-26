package com.orangeandbronze.enlistment.dao;

import java.util.Collection;

import com.orangeandbronze.enlistment.domain.Subject;

public interface SubjectDAO {
	Subject findBy(String sectionId);
	
	Collection<Subject> findAll();
}
