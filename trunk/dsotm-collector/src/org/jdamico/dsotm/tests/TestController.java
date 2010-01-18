package org.jdamico.dsotm.tests;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jdamico.dsotm.dataobjects.Visit;
import org.jdamico.dsotm.exceptions.DSOTMexception;
import org.jdamico.dsotm.util.Controller;
import org.jdamico.dsotm.util.SystemOper;
import org.junit.Test;


public class TestController {
	//@Test
	public void testConsolidateVisits() throws DSOTMexception {
		String profilePath = SystemOper.singleton().getProfilePath().get(0);
		String path = SystemOper.singleton().getDbCopy(profilePath, "places.sqlite");
		Controller control = new Controller();
		Map<Integer, Visit> map = control.consolidateVisits(path);
		Set<Integer> keySet = map.keySet();
		Iterator<Integer> iter = keySet.iterator();
		while(iter.hasNext()){
			Integer element = iter.next();
			String xml = 	"<visit timestamp='"+map.get(element).getTimestamp()+"' bookmarked='"+String.valueOf(map.get(element).isBookmarked())+"'>";
			System.err.println(xml);
		}
	}
	
	@Test
	public void testGenerateOutput() throws DSOTMexception{
		Controller control = new Controller();
		System.err.println(control.generateOutput(null, null));
	}
	
}
