package com.bstackdemo.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bstackdemo.Utility.ElementUtil;

public class ProductPage {

	private WebDriver driver;

	public ProductPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
//locators

@FindBy(css="div.sort>select")
private WebElement orderBy;

@FindBy(css="div.filters>div.filters-available-size span.checkmark")
private List<WebElement> vendorSelection;

@FindBy(css="div.shelf-container div.shelf-item:not([style*='display: none'])")
private List<WebElement> Products;   /*Each div.shelf-item contains:
                                            p.shelf-item__title → product name
                                            div.shelf-item__buy-btn → add to cart button*/
@FindBy(css="div.float-cart__close-btn")
private WebElement closeCart;

@FindBy(css="span.bag__quantity")
private WebElement bagQty;      //count displayed near bagImage  -->we can use it for opening cart aswell

@FindBy(css="div.shelf-item__details")
private List<WebElement> itemsInCart;    //no.of items in cart

@FindBy(css="div.shelf-item__details>p.title")
private List<WebElement> itemsInCartTitle;

@FindBy(css="div.shelf-item__details>p.desc")
private List<WebElement>qtyDisplayInCart;   //quantity of the corresponding product 

@FindBy(css="div.shelf-item__del")
private List<WebElement> removeProdfromCart;
@FindBy(css = "small.products-found>h3")
private WebElement productsFoundText; 

//cart - each product row inside the open cart (re-looked-up on every access, no @CacheLookup)
@FindBy(css="div.float-cart__shelf-container div.shelf-item")
private List<WebElement> cartItems;

//child locators inside ONE cart row (leading dot = search only inside that row)
private final By cartItemTitle = By.cssSelector("p.title");
private final By cartItemDesc  = By.cssSelector("p.desc");          // "...Quantity: 2"
private final By cartPlusBtn   = By.xpath(".//button[normalize-space()='+']");
private final By cartMinusBtn  = By.xpath(".//button[normalize-space()='-']");
private final By cartItemPrice = By.cssSelector("div.shelf-item__price p");   // unit price "$ 799.00"

//subtotal at the bottom of the cart (second selector kept as fallback)
@FindBy(css="div.float-cart__footer p.sub-price__val, div.float-cart__subtotal span.subtotal-value")
private WebElement subTotalValue;

@FindBy(css="div.buy-btn")
private WebElement checkoutBtn;

//methods

public ProductPage orderselection() {
	Select options =new Select(orderBy);
	options.selectByIndex(1);
	return this;
}
public String getProductsFoundText() {
	return productsFoundText.getText();
}
public ProductPage VendorSelect(String name) {
    for(WebElement vendors:vendorSelection) {
        vendors.getText();
        if(vendors.getText().contains(name)) {
            vendors.click();
            break;
        }
    }
    return this;
}

public int Productsfound() {
	return Products.size();
}

public ProductPage AddProdtoCart(String ProdName) throws InterruptedException {
	Thread.sleep(2000); // Wait for 2 seconds to ensure the products are loaded
	for(WebElement Product:Products) {
		String Productname=Product.findElement(By.cssSelector("p.shelf-item__title")).getText();
		if(Productname.equalsIgnoreCase(ProdName)) {
			 WebElement addBtn = Product.findElement(By.cssSelector("div.shelf-item__buy-btn"));
			ElementUtil.waitForElementClickable(driver,addBtn, 10).click();
			break;
		}
	}
	return this;	
}

public ProductPage closeCart() {
	closeCart.click();
	return this;
}

public void openCart() {
	bagQty.click();
}

public String BagQuantity() {
	return bagQty.getText();
	
}

public ProductPage printCartItemsAndQuantities() {
    for (int i = 0; i < itemsInCartTitle.size(); i++) {
        String title = itemsInCartTitle.get(i).getText().trim();
        String qtyText = qtyDisplayInCart.get(i).getText().trim(); // e.g. "Quantity: 2"
        int qty = Integer.parseInt(qtyText.split("Quantity:")[1].trim());

        System.out.println("Product: " + title + " | Quantity: " + qty);
    }
    
    return this;
}

public ProductPage removeProductFromCart(String productName) {
    By deleteIcon = By.xpath("//p[@class='title' and normalize-space(text())='"+productName+"']"
        + "/ancestor::div[@class='shelf-item']//div[contains(@class,'shelf-item__del')]");
    
    driver.findElement(deleteIcon).click();
    System.out.println("Removed product: " + productName);
    return this;
}

//---------- cart quantity ----------

//finds the cart row of the given product
private WebElement findCartItem(String productName) {
	for (WebElement item : cartItems) {
		if (item.findElement(cartItemTitle).getText().trim().equalsIgnoreCase(productName)) {
			return item;
		}
	}
	throw new IllegalArgumentException("Product not found in cart: " + productName);
}

//reads current quantity of the product from "Quantity: N"
public int getCartQuantity(String productName) {
	String text = findCartItem(productName).findElement(cartItemDesc).getText();
	return Integer.parseInt(text.split("Quantity:")[1].trim());
}

//clicks + / - of the product once and waits until the quantity really changes
private void clickQtyButton(String productName, By button, int expectedQty) {
	WebElement btn = findCartItem(productName).findElement(button);
	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
	ElementUtil.waitForElementClickable(driver, btn, 10);
	((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

	WebDriverWait qtyWait = new WebDriverWait(driver, Duration.ofSeconds(10));
	qtyWait.ignoring(StaleElementReferenceException.class);   //cart re-renders after each click
	qtyWait.until(d -> getCartQuantity(productName) == expectedQty);
}

public ProductPage increaseQuantity(String productName, int times) {
	for (int i = 0; i < times; i++) {
		int before = getCartQuantity(productName);
		clickQtyButton(productName, cartPlusBtn, before + 1);
		System.out.println("Increased " + productName + " quantity: " + before + " -> " + (before + 1));
	}
	return this;
}

public ProductPage decreaseQuantity(String productName, int times) {
	for (int i = 0; i < times; i++) {
		int before = getCartQuantity(productName);
		WebElement minus = findCartItem(productName).findElement(cartMinusBtn);
		if (before <= 1 || !minus.isEnabled()) {          //"-" is disabled at quantity 1
			System.out.println(productName + " is already at minimum quantity 1");
			break;
		}
		clickQtyButton(productName, cartMinusBtn, before - 1);
		System.out.println("Decreased " + productName + " quantity: " + before + " -> " + (before - 1));
	}
	return this;
}

//---------- subtotal ----------

//"$ 1,598.00" -> 1598.0
private double parsePrice(String text) {
	return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
}

//subtotal shown in the cart (waits until it matches the cart items, since the cart re-renders)
public double getSubTotal() {
	WebDriverWait subWait = new WebDriverWait(driver, Duration.ofSeconds(10));
	subWait.ignoring(StaleElementReferenceException.class);
	try {
		subWait.until(d -> Math.abs(parsePrice(subTotalValue.getDomProperty("textContent")) - sumCartPrices(false)) < 0.01);
	} catch (TimeoutException e) {
		//no match within 10s - return the displayed value anyway, the assertion will report it
	}
	String text = subTotalValue.getDomProperty("textContent").trim();
	System.out.println("Subtotal displayed: " + text);
	return parsePrice(text);
}

//adds up the prices of all products currently in the cart (price x quantity)
public double getCalculatedCartTotal() {
	return sumCartPrices(true);
}

private double sumCartPrices(boolean print) {
	double total = 0;
	for (WebElement item : cartItems) {
		String title = item.findElement(cartItemTitle).getText().trim();
		double price = parsePrice(item.findElement(cartItemPrice).getDomProperty("textContent"));
		String desc = item.findElement(cartItemDesc).getText();
		int qty = Integer.parseInt(desc.split("Quantity:")[1].trim());
		total += price * qty;
		if (print) System.out.println("Cart item: " + title + " | Price: " + price + " | Qty: " + qty + " | Line total: " + (price * qty));
	}
	if (print) System.out.println("Sum of cart item prices: " + total);
	return total;
}

//clicks "Checkout" in the cart and waits until the browser navigates to the checkout page
public CheckoutPage clickCheckout() {
	WebDriverWait checkoutWait = new WebDriverWait(driver, Duration.ofSeconds(15));
	checkoutWait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
	checkoutWait.until(ExpectedConditions.urlContains("checkout"));
	System.out.println("Navigated to: " + driver.getCurrentUrl());
	return new CheckoutPage(driver);
}
}