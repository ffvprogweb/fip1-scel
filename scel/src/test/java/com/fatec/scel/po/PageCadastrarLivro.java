package com.fatec.scel.po;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageCadastrarLivro {
	private WebDriver driver;
	private By isbnBy = By.id("isbn");
	private By autorBy = By.id("autor");
	private By tituloBy = By.id("titulo");
	private By btnMenuBy = By.linkText("Livros");
	private By btnCadastrarLivroBy = By.cssSelector(".btn:nth-child(1)");
	private By resultadoCadastroComSucessoBy = By.id("paginaConsulta");
	private By resultadoLivroJaCadastradoBy = By.cssSelector(".text-danger");
	private By resultadoISBNInvalidoBy = By.cssSelector(".text-danger:nth-child(3)");
	private By btnExcluirBy = By.cssSelector("tr:nth-child(2) .delete");
	private By btnVoltarBy = By.linkText("Voltar");
	private String resultadoObtido;

	public PageCadastrarLivro(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Cadastrar livro
	 * 
	 * @param isbn
	 * @param autor
	 * @param titulo
	 * @return – um objeto do tipo pagina é retornado
	 */
	public PageCadastrarLivro cadastrar(String isbn, String autor, String titulo) {
		espera();
		driver.findElement(btnMenuBy).click();
		driver.findElement(isbnBy).sendKeys(isbn);
		driver.findElement(autorBy).sendKeys(autor);
		driver.findElement(tituloBy).sendKeys(titulo);
		driver.findElement(btnCadastrarLivroBy).click();
		driver.manage().timeouts().implicitlyWait(2000,TimeUnit.SECONDS);
		return new PageCadastrarLivro(driver);
	}

	/*
	 * Resultado do teste - compara com o titulo da pagina de consulta
	 */
	public String getResultadoCadastroComSucesso() {
		espera();
		resultadoObtido = driver.findElement(resultadoCadastroComSucessoBy).getText();
		return resultadoObtido;
	}

	public String getResultadoLivroJaCadastrado() {
		resultadoObtido = driver.findElement(resultadoLivroJaCadastradoBy).getText();
		return resultadoObtido;
	}

	public String getResultadoISBNInvalido() {
		resultadoObtido = driver.findElement(resultadoISBNInvalidoBy).getText();
		return resultadoObtido;
	}

	public void excluiRegistro() {
		driver.findElement(btnExcluirBy).click();
	}

	public void voltarParaMenu() {
		driver.findElement(btnVoltarBy).click();
	}

	public String getResultadoObtido(String re) {
		if (re.equals("Lista de livros")) {
			resultadoObtido = driver.findElement(resultadoCadastroComSucessoBy).getText();
		}
		if (re.equals("Livro ja cadastrado")) {
			resultadoObtido = driver.findElement(resultadoLivroJaCadastradoBy).getText();
		}
		if (re.equals("ISBN deve ter 4 caracteres")) {
			resultadoObtido = driver.findElement(resultadoISBNInvalidoBy).getText();
		}
		return resultadoObtido;
	}

	public void espera() { // fixa
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}