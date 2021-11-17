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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.scel.mantemLivro.model.Livro;
import com.fatec.scel.mantemLivro.ports.LivroServico;

@RestController
@RequestMapping("/api/v1/livros")
public class APILivroController {
	Logger logger = LogManager.getLogger(APILivroController.class);
	@Autowired
	LivroServico servico; // controller nao conhece a implementacao

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid Livro livro, BindingResult result) {

		logger.info(">>>>>> controller create - post iniciado");
		ResponseEntity<?> response = null;
		if (result.hasErrors()) {
			logger.info(">>>>>> controller create - dados inválidos => " + livro.toString());
			response = ResponseEntity.badRequest().body("Dados inválidos.");
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
	public List<Livro> consultaTodos() {
		return servico.consultaTodos();
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
}
