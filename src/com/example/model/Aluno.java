package com.example.model;

public class Aluno {
    private Integer numero;
    private String nome;

    public Aluno() {}

    public Aluno(int numero, String nome) {
        this.numero = numero;
        this.nome = nome;
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
