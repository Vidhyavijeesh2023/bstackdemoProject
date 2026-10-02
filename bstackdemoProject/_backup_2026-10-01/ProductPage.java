package com.bstackdemo.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
	private WebDriverWait wait;
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

public void increaseQTyincartMultipleTimes(String productName) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Locate quantity text
    By qtyDisplay = By.xpath("//p[@class='title' and normalize-space(text())='" + productName + "']"
        + "/ancestor::div[@class='shelf-item']//p[contains(@class,'desc')]");

    // Wait until quantity text is visible
    wait.until(ExpectedConditions.visibilityOfElementLocated(qtyDisplay));
    String qtyText = driver.findElement(qtyDisplay).getText().trim();
    int qty = Integer.parseInt(qtyText.split("Quantity:")[1].trim());

    // Locate + button
    By increaseQtyButton = By.xpath("//p[@class='title' and normalize-space(text())='" + productName + "']"
        + "/ancestor::div[@class='shelf-item']//button[contains(@class,'shelf-item__qty-btn') and text()='+']");

    // Scroll into view before clicking
    WebElement addButton = driver.findElement(increaseQtyButton);
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addButton);

    // Wait until clickable and click via JS to bypass overlay
    wait.until(ExpectedConditions.elementToBeClickable(addButton));
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);

    // Wait for quantity text to update
    wait.until(ExpectedConditions.textToBePresentInElementLocated(qtyDisplay, "Quantity: " + (qty + 1)));

    System.out.println("Increased quantity of product: " + productName + " from " + qty + " to " + (qty + 1));
}



public void decreaseQTyincartMultipleTimes(String productName) {
	// Find the product in the cart and get its current quantity
	By qtyDisplay = By.xpath("//p[@class='title' and normalize-space(text())='"+productName+"']"
			+ "/ancestor::div[@class='shelf-item']//p[contains(@class,'desc')]");
	
	String qtyText = driver.findElement(qtyDisplay).getText().trim(); // e.g. "Quantity: 2"
	int qty = Integer.parseInt(qtyText.split("Quantity:")[1].trim());
	
	String title = driver.findElement(By.xpath("//p[@class='title' and normalize-space(text())='"+productName+"']")).getText().trim();
	// Decrease quantity by 1
	By decreaseQtyButton = By.xpath("//p[@class='title' and normalize-space(text())='"+productName+"']"
			+ "/ancestor::div[@class='shelf-item']//button[contains(@class,'shelf-item__qty-btn') and text()='-']");
	WebElement Removebutton=driver.findElement(decreaseQtyButton);
	ElementUtil.waitForElementClickable(driver,Removebutton,10).click();
	ElementUtil.waitForTextToBePresent(driver, qtyDisplay, "Quantity: " + (qty + 1), 5);
	System.out.println("Decreased quantity of product: " + title + " from " + qty + " to " + (qty - 1));
}

public ProductPage getSubTotal() {
	By subTotalLocator = By.cssSelector("div.float-cart__subtotal>span.subtotal-value");
    driver.findElement(subTotalLocator).getText();
	System.out.println("Subtotal: " + driver.findElement(subTotalLocator).getText());
    return this;
}

}