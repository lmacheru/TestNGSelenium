package com.gui.co.za.config;

import com.gui.co.za.utils.datetime.DateTime;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class TestInit {
	
	private String TestName;
	private String genericTestName;
	private String genericSuiteName;
	private String SuiteName;
	
	DateTime dt = new DateTime();
	
	@BeforeTest
	public void beforeTest() {
		//Todo: What to do before Test
		//set test name and other properties
	}
	
	@BeforeSuite
	public void beforeSuite() {
		String projectName = "GuiAutomation";
		this.setSuiteName(projectName);
		System.out.println("This is the beginning of the test Suite");
	}
	 
	@AfterTest
	public void afterTest() {
		//Todo: What to do after test
	}
	
	@AfterSuite
	public void afterSuite() {
		//Todo
	}
	
	@Test
	public void getID() {
		System.out.println("This is the beginning of the test");
	}
	
	private void setSuiteName(String name) {
		String currentDateTime = dt.getTimeStamp();
		SuiteName = name;
		genericSuiteName = name+currentDateTime;
	}
	
	private String getSuiteName() {
		return this.SuiteName;
	}
	
	private String getGenericSuiteName() {
		return this.genericSuiteName;
	}
	
	private void setTestName(String name) {
		String currentDateTime = dt.getTimeStamp();
		TestName = name;
		genericTestName = name+currentDateTime;
	}
	private String getGenericTestName() {
		return this.genericTestName;
	}
}
