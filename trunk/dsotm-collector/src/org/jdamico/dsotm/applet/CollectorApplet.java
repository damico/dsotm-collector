package org.jdamico.dsotm.applet;

import java.applet.Applet;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jdamico.dsotm.exceptions.DSOTMexception;
import org.jdamico.dsotm.util.Controller;

import netscape.javascript.JSObject;
public class CollectorApplet extends Applet {
	/**
	 * This is the applet that calls the controller and executes the Java script.
	 */
	private static final long serialVersionUID = -8198523345649062342L;

	public void init() {
		JSObject win = JSObject.getWindow(this);
		Controller control = new Controller();
		
		String ip = getParameter("ip");
		String useragent = getParameter("useragent");
		
		win.call("setDivAlert", new String[]{"Applet Loaded. Searching data..."});
		String[] xml = new String[1];
		
		if(useragent.contains("Iceweasel/3") || useragent.contains("Firefox/3")){
			String result = null;
			
			try {
				result = control.generateOutput(ip, useragent);
				xml[0] =  URLEncoder.encode(result,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				xml[0] = "Encoding Error!";
			} catch (DSOTMexception e) {
				xml[0] = "Impossible to read internals!";
			}
		}else{
			xml[0] = "Wrong ff version.";
		}
		
		
		
		win.call("sendCollection", xml);		  	     

	}
}
