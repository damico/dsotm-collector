package org.jdamico.dsotm.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jdamico.dsotm.adaptors.DbAdaptor;
import org.jdamico.dsotm.dataobjects.SearchItem;
import org.jdamico.dsotm.dataobjects.Visit;
import org.jdamico.dsotm.exceptions.DSOTMexception;

public class Controller {
/*
 * which browser
 * which OS
 * Grab places.sqlite
 * Grab formhistory.sqlite
 * Send results as xml
 */
	
	private String xml = 	"<ffusage>" +
								"<visit timestamp='' bookmarked=''>" +
								"<search timestamp='' times='' lasttime='' fieldname=''>" +
							"</ffusage>";
	
	
	public Map<Integer, Visit> consolidateVisits(String path) throws DSOTMexception{
		DbAdaptor dba = new DbAdaptor(); 
		Map<Integer, Visit> visitMap = dba.getVisit(path);
		Map<Integer, String> bookmarkMap = dba.getBookmarks(path);
		
		Set<Integer> bSet = bookmarkMap.keySet();
		Iterator<Integer> iter = bSet.iterator();
		while(iter.hasNext()){
			Integer key = iter.next();
			Visit vis = visitMap.get(key);
			if(vis!=null){
				vis.setBookmarked(true);
				visitMap.put(key, vis);
			}
			
		}
		return visitMap;
	}
	
	public String generateOutput(String ip, String useragent) throws DSOTMexception{
		
		String profilePath = SystemOper.singleton().getProfilePath().get(0);
		String path = SystemOper.singleton().getDbCopy(profilePath, "places.sqlite");
		String username = SystemOper.singleton().getUserName();
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				  "<ffusage ip='"+ip+"' useragent='"+useragent+"' username='"+username+"' profile='"+profilePath+"'>\n");
		Map<Integer, Visit> map = consolidateVisits(path);
		Set<Integer> keySet = map.keySet();
		Iterator<Integer> iter = keySet.iterator();
		while(iter.hasNext()){
			Integer element = iter.next();
			String xml = 	"<visit timestamp='"+map.get(element).getTimestamp()+"' timesused='"+map.get(element).getTimesUsed()+"' bookmarked='"+String.valueOf(map.get(element).isBookmarked())+"'/>\n";
			sb.append(xml);

		}
		
		
		DbAdaptor dba = new DbAdaptor();
		ArrayList<SearchItem> sArray = dba.getSearchItems(profilePath+"/formhistory.sqlite");
		
		for(int i=0; i<sArray.size(); i++){
			String xml =  "<search firstused='"+sArray.get(i).getFirstUsed()+"' lastused='"+sArray.get(i).getLastUsed()+"' timesused='"+sArray.get(i).getTimesUsed()+"' fieldname='"+sArray.get(i).getFieldName()+"'/>\n";
			sb.append(xml);
		}
		sb.append("</ffusage>");
		return sb.toString();
	}
	
}
