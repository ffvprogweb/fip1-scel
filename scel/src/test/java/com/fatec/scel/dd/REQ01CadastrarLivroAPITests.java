package com.fatec.scel.dd;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
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
import com.fatec.scel.mantemLivro.model.Livro;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class REQ01CadastrarLivroAPITests {
	String urlBase = "/api/v1/livros";
	@Autowired
	private TestRestTemplate testRestTemplate;
	@ParameterizedTest
//	@CsvSource({
//	"1111, Engenharia de Software, Padua, 201 CREATED",       		//com sucesso
//	"2222, Engenharia de Software, Sommerville, 201 CREATED",   	//com sucesso
//	"2222, Engenharia de Software, Sommerville, 400 BAD_REQUEST",  	//ISBN ja cadastrado
//	"'', Engenharia de Software, Sommerville, 400 BAD_REQUEST",   	//ISBN em branco
//	", Engenharia de Software, Sommerville, 400 BAD_REQUEST",  		//ISBN null
//	"3333, ' ', Padua, 400 BAD_REQUEST",   							//Titulo em branco
//	"3333, , Padua, 400 BAD_REQUEST",       						//Titulo null
//	  })
	@CsvFileSource(resources = "/livro_cadastro.csv", numLinesToSkip = 1)
	
	public void ct01_validacao_do_cadastro(String isbn, String titulo, String autor, String re) {
		//**************************************************************************************  	
    	//setup
    	//**************************************************************************************
    	Livro livro = new Livro(isbn, titulo, autor);
    	//**************************************************************************************  	
    	//acao
    	//**************************************************************************************
       	HttpEntity<Livro> httpEntity = new HttpEntity<>(livro);  //inclui no corpo da mensagem o objeto livro
       	ResponseEntity<String> resposta = testRestTemplate.exchange(urlBase, HttpMethod.POST, httpEntity,String.class);
       	//**************************************************************************************
    	//valida o status de acordo com a condição testada
    	//************************************************************************************
 
    	assertEquals(re, resposta.getStatusCode().toString());

	}

}
