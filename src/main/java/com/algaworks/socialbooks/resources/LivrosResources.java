package com.algaworks.socialbooks.resources;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooks.domain.Comentario;
import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.services.LivrosService;

@RestController
@RequestMapping(value="/livros")
public class LivrosResources {
	
	@Autowired
	private LivrosService livrosService;

	/*
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Livro buscar(@PathVariable("id") Long id){
		Livro livro = livrosRepository.findOne(id);
		if(livro == null)
		return 
	}*/
	
	/* 
	 * o Tratamento de erro que estava no try.catch estga agora na classe
	 * com.algaworks.socialbooks.handler.ResourceExceptionHandler.handlerLivroNaoEncontradoException
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?>  buscar(@PathVariable("id") Long id){
		Livro livro = livrosService.buscar(id);
		
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS); 
		
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(livro);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody Livro livro){
		livro = livrosService.salvar(livro);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(livro.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
		
	/*
	@RequestMapping(value="/livros",method=RequestMethod.GET)
	public List<Livro> listar(){
		Livro l1 = new Livro("Rest Aplicado");
		Livro l2 = new Livro("Git Passo-a-Passo");
		Livro[] livros = {l1,l2};
		
		return Arrays.asList(livros);
	}*/
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Livro>> listar(){
		return ResponseEntity.status(HttpStatus.OK)
				.header("Accept","application/json")
				.body(livrosService.listar());
	}
		
	
	/* 
	 * o Tratamento de erro que estava no try.catch estga agora na classe
	 * com.algaworks.socialbooks.handler.ResourceExceptionHandler.handlerLivroNaoEncontradoException
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id){
		livrosService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
		
	/* 
	 * o Tratamento de erro que estava no try.catch estga agora na classe
	 * com.algaworks.socialbooks.handler.ResourceExceptionHandler.handlerLivroNaoEncontradoException
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Livro livro, @PathVariable("id") Long id ){
		livro.setId(id);
		livrosService.atualizar(livro);	
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}/comentarios")
	public ResponseEntity<Void> adicionarComentario(@PathVariable("id") Long livroId,@RequestBody  Comentario comentario){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		comentario.setUsuario(auth.getName());
		
		livrosService.salvarComentario(livroId, comentario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(uri).build();
		
		
	}
	@RequestMapping(value="/{id}/comentarios", method=RequestMethod.GET)
	public ResponseEntity<List<Comentario>> listarComentario(@PathVariable("id") Long livroId){
		List<Comentario> comentarios = livrosService.listarComentarios(livroId);
		return ResponseEntity.status(HttpStatus.OK).body(comentarios);
	}
	
}
