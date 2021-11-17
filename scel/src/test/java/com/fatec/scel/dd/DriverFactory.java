package com.fatec.scel.dd;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
public class DriverFactory {
	private static WebDriver driver;
	private static Logger logger = LogManager.getLogger(DriverFactory.class);
	private DriverFactory() {}
	public static WebDriver getDriver(){
		if(driver == null) {
			System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
			driver = new ChromeDriver();
			//driver.manage().window().setSize(new Dimension(1200, 765));
			logger.info(">>>>>> 1. Abre a aplicação sob teste");
		}
		return driver;
	}
	public static void finaliza(){
		if(driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
