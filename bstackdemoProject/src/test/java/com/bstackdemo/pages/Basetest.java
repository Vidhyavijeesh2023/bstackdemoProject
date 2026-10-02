package com.bstackdemo.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.bstackdemo.Utility.BrowserProvider;



public class Basetest {
	protected WebDriver driver;
	protected LoginPage loginPage;
	protected ProductPage productPage;
	protected CheckoutPage checkoutPage;
	protected ConfirmationPage confirmationPage;
	@Parameters({"bname"})
	@BeforeClass
	public void setup(String bname) {
		driver=BrowserProvider.setDriver(bname);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get("https://bstackdemo.com/");
		driver.manage().window().maximize();
		loginPage=new LoginPage(driver);
		productPage=new ProductPage(driver);
		checkoutPage=new CheckoutPage(driver);
		confirmationPage=new ConfirmationPage(driver);
	}
	@AfterClass
	public void teardown() {
		BrowserProvider.getDriver().quit();
	}
}
