package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.MateriaDAO;
import com.example.model.Materia;

public class MateriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MateriaDAO dao = new MateriaDAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {

			// ---- Excluir ----
			if (request.getParameter("delete") != null) {
				String titulo = request.getParameter("delete");
				dao.delete(titulo);
				response.sendRedirect("materias");
				return;
			}

			// ---- Editar ----
			if (request.getParameter("edit") != null) {
				String titulo = request.getParameter("edit");
				Materia materia = dao.findByTitulo(titulo);
				exibirFormularioEdicao(out, materia);
				return;
			}

			// ---- Pesquisa ----
			List<Materia> materias;

			String search = request.getParameter("search");
			if (search != null && !search.trim().isEmpty()) {
				try {
					// Se for número → busca por período
					int periodo = Integer.parseInt(search);
					materias = dao.findByPeriodo(periodo);
				} catch (NumberFormatException e) {
					// Se não for número → busca por título (1 resultado)
					Materia m = dao.findByTitulo(search);
					materias = new java.util.ArrayList<>();
					if (m != null)
						materias.add(m);
				}
			} else {
				// Search vazio → lista tudo
				materias = dao.findAll();
			}

			exibir(out, materias);

		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String action = request.getParameter("action");

			// ---- Atualizar ----
			if ("update".equals(action)) {
				String titulo = request.getParameter("titulo");
				int periodo = Integer.parseInt(request.getParameter("periodo"));

				dao.update(new Materia(titulo, periodo));
				response.sendRedirect("materias");
				return;
			}

			// ---- Inserir ----
			String titulo = request.getParameter("titulo");
			int periodo = Integer.parseInt(request.getParameter("periodo"));

			dao.insert(new Materia(titulo, periodo));
			response.sendRedirect("materias");

		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	// ------------------ HTML PRINCIPAL ------------------
	private void exibir(PrintWriter out, List<Materia> materias) {

		out.println("<html><head><title>Materias</title></head><body>");
		out.println("<h1>Cadastro de Materias</h1>");

		// Pesquisa
		out.println("<form method='get' action='materias'>");
		out.println("Pesquisar (título ou período): <input type='text' name='search'>");
		out.println("<input type='submit' value='Buscar'>");
		out.println("</form><hr>");

		// Formulário de inserção
		out.println("<h2>Nova Materia</h2>");
		out.println("<form method='post' action='materias'>");
		out.println("<input type='hidden' name='action' value='insert'>");
		out.println("Título: <input type='text' name='titulo'><br>");
		out.println("Período: <input type='number' name='periodo'><br><br>");
		out.println("<input type='submit' value='Salvar'>");
		out.println("</form><hr>");

		// Lista
		out.println("<h2>Lista de Materias</h2>");
		out.println("<table border='1' cellpadding='5'>");
		out.println("<tr><th>Título</th><th>Período</th><th>Ações</th></tr>");

		for (Materia m : materias) {
			out.println("<tr>");
			out.println("<td>" + m.getTitulo() + "</td>");
			out.println("<td>" + m.getPeriodo() + "</td>");
			out.println("<td>");
			out.println("<a href='materias?edit=" + m.getTitulo() + "'>Editar</a> | ");
			out.println("<a href='materias?delete=" + m.getTitulo() + "'>Excluir</a>");
			out.println("</td>");
			out.println("</tr>");
		}

		out.println("</table>");
		out.println("</body></html>");
	}

	// ------------------ FORMULÁRIO DE EDIÇÃO ------------------
	private void exibirFormularioEdicao(PrintWriter out, Materia materia) {

		out.println("<html><head><title>Editar</title></head><body>");
		out.println("<h1>Editar Materia</h1>");

		out.println("<form method='post' action='materias'>");
		out.println("<input type='hidden' name='action' value='update'>");

		out.println("Título: <input type='text' name='titulo' value='" + materia.getTitulo() + "'><br>");

		out.println("Período: <input type='number' name='periodo' value='" + materia.getPeriodo() + "'><br><br>");

		out.println("<input type='submit' value='Salvar Alterações'>");
		out.println("</form>");

		out.println("<br><a href='materias'>Voltar</a>");
		out.println("</body></html>");
	}
}
