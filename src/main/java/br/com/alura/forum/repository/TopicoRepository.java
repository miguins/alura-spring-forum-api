package br.com.alura.forum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{

	/* 
	 * Como é um relacionamento, por baixo dos panos
	 * o spring faz uma busca em Curso para poder comparar
	 * o nome.
	 * 
	 * É possível usar também Curso_Nome para que o spring
	 * saiba com certeza que fará a busca dentro do relacionamento.
	 * 
	 * 
	 * 
	 * */
	
	Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);

	/* 
	 * Caso não siga a nomenclatura de findBy ou precise de uma busca mais precisa
	 * a consulta pode ser feita com JPQL ou Native Query.
	 * */
	// @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	// @Query(value = "SELECT * FROM topicos t WHERE t.curso.nome = :nomeCurso", nativeQuery = true)
	// List<Topico> procurarPorCursoNome(@Param("nomeCurso") String nomeCurso);
	
}
