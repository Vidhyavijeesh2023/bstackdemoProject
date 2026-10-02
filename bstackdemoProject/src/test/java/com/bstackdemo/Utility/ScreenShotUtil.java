package com.bstackdemo.Utility;

	import java.io.File;
	import java.io.IOException;
	import java.text.SimpleDateFormat;
	import java.util.Date;

	import org.apache.commons.io.FileUtils;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.WebDriver;

	public class ScreenShotUtil 
	{
		public static String captureScreenshot(String testName) {

	        WebDriver driver = BrowserProvider.getDriver();

	        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

	        String screenshotPath = System.getProperty("user.dir")
	                + "/Screenshots/"
	                + testName
	                + "_"
	                + timestamp
	                + ".png";

	        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

	        try {
	            FileUtils.copyFile(source, new File(screenshotPath));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return screenshotPath;
	    }

	}


