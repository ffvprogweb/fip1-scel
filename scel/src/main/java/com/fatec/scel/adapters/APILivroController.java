package com.fatec.scel.adapters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fatec.scel.mantemLivro.model.Livro;
import com.fatec.scel.mantemLivro.ports.LivroServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api/v1/livros")
public class APILivroController {
	Logger logger = LogManager.getLogger(APILivroController.class);
	@Autowired
	LivroServico servico; // controller nao conhece a implementacao
	
	@PostMapping
	@Operation (summary = "Cadastrar um livro na biblioteca - response: 201 livro cadastrado- 404 dados inválidos - 400 livro já cadastrado")
	@ApiResponse (responseCode = "201", description = "Livro cadastrado")
	@ApiResponse (responseCode = "404", description = "Dados inválidos.")
	@ApiResponse (responseCode = "400", description = "Livro já cadastrado")
	public ResponseEntity<?> create(@RequestBody @Valid Livro livro, BindingResult result) {
		logger.info(">>>>>> controller create - post iniciado");
		ResponseEntity<?> response = null;
		if (result.hasErrors()) {
			logger.info(">>>>>> controller create - dados inválidos => " + livro.toString());
			response = ResponseEntity.badRequest().body("Dados inválidos.");
			logger.info(">>>>>> controller create - dados inválidos status code=> " + response.getStatusCode());
		} else {
			Optional<Livro> umLivro = Optional.ofNullable(servico.consultaPorIsbn(livro.getIsbn()));
			if (umLivro.isPresent()) {
				logger.info(">>>>>> controller create - livro já cadastrado");
				response = ResponseEntity.badRequest().body("Livro já cadastrado");
			} else {
				response = ResponseEntity.status(HttpStatus.CREATED).body(servico.save(livro));
				logger.info(">>>>>> controller create - cadastro realizado com sucesso");
			}
		}
		return response;
	}
	@GetMapping
	public ResponseEntity<List<Livro>> consultaTodos() {
		List<Livro> listaDeLivros = new ArrayList<Livro>();
		servico.consultaTodos().forEach(listaDeLivros::add);
		logger.info(">>>>>> controller consulta todos chamado");
		if (listaDeLivros.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listaDeLivros, HttpStatus.OK);
	}
	@GetMapping("/{isbn}")
	public ResponseEntity<Livro> findByIsbn(@PathVariable String isbn) {
		logger.info(">>>>>> 1. controller chamou servico consulta por isbn => " + isbn);
		ResponseEntity<Livro> response = null;
		Livro livro = servico.consultaPorIsbn(isbn);
		Optional<Livro> optLivro = Optional.ofNullable(livro);
		if (optLivro.isPresent()) {
			response = ResponseEntity.status(HttpStatus.OK).body(optLivro.get());
		} else {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return response;
	}

	@GetMapping("/id/{id}")
	@Operation (summary = "Consulta livro por id")
	public ResponseEntity<Livro> findById(@PathVariable long id) {
		logger.info(">>>>>> 1. controller chamou servico consulta por id => " + id);
		Optional<Livro> livro = Optional.ofNullable(servico.consultaPorId(id));
		if (livro.isPresent()) {
			return new ResponseEntity<>(livro.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	@Operation (summary = "Atualiza as informações de um livro cadastrado na biblioteca")
	@ApiResponse (responseCode = "200", description = "Livro atualizado")
	@ApiResponse (responseCode = "404", description = "O id do livro não foi localizado")
			
	
	public ResponseEntity<Livro> update(@PathVariable("id") long id, @Valid @RequestBody Livro livro) {
		logger.info(">>>>>> 1. controller update chamou servico consulta por id => " + id);
		Livro umLivro = servico.consultaPorId(id);
		if (umLivro != null) {
			umLivro.setTitulo(livro.getTitulo());
			umLivro.setAutor(livro.getAutor());
			return new ResponseEntity<>(servico.save(umLivro), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
