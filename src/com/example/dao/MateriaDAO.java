package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.db.DBConnection;
import com.example.model.Materia;

public class MateriaDAO {
	
    public void insert(Materia materia) throws SQLException {
        String sql = "INSERT INTO materia(titulo, periodo) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, materia.getTitulo());
            ps.setInt(2, materia.getPeriodo());
            ps.executeUpdate();
        }
    }
	
    public void update(Materia materia) throws SQLException {
        String sql = "UPDATE materia SET periodo = ? WHERE titulo = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, materia.getPeriodo());
            ps.setString(2, materia.getTitulo());
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
    
	public List<Materia> findAll() throws SQLException {
        String sql = "SELECT titulo, periodo FROM materia ORDER BY periodo";
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
	
	public List<Materia> findByPeriodo(int periodo) throws SQLException {
	    String sql = "SELECT titulo, periodo FROM materia WHERE periodo = ?";
	    List<Materia> list = new ArrayList<>();

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, periodo);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                list.add(new Materia(
	                        rs.getString("titulo"),
	                        rs.getInt("periodo")
	                ));
	            }
	        }
	    }
	    return list;
	}
	
    
    public List<Materia> findByTitulo(String titulo) throws SQLException {
        String sql = "SELECT titulo, periodo FROM materia WHERE titulo LIKE ?";
        List<Materia> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + titulo + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Materia(rs.getString("titulo"), rs.getInt("periodo")));
                }
            }
        }
        return list;
    }
}


