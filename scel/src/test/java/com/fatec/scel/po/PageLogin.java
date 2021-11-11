package com.fatec.scel.po;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageLogin {
	private WebDriver driver;
	private By nameBy = By.name("username");
	private By passwordBy = By.name("password");
	private By btnLoginBy = By.cssSelector("button");

	public PageLogin(WebDriver driver) {
		this.driver = driver;
	}

	public PageLogin login(String user, String password) {
		driver.findElement(nameBy).click();
		driver.findElement(nameBy).sendKeys(user);
		driver.findElement(passwordBy).sendKeys(password);
		driver.findElement(btnLoginBy).click();
		return new PageLogin(driver);
	}
}