package com.fatec.scel;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import com.fatec.scel.mantemLivro.model.Livro;
import com.fatec.scel.mantemLivro.ports.LivroRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
/*
 * Valida a integração da camada de persistencia com o componente de banco de
 * dados
 */
class REQ03AtualizarLivroTests {
	private Logger logger = LogManager.getLogger(REQ03AtualizarLivroTests.class);;
	@Autowired
	LivroRepository repository;
	private Validator validator;
	private ValidatorFactory validatorFactory;

	@ParameterizedTest // req03_atualizar_livro.csv
	@CsvSource({ "3333, Engenharia de Software, Padua, SETUP",
			"3333, Engenharia de Software, Sommerville, Dados válidos",
			"3333, Engenharia de Software, , não pode ser nulo",
			"3333, Engenharia de Software,'', Autor deve ter entre 1 e 50 caracteres" })
	/*
	 * Valida o dominio do atributo autor
	 */
	public void atualizar_livro(String isbn, String titulo, String autor, String re) {

		if (re.equals("SETUP")) {
			logger.info(">>>>>> setup de teste ");
			Livro livro = new Livro(isbn, titulo, autor);
			repository.save(livro);
			logger.info(">>>>>> autor setup  => " + livro.toString());
		} else {
			Livro livro = new Livro(isbn, titulo, autor);
			if (violations(livro).isEmpty()) {
				String resultado = "Dados válidos";
				logger.info(">>>>>> dados validos");
				Livro ro = repository.findByIsbn(livro.getIsbn());
				livro.setId(ro.getId()); //sem id registro novo com id update
				repository.save(livro);
				ro = repository.findByIsbn(livro.getIsbn());
				assertThat(livro).isEqualTo(ro);
				assertEquals(re, resultado);
				logger.info(">>>>>> autor valido => " + ro.toString());
			} else {
				assertFalse(violations(livro).isEmpty());
				assertEquals(re, violations(livro).iterator().next().getMessage());
			}
		}
	}

	public Set<ConstraintViolation<Livro>> violations(Livro l) {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Livro>> violations = validator.validate(l);
		return violations;
	}
}
