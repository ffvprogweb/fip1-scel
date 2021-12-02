package com.fatec.scel.adapters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.fatec.scel.mantemLivro.model.Livro;
import com.fatec.scel.mantemLivro.ports.LivroServico;
@Controller
public class TesteController {
	Logger logger = LogManager.getLogger(TesteController.class);
	@Autowired
	LivroServico servico; // inboud port - controller nao conhece a implementacao
	public void create() {
		Livro livro = new Livro("3333", "Engenharia de Software", "Padua");
		servico.save(livro);
		logger.info(">>>>>> setup de teste => " + livro.toString());
	}
}
