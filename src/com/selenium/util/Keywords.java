package com.selenium.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class Keywords {
	Properties OR=null;
	Properties Config=null;
	FileInputStream f=null;
	WebDriver driver;
	public static Xls_Reader xls=new Xls_Reader(System.getProperty("user.dir")+"\\src\\com\\selenium\\config\\Hybrid.xlsx");
	public String openBrowser(String browser){
		if(browser.equals("firefox"))
			driver=new FirefoxDriver();
		else if(browser.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\com\\selenium\\drivers\\chromedriver.exe");
			driver=new ChromeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		return "Pass";
		
	}
	

	public String navigate(String url){
		
		try{
			driver.get(url);
		}catch(Exception e){
			return "Navigation failed";
		}
		return "Pass";
	}
	
	
	
	public Keywords() throws IOException{
		
		f=new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\selenium\\config\\OR.properties");
		OR=new Properties();
		OR.load(f);
		
		f=new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\selenium\\config\\config.properties");
		Config=new Properties();
		Config.load(f);
		
		
	}
	
	public String sendkeys(String xpath,String value){
		try{
		driver.findElement(By.xpath(xpath)).sendKeys(value);
		}catch(Exception e){
			return "Text field not entered";
		}
		return "Pass";
	}
	
	public String click(String xpath){
		try{
		driver.findElement(By.xpath(xpath)).click();
		}catch(Exception e){
			return "Not clicked";
		}
		return "Pass";
	}
	
	public String selectDropdown(String xpath,String valtoSelect){
		try{
		new Select(driver.findElement(By.xpath(xpath))).selectByVisibleText(valtoSelect);
		}catch(Exception e){
			return "Dropdown not selected"+e;
		}
		return "Pass";
	}
	
	public String getText(String xpath){
		return driver.findElement(By.xpath(xpath)).getText();
		
	}
	
	public String verifyCheckpoint(String xpath,String expectedVal){
		try{
		String actual=driver.findElement(By.xpath(xpath)).getText();
		Assert.assertEquals(actual, expectedVal);
		}catch(Exception e){
			return "Validation failed"+e;
		}
		return "Pass";
	}
	
	public void closebrowser(){
		driver.quit();
	}
	
	public void takescreen(String fileName){
		File src= ((TakesScreenshot)driver). getScreenshotAs(OutputType. FILE);
		try {
		FileUtils. copyFile(src, new File(System.getProperty("user.dir")+"\\src\\com\\selenium\\screens"+fileName));
		}catch (IOException e){
			
		}
	}
	
	
	public void executeKeywords(String sheetName,String tcName,Hashtable<String,String> table){
		String Keywordsval=null;
		String ObjectVal=null;
		String Data=null;
		
		for(int rNum=2;rNum<=xls.getRowcount(sheetName);rNum++){
			if(xls.getCellData(sheetName, "TCID", rNum).equals(tcName)){
				Keywordsval=xls.getCellData(sheetName, "Keyword", rNum);
				ObjectVal=xls.getCellData(sheetName, "ObjectValue", rNum);
				Data=xls.getCellData(sheetName, "Data", rNum);
				System.out.println(Keywordsval);
				System.out.println(ObjectVal);
				System.out.println(Data);
				String result=null;
				if(Keywordsval.equals("openBrowser"))
					result=openBrowser(table.get(Data));
				else if(Keywordsval.equals("navigate"))
					result=navigate(Config.getProperty(Data));
				else if(Keywordsval.equals("sendkeys"))	
					result=sendkeys(OR.getProperty(ObjectVal),table.get(Data));
				else if(Keywordsval.equals("selectDropdown"))
					result=selectDropdown(OR.getProperty(ObjectVal),table.get(Data));
				else if(Keywordsval.equals("click"))
					result=click(OR.getProperty(ObjectVal));
				else if(Keywordsval.equals("verifyCheckpoint"))
					result=verifyCheckpoint(OR.getProperty(ObjectVal),table.get(Data));
				
				if(!result.equals("Pass"))
					Assert.fail(tcName +" failed due to "+result);
			}
		}
		
	}

}
