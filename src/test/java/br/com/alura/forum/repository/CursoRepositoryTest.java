package br.com.alura.forum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import br.com.alura.forum.modelo.Curso;

@DataJpaTest
// Notação para que o Spring passe a utilizar outro banco que não seja em memória
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test") // Força o profile a ser de test
class CursoRepositoryTest {

	@Autowired private CursoRepository repository;
	
	@Autowired private TestEntityManager em;
	
	// Por padrão o Spring utilza os teste com um BD em memória
	@Test
	public void temQueCarregarUmCursoAoBuscarPeloNome() {
		String nomeCurso = "HTML 1";
		
		/* 
		 * Como estamos carregando um banco vazio,
		 * precisamos criar o objeto
		 * */
		Curso html5 = new Curso();
		html5.setNome(nomeCurso);
		html5.setCategoria("Programação");
		em.persist(html5);

		Curso curso = repository.findByNome(nomeCurso);
		
		assertNotNull(curso);
		assertEquals(nomeCurso, curso.getNome());
	}
	
	@Test
	public void naoTemQueCarregarUmCursoQueNaoExiste() {
		String nomeCurso = "HTML 55";
		Curso curso = repository.findByNome(nomeCurso);
		
		assertNull(curso);
	}

}
