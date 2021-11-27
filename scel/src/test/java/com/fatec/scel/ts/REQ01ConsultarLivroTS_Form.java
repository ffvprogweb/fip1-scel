package com.fatec.scel.ts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class REQ01ConsultarLivroTS_Form {

	private WebDriver driver;

	@BeforeEach
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://scel.herokuapp.com/login");
	}
	@AfterEach
	public void tearDown() {
		driver.quit();
	}
	@Test
	public void CT01_consulta_com_sucesso(){
		
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
	}

}
