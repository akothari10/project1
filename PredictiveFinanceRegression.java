package predictiveFinance;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PredictiveFinanceRegression {
	WebDriver driver;
	File src;  FileInputStream fils;  Workbook workbooks;  Sheet userInfo,accountInfo;
	public void getData() throws Exception {

		
		src=new File("E:/selenium/Test-Data-Selenium.xls");
		fils=new FileInputStream(src);
		Workbook workbooks=WorkbookFactory.create(fils);
		userInfo=workbooks.getSheetAt(0);
		accountInfo=workbooks.getSheetAt(1);
	} 
	@Test
	public void testLogin() throws Exception {
		  String USERNAME = "akothari10";
		  String ACCESS_KEY = "6a672b25-b10e-489a-8602-98530f8fd012";
		String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
		 DesiredCapabilities caps = DesiredCapabilities.chrome();
		    caps.setCapability("platform", "Windows 8");
		    caps.setCapability("version", "latest");
		    System.out.println(caps.getBrowserName());
		    driver = new RemoteWebDriver(new URL(URL), caps);
		getData();
		//System.setProperty("webdriver.chrome.driver", "E:\\selenium\\chromedriver.exe");
		//driver=new ChromeDriver();
		driver.get("https:\\login.salesforce.com");
		driver.findElement(By.xpath("//*[@id='username']")).sendKeys(userInfo.getRow(1).getCell(0).getStringCellValue());
		driver.findElement(By.xpath("//*[@id='password']")).sendKeys("ashita kothari10");
		driver.findElement(By.xpath("//*[@id='Login']")).click();
		createAccounts();
		//searchOfficers();
		searchCompany();
	}
	
	public void createAccounts() throws Exception {
		getData();
		driver.get("https://ashita-dev-ed.my.salesforce.com/001");
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//*[@id='tryLexDialogX']")).click();
		driver.findElement(By.xpath("//*[@id='00B7F000008P2zI_listButtons']/ul/li[1]/input")).click();
		driver.findElement(By.xpath("//*[@id='acc2']")).sendKeys(accountInfo.getRow(2).getCell(0).getStringCellValue());
		driver.findElement(By.cssSelector("#topButtonRow > input:nth-child(1)")).click();
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		js.executeScript("window.scrollBy(0,600)");
	}
	
	public void searchCompany() throws Exception{
		getData();
		Thread.sleep(15000);
		WebElement my_frame=driver.findElement(By.xpath("//iframe[@title='AccountInlineSync']"));
		driver.switchTo().frame(my_frame);
		driver.findElement(By.cssSelector("#\\36 9\\:2\\;a")).sendKeys(accountInfo.getRow(2).getCell(0).getStringCellValue());
		driver.findElement(By.xpath("//*[@id='lightning']/div[1]/div[2]/div/div[3]/button")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("#lightning > div.vyom.PridictiveFinAccountInlineSync > div:nth-child(5) > table > tbody > tr:nth-child(1) > th.slds-cell-shrink.thLeftAlign.thLeftAlign > label")).click();
		driver.findElement(By.xpath("//*[@id='lightning']/div[1]/div[3]/center/button")).click();
		driver.findElement(By.xpath("//*[@id='lightning']/div[2]/div/div[2]/div/button[2]")).click();
		assertion();
		Thread.sleep(2000);
		List<WebElement> officers = driver.findElements(By.className("dataRow"));
		String Ids;
		for(WebElement element:officers){
			Ids=element.findElement(By.xpath(".//*")).findElement(By.xpath(".//*")).getAttribute("href");
			Ids=Ids.substring(Ids.indexOf('/',Ids.indexOf('/',Ids.indexOf('/')+1)+1)+1);
			System.out.println("Company Officers are: \n"+Ids.substring(0,Ids.indexOf('/')));
		}
	}
	public void assertion() throws Exception {
		Thread.sleep(30000);
		String name=driver.findElement(By.cssSelector("#\\31 3\\3a 0 > div > div > div:nth-child(1) > div > div.slds-p-horizontal--small.slds-size--1-of-1.slds-cell-wrap")).getText();
		driver.get(driver.getCurrentUrl());
		Thread.sleep(5000);
		String companyName=driver.findElement(By.xpath("//*[@id='00N7F00000O2zGh_ileinner']")).getText();
		Assert.assertEquals(companyName,name);
		System.out.println(companyName+"   <--   Matches  -->   "+name);
	}
	public void searchOfficers() throws Exception{
		getData();
		Thread.sleep(15000);
		WebElement my_frame=driver.findElement(By.xpath("//iframe[@title='AccountInlineSync']"));
		driver.switchTo().frame(my_frame);
		driver.findElement(By.xpath("//*[@id=\"lightning\"]/div[1]/div[2]/div/div[1]/span[2]/label/span[1]")).click();
		driver.findElement(By.cssSelector("#\\36 9\\:2\\;a")).sendKeys(accountInfo.getRow(2).getCell(0).getStringCellValue());
		driver.findElement(By.xpath("//*[@id='lightning']/div[1]/div[2]/div/div[3]/button")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("#row0 > th > label > span.slds-radio_faux")).click();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("#lightning > div.vyom.PridictiveFinAccountInlineSync > div:nth-child(6) > table > tbody > tr > th.slds-cell-shrink.thLeftAlign.thLeftAlign > label > span.slds-radio--faux")).click();
		driver.findElement(By.xpath("//*[@id='lightning']/div[1]/div[3]/center/button")).click();
		driver.findElement(By.xpath("//*[@id='lightning']/div[2]/div/div[2]/div/button[2]")).click();
		assertion();
	}

}
