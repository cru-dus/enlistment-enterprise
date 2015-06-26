CREATE TABLE students (
		student_number INT PRIMARY KEY
	);
	
CREATE TABLE subjects (
		subject_id VARCHAR(25) PRIMARY KEY,
		prerequisite_subject_id VARCHAR(25),
		FOREIGN KEY (prerequisite_subject_id) REFERENCES subjects (subject_id)
	);

CREATE TABLE rooms (
		room_name VARCHAR(25) PRIMARY KEY,
		capacity INT
	);

CREATE TABLE faculty (
		faculty_number INT PRIMARY KEY
	);
	
	CREATE TABLE sections (
		section_id VARCHAR(25) PRIMARY KEY,
		schedule VARCHAR(25),
		subject_id VARCHAR(25),
		room_name VARCHAR(25),
		FOREIGN KEY (subject_id) REFERENCES subjects (subject_id),
		FOREIGN KEY (room_name) REFERENCES rooms (name)
	);

CREATE TABLE semester_enlistment (
		student_number INT,
		semester VARCHAR(25),
		PRIMARY KEY (student_number, semester),
		FOREIGN KEY (student_number) REFERENCES students (student_number)
	);

CREATE TABLE semester_enlistment_sections (
		student_number INT,
		semester VARCHAR(25),
		section_id VARCHAR(25),
		PRIMARY KEY (student_number, semester, section_id),
		FOREIGN KEY (student_number, semester) REFERENCES semester_enlistment (student_number, semester),
		FOREIGN KEY (section_id) REFERENCES sections (section_id)
	);