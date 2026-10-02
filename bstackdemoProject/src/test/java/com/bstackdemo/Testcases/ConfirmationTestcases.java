package com.bstackdemo.Testcases;



import org.testng.Assert;
import org.testng.annotations.Test;

import com.bstackdemo.pages.Basetest;

public class ConfirmationTestcases extends Basetest{

  
  @Test(priority=1)
  public void setup() throws InterruptedException {
	  loginPage.Signin();
	  loginPage.selectUsername("demouser");
	  loginPage.selectPassword("testingisfun99");
	  productPage=loginPage.clickLogin();
	  productPage.AddProdtoCart("iPhone 12");
	  checkoutPage=productPage.clickCheckout();
	  confirmationPage=checkoutPage.fillShippingAddressForm("Vidhya", "Ganeshan", "RamNagar Street", "Bangalore", "767588");
  }

  @Test(priority=2, dependsOnMethods="setup")
  public void verifyConfirmationMessage() {
	  String msg = confirmationPage.getConfirmationMessage();
	  Assert.assertTrue(msg.toLowerCase().contains("successfully placed"),
			  "Confirmation message not as expected. Actual: " + msg);
  }

  @Test(priority=3, dependsOnMethods="verifyConfirmationMessage")
  public void verifyDownloadReceipt() {
	  Assert.assertTrue(confirmationPage.isDownloadReceiptLinkDisplayed(), "Download order receipt link is not displayed");

	 
  }
}
