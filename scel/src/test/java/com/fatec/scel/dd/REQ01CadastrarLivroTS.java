package com.fatec.scel.dd;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import com.fatec.scel.mantemLivro.ports.LivroRepository;
import com.fatec.scel.po.PageCadastrarLivro;
import com.fatec.scel.po.PageLogin;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)

class REQ01CadastrarLivroTS {
	static private WebDriver driver;
	static JavascriptExecutor js;
	private PageCadastrarLivro pageCadastrarLivro;
	private PageLogin pageLogin;
	private static Logger logger;
	@Autowired
	LivroRepository repository;

	@BeforeAll
	public static void inicializa() {
		logger = LogManager.getLogger(REQ01CadastrarLivroTS.class);
		driver = DriverFactory.getDriver();
		driver.get("https://ts-scel-web.herokuapp.com/login");
		// js = (JavascriptExecutor) driver;
		try {
			ManipulaExcel.setExcelFile("C:\\temp\\cadastrarLivro2.xlsx", "Planilha1");

		} catch (Exception e) {
			logger.info(">>>>>> 3. Erro no path ou workbook =" + e.getMessage());
		}
	}

	@AfterAll
	public static void tearDown() {
		DriverFactory.finaliza();
	}

	@Test
	public void cadastrarLivro() throws Exception {
		// se o campo for lancado na planilha como numerico mas a entrada eh String deve
		// ser tratado ou na planilha indicando string ou aqui (no script de teste)
		// transformando para string
		pageLogin = new PageLogin(driver);
		pageLogin.login("jose", "123");
		int linha = 1; // linha 0 cabecalho
		// *************************************************************************************************************
		// Interpretador das condicoes de teste
		// *************************************************************************************************************
		while (!ManipulaExcel.getCellData(linha, 0).equals("final".trim())) {
			logger.info(">>>>>> 4. processando a linha =" + linha + "-" + "condição de teste = " + ManipulaExcel.getCellData(linha, 3));
			pageCadastrarLivro = new PageCadastrarLivro(driver);
			try {
				pageCadastrarLivro.cadastrar(ManipulaExcel.getCellData(linha, 0), ManipulaExcel.getCellData(linha, 1), ManipulaExcel.getCellData(linha, 2));
				//driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
				espera();
				assertEquals(ManipulaExcel.getCellData(linha, 3), pageCadastrarLivro.getResultadoObtido(ManipulaExcel.getCellData(linha, 3)));
				pageCadastrarLivro.voltarParaMenu();
			} catch (NoSuchElementException e) {
				logger.info(">>>>>> 5. Elemento não localizado =" + e.getMessage() + "re - " + ManipulaExcel.getCellData(linha, 3));
				fail("Exception localizador não encontrado - " + e.getMessage());
			} catch (Exception e) {
				logger.info(">>>>>> 5. Exception nao esperada=" + e.getMessage() + "re - " + ManipulaExcel.getCellData(linha, 3));
				fail("Exception nao esperada - " + e.getMessage());
			}
			linha = linha + 1;
		}
	}

	public void espera() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
