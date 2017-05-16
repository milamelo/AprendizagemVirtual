/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.dao;

import banco.Conexao;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import negocio.entidade.Medalha;
import negocio.entidade.Usuario;

/**
 *
 * @author Camila
 */
public class DAOUsuario extends Conexao {

    public DAOUsuario() {

    }

    public Usuario logar(final Usuario usuario) throws Exception {
        Usuario usu = null;
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sql = new StringBuilder();
            sql.append("    SELECT U.*, ");
            sql.append("           M.ID ID_MEDALHA, M.NOME NOME_MEDALHA, M.PONTUACAO_NECESSARIA ");
            sql.append("      FROM USUARIO U");
            sql.append(" LEFT JOIN MEDALHA M ON M.ID = U.ID_MEDALHA ");
            sql.append("     WHERE EMAIL = ? ");
            sql.append("       AND SENHA = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, usuario.getEmail().trim().toUpperCase());
            preparedStatement.setString(i++, usuario.getSenha());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                usu = createUsuario(resultSet);
            }

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOUsuario.logar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }
        return usu;
    }

    private Usuario createUsuario(final ResultSet rs) throws SQLException {
        final Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("ID"));
        usuario.setCpf(rs.getString("CPF"));
        usuario.setNome(rs.getString("NOME"));
        usuario.setEmail(rs.getString("EMAIL"));
        usuario.setSenha(rs.getString("SENHA"));
        usuario.setPontuacaoAcumulada(rs.getBigDecimal("PONTUACAO_ACUMULADA"));
        usuario.setPermissaoEspecial(rs.getBoolean("PERMISSAO_ESPECIAL"));
        usuario.setDataInclusao(rs.getTimestamp("DATA_INCLUSAO"));
        usuario.setUltimoAcesso(rs.getTimestamp("ULTIMO_ACESSO"));

        final Medalha medalha = new Medalha();
        if (rs.getInt("ID_MEDALHA") != 0) {
            medalha.setId(rs.getInt("ID_MEDALHA"));
            medalha.setNome(rs.getString("NOME_MEDALHA"));
            medalha.setPontuacaoNecessaria(rs.getInt("PONTUACAO_NECESSARIA"));
        }
        usuario.setMedalha(medalha);

        return usuario;
    }

    public boolean existeCpf(final String cpf) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean retorno = false;

        try {
            sql = new StringBuilder();
            sql.append("    SELECT * ");
            sql.append("      FROM USUARIO U ");
            sql.append("     WHERE CPF = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, cpf);

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOUsuario.existeCpf \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }

    public boolean existeEmail(final String email) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        boolean retorno = false;

        try {
            sql = new StringBuilder();
            sql.append("    SELECT * ");
            sql.append("      FROM USUARIO U ");
            sql.append("     WHERE EMAIL = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, email);

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOUsuario.existeEmail \n" + e.getMessage());
        } finally {
            fecharConexao(conexao);
        }
        return retorno;
    }

    public int inserir(final Usuario usuario) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;

        try {
            sql = new StringBuilder();
            sql.append(" INSERT INTO USUARIO ");
            sql.append("    (CPF, NOME, EMAIL, SENHA, PONTUACAO_ACUMULADA, DATA_INCLUSAO, ULTIMO_ACESSO) ");
            sql.append(" VALUES(?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, usuario.getCpf());
            preparedStatement.setString(i++, usuario.getNome().trim().toUpperCase());
            preparedStatement.setString(i++, usuario.getEmail().trim().toUpperCase());
            preparedStatement.setString(i++, usuario.getSenha());
            preparedStatement.setBigDecimal(i++, new BigDecimal(BigInteger.ZERO));

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOUsuario.existeEmail \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharConexao(conexao);
        }
    }

    public int atualizarUltimoAcesso(final Usuario usuario) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;

        try {
            sql = new StringBuilder();
            sql.append(" UPDATE USUARIO ");
            sql.append("    SET ULTIMO_ACESSO = CURRENT_TIMESTAMP ");
            sql.append("  WHERE ID = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, usuario.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOUsuario.atualizarUltimoAcesso \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharConexao(conexao);
        }
    }

    public int alterar(final Usuario usuario) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;

        try {
            sql = new StringBuilder();
            sql.append(" UPDATE USUARIO ");
            sql.append("    SET NOME = ?, SENHA = ? ");
            sql.append("  WHERE ID = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, usuario.getNome().trim().toUpperCase());
            preparedStatement.setString(i++, usuario.getSenha());
            preparedStatement.setInt(i++, usuario.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOUsuario.alterar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharConexao(conexao);
        }
    }

    public Usuario consultarUsuario(final Usuario usuario) throws Exception {
        Usuario usu = null;
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sql = new StringBuilder();
            sql.append("    SELECT U.*, ");
            sql.append("           M.ID ID_MEDALHA, M.NOME NOME_MEDALHA, M.PONTUACAO_NECESSARIA ");
            sql.append("      FROM USUARIO U");
            sql.append(" LEFT JOIN MEDALHA M ON M.ID = U.ID_MEDALHA ");
            sql.append("     WHERE U.ID = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, usuario.getId());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                usu = createUsuario(resultSet);
            }

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOUsuario.consultarUsuario \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }
        return usu;
    }
}
