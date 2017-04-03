package com.algaworks.socialbooks.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
public class Livro {

	@JsonInclude(Include.NON_NULL)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@JsonInclude(Include.NON_NULL)
	@NotEmpty(message="o nome não pode ser vazio")
	private String nome;
	
	@JsonInclude(Include.NON_NULL)
	@JsonFormat(pattern="dd/MM/yyyy")
	@NotNull(message="O campo publicação é de preenchimento obrigatorio")
	private LocalDate publicacao;
	
	@JsonInclude(Include.NON_NULL)
	@NotNull(message="O campo editoria é de preenchimento obrigatorio")
	private String editora;
	
	@JsonInclude(Include.NON_NULL)
	@NotNull(message="O campo resumo é de preenchimento obrigatorio")
	@Size(max=1500,message="O Resumo não pode conter mais de 1500 caracteres")
	private String resumo;
	
	@JsonInclude(Include.NON_EMPTY)
	@OneToMany(mappedBy="livro")
	private List<Comentario> comentarios;
	
	@ManyToOne
	@JoinColumn(name="autor_id")
	@JsonInclude(Include.NON_NULL)
	private Autor autor;
	
	
	public Livro() {
		super();
	}

	public Livro(String nome) {
		super();
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public LocalDate getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(LocalDate publicacao) {
		this.publicacao = publicacao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
	
	
	
}
