package com.orangeandbronze.enlistment.dao;

import com.orangeandbronze.enlistment.domain.*;
import java.util.*;

public interface SectionDAO {

	Section findBy(String sectionId);
	
	Collection<Section> findAll();
	
	Section create(String sectionId, Subject subject, Schedule schedule, Room room);
}
