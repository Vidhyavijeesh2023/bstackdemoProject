package com.bstackdemo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;
	private WebDriverWait wait;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);
	}
    @FindBy(id="signin")
	private WebElement signIn;

	
	@FindBy(css = "#username input")
	private WebElement usernameInput;


	@FindBy(css = "#username div[class*='singleValue']")
	private WebElement selectedUsername;

	
	@FindBy(css = "#password input")
	private WebElement passwordInput;


	@FindBy(css = "#password div[class*='singleValue']")
	private WebElement selectedPassword;

	
	@FindBy(id = "login-btn")
	private WebElement loginButton;

	@FindBy(css = "span.username")
	private WebElement loggedInUser;



	public LoginPage Signin() {
		signIn.click();
		wait.until(ExpectedConditions.urlContains("signin"));
		return this;
	}

	
	public LoginPage selectUsername(String username) {
		wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
		usernameInput.sendKeys(username);
		usernameInput.sendKeys(Keys.ENTER);
		return this;
	}

	
	public LoginPage selectPassword(String password) {
		wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
		passwordInput.sendKeys(password);
		passwordInput.sendKeys(Keys.ENTER);
		return this;
	}

	
	public ProductPage clickLogin() {
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
		return new ProductPage(driver);
	}


	public String getSelectedUsername() {
		return wait.until(ExpectedConditions.visibilityOf(selectedUsername)).getText().trim();
	}

	public String getSelectedPassword() {
		return wait.until(ExpectedConditions.visibilityOf(selectedPassword)).getText().trim();
	}

	public String getLoggedInUsername() {
		return wait.until(ExpectedConditions.visibilityOf(loggedInUser)).getText().trim();
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
}