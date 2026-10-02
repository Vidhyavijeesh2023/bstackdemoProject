package com.bstackdemo.Testcases;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.bstackdemo.pages.Basetest;

public class LoginPageTestcases extends Basetest{
	
	@Test(priority = 1)
	public void navigateToSignInPage() {
		loginPage.Signin();

		Assert.assertTrue(loginPage.getCurrentUrl().contains("signin"),
				"Did not navigate to the Sign In page");
		System.out.println(loginPage.getCurrentUrl());
	}

	@Test(priority = 2, dependsOnMethods = "navigateToSignInPage")
	public void selectDemoUser() {
		loginPage.selectUsername("demouser");

		Assert.assertEquals(loginPage.getSelectedUsername(), "demouser",
				"Username dropdown does not show 'demouser'");
	}

	@Test(priority = 3, dependsOnMethods = "selectDemoUser")
	public void selectPassword() {
		loginPage.selectPassword("testingisfun99");

		Assert.assertEquals(loginPage.getSelectedPassword(), "testingisfun99",
				"Password dropdown does not show 'testingisfun99'");
	}

	@Test(priority = 4, dependsOnMethods = "selectPassword")
	public void loginAsDemoUser() {
		loginPage.clickLogin();

		Assert.assertEquals(loginPage.getLoggedInUsername(), "demouser",
				"Header does not show logged-in user 'demouser'");
		System.out.println(loginPage.getCurrentUrl());
	}
  }
  
  

