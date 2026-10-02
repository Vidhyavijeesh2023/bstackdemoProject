package com.bstackdemo.Testcases;


import org.testng.Assert;
import org.testng.annotations.Test;


import com.bstackdemo.pages.Basetest;

public class ProductCartTestcases extends Basetest{
  @Test(priority=1)
  public void setup() {
	  loginPage.Signin();
	  loginPage.selectUsername("demouser");
	  loginPage.selectPassword("testingisfun99");
	  productPage=loginPage.clickLogin();
  }
  
  @Test(priority=2)
  public void VerifyProductPageBasicValidation() throws InterruptedException {
	  
	  productPage.orderselection();
	  productPage.VendorSelect("Apple");
	  Thread.sleep(2000);
	 
	  productPage.getProductsFoundText();
	  Assert.assertEquals(productPage.getProductsFoundText(), "9 Product(s) found.", "Products found is not matching");
	 
  }
  @Test(priority=3)
  public void VerifyAddproducttoCart() throws InterruptedException {
	  productPage.AddProdtoCart("iPhone 12 Mini");
	  productPage.closeCart();
	  Thread.sleep(2000);
	  productPage.AddProdtoCart("iPhone 12");
	  Thread.sleep(2000);
	  System.out.println("Bag quantity is: "+productPage.BagQuantity());
	  Assert.assertEquals(productPage.BagQuantity(), "2", "Bag quantity is not matching");
  }
  @Test(priority=5,dependsOnMethods="IncreaseandDecreaseQuantity")
  public void VerifyCartItemsandRemoveAnyProduct() {
	
     productPage.printCartItemsAndQuantities();
    productPage.removeProductFromCart("iPhone 12 Mini");
     Assert.assertEquals(productPage.BagQuantity(), "1", "Bag quantity is not matching");
  }
  
  @Test(priority=4)
  public void IncreaseandDecreaseQuantity() throws InterruptedException {
	productPage.increaseQTyincartMultipleTimes("iPhone 12 Mini");
	Thread.sleep(2000);
	Assert.assertEquals(productPage.BagQuantity(), "3", "Bag quantity is not matching");
	productPage.decreaseQTyincartMultipleTimes("iPhone 12 Mini");
	Thread.sleep(2000);
	Assert.assertEquals(productPage.BagQuantity(), "2", "Bag quantity is not matching");
  }
  
  @Test(priority=6)
  public void VerifySubTotal() {
  productPage.getSubTotal();
  Assert.assertEquals(productPage.getSubTotal(),"799.00", "Subtotal is not matching");
  
}
}