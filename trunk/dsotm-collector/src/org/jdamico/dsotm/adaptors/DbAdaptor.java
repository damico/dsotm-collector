 package org.jdamico.dsotm.adaptors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jdamico.dsotm.dataobjects.SearchItem;
import org.jdamico.dsotm.dataobjects.Visit;
import org.jdamico.dsotm.exceptions.DSOTMexception;

public class DbAdaptor {
	
	
	public Map<Integer, Visit> getVisit(String dbPath) throws DSOTMexception{

		String sql = "SELECT datetime(moz_historyvisits.visit_date/1000000,'unixepoch'), place_id " +
		"FROM moz_historyvisits";

		Map<Integer, Visit> visitMap = new HashMap<Integer, Visit>();
		Visit vis = null;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				
				int key = rs.getInt(2);
				int tu = 1;
				if(visitMap.containsKey(key)){
					Visit existent = visitMap.get(key);
					tu = existent.getTimesUsed();
					tu++;
				}
				
				vis = new Visit(key, rs.getString(1), false, tu);
				visitMap.put(key, vis);
			}

		} catch (ClassNotFoundException e) {
			throw new DSOTMexception(e);
		} catch (SQLException e) {
			throw new DSOTMexception(e);
		} finally {
			try {
				if(rs!=  null) rs.close();
				if(stat!=null) stat.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				throw new DSOTMexception(e);
			}
		}

		return visitMap;
	}

	public Map<Integer, String> getBookmarks(String dbPath) throws DSOTMexception{
		String sql = "SELECT datetime(dateAdded/1000000,'unixepoch'), fk FROM moz_bookmarks where fk > 0";

		Map<Integer, String> bookmarkMap = new HashMap<Integer, String>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				bookmarkMap.put(rs.getInt(2), rs.getString(1));
			}

		} catch (ClassNotFoundException e) {
			throw new DSOTMexception(e);
		} catch (SQLException e) {
			throw new DSOTMexception(e);
		} finally {
			try {
				if(rs!=  null) rs.close();
				if(stat!=null) stat.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				throw new DSOTMexception(e);
			}
		}
		return bookmarkMap;
	}
	
	public ArrayList<SearchItem> getSearchItems(String dbPath) throws DSOTMexception{
		String sql = 	"SELECT datetime(firstUsed/1000000,'unixepoch'), datetime(lastUsed/1000000,'unixepoch'), timesUsed, fieldName " +
						"FROM moz_formhistory " +
						"WHERE fieldName in ('q','search','searchbar-history')";
		
		ArrayList<SearchItem> searchItemArray =  new ArrayList<SearchItem>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				searchItemArray.add(new SearchItem(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)), rs.getString(4)));
			}

		} catch (ClassNotFoundException e) {
			throw new DSOTMexception(e);
		} catch (SQLException e) {
			throw new DSOTMexception(e);
		} finally {
			try {
				if(rs!=  null) rs.close();
				if(stat!=null) stat.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				throw new DSOTMexception(e);
			}
		}
		
		return searchItemArray;
	}

}
