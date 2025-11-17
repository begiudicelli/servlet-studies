package com.example.servlets;

import com.example.dao.AlunoDAO;
import com.example.model.Aluno;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class AlunoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
	private AlunoDAO dao = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            // --- Exclusão ---
            if (request.getParameter("delete") != null) {
                int numero = Integer.parseInt(request.getParameter("delete"));
                dao.delete(numero);
                response.sendRedirect("alunos");
                return;
            }

            // --- Edição ---
            if (request.getParameter("edit") != null) {
                int numero = Integer.parseInt(request.getParameter("edit"));
                Aluno aluno = dao.findByNumero(numero);
                exibirFormularioEdicao(out, aluno);
                return;
            }

            // --- Pesquisa ---
            List<Aluno> alunos;
            if (request.getParameter("search") != null) {
                String nome = request.getParameter("search");
                alunos = dao.searchByName(nome);
            } else {
                alunos = dao.findAll();
            }

            exibir(out, alunos);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String action = request.getParameter("action");

            if ("update".equals(action)) {
                // Atualizar
                int numero = Integer.parseInt(request.getParameter("numero"));
                String nome = request.getParameter("nome");

                dao.update(new Aluno(numero, nome));
                response.sendRedirect("alunos");
                return;
            }

            // Caso contrário, inserir
            int numero = Integer.parseInt(request.getParameter("numero"));
            String nome = request.getParameter("nome");

            dao.insert(new Aluno(numero, nome));
            response.sendRedirect("alunos");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }


    // --------------------------- HTML PRINCIPAL ------------------------------
    private void exibir(PrintWriter out, List<Aluno> alunos) {

        out.println("<html><head><title>Alunos</title></head><body>");
        out.println("<h1>Cadastro de Alunos</h1>");

        // ---- Pesquisa ----
        out.println("<form method='get' action='alunos'>");
        out.println("Pesquisar: <input type='text' name='search'>");
        out.println("<input type='submit' value='Buscar'>");
        out.println("</form><hr>");

        // ---- Formulário de inserção ----
        out.println("<h2>Novo Aluno</h2>");
        out.println("<form method='post' action='alunos'>");
        out.println("<input type='hidden' name='action' value='insert'>");
        out.println("Número: <input type='number' name='numero'><br>");
        out.println("Nome: <input type='text' name='nome'><br><br>");
        out.println("<input type='submit' value='Salvar'>");
        out.println("</form><hr>");

        // ---- Lista ----
        out.println("<h2>Lista</h2>");
        out.println("<table border='1' cellpadding='5'>");
        out.println("<tr><th>Número</th><th>Nome</th><th>Ações</th></tr>");

        for (Aluno a : alunos) {
            out.println("<tr>");
            out.println("<td>" + a.getNumero() + "</td>");
            out.println("<td>" + a.getNome() + "</td>");
            out.println("<td>");
            out.println("<a href='alunos?edit=" + a.getNumero() + "'>Editar</a> | ");
            out.println("<a href='alunos?delete=" + a.getNumero() + "'>Excluir</a>");
            out.println("</td>");
            out.println("</tr>");
        }

        out.println("</table></body></html>");
    }

    // --------------------------- FORMULÁRIO DE EDIÇÃO ------------------------------
    private void exibirFormularioEdicao(PrintWriter out, Aluno aluno) {
        out.println("<html><head><title>Editar</title></head><body>");
        out.println("<h1>Editar Aluno</h1>");

        out.println("<form method='post' action='alunos'>");
        out.println("<input type='hidden' name='action' value='update'>");

        out.println("Número: <input type='number' name='numero' value='" +
                aluno.getNumero() + "' readonly><br>");

        out.println("Nome: <input type='text' name='nome' value='" +
                aluno.getNome() + "'><br><br>");

        out.println("<input type='submit' value='Salvar Alterações'>");
        out.println("</form>");

        out.println("<br><a href='alunos'>Voltar</a>");
        out.println("</body></html>");
    }
}
