package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesTopicoDTO;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.controller.repository.CursoRepository;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired private TopicoRepository topicoRepository;
	@Autowired private CursoRepository cursoRepository;
	
	@GetMapping
	public Page<TopicoDTO> lista(
			@RequestParam(required = false) String nomeCurso, 
			@RequestParam int pagina, 
			@RequestParam int qtd,
			@RequestParam String ordenacao) {
		
		Pageable paginacao = PageRequest.of(pagina, qtd, Direction.DESC, ordenacao);
		
		if (nomeCurso == null) {
			
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			
			return TopicoDTO.converter(topicos);			
		} else {
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
			
			return TopicoDTO.converter(topicos);
		}
		
	}
	
	
	/*
	 * @Valid executa as validações do Bean Validation para isso  ele
	 * utiliza as anotações colocadas nos atributos do objeto
	 *  */
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	@GetMapping("/{id}")// @PathVariable("id") Long codigo 
	public ResponseEntity<TopicoDTO> detalhar(@PathVariable Long id) {
		
		// Caso não encontrar o getOne joga uma exeception
		// Topico topico = topicoRepository.getOne(id);
		
		Optional<Topico> topico = topicoRepository.findById(id);
		
		if (topico.isPresent()) {
			return ResponseEntity.ok(new TopicoDTO(topico.get()));			
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/detalhe/{id}")
	public DetalhesTopicoDTO detalharMais(@PathVariable Long id) {
		
		Topico topico = topicoRepository.getOne(id);
		
		return new DetalhesTopicoDTO(topico);
	}
	
	@PutMapping("/{id}")
	@Transactional // Necessário para commitar a transação, mas também é uma boa prático colocar nos métodos POST e DELETE
	public ResponseEntity<TopicoDTO> atualizar(@RequestBody @Valid AtualizacaoTopicoForm form, @PathVariable Long id) {
		
		Optional<Topico> opt = topicoRepository.findById(id);
		
		if (opt.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			
			return ResponseEntity.ok(new TopicoDTO(topico));			
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> atualizar(@PathVariable Long id) {
		
		Optional<Topico> topico = topicoRepository.findById(id);
		
		if (topico.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();			
		}
		
		return ResponseEntity.notFound().build();
	}
}
