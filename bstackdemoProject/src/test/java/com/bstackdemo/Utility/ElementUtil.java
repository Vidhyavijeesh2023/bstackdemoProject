package com.bstackdemo.Utility;

	import java.io.File;
	import java.io.IOException;
	import java.time.Duration;
	import java.util.List;


import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.io.FileHandler;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.Select;
	import org.openqa.selenium.support.ui.WebDriverWait;


public class ElementUtil {
	
	
		public static void getScreenshot(WebDriver driver,String FileName)
		{
			TakesScreenshot ts=(TakesScreenshot) driver;
			  File temp=ts.getScreenshotAs(OutputType.FILE);
			  File dest=new File(System.getProperty("user.dir")+"//ScreenshotsFolder//"+FileName+".png");
			  try {
				FileHandler.copy(temp,dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//first reusable code for select based dropdown

		public static void selectBasedDropdown(WebElement ele,String value)
		{
			Select sc=new Select(ele);
			  System.out.println("Is dropdown support multiple selection?: "+sc.isMultiple());
			  
					  List<WebElement> allOptions=sc.getOptions();
					  System.out.println("Total options are: "+allOptions.size());
					  for(WebElement i:allOptions)
					  {
						  System.out.println(i.getText());
						  if(i.getText().contains(value))
						  {
							  i.click();
							  break;
						  }
					  }
			  
			  
		}
		
		//Javascript Utilities forScroll
		
		public static void scrollDown(WebDriver driver)
		{
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
			  
		}
		
		
		public static void scrollUp(WebDriver driver)
		{
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
			  
		}
		
		
		public static void scrollUpToElement(WebDriver driver,WebElement ele)
		{
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript("arguments[0].scrollIntoView(true);",ele);
		}
		
		
		//multiple windowHandling
		
		public static void switchToExpectedWindow(WebDriver driver,List<String> allWindows,String windowTitle)
		{
			for(String childId:allWindows)
			  {
				  //we are switching to every window and collecting title
				  String appTitle=driver.switchTo().window(childId).getTitle();
				  if(appTitle.contains(windowTitle))
				  {
					  System.out.println("Expected Window found");
					  System.out.println("Current url: "+driver.getCurrentUrl());
					  break;
				  }
			  }
			
		}
		
		public static void switchToParentWindow(WebDriver driver,String parentId)
		{
			driver.switchTo().window(parentId);
		}
		
		
		//ExplicitWait utility
		public static WebElement waitForVisibilityOfElement(WebDriver driver,By loc,int timeout)
		{
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
		}
		
		public static WebElement waitForPresenceOfElement(WebDriver driver,By loc,int timeout)
		{
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.presenceOfElementLocated(loc));
		}
		
		public static WebElement waitForElementClickable(WebDriver driver,WebElement element,int timeout)
		{
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.elementToBeClickable(element));
			
		}
		
		public static boolean waitForUrlContains(WebDriver driver,String url,int timeout)
		{
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.urlContains(url));
		}
		
		
		public static boolean waitForTitleContains(WebDriver driver,String title,int timeout)
		{
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.titleContains(title));
		
		}


		public static void waitForPageLoad(WebDriver driver) {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until( webDriver -> ((JavascriptExecutor) webDriver)
	                    .executeScript("return document.readyState")
	                    .equals("complete"));
	    }


		public static Boolean waitForTextToBePresent(WebDriver driver, By loc, String name, int timeout) {
			// TODO Auto-generated method stub
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.textToBePresentInElementLocated(loc, name));
		}
		
		
		
		
}




