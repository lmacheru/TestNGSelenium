package com.gui.co.za.utils.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	
	//Default Date Formating..
	private String toFullDateFormat = "dd-MM-yyyy";
	private String timeStampFormat= "yyyyMMddHHmmss";
	private String toDateFormat = "yyyyMMdd";
	
	public String getFullDate(){
		String displayTime="";
		try{
			displayTime = (new  SimpleDateFormat(toFullDateFormat).format(new Date()));
		}catch(Exception e){
			System.out.println("Date Time Seems To Be Incorrect. : "+e.getMessage());
		}
		return displayTime;
	}
	
	public String getDate(){
		String displayTime="";
		try{
			displayTime = (new  SimpleDateFormat(toDateFormat).format(new Date()));
		}catch(Exception e){
			System.out.println("Date Time Seems To Be Incorrect. : "+e.getMessage());
		}
		return displayTime;
	}
	
	public String getTimeStamp(){
		String displayTime="";
		try{
			displayTime = (new  SimpleDateFormat(timeStampFormat).format(new Date()));
		}catch(Exception e){
			System.out.println("Date Time Seems To Be Incorrect. : "+e.getMessage());
		}
		return displayTime;
	}
}
