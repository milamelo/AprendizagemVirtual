/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.dao;

import banco.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import negocio.entidade.Curso;
import negocio.entidade.Usuario;

/**
 *
 * @author Camila
 */
public class DAOCurso extends Conexao {

    public DAOCurso() {

    }

    public List<Curso> listar(final Curso curso) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Curso> cursos = new ArrayList<>();

        try {
            sql = new StringBuilder();
            sql.append("     SELECT C.ID, C.NOME, C.ID_USUARIO, ");
            sql.append("            C.DESCRICAO, C.CANCELADO, ");
            sql.append("            U.NOME USUARIO_NOME, ");
            sql.append("            UC.ID_USUARIO USUARIO_CURSO_ID_USUARIO ");
            sql.append("       FROM CURSO C ");
            sql.append(" INNER JOIN USUARIO U ON U.ID = C.ID_USUARIO ");
            sql.append("  LEFT JOIN USUARIO_CURSO UC ON UC.ID_CURSO = C.ID ");
            sql.append("  WHERE 1 = 1 ");
            if (curso != null && curso.getId() != null && curso.getId() != 0) {
                sql.append(" AND C.ID = ? ");
            }
            if (curso != null && curso.getNome() != null && !curso.getNome().trim().isEmpty()) {
                sql.append(" AND TRIM(C.NOME) ILIKE ? ");
            }
            sql.append(" ORDER BY C.CANCELADO, C.ID, U.ID ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            if (curso != null && curso.getId() != null && curso.getId() != 0) {
                preparedStatement.setInt(i++, curso.getId());
            }
            if (curso != null && curso.getNome() != null && !curso.getNome().trim().isEmpty()) {
                preparedStatement.setString(i++, "%" + curso.getNome().trim().toUpperCase() + "%");
            }

            resultSet = preparedStatement.executeQuery();
            int codigoCurso = 0;
            Curso cur;
            while (resultSet.next()) {
                if (codigoCurso != resultSet.getInt("ID")) {
                    cur = new Curso();
                    cur.setId(resultSet.getInt("ID"));
                    cur.setNome((resultSet.getString("NOME")).trim());
                    cur.setDescricao((resultSet.getString("DESCRICAO")).trim());
                    cur.setCancelado(resultSet.getBoolean("CANCELADO"));
                    cur.getUsuario().setId(resultSet.getInt("ID_USUARIO"));
                    cur.getUsuario().setNome(resultSet.getString("USUARIO_NOME"));
                    cursos.add(cur);

                    codigoCurso = cur.getId();
                }
                if (codigoCurso == resultSet.getInt("ID")
                        && resultSet.getInt("USUARIO_CURSO_ID_USUARIO") != 0) {
                    Usuario usu = new Usuario();
                    usu.setId(resultSet.getInt("USUARIO_CURSO_ID_USUARIO"));
                    cursos.get(cursos.size() - 1).getAlunos().add(usu);
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOCurso.listar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return cursos;
    }
    
    public boolean existeNome(final Curso curso) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean retorno = false;

        try {
            sql = new StringBuilder();
            sql.append("    SELECT * ");
            sql.append("      FROM CURSO C ");
            sql.append("     WHERE NOME = ? ");
            sql.append("       AND ID <> ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, curso.getNome().trim().toUpperCase());
            preparedStatement.setInt(i++, curso.getId() == null ? 0 : curso.getId());

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOCurso.existeNome \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }
    
    public int inserir(final Curso curso) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" INSERT INTO CURSO ");
            sql.append("    (NOME, DESCRICAO, CANCELADO, ID_USUARIO) ");
            sql.append(" VALUES(?, ?, ?, ?) ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            preparedStatement.setString(i++, curso.getNome().trim().toUpperCase());
            preparedStatement.setString(i++, curso.getDescricao().trim().toUpperCase());
            preparedStatement.setBoolean(i++, Boolean.FALSE);
            preparedStatement.setInt(i++, curso.getUsuario().getId());

            int retorno = preparedStatement.executeUpdate();
            result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                curso.setId(result.getInt(1));
            }

            return retorno;
        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new SQLException("Erro: DAOCurso.inserir \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }
    
    public int atualizar(final Curso curso) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" UPDATE CURSO ");
            sql.append("    SET NOME = ?, DESCRICAO = ? ");
            sql.append("  WHERE ID = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, curso.getNome().trim().toUpperCase());
            preparedStatement.setString(i++, curso.getDescricao().trim().toUpperCase());
            preparedStatement.setInt(i++, curso.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOCurso.atualizar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }
    
    public int seInscrever(final Curso curso, final Usuario usuario) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" INSERT INTO USUARIO_CURSO ");
            sql.append("    (ID_CURSO, ID_USUARIO, DATA_INGRESSO) ");
            sql.append(" VALUES(?, ?, CURRENT_DATE) ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, curso.getId());
            preparedStatement.setInt(i++, usuario.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOCurso.seInscrever \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }
    
    public int cancelar(final Curso curso) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" UPDATE CURSO ");
            sql.append("    SET CANCELADO = ? ");
            sql.append("  WHERE ID = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setBoolean(i++, Boolean.TRUE);
            preparedStatement.setInt(i++, curso.getId());
            
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOCurso.cancelar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }
}
