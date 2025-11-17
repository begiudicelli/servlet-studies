package com.example.model;

public class Materia {
	private String titulo;
	private Integer periodo;
	
	public Materia() {}
	
	public Materia(String titulo, Integer periodo) {
		this.titulo = titulo;
		this.periodo = periodo;
	}

	public String getTitulo() { return titulo; }
	public void setTitulo(String titulo) { this.titulo = titulo; }

	public Integer getPeriodo() { return periodo; }
	public void setPeriodo(Integer periodo) { this.periodo = periodo; }
	
}
