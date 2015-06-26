SELECT * FROM students LEFT OUTER JOIN semester_enlistment
USING (student_number)
LEFT OUTER JOIN semester_enlistment_sections 
USING (student_number, semester)
LEFT OUTER JOIN sections USING (section_id)
LEFT OUTER JOIN rooms USING (room_name)
LEFT OUTER JOIN subjects USING (subject_id)
WHERE student_number = ?
ORDER BY semester