package com.test.Dummy.SingleClass;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestClass_FindingbrokenLinks {

	public static WebDriver driver;
	
	
	public static void main(String[] args)throws Exception
	{
		WebDriverManager.chromedriver().setup();
		ChromeOptions op = new ChromeOptions();
		op.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); 
	//	System.setProperty("webdriver.chrome.driver", "C:\\\\Users\\\\Vaibhav\\\\Downloads\\\\chromedriver.exe");
		driver= new ChromeDriver(op);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//driver.get("https://rediff.com");
		driver.get("https://www.google.com");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//input[@name='q']")).sendKeys("gainsight");
		driver.findElement(By.xpath("//input[@name='q']")).sendKeys(Keys.ENTER);
		List<WebElement> links= driver.findElements(By.tagName("a"));

		System.out.println(links.size());
		
		List<String> urllist= new ArrayList();
		Iterator<String> it= urllist.iterator();
		for(WebElement e:links) {
			// the below line gets text in every link
		//	System.out.println(e.getText());
			String url= e.getAttribute("href");
			if(url==null|| url.isEmpty())
				System.out.println("URL is either not configured for anchor tag or it is empty");
		else {
			try {
				checkresponse(url);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		}
	}


	private static void checkresponse(String urllink) throws IOException {
		URL url = new URL(urllink);
		HttpURLConnection httpurlconnection= (HttpURLConnection) url.openConnection();
		if(httpurlconnection.getResponseCode()>=400) {
			System.out.println(urllink+"------->"+ httpurlconnection.getResponseMessage()+ ",  It is a broken link");
		}
		else {
			System.out.println(urllink+"--------->"+httpurlconnection.getResponseMessage());
		}
	}
}
