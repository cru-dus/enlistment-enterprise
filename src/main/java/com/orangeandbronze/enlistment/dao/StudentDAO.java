package com.orangeandbronze.enlistment.dao;

import com.orangeandbronze.enlistment.domain.Student;

public interface StudentDAO {

	/**
	 * Updates student information, including the student's enlistments. The
	 * Student, SemesterEnlistments, Sections and Subjects must already exist in
	 * the database, otherwise an exception will be thrown.
	 */
	void update(Student student);

	/**
	 * Returns Student.NONE if the given student number was not found in the
	 * database.
	 */
	Student findBy(int studentNumber);
}
