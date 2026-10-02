package com.bstackdemo.Utility; //helper class to provide browser instance for each thread in parallel execution

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserProvider {
	
	private static ThreadLocal<WebDriver> tdriver=new ThreadLocal<WebDriver>();
	
	
	public static WebDriver getDriver() {
		return tdriver.get();
	}
    public static WebDriver setDriver(String bname) {
    	WebDriver driver;
		switch(bname.toLowerCase().trim()){
		case "chrome":driver=new ChromeDriver(); break;
		case "edge":driver=new EdgeDriver(); break;
		case "firefox":driver=new FirefoxDriver(); break;
		default:throw new IllegalArgumentException("Unsupported browser: " + bname);
		}
		tdriver.set(driver);
		return getDriver();
		
    
    }

}
