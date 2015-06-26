SELECT * FROM sections
LEFT OUTER JOIN subjects USING (subject_id)
LEFT OUTER JOIN rooms USING (room_name)