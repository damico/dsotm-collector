package org.jdamico.dsotm.tests;

import junit.framework.Assert;

import org.jdamico.dsotm.exceptions.DSOTMexception;
import org.jdamico.dsotm.util.SystemOper;
import org.junit.Test;

public class TestSystemOper {

	//@Test
	public void testGetFFprofile() throws DSOTMexception {
		
		SystemOper.singleton().getProfilePath();
		
		//fail("Not yet implemented");
	}
	@Test
	public void testCreateChecksum() throws Exception{
		 String f1 = SystemOper.singleton().createChecksum("/home/jdamico/.mozilla/firefox/8x1nysrz.default/TEMP_places.sqlite");
		 String f2 = SystemOper.singleton().createChecksum("/home/jdamico/.mozilla/firefox/8x1nysrz.default/TEMP_places.sqlite");
		 
		 Assert.assertEquals(f1, f2);

	}
}
