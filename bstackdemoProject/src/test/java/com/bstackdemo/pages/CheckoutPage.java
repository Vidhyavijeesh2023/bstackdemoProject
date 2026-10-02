package com.bstackdemo.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {
	private WebDriver driver;
	public CheckoutPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}

//locators

@FindBy(id="firstNameInput")
private WebElement firstName;      //first field of the shipping address form

@FindBy(id="lastNameInput")
private WebElement lastName;       //second field of the shipping address form

@FindBy(id="addressLine1Input")
private WebElement address;  //third field of the shipping address form

@FindBy(id="provinceInput")
private WebElement State;  //fourth field of the shipping address form

@FindBy(id="postCodeInput")
private WebElement zipCode;  //fifth field of the shipping address form

@FindBy(id="checkout-shipping-continue")
private WebElement submitBtn;     
//methods

public String getCurrentUrl() {
	return driver.getCurrentUrl();
}

public ConfirmationPage fillShippingAddressForm(String fName,String lName,String addr,String state,String zip) {
	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
	wait.until(ExpectedConditions.visibilityOf(firstName));
	firstName.clear();
	firstName.sendKeys(fName);
	lastName.clear();
	lastName.sendKeys(lName);
	address.clear();
	address.sendKeys(addr);
	State.clear();
	State.sendKeys(state);
	zipCode.clear();
	zipCode.sendKeys(zip);
	submitBtn.click();
	wait.until(ExpectedConditions.urlContains("confirmation"));
	
	return new ConfirmationPage(driver);
}

}
