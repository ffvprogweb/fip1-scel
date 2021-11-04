package com.fatec.scel.mantemLivro.ports;

import java.util.List;
import java.util.Optional;
import com.fatec.scel.mantemLivro.model.Livro;

public interface LivroServico {
	public List<Livro> consultaTodos();

	public Livro save(Livro livro);

	public Optional<Livro> consultaPorId(Long id);

	public Livro consultaPorIsbn(String isbn);

	public void delete(Long id);
}