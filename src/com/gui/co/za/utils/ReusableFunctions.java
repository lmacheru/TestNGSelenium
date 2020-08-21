package com.gui.co.za.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;


import com.gui.co.za.utils.datetime.DateTime;
import com.gui.co.za.utils.excel.ExcelObjectDataUtil;
import com.gui.co.za.utils.excel.ExcelObjectFactory;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.ITestResult;

import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
//import sun.rmi.runtime.Log;

public class ReusableFunctions extends ExcelObjectDataUtil {

	public WebDriver driver;
	public WebDriverWait driverWait;
	public JavascriptExecutor jExecutor;
	private String propertyFileLocation = "./Framework.properties";
	private String excelPath = getPropertyValue("./Framework.properties", "baseObjectExcel");
	protected String baseUrl = "";
	ExtentTest logger;
	ExtentReports extent;

	DateTime dateFunctions = new DateTime();

	public ExtentReports setCommonEnviromentalVariables(String Environment, String currentClassName) {

		baseUrl = getPropertyValue(propertyFileLocation, Environment + "BaseUrl");

		ExtentReports extent = new ExtentReports(System.getProperty("user.dir") + "/guiReports/" + currentClassName + ".html", true);
		extent.addSystemInfo("Environment", Environment);
		extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
		return extent;
	}

	public void highlighThisElement(WebElement elem,ExtentTest logger) {
		try {
			jExecutor.executeScript("arguments[0].style.border='4px solid #4646FF'", elem);
			logger.log(LogStatus.INFO, "Element found ::"+elem +" & Highlighted in blue");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unhighlighThisElement(WebElement elem) {
		try {
			jExecutor.executeScript("arguments[0].style.border='0px'", elem);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Delay time in seconds to pause or hold for some page objects to load
	 * takes only integer value of number of seconds to pause
	 */
	public void secondsDelay(int sec,ExtentTest logger) {
		int timeinMilliSeconds;
		try {
			timeinMilliSeconds = sec * 1000;
			Thread.sleep(timeinMilliSeconds);
			logger.log(LogStatus.INFO, "Delay in "+ sec  + " seconds");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * get real path on a machine using a relative path with respect to the current root directory (i.e project root directory)
	 * it takes one argument just the relative path
	 * */
	public static String toAbsolutePath(String relativePath) {
		Path relPath = Paths.get(relativePath);
		Path absolutePath = null;
		if (!relPath.isAbsolute()) {
			Path base = Paths.get("");
			absolutePath = base.resolve(relPath).toAbsolutePath();
		}
		return absolutePath.normalize().toString();
	}

	/*
	 * get the os type that the code is running on
	 * takes no variable but returns the os type such as:
	 * Windows, Mac OS, Linux
	 * */
	public String getOsName() {
		String osType;
		String osName = "";

		osType = System.getProperty("os.name");

		if (osType.contains("Windows") || osType.contains("windows")) {
			osName = "Windows";
		} else if (osType.contains("Mac") || osType.contains("mac")) {
			osName = "Mac OS";
		} else if (osType.contains("Linux") || osType.contains("linux")) {
			osName = "Linux";
		}

		System.out.println("os Type is ::: " + osType + "found os Name ::: " + osName);

		return osName;
	}

	/*
	 * this reads a property file and returns a property value
	 * takes 2 arguments filePath and propertyName
	 *
	 * */
	public String getPropertyValue(String filePath, String propertyName) {
		String value = "";

		try {
			File propFile = new File(filePath);
			FileInputStream inStream = new FileInputStream(propFile);

			Properties fileProp = new Properties();
			fileProp.load(inStream);

			value = fileProp.getProperty(propertyName);

		} catch (Exception e) {
			System.out.println("Error While Reading File : " + e.getMessage());

		}
		return value;
	}

	/*
	 * this sets up the the web driver for testNG multi-platform run
	 * takes only the browser name and then sets up the web driver for the specified driver
	 *
	 * */
	public void setupWebDriver(String browserName) {
		String macDriverLocation = "./Drivers/Mac/";
		String linuxDriverLocation = "./Drivers/Linux/";
		String windowsDriverLocation = "./Drivers/windows/";

		System.out.println("Browser name : " + browserName);
		if (browserName.equalsIgnoreCase("chrome")) {
			String chromeDriverPath = null;

			if (this.getOsName().equalsIgnoreCase("Windows")) {
				chromeDriverPath = windowsDriverLocation + "chromedriver.exe";
			} else if (this.getOsName().equalsIgnoreCase("Mac OS")) {
				chromeDriverPath = macDriverLocation + "chromedriver";
			} else if (this.getOsName().equalsIgnoreCase("Linux")) {
				chromeDriverPath = linuxDriverLocation + "chromedriver";
			}

			System.out.println("This is the chrome driver path is :::: " + chromeDriverPath);

			String absoluteChromeDriverPath = toAbsolutePath(chromeDriverPath);
			System.out.println("This is the chrome driver real path is :::: " + absoluteChromeDriverPath);

			System.setProperty("webdriver.chrome.driver", absoluteChromeDriverPath);
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			String firefoxDriverPath = null;
			System.out.println("Firefox ?: " + browserName);
			if (this.getOsName().equalsIgnoreCase("Windows")) {
				firefoxDriverPath = windowsDriverLocation + "geckodriver.exe";
			} else if (this.getOsName().equalsIgnoreCase("Mac OS")) {
				firefoxDriverPath = macDriverLocation + "geckodriver";
			} else if (this.getOsName().equalsIgnoreCase("Linux")) {
				firefoxDriverPath = linuxDriverLocation + "geckodriver";
			}
			System.out.println("This is the firefox driver path is :::: " + firefoxDriverPath);

			String absoluteFirefoxDriverPath = toAbsolutePath(firefoxDriverPath);
			System.out.println("This is the firefox driver real path is :::: " + absoluteFirefoxDriverPath);

			System.setProperty("webdriver.gecko.driver", absoluteFirefoxDriverPath);
			driver = new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase("IE")) {
			String ieDriverPath = null;

			if (this.getOsName().equalsIgnoreCase("Windows")) {
				ieDriverPath = windowsDriverLocation + "IEDriverServer.exe";
			} else if (this.getOsName().equalsIgnoreCase("Mac OS")) {
				ieDriverPath = macDriverLocation + "IEDriverServer";
			} else if (this.getOsName().equalsIgnoreCase("Linux")) {
				ieDriverPath = linuxDriverLocation + "IEDriverServer";
			}
			System.out.println("This is the IE driver path is :::: " + ieDriverPath);

			String absoluteIeDriverPath = toAbsolutePath(ieDriverPath);
			System.out.println("This is the IE driver real path is :::: " + absoluteIeDriverPath);

			System.setProperty("webdriver.ie.driver", absoluteIeDriverPath);

			DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capability.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, 1);
			capability.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);

			driver = new InternetExplorerDriver(capability);
		}
		System.out.println("Base 2 check : " + baseUrl);
		jExecutor = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		driverWait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void scrollPageDown(ExtentTest logger) {
		jExecutor.executeScript("window.scrollTo(0,500);");
		logger.log(LogStatus.INFO, "Scrolling down the page");

	}

	public void scrollPageUp(ExtentTest logger) {
		jExecutor.executeScript("window.scrollTo(0,210);");
		logger.log(LogStatus.INFO, "Scrolling down the page");
	}
	/*
	 * Auto update the GUI Automation Report*
	 */
	@SuppressWarnings("static-access")


	public String getTestName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
	/*
	 * This functiion refreshes the current web page
	 */
	public void doPageRefresh(ExtentTest logger) {
		driver.navigate().refresh();
		logger.log(LogStatus.INFO, "Refreshing the page to clear current chached data");
		secondsDelay(5,logger);
	}

	/*
	 * This creates the a new folder to store artifacts
	 * */
	public void createArtifactsFolder(String folderName) {
		String osName = getOsName();
		File downloadFolder = null;
		File folderPath = new File("./guiReports/TestArtifacts/" + folderName);
		try {
			folderPath.mkdir();
			System.out.println("Folder Creation Successful location is :: " + folderPath.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * this method takes screenshot at anytime it is called it returns the file name
	 * of the screenshot taken
	 */
	public String takeScreenShot(String initFileName, WebDriver driver, ExtentTest logger) {
		String fileName = null;
		//dateFunctions.getTimeStamp() +
		try {
			fileName = initFileName + ".png";
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File outFile = new File("./guiReports/TestArtifacts/" + fileName);
			FileUtils.copyFile(scrFile, outFile);
			String outFileToLog = "./TestArtifacts/" + fileName;
			System.out.println("Out File is" + outFileToLog);
			logger.log(LogStatus.INFO, logger.addScreenCapture(outFileToLog));

		} catch (IOException e) {
			logger.log(LogStatus.INFO, "Unable to log screenshot " + initFileName);
		}
		return fileName;
	}
	public void ElementClickByText(String PageObj,String sheetName ,ExtentTest logger){
		ExcelObjectFactory ObjFac = getObjectDataForRuntime(excelPath, PageObj, sheetName);
		String SelcObject = ObjFac.getValueToUse();
		System.out.println("Found Value :::" + SelcObject );
		logger.log(LogStatus.INFO, "Element found ::"+SelcObject +" & Clicked");

		driver.findElement(By.partialLinkText(SelcObject)).click();

	}
	public void ElementClickByid(String PageObj,String sheetName,ExtentTest logger){
		ExcelObjectFactory ObjFac = getObjectDataForRuntime(excelPath, PageObj, sheetName);
		String SelcObject = ObjFac.getSelectorContent();
		System.out.println("Found Value :::" + SelcObject );
		driver.findElement(By.id(SelcObject)).click();
		logger.log(LogStatus.INFO, "Element found ::"+SelcObject +" & Clicked");
	}

	public void ElementClickByxpath(String PageObj,String sheetName,ExtentTest logger){
		ExcelObjectFactory ObjFac = getObjectDataForRuntime(excelPath, PageObj, sheetName);
		String SelcObject = ObjFac.getSelectorContent();
		System.out.println("Found Value :::" + SelcObject );

		driver.findElement(By.xpath(SelcObject)).click();
		logger.log(LogStatus.INFO, "Element found ::"+SelcObject +" & Clicked");

	}
	public String GenerateNumber(ExtentTest logger){

		int num1, num2, num3; //3 numbers in area code
		int set2, set3; //sequence 2 and 3 of the phone number
		int min= 6, max = 8;
		Random generator = new Random();

		num1 = 0;
		num2 = generator.nextInt((max - min) + 1) + min; //randomize so that the second number can only be 6,7 and 8
		num3 = generator.nextInt(8);

		// Sequence two of phone number add 100 is so there will always be a 3 digit number
		set2 = generator.nextInt(643) + 100;

		//Sequence 3 of number add 1000 so there will always be 4 numbers
		set3 = generator.nextInt(8999) + 1000;

		String Cellnumeber =  num1 + ""  + num2  + ""+ num3 +  " " + set2 + " " + set3;

		System.out.println ("Cell number is ::"+ Cellnumeber);
		logger.log(LogStatus.INFO, "Auto Generated Phone number which is ::"+Cellnumeber +" & insert on website");

		return Cellnumeber;
	}
}


