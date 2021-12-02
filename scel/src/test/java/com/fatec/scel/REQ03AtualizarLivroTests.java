package com.fatec.scel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
import com.fatec.scel.adapters.TesteController;
import com.fatec.scel.mantemLivro.model.Livro;
import com.fatec.scel.mantemLivro.ports.LivroRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class REQ03AtualizarLivroTests {
	private Logger logger = LogManager.getLogger(REQ03AtualizarLivroTests.class);;
	@Autowired
	LivroRepository repository;
	@Autowired
	TesteController testeController;
	private Validator validator;
	private ValidatorFactory validatorFactory;
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
	public void atualizar_livro(String isbn, String titulo, String autor, String re) {
	
		Livro livro = new Livro(isbn, titulo, autor);
		if (violations(livro).isEmpty()) {
			logger.info(">>>>>> dados validos");
			Livro ro = repository.findByIsbn(livro.getIsbn()); 
			livro.setId(ro.getId()); // sem id = registro novo com id = update
			repository.save(livro);
			assertFalse (livro.equals(ro));
			logger.info(">>>>>> autor valido => " + livro.toString());
		} else {
			logger.info(">>>>>> dados invalidos");
			assertFalse(violations(livro).isEmpty());
			assertEquals(re, violations(livro).iterator().next().getMessage());
		}
	}
	public Set<ConstraintViolation<Livro>> violations(Livro l) {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Livro>> violations = validator.validate(l);
		return violations;
	}
}
