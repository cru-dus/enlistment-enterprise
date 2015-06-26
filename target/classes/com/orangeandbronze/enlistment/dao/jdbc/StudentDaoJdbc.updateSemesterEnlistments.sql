MERGE INTO semester_enlistment t
USING (SELECT student_number FROM students) s
ON (t.student_number = s.student_number AND t.semester LIKE ?)
WHEN NOT MATCHED THEN
	INSERT VALUES (? , ?)