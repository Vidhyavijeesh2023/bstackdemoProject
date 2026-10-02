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
  public void IncreaseandDecreaseQuantity() {
	String product = "iPhone 12 Mini";      //already in cart from VerifyAddproducttoCart (cart: Mini x1, iPhone 12 x1)
	Assert.assertEquals(productPage.getCartQuantity(product), 1, "Initial quantity is not matching");

	//increase 2 times -> Mini x3, bag = 4
	productPage.increaseQuantity(product, 2);
	Assert.assertEquals(productPage.getCartQuantity(product), 3, "Quantity after increase is not matching");
	Assert.assertEquals(productPage.BagQuantity(), "4", "Bag quantity after increase is not matching");

	//decrease 2 times -> Mini x1, bag = 2
	productPage.decreaseQuantity(product, 2);
	Assert.assertEquals(productPage.getCartQuantity(product), 1, "Quantity after decrease is not matching");
	Assert.assertEquals(productPage.BagQuantity(), "2", "Bag quantity after decrease is not matching");

	//"-" is disabled at 1, quantity must not go below 1
	productPage.decreaseQuantity(product, 1);
	Assert.assertEquals(productPage.getCartQuantity(product), 1, "Quantity went below 1");
  }
  
  @Test(priority=6, dependsOnMethods="VerifyCartItemsandRemoveAnyProduct")
  public void VerifySubTotal() {
	//subtotal shown in cart must equal the addition of prices of all products in the cart
	double subTotal = productPage.getSubTotal();
	double sumOfPrices = productPage.getCalculatedCartTotal();
	System.out.println("Subtotal displayed: " + subTotal + " | Sum of product prices: " + sumOfPrices);

	Assert.assertTrue(sumOfPrices > 0, "Cart is empty - nothing to verify");
	Assert.assertEquals(subTotal, sumOfPrices, 0.01, "Subtotal does not match the sum of product prices in cart");
  }
  
  @Test(priority=7, dependsOnMethods="VerifySubTotal")
  public void VerifyCheckout() {
	//cart is still open from previous tests - click Checkout
	checkoutPage = productPage.clickCheckout();

	System.out.println("Current URL: " + checkoutPage.getCurrentUrl());
	Assert.assertTrue(checkoutPage.getCurrentUrl().contains("checkout"), "Not navigated to checkout page");
  }
  
}