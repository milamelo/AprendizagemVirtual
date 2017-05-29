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
import java.sql.SQLType;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import negocio.entidade.Atividade;
import negocio.entidade.Usuario;

/**
 *
 * @author Camila
 */
public class DAOAtividade extends Conexao {

    public DAOAtividade() {

    }

    public List<Atividade> listar(final Atividade atividade) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Atividade> atividades = new ArrayList<>();

        try {
            sql = new StringBuilder();
            sql.append("     SELECT P.ID, P.TITULO, P.DATA_INCLUSAO, ");
            sql.append("            P.PONTUACAO, P.ULTIMA_ATUALIZACAO, ");
            sql.append("            P.CANCELADO, P.ID_CURSO, ");
            sql.append("            A.ID_TIPO_ATIVIDADE, A.DATA_ENTREGA, ");
            sql.append("            A.DESCRICAO ATIVIDADE_DESCRICAO, ");
            sql.append("            TA.DESCRICAO TIPO_ATIVIDADE_DESCRICAO, ");
            sql.append("            TA.MULTIPLICIDADE, ");
            sql.append("            C.NOME, C.ID_USUARIO, ");
            sql.append("            U.NOME USUARIO_NOME, U.CPF ");
            sql.append("       FROM POSTAGEM P ");
            sql.append(" INNER JOIN ATIVIDADE A ON A.ID_POSTAGEM = P.ID ");
            sql.append(" INNER JOIN CURSO C ON C.ID = P.ID_CURSO ");
            sql.append(" INNER JOIN USUARIO U ON U.ID = C.ID_USUARIO ");
            sql.append(" INNER JOIN TIPO_ATIVIDADE TA ON TA.ID = A.ID_TIPO_ATIVIDADE ");
            sql.append("      WHERE C.ID = ? ");
            sql.append("        AND P.CANCELADO = ? ");
            if (atividade.getId() != null && atividade.getId() != 0) {
                sql.append(" AND P.ID = ? ");
            }
            if (atividade.getTitulo() != null
                    && !atividade.getTitulo().trim().isEmpty()) {
                sql.append(" AND TRIM(P.TITULO) ILIKE ? ");
            }
            sql.append(" ORDER BY P.DATA_INCLUSAO ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, atividade.getCurso().getId());
            preparedStatement.setBoolean(i++, Boolean.FALSE);
            if (atividade.getId() != null && atividade.getId() != 0) {
                preparedStatement.setInt(i++, atividade.getId());
            }
            if (atividade.getTitulo() != null
                    && !atividade.getTitulo().trim().isEmpty()) {
                preparedStatement.setString(i++, "%" + atividade.getTitulo().trim().toUpperCase() + "%");
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final Atividade a = new Atividade();
                a.setId(resultSet.getInt("ID"));
                a.setTitulo((resultSet.getString("TITULO")).trim());
                a.setDataInclusao(resultSet.getObject("DATA_INCLUSAO", LocalDateTime.class));
                a.setPontuacao(resultSet.getDouble("PONTUACAO"));
                a.setUltimaAtualizacao(resultSet.getObject("ULTIMA_ATUALIZACAO", LocalDateTime.class));
                a.setCancelado(resultSet.getBoolean("CANCELADO"));
                a.setDataEntrega(resultSet.getObject("DATA_ENTREGA", LocalDate.class));
                a.setDescricao(resultSet.getString("ATIVIDADE_DESCRICAO"));
                a.getTipoAtividade().setId(resultSet.getInt("ID_TIPO_ATIVIDADE"));
                a.getTipoAtividade().setDescricao(resultSet.getString("TIPO_ATIVIDADE_DESCRICAO"));
                a.getTipoAtividade().setMultiplicidade(resultSet.getInt("MULTIPLICIDADE"));
                a.getCurso().setId(resultSet.getInt("ID_CURSO"));
                a.getCurso().setNome(resultSet.getString("NOME"));
                a.getCurso().getUsuario().setId(resultSet.getInt("ID_USUARIO"));
                a.getCurso().getUsuario().setNome(resultSet.getString("USUARIO_NOME"));
                a.getCurso().getUsuario().setNome(resultSet.getString("CPF"));
                atividades.add(a);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOAtividade.listar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return atividades;
    }

    public boolean atividadeJaAcessadaPeloUsuario(final Atividade atividade, final Usuario usuario) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean retorno = false;

        try {
            sql = new StringBuilder();
            sql.append("     SELECT * ");
            sql.append("       FROM USUARIO_ATIVIDADE UA ");
            sql.append("      WHERE UA.ID_ATIVIDADE = ? ");
            sql.append("        AND UA.ID_USUARIO = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, atividade.getId());
            preparedStatement.setInt(i++, usuario.getId());

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOAtividade.atividadeJaAcessadaPeloUsuario \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }
    
    public int inserirAtividadeUsuario(final Atividade atividade, final Usuario usuario) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" INSERT INTO USUARIO_ATIVIDADE ");
            sql.append("    (ID_USUARIO, ID_ATIVIDADE, DATA_ACESSO) ");
            sql.append(" VALUES(?, ?, CURRENT_TIMESTAMP) ");

            conexao = abrirConexao();
            conexao.setAutoCommit(false);
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, usuario.getId());
            preparedStatement.setInt(i++, atividade.getId());

            preparedStatement.executeUpdate();

            sql.setLength(0);
            sql.append(" UPDATE USUARIO ");
            sql.append("    SET PONTUACAO_ACUMULADA = ?  ");
            sql.append("  WHERE ID = ? ");

            preparedStatement = conexao.prepareStatement(sql.toString());

            i = 1;
            preparedStatement.setDouble(i++, usuario.getPontuacaoAcumulada() + atividade.calcularPontuacao());
            preparedStatement.setInt(i++, usuario.getId());

            int retorno = preparedStatement.executeUpdate();

            conexao.commit();
            return retorno;
        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new SQLException("Erro: DAOAtividade.inserirAtividadeUsuario \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }
    
    public boolean teveAcesso(final Atividade atividade) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean retorno = false;

        try {
            sql = new StringBuilder();
            sql.append("     SELECT * ");
            sql.append("       FROM USUARIO_ATIVIDADE UA ");
            sql.append("      WHERE UA.ID_ATIVIDADE = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, atividade.getId());

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOAtividade.teveAcesso \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }
    
    public int excluir(final Atividade atividade) throws Exception {
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
            preparedStatement.setInt(i++, atividade.getId());

            int retorno = preparedStatement.executeUpdate();

            return retorno;
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOAtividade.excluir \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }
    
    public boolean existeTitulo(final Atividade atividade) throws Exception {
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
            preparedStatement.setString(i++, atividade.getTitulo().trim().toUpperCase());
            preparedStatement.setInt(i++, atividade.getCurso().getId());
            preparedStatement.setInt(i++, atividade.getId() == null ? 0 : atividade.getId());

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOAtividade.existeTitulo \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }
    
    public int inserir(final Atividade atividade) throws Exception {
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
            preparedStatement.setString(i++, atividade.getTitulo().trim().toUpperCase());
            preparedStatement.setDouble(i++, atividade.getPontuacao());
            preparedStatement.setBoolean(i++, Boolean.FALSE);
            preparedStatement.setInt(i++, atividade.getCurso().getId());

            preparedStatement.executeUpdate();
            result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                atividade.setId(result.getInt(1));
            }

            sql.setLength(0);
            sql.append(" INSERT INTO ATIVIDADE ");
            sql.append("    (ID_POSTAGEM, ID_TIPO_ATIVIDADE, DESCRICAO, DATA_ENTREGA) ");
            sql.append(" VALUES(?, ?, ?, ?) ");

            preparedStatement = conexao.prepareStatement(sql.toString());

            i = 1;
            preparedStatement.setInt(i++, atividade.getId());
            preparedStatement.setInt(i++, atividade.getTipoAtividade().getId());
            preparedStatement.setString(i++, atividade.getDescricao());
            preparedStatement.setObject(i++, atividade.getDataEntrega());

            int retorno = preparedStatement.executeUpdate();

            conexao.commit();
            return retorno;
        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new SQLException("Erro: DAOAtividade.inserir \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }
    
    public int alterar(final Atividade atividade) throws Exception {
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
            preparedStatement.setString(i++, atividade.getTitulo().trim().toUpperCase());
            preparedStatement.setDouble(i++, atividade.getPontuacao());
            preparedStatement.setInt(i++, atividade.getId());

            preparedStatement.executeUpdate();

            sql.setLength(0);
            sql.append(" UPDATE ATIVIDADE ");
            sql.append("    SET ID_TIPO_ATIVIDADE = ?, DESCRICAO = ?, DATA_ENTREGA = ? ");
            sql.append("  WHERE ID_POSTAGEM = ? ");

            preparedStatement = conexao.prepareStatement(sql.toString());

            i = 1;
            preparedStatement.setInt(i++, atividade.getTipoAtividade().getId());
            preparedStatement.setString(i++, atividade.getDescricao());
            preparedStatement.setObject(i++, atividade.getDataEntrega());
            preparedStatement.setInt(i++, atividade.getId());

            int retorno = preparedStatement.executeUpdate();

            conexao.commit();
            return retorno;
        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new SQLException("Erro: DAOAtividade.alterar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }
}
