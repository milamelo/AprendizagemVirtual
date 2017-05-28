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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import negocio.entidade.Aula;
import negocio.entidade.Usuario;

/**
 *
 * @author Camila
 */
public class DAOAula extends Conexao {

    public DAOAula() {

    }

    public List<Aula> listar(final Aula aula) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Aula> aulas = new ArrayList<>();

        try {
            sql = new StringBuilder();
            sql.append("     SELECT P.ID, P.TITULO, P.DATA_INCLUSAO, ");
            sql.append("            P.PONTUACAO, P.ULTIMA_ATUALIZACAO, ");
            sql.append("            P.CANCELADO, P.ID_CURSO, ");
            sql.append("            A.CONTEUDO, ");
            sql.append("            C.NOME, C.ID_USUARIO, ");
            sql.append("            U.NOME USUARIO_NOME, U.CPF ");
            sql.append("       FROM POSTAGEM P ");
            sql.append(" INNER JOIN AULA A ON A.ID_POSTAGEM = P.ID ");
            sql.append(" INNER JOIN CURSO C ON C.ID = P.ID_CURSO ");
            sql.append(" INNER JOIN USUARIO U ON U.ID = C.ID_USUARIO ");
            sql.append("      WHERE C.ID = ? ");
            sql.append("        AND P.CANCELADO = ? ");
            if (aula.getId() != null && aula.getId() != 0) {
                sql.append(" AND P.ID = ? ");
            }
            if (aula.getTitulo() != null
                    && !aula.getTitulo().trim().isEmpty()) {
                sql.append(" AND TRIM(P.TITULO) ILIKE ? ");
            }
            sql.append(" ORDER BY P.DATA_INCLUSAO ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, aula.getCurso().getId());
            preparedStatement.setBoolean(i++, Boolean.FALSE);
            if (aula.getId() != null && aula.getId() != 0) {
                preparedStatement.setInt(i++, aula.getId());
            }
            if (aula.getTitulo() != null
                    && !aula.getTitulo().trim().isEmpty()) {
                preparedStatement.setString(i++, "%" + aula.getTitulo().trim().toUpperCase() + "%");
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final Aula a = new Aula();
                a.setId(resultSet.getInt("ID"));
                a.setTitulo((resultSet.getString("TITULO")).trim());
                a.setDataInclusao(resultSet.getObject("DATA_INCLUSAO", LocalDateTime.class));
                a.setPontuacao(resultSet.getDouble("PONTUACAO"));
                a.setUltimaAtualizacao(resultSet.getObject("ULTIMA_ATUALIZACAO", LocalDateTime.class));
                a.setCancelado(resultSet.getBoolean("CANCELADO"));
                a.setConteudo(resultSet.getString("CONTEUDO"));
                a.getCurso().setId(resultSet.getInt("ID_CURSO"));
                a.getCurso().setNome(resultSet.getString("NOME"));
                a.getCurso().getUsuario().setId(resultSet.getInt("ID_USUARIO"));
                a.getCurso().getUsuario().setNome(resultSet.getString("USUARIO_NOME"));
                a.getCurso().getUsuario().setNome(resultSet.getString("CPF"));
                aulas.add(a);

            }

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOAula.listar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return aulas;
    }

    public boolean existeTitulo(final Aula aula) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean retorno = false;

        try {
            sql = new StringBuilder();
            sql.append("    SELECT * ");
            sql.append("      FROM POSTAGEM P ");
            sql.append("     WHERE TITULO = ? ");
            sql.append("       AND ID_CURSO = ? ");
            sql.append("       AND ID <> ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, aula.getTitulo().trim().toUpperCase());
            preparedStatement.setInt(i++, aula.getCurso().getId());
            preparedStatement.setInt(i++, aula.getId() == null ? 0 : aula.getId());

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOAula.existeTitulo \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }

    public int inserir(final Aula aula) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" INSERT INTO POSTAGEM ");
            sql.append("    (TITULO, DATA_INCLUSAO, PONTUACAO, CANCELADO, ULTIMA_ATUALIZACAO, ID_CURSO) ");
            sql.append(" VALUES(?, CURRENT_TIMESTAMP, ?, ?, CURRENT_TIMESTAMP, ?) ");

            conexao = abrirConexao();
            conexao.setAutoCommit(false);
            preparedStatement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            preparedStatement.setString(i++, aula.getTitulo().trim().toUpperCase());
            preparedStatement.setDouble(i++, aula.getPontuacao());
            preparedStatement.setBoolean(i++, Boolean.FALSE);
            preparedStatement.setInt(i++, aula.getCurso().getId());

            preparedStatement.executeUpdate();
            result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                aula.setId(result.getInt(1));
            }

            sql.setLength(0);
            sql.append(" INSERT INTO AULA ");
            sql.append("    (ID_POSTAGEM, CONTEUDO) ");
            sql.append(" VALUES(?, ?) ");

            preparedStatement = conexao.prepareStatement(sql.toString());

            i = 1;
            preparedStatement.setInt(i++, aula.getId());
            preparedStatement.setString(i++, aula.getConteudo());

            int retorno = preparedStatement.executeUpdate();

            conexao.commit();
            return retorno;
        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new SQLException("Erro: DAOAula.inserir \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

    public boolean teveAcesso(final Aula aula) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean retorno = false;

        try {
            sql = new StringBuilder();
            sql.append("     SELECT * ");
            sql.append("       FROM USUARIO_AULA UA ");
            sql.append("      WHERE UA.ID_AULA = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, aula.getId());

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOAula.teveAcesso \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }

    public boolean aulaJaAcessadaPeloUsuario(final Aula aula, final Usuario usuario) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean retorno = false;

        try {
            sql = new StringBuilder();
            sql.append("     SELECT * ");
            sql.append("       FROM USUARIO_AULA UA ");
            sql.append("      WHERE UA.ID_AULA = ? ");
            sql.append("        AND UA.ID_USUARIO = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, aula.getId());
            preparedStatement.setInt(i++, usuario.getId());

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOAula.aulaJaAcessadaPeloUsuario \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }

    public int inserirAulaUsuario(final Aula aula, final Usuario usuario) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" INSERT INTO USUARIO_AULA ");
            sql.append("    (ID_USUARIO, ID_AULA, DATA_ACESSO) ");
            sql.append(" VALUES(?, ?, CURRENT_TIMESTAMP) ");

            conexao = abrirConexao();
            conexao.setAutoCommit(false);
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, usuario.getId());
            preparedStatement.setInt(i++, aula.getId());

            preparedStatement.executeUpdate();

            sql.setLength(0);
            sql.append(" UPDATE USUARIO ");
            sql.append("    SET PONTUACAO_ACUMULADA = ?  ");
            sql.append("  WHERE ID = ? ");

            preparedStatement = conexao.prepareStatement(sql.toString());

            i = 1;
            preparedStatement.setDouble(i++, usuario.getPontuacaoAcumulada() + aula.calcularPontuacao());
            preparedStatement.setInt(i++, usuario.getId());

            int retorno = preparedStatement.executeUpdate();

            conexao.commit();
            return retorno;
        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new SQLException("Erro: DAOAula.inserirAulaUsuario \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

    public int alterar(final Aula aula) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" UPDATE POSTAGEM ");
            sql.append("    SET TITULO = ?, PONTUACAO = ?, ULTIMA_ATUALIZACAO = CURRENT_TIMESTAMP ");
            sql.append("  WHERE ID = ? ");

            conexao = abrirConexao();
            conexao.setAutoCommit(false);
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, aula.getTitulo().trim().toUpperCase());
            preparedStatement.setDouble(i++, aula.getPontuacao());
            preparedStatement.setInt(i++, aula.getId());

            preparedStatement.executeUpdate();

            sql.setLength(0);
            sql.append(" UPDATE AULA ");
            sql.append("    SET CONTEUDO = ? ");
            sql.append("  WHERE ID_POSTAGEM = ? ");

            preparedStatement = conexao.prepareStatement(sql.toString());

            i = 1;
            preparedStatement.setString(i++, aula.getConteudo());
            preparedStatement.setInt(i++, aula.getId());

            int retorno = preparedStatement.executeUpdate();

            conexao.commit();
            return retorno;
        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new SQLException("Erro: DAOAula.alterar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }
    
    public int excluir(final Aula aula) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" UPDATE POSTAGEM ");
            sql.append("    SET CANCELADO = ?, ULTIMA_ATUALIZACAO = CURRENT_TIMESTAMP ");
            sql.append("  WHERE ID = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setBoolean(i++, Boolean.TRUE);
            preparedStatement.setInt(i++, aula.getId());

            int retorno = preparedStatement.executeUpdate();

            return retorno;
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOAula.excluir \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }
}
