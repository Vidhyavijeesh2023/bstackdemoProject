package com.bstackdemo.Testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.bstackdemo.pages.Basetest;

public class ConfirmationTestcases extends Basetest{
  @Test
  public void setup() throws InterruptedException {
	  loginPage.Signin();
	  loginPage.selectUsername("demouser");
	  loginPage.selectPassword("testingisfun99");
	  productPage=loginPage.clickLogin();
	  productPage.AddProdtoCart("iPhone 12");
	  checkoutPage=productPage.clickCheckout();
	  checkoutPage.fillShippingAddressForm("Vidhya", "Ganeshan", "RamNagar Street", "Bangalore", "767588");
  }
  
  @Test
  public void confirmationPageValidation() {
	  confirmationPage.confirmationMessageDisplayed();
	  Assert.assertTrue(confirmationPage.confirmationMessageDisplayed().contains("Your Order has been successfully placed."));
	  confirmationPage.isDownloadReceiptLinkDisplayed();
	 
  }
}
