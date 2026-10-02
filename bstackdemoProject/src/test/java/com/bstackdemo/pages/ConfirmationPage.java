package com.bstackdemo.pages;



import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class ConfirmationPage {
    private WebDriver driver;
    private WebDriverWait wait;

	public ConfirmationPage(WebDriver driver) {
		this.driver=driver;
		this.wait=new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver,this);
	}

	//locators

	@FindBy(id="confirmation-message")
	private WebElement confirmationMessage;          //"Your Order has been successfully placed."

	@FindBy(xpath="//a[@id='downloadpdf' or contains(normalize-space(),'Download order receipt')]")
	private WebElement downloadReceiptLink;

	//methods

	//waits for the confirmation page to load and returns the message text
	public String getConfirmationMessage() {
		wait.until(ExpectedConditions.urlContains("confirmation"));
		String msg = wait.until(ExpectedConditions.visibilityOf(confirmationMessage)).getText().trim();
		System.out.println("Confirmation message: " + msg);
		return msg;
	}

	public boolean isDownloadReceiptLinkDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(downloadReceiptLink)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	

}
