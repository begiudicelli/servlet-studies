package com.example.dao;

import com.example.db.DBConnection;
import com.example.model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public List<Aluno> findAll() throws SQLException {
        String sql = "SELECT numero, nome FROM aluno ORDER BY numero";
        List<Aluno> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Aluno(rs.getInt("numero"), rs.getString("nome")));
            }
        }
        return list;
    }

    public Aluno findByNumero(int numero) throws SQLException {
        String sql = "SELECT numero, nome FROM aluno WHERE numero = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Aluno(rs.getInt("numero"), rs.getString("nome"));
            }
        }
        return null;
    }

    public void insert(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO aluno(numero, nome) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, aluno.getNumero());
            ps.setString(2, aluno.getNome());
            ps.executeUpdate();
        }
    }

    public void update(Aluno aluno) throws SQLException {
        String sql = "UPDATE aluno SET nome = ? WHERE numero = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, aluno.getNome());
            ps.setInt(2, aluno.getNumero());
            ps.executeUpdate();
        }
    }

    public void delete(int numero) throws SQLException {
        String sql = "DELETE FROM aluno WHERE numero = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numero);
            ps.executeUpdate();
        }
    }

    public List<Aluno> searchByName(String nome) throws SQLException {
        String sql = "SELECT numero, nome FROM aluno WHERE nome LIKE ?";
        List<Aluno> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Aluno(rs.getInt("numero"), rs.getString("nome")));
            }
        }
        return list;
    }
}
