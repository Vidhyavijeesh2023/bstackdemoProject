package com.bstackdemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage {
    private WebDriver driver;
	public ConfirmationPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(css="legend#confirmation-message")
	private WebElement confirmationMessage;
	
	@FindBy(linkText="Download order receipt")
	private WebElement downloadReceiptLink;
	
	public String confirmationMessageDisplayed() {
	
	return confirmationMessage.getText();
	}
	
	public ConfirmationPage isDownloadReceiptLinkDisplayed() {
		
		downloadReceiptLink.click();
		return this;
		
	}

}
