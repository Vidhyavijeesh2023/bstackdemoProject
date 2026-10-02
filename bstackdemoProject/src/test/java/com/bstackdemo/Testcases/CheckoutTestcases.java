package com.bstackdemo.Testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.bstackdemo.pages.Basetest;

public class CheckoutTestcases extends Basetest{
  @Test
  public void setup() throws InterruptedException {
	  loginPage.Signin();
	  loginPage.selectUsername("demouser");
	  loginPage.selectPassword("testingisfun99");
	  productPage=loginPage.clickLogin();
	  productPage.AddProdtoCart("iPhone 12");
	  checkoutPage=productPage.clickCheckout();
  }
  
  @Test
  public void  shippingFormValidation() {
	  String currentUrl=checkoutPage.getCurrentUrl();
	  System.out.println("Current URL is: "+currentUrl);
	  checkoutPage.fillShippingAddressForm("Vidhya", "Ganeshan", "RamNagar Street", "Bangalore", "767588");
	 Assert.assertTrue(driver.getCurrentUrl().contains("confirmation"));
	 
  }
  
  
  
}
