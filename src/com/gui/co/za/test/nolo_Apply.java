package com.gui.co.za.test;


import com.gui.co.za.utils.datetime.DateTime;
import com.gui.co.za.utils.excel.ExcelObjectFactory;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;
import com.gui.co.za.utils.ReusableFunctions;
import com.gui.co.za.utils.excel.ExcelObjectDataUtil;
import org.testng.ITestResult;


public class nolo_Apply extends ReusableFunctions {

    private String excelPath = getPropertyValue("./Framework.properties", "baseObjectExcel");

    ExtentTest logger;
    ExtentReports extent;
    DateTime dateFunctions = new DateTime();
    String sheetName = "DataInsert";

    @Parameters("Environment" )
    @BeforeTest
    public void setEnvVariables(@Optional("UAT")String Environment) {
        String currentClassName = this.getClass().getSimpleName();
        System.out.println("Environment : " + Environment);
        extent = setCommonEnviromentalVariables(Environment, currentClassName);

    }

    @BeforeTest
    @Parameters("BrowserType")
    public void setUp(String browser) throws Exception {
        System.out.println("This browser was sent ::: " + browser);
        setupWebDriver(browser);
    }

    @AfterTest
    public void endReport() {
        extent.flush();
        extent.close();
     //   driver.close();
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
            logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
        }
        // ending test
        //endTest(logger) : It ends the current test and prepares to create HTML report
        extent.endTest(logger);
    }

    boolean testlougflag = false;
    @Parameters("BrowserType")

    @Test(enabled = true, priority = 0)
    public void successfulLogin() throws Exception {

        logger = extent.startTest("ILabCareer", "Go to Careers and fill in details");

        this.initTest("Careers");

        //creates folder to store screenshots
        //String artifactsFolderName = getTestName() + "_" + dateFunctions.getTimeStamp();
        //System.out.println("artifact folder name is ::::::" + artifactsFolderName);
        //createArtifactsFolder(artifactsFolderName);

        try {

            logger.log(LogStatus.INFO, "Ilab landing page");
            this.takeScreenShot("Ilab Website",driver,logger);

            ElementClickByText("Landing.Careers",sheetName,logger);
            scrollPageDown(logger);

            //Click south africa on the webpage
            //Take screenshot
            this.takeScreenShot("Select Country",driver,logger);
            secondsDelay(3,logger);
            ElementClickByText("Careers.Country",sheetName,logger);

            //select any job listed
            this.takeScreenShot("Ilab job list",driver,logger);
            ElementClickByxpath("Careers.JobSelect",sheetName,logger);

            scrollPageDown(logger);
            //scroll down so that you click apply online
            ElementClickByText("Career.Apply",sheetName,logger);

            secondsDelay(3,logger);
            //scroll down to fill in the form
            scrollPageDown(logger);

            ExcelObjectFactory nameObjFac = getObjectDataForRuntime(excelPath, "Apply.Name", sheetName);
            String Name = nameObjFac.getValueToUse();

            ExcelObjectFactory EmailObjFac = getObjectDataForRuntime(excelPath, "Apply.Email", sheetName);
            String Email = EmailObjFac.getValueToUse();

            ExcelObjectFactory PhoneObjFac = getObjectDataForRuntime(excelPath, "Apply.Phone", sheetName);

            //------------------------------------------
            driver.findElement( By.id(nameObjFac.getSelectorContent()) ).sendKeys(Name);
            logger.log(LogStatus.INFO, "Entered the following value on textfield ::"+Name);

            driver.findElement( By.id(EmailObjFac.getSelectorContent()) ).sendKeys(Email);
            logger.log(LogStatus.INFO, "Entered the following value on textfield ::"+Email);

            driver.findElement( By.id(PhoneObjFac.getSelectorContent()) ).sendKeys(GenerateNumber(logger));

            //Take screenshot
            this.takeScreenShot("Data Filled in",driver,logger);

            secondsDelay(3,logger);
            scrollPageDown(logger);

            //click submit
            //scroll down so that you click apply online
            ElementClickByid("Apply.Submit",sheetName,logger);

            //validate if text shows
            ExcelObjectFactory validatetxtObjFac = getObjectDataForRuntime(excelPath, "Apply.validateText", sheetName);
            String Errtxt = validatetxtObjFac.getSelectorContent();
            WebElement element3 = driver.findElement(By.xpath(Errtxt));

            if(driver.getPageSource().contains(validatetxtObjFac.getValueToUse())){
                System.out.println("Text is present");
                highlighThisElement(element3,logger);
            }
            else
            {
                System.out.println("Text is absent");
            }
        } catch (Error e) {
            logger.log(LogStatus.INFO, "Exception Caught at " + getTestName() + " " + e.toString());
        }
        this.takeScreenShot("data found",driver,logger);
    }
    @Parameters("browser")// this annotation is used to insert browser parameter from TestNG xml
    public void initTest(String testType)
    {
        ExcelObjectDataUtil edu = new ExcelObjectDataUtil();
        excelPath = getPropertyValue("./Framework.properties", "baseObjectExcel");
        System.out.println("Found base url :::"+baseUrl+" for class  :::::"+this.getClass().getName());

        if(testType == "iLabAutomation" ) {
        }
        System.out.println("Is This base Empty :"+baseUrl  );
        driver.get(baseUrl);
    }
}
