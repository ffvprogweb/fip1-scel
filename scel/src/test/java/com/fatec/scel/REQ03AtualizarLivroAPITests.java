package com.fatec.scel;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fatec.scel.adapters.TesteController;
import com.fatec.scel.mantemLivro.model.Livro;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class REQ03AtualizarLivroAPITests {
	String urlBase = "/api/v1/livros";
	private Logger logger = LogManager.getLogger(REQ03AtualizarLivroTests.class);;
	@Autowired
	TesteController testeController;
	@Autowired
	TestRestTemplate testRestTemplate;
	@BeforeAll
	public void setup() {
		testeController.create();
	}
	@ParameterizedTest // pode-se criar o arquivo req03_atualizar_livro.csv
	@CsvSource({
		    "3333, Engenharia de Software, Sommerville, dados validos, 200 OK",
			"3333, Engenharia de Software, , não pode ser nulo, 400 BAD_REQUEST",
			"3333, Engenharia de Software,'', Autor deve ter entre 1 e 50 caracteres, 400 BAD_REQUEST" 
			})
	
	public void ct01_validacao_da_alteracao_do_cadastro(String isbn, String titulo, String autor, String re, String rest) {
		//**************************************************************************************  	
    	//setup - obtem o id do livro
    	//**************************************************************************************
    	Livro livro = new Livro(isbn, titulo, autor);
    	ResponseEntity<Livro> resposta1 = testRestTemplate.getForEntity(urlBase + "/" + livro.getIsbn(), Livro.class);
		Livro livro1 = resposta1.getBody();
		livro.setId(livro1.getId());
		
    	//**************************************************************************************  	
    	//acao
    	//**************************************************************************************
       	HttpEntity<Livro> httpEntity = new HttpEntity<>(livro);  //inclui no corpo da mensagem o objeto livro
       	ResponseEntity<String> resposta2 = testRestTemplate.exchange(urlBase + "/" + livro.getId(), HttpMethod.PUT, httpEntity,String.class);
              	
       	//**************************************************************************************
    	//valida o status de acordo com a condição testada
    	//************************************************************************************
       	ResponseEntity<Livro> resposta3 = testRestTemplate.getForEntity(urlBase + "/" + livro.getIsbn(), Livro.class);
       	Livro livro2 = resposta3.getBody();
       	logger.info(">>>>>> livro modificado ==>" + livro2.toString());
    	assertEquals(rest, resposta2.getStatusCode().toString());

	}
}
