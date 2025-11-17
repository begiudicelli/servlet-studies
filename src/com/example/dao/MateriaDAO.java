package com.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.db.DBConnection;
import com.example.model.Materia;

public class MateriaDAO {

    public void insert(Materia m) throws SQLException {
        String sql = "INSERT INTO materia(titulo, periodo) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getTitulo());
            ps.setInt(2, m.getPeriodo());
            ps.executeUpdate();
        }
    }


    public void update(Materia m) throws SQLException {
        String sql = "UPDATE materia SET periodo = ? WHERE titulo = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, m.getPeriodo());
            ps.setString(2, m.getTitulo());
            ps.executeUpdate();
        }
    }


    public void delete(String titulo) throws SQLException {
        String sql = "DELETE FROM materia WHERE titulo = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, titulo);
            ps.executeUpdate();
        }
    }


    public Materia findByTitulo(String titulo) throws SQLException {
        String sql = "SELECT titulo, periodo FROM materia WHERE titulo = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, titulo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Materia(rs.getString("titulo"), rs.getInt("periodo"));
                }
            }
        }
        return null;
    }


    public List<Materia> findByPeriodo(int periodo) throws SQLException {
        String sql = "SELECT titulo, periodo FROM materia WHERE periodo = ?";

        List<Materia> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, periodo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Materia(rs.getString("titulo"), rs.getInt("periodo")));
                }
            }
        }

        return list;
    }


    public List<Materia> findAll() throws SQLException {
        String sql = "SELECT titulo, periodo FROM materia ORDER BY titulo";

        List<Materia> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Materia(rs.getString("titulo"), rs.getInt("periodo")));
            }
        }

        return list;
    }
}
