package org.jdamico.dsotm.tests;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jdamico.dsotm.adaptors.DbAdaptor;
import org.jdamico.dsotm.dataobjects.Visit;
import org.jdamico.dsotm.exceptions.DSOTMexception;
import org.jdamico.dsotm.util.SystemOper;
import org.junit.Test;

public class TestDbAdaptor {

	@Test
	public void testGetVisit() throws DSOTMexception {
		String profilePath = SystemOper.singleton().getProfilePath().get(0);
		DbAdaptor dba = new DbAdaptor(); 
		Map<Integer, Visit> visitMap = dba.getVisit(SystemOper.singleton().getDbCopy(profilePath, "places.sqlite"));
		//Map<Integer, Visit> visitMap = dba.getVisit(profilePath+"/TEMP_places.sqlite");
		
		Set<Integer> bSet = visitMap.keySet();
		Iterator<Integer> iter = bSet.iterator();
		while(iter.hasNext()){
			Integer key = iter.next();
			System.err.println(key);
			
			
		}
	}

	@Test
	public void testGetBookmarks() {

	}

	@Test
	public void testGetSearchItems() {

	}

}
