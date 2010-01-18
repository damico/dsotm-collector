package org.jdamico.dsotm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;

import org.jdamico.dsotm.exceptions.DSOTMexception;

public class SystemOper {
	private static final SystemOper getInstance = new SystemOper();

	public static SystemOper singleton() {
		return getInstance;
	}

	public boolean isWindows() {
		boolean ret = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().contains("linux")) {
			ret = false;
		} else if (osName.toLowerCase().contains("win")) {
			ret = true;
		}
		return ret;
	}

	public String getFFsqlitePath(){
		return null;
	}

	public String getUserName() throws DSOTMexception{
		String userName;
		if (isWindows()) userName = System.getProperty("user.name");
		else userName = SystemOper.singleton().execExtCommand("whoami");
		return userName;
	}
	
	public String getFFprofile() throws DSOTMexception {
		String path = null;
		String userName = getUserName();
		if (isWindows()) {
			
			path = "C:/Documents and Settings/"+userName+"/Application Data/Mozilla/Firefox/";
			File f = new File(path);
			if(!f.exists()){
				path = "C:/Documents and Settings/"+userName+"/Dados de aplicativos/Mozilla/Firefox/";
			}else if(!f.exists()){
				path = "C:/Documents and Settings/Administrator/Application Data/Mozilla/Firefox/";
				f = new File(path);
			}else if(!f.exists()){
				path = "C:/Documents and Settings/Administrador/Application Data/Mozilla/Firefox/";
				f = new File(path);
			}else if(!f.exists()){
				path = "C:/Documents and Settings/All Users/Application Data/Mozilla/Firefox/";
				f = new File(path);
			}else if(!f.exists()){
				path = "C:/Documents and Settings/Default User/Application Data/Mozilla/Firefox/";
				f = new File(path);
			}else if(!f.exists()){
				path = "C:/Documents and Settings/Administrator/Dados de aplicativos/Mozilla/Firefox/";
				f = new File(path);
			}else if(!f.exists()){
				path = "C:/Documents and Settings/Administrador/Dados de aplicativos/Mozilla/Firefox/";
				f = new File(path);
			}else if(!f.exists()){
				path = "C:/Documents and Settings/All Users/Dados de aplicativos/Mozilla/Firefox/";
				f = new File(path);
			}else if(!f.exists()){
				path = "C:/Documents and Settings/Default User/Dados de aplicativos/Mozilla/Firefox/";
				
			}
			
		} else {
			path = "/home/" + userName+"/.mozilla/firefox/";
		}

		return path;
	}

	public String execExtCommand(String command) throws DSOTMexception {
		StringBuffer sb = new StringBuffer();
		try {
			Process child = Runtime.getRuntime().exec(command);
			InputStream in = child.getInputStream();
			int c;
			while ((c = in.read()) != -1) {
				sb.append((char) c);
			}
			in.close();
		} catch (IOException e) {
			throw new DSOTMexception(e);
		}
		String ret = sb.toString();
		ret = ret.replaceAll("\n", "");
		return ret;
	}

	public ArrayList<String> getProfilePath() throws DSOTMexception{

		ArrayList<String> profiles = new ArrayList<String>();

		FileReader fr = null;

		String path = getFFprofile();


		try { 
			fr = new FileReader(path+"profiles.ini");
			BufferedReader input = new BufferedReader(fr); 
			String str; 
			while ((str = input.readLine()) != null) { 

				if(str.contains("Path")){
					String rawPath = str.substring(5);

					String finalPath;
					if(isWindows()) finalPath = path+rawPath;
					else finalPath = path+rawPath;

					profiles.add(finalPath);
				}


			} 
			input.close(); 
			fr.close();
		} catch (IOException e) { throw new DSOTMexception(e); } 

		return profiles;
	}

	public String getDbCopy(String sPath, String sFile) throws DSOTMexception{

		String dFile = sPath + "/TEMP_"+sFile;
		
		if(isWindows()) dFile = "C:/WINDOWS/Temp/TEMP_"+sFile;
		else dFile = "/tmp/TEMP_"+sFile;
		
		sFile = sPath+"/"+sFile;
		String ret = null;
		try{
			File d = new File(dFile);
			if(d.exists()) d.delete();

			File f1 = new File(sFile);
			File f2 = new File(dFile);
			InputStream in = new FileInputStream(f1);

			//For Append the file.
			//		      OutputStream out = new FileOutputStream(f2,true);

			//For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			System.out.println("File copied.");

			//TODO check f1`f2 md5sum

			if(createChecksum(dFile).equals(createChecksum(sFile))){
				ret = dFile;
			}
			
			
		}
		catch(FileNotFoundException ex){
			throw new DSOTMexception(ex);
		}
		catch(IOException e){
			throw new DSOTMexception(e);    
		} catch (Exception e) {
			throw new DSOTMexception(e);
		}

		
		
		return ret;
	}

	public String createChecksum(String filename) throws	Exception {
		InputStream fis =  new FileInputStream(filename);

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;
		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);
		fis.close();
		
		byte[] f1 = complete.digest();
		
		String result = "";
	     for (int i=0; i < f1.length; i++) {
	       result +=
	          Integer.toString( (f1[i] & 0xff ) + 0x100, 16).substring( 1 );
	      }
		
		return result;
	}


}
