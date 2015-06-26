MERGE INTO semester_enlistment_sections t
USING temp_sections s
ON (t.student_number = s.student_number AND t.semester LIKE s.semester AND t.section_id LIKE s.section_id)
WHEN NOT MATCHED THEN
INSERT VALUES (s.student_number, s.semester, s.section_id);