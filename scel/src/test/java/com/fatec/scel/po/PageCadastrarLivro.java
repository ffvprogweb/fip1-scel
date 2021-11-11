package com.fatec.scel.po;

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
		espera();
		return new PageCadastrarLivro(driver);
	}

	/*
	 * Resultado do teste - compara com o titulo da pagina de consulta
	 */
	public String getResultadoCadastroComSucesso() {
		espera();
		return driver.findElement(resultadoCadastroComSucessoBy).getText();
	}

	public String getResultadoLivroJaCadastrado() {
		return driver.findElement(resultadoLivroJaCadastradoBy).getText();
	}

	public String getResultadoISBNInvalido() {
		return driver.findElement(resultadoISBNInvalidoBy).getText();
	}

	public void excluiRegistro() {
		driver.findElement(btnExcluirBy).click();
	}

	public void voltarParaMenu() {
		driver.findElement(btnVoltarBy).click();
	}

	public void espera() { // fixa
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}