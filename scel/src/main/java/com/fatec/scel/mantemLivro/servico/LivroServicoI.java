package com.fatec.scel.mantemLivro.servico;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import com.fatec.scel.mantemLivro.model.Livro;
import com.fatec.scel.mantemLivro.ports.LivroRepository;
import com.fatec.scel.mantemLivro.ports.LivroServico;

@Service
public class LivroServicoI implements LivroServico {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	LivroRepository repository;

	@Override
	public Iterable<Livro> consultaTodos() {
		return repository.findAll();
	}

	@Override
	public Livro save(Livro livro) {
		logger.info(">>>>>> servico save - cadastro de livro ");
		return repository.save(livro); // retorna o livro com id
	}

	@Override
	public Optional<Livro> consultaPorId(Long id) {
		logger.info(">>>>>> servico consulta por id chamado");
		return repository.findById(id);
	}

	@Override
	public Livro consultaPorIsbn(String isbn) {
		logger.info(">>>>>> servico consulta por isbn chamado");
		return repository.findByIsbn(isbn);
	}

	@Override
	public void delete(Long id) {
		logger.info(">>>>>> servico delete por id chamado");
		repository.deleteById(id);
	}
}