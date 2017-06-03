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
import negocio.entidade.Medalha;
import negocio.interfaces.IMedalha;

/**
 *
 * @author Camila
 */
public class DAOMedalha extends Conexao implements IMedalha {

    public DAOMedalha() {

    }

    @Override
    public List<Medalha> listar(final Medalha medalha) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Medalha> medalhas = new ArrayList<>();

        try {
            sql = new StringBuilder();
            sql.append(" SELECT * ");
            sql.append("   FROM MEDALHA M ");
            sql.append("  WHERE 1 = 1 ");
            if (medalha != null && medalha.getNome() != null && !medalha.getNome().trim().isEmpty()) {
                sql.append(" AND TRIM(NOME) ILIKE ? ");
            }
            if (medalha != null && medalha.getPontuacaoNecessaria() != null && medalha.getPontuacaoNecessaria() > 0) {
                sql.append(" AND PONTUACAO_NECESSARIA = ? ");
            }
            sql.append(" ORDER BY PONTUACAO_NECESSARIA ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            if (medalha != null && medalha.getNome() != null && !medalha.getNome().trim().isEmpty()) {
                preparedStatement.setString(i++, "%" + medalha.getNome().trim().toUpperCase() + "%");
            }
            if (medalha != null && medalha.getPontuacaoNecessaria() != null && medalha.getPontuacaoNecessaria() > 0) {
                preparedStatement.setDouble(i++, medalha.getPontuacaoNecessaria());
            }

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Medalha med = new Medalha();
                med.setId(resultSet.getInt("ID"));
                med.setNome((resultSet.getString("NOME")).trim());
                med.setPontuacaoNecessaria(resultSet.getDouble("PONTUACAO_NECESSARIA"));
                medalhas.add(med);
            }

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOMedalha.listar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return medalhas;
    }

    @Override
    public int inserir(final Medalha medalha) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" INSERT INTO MEDALHA ");
            sql.append("    (NOME, PONTUACAO_NECESSARIA) ");
            sql.append(" VALUES(?, ?) ");

            conexao = abrirConexao();
            conexao.setAutoCommit(false);
            preparedStatement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            preparedStatement.setString(i++, medalha.getNome().trim().toUpperCase());
            preparedStatement.setDouble(i++, medalha.getPontuacaoNecessaria());

            int retorno = preparedStatement.executeUpdate();
            result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                medalha.setId(result.getInt(1));
            }

            sql.setLength(0);
            sql.append("  UPDATE USUARIO U ");
            sql.append("     SET ID_MEDALHA = (  SELECT ID ");
            sql.append("		 	   FROM MEDALHA ");
            sql.append("			  WHERE PONTUACAO_NECESSARIA <= U.PONTUACAO_ACUMULADA ");
            sql.append("		       ORDER BY PONTUACAO_NECESSARIA DESC ");
            sql.append("			  LIMIT 1) ");

            preparedStatement = conexao.prepareStatement(sql.toString());
            preparedStatement.executeUpdate();

            conexao.commit();
            return retorno;
        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new SQLException("Erro: DAOMedalha.inserir \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

    @Override
    public int atualizar(final Medalha medalha) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" UPDATE MEDALHA ");
            sql.append("    SET NOME = ?, PONTUACAO_NECESSARIA = ? ");
            sql.append(" WHERE ID = ? ");

            conexao = abrirConexao();
            conexao.setAutoCommit(false);
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, medalha.getNome().trim().toUpperCase());
            preparedStatement.setDouble(i++, medalha.getPontuacaoNecessaria());
            preparedStatement.setInt(i++, medalha.getId());

            int retorno = preparedStatement.executeUpdate();

            sql.setLength(0);
            sql.append("  UPDATE USUARIO U ");
            sql.append("     SET ID_MEDALHA = (  SELECT ID ");
            sql.append("		 	   FROM MEDALHA ");
            sql.append("			  WHERE PONTUACAO_NECESSARIA <= U.PONTUACAO_ACUMULADA ");
            sql.append("		       ORDER BY PONTUACAO_NECESSARIA DESC ");
            sql.append("			  LIMIT 1) ");

            preparedStatement = conexao.prepareStatement(sql.toString());
            preparedStatement.executeUpdate();

            conexao.commit();
            return retorno;
        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new SQLException("Erro: DAOMedalha.atualizar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

    @Override
    public int remover(final Medalha medalha) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" DELETE FROM MEDALHA ");
            sql.append(" WHERE ID = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, medalha.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOMedalha.remover \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

    @Override
    public boolean existePontuacao(final Medalha medalha) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean retorno = false;

        try {
            sql = new StringBuilder();
            sql.append("    SELECT * ");
            sql.append("      FROM MEDALHA M ");
            sql.append("     WHERE PONTUACAO_NECESSARIA = ? ");
            sql.append("       AND ID <> ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setDouble(i++, medalha.getPontuacaoNecessaria());
            preparedStatement.setInt(i++, medalha.getId() == null ? 0 : medalha.getId());

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOMedalha.existePontuacao \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }

    @Override
    public boolean existeNome(final Medalha medalha) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean retorno = false;

        try {
            sql = new StringBuilder();
            sql.append("    SELECT * ");
            sql.append("      FROM MEDALHA M ");
            sql.append("     WHERE NOME = ? ");
            sql.append("       AND ID <> ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, medalha.getNome().trim().toUpperCase());
            preparedStatement.setInt(i++, medalha.getId() == null ? 0 : medalha.getId());

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOMedalha.existeNome \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }

    @Override
    public int countUsuariosComMedalha(final Medalha medalha) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int retorno = 0;

        try {
            sql = new StringBuilder();
            sql.append("     SELECT COUNT(*) TOTAL ");
            sql.append("       FROM MEDALHA M ");
            sql.append(" INNER JOIN USUARIO U ON U.ID_MEDALHA = M.ID ");
            sql.append("      WHERE M.ID = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, medalha.getId());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                retorno = resultSet.getInt("TOTAL");
            }

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOMedalha.countUsuariosComMedalha \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }
}
