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
import negocio.entidade.Grupo;
import negocio.entidade.Usuario;
import negocio.interfaces.IGrupo;

/**
 *
 * @author Camila
 */
public class DAOGrupo extends Conexao implements IGrupo {

    public DAOGrupo() {

    }

    @Override
    public List<Grupo> listar(final Grupo grupo) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Grupo> grupos = new ArrayList<>();

        try {
            sql = new StringBuilder();
            sql.append("     SELECT G.ID, G.NOME, G.ID_USUARIO, ");
            sql.append("            U_CRIADOR.NOME USUARIO_CRIADOR_NOME, ");
            sql.append("            UG.ID_USUARIO USUARIO_GRUPO_ID_USUARIO, ");
            sql.append("            UG.ID_USUARIO USUARIO_GRUPO_ID_USUARIO, ");
            sql.append("            U.NOME USUARIO_GRUPO_NOME ");
            sql.append("       FROM GRUPO G ");
            sql.append(" INNER JOIN USUARIO U_CRIADOR ON U_CRIADOR.ID = G.ID_USUARIO ");
            sql.append(" INNER JOIN USUARIO_GRUPO UG ON UG.ID_GRUPO = G.ID ");
            sql.append(" INNER JOIN USUARIO U ON U.ID = UG.ID_USUARIO ");
            sql.append("  WHERE 1 = 1 ");
            if (grupo != null && grupo.getNome() != null && !grupo.getNome().trim().isEmpty()) {
                sql.append(" AND TRIM(G.NOME) ILIKE ? ");
            }
            sql.append(" ORDER BY G.NOME, U.ID ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            if (grupo != null && grupo.getNome() != null && !grupo.getNome().trim().isEmpty()) {
                preparedStatement.setString(i++, "%" + grupo.getNome().trim().toUpperCase() + "%");
            }

            resultSet = preparedStatement.executeQuery();
            int codigoGrupo = 0;
            Grupo gru;
            while (resultSet.next()) {
                if (codigoGrupo != resultSet.getInt("ID")) {
                    gru = new Grupo();
                    gru.setId(resultSet.getInt("ID"));
                    gru.setNome((resultSet.getString("NOME")).trim());
                    gru.getUsuario().setId(resultSet.getInt("ID_USUARIO"));
                    gru.getUsuario().setNome(resultSet.getString("USUARIO_CRIADOR_NOME"));
                    grupos.add(gru);

                    codigoGrupo = gru.getId();
                }
                Usuario usu = new Usuario();
                usu.setId(resultSet.getInt("USUARIO_GRUPO_ID_USUARIO"));
                usu.setNome(resultSet.getString("USUARIO_GRUPO_NOME"));
                grupos.get(grupos.size() - 1).getUsuarios().add(usu);
            }

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOGrupo.listar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return grupos;
    }

    @Override
    public boolean existeNome(final Grupo grupo) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean retorno = false;

        try {
            sql = new StringBuilder();
            sql.append("    SELECT * ");
            sql.append("      FROM GRUPO G ");
            sql.append("     WHERE NOME = ? ");
            sql.append("       AND ID <> ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, grupo.getNome().trim().toUpperCase());
            preparedStatement.setInt(i++, grupo.getId() == null ? 0 : grupo.getId());

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOGrupo.existeNome \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }

    @Override
    public int inserir(final Grupo grupo) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" INSERT INTO GRUPO ");
            sql.append("    (NOME, ID_USUARIO) ");
            sql.append(" VALUES(?, ?) ");

            conexao = abrirConexao();
            conexao.setAutoCommit(false);
            preparedStatement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            preparedStatement.setString(i++, grupo.getNome().trim().toUpperCase());
            preparedStatement.setInt(i++, grupo.getUsuario().getId());

            preparedStatement.executeUpdate();
            result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                grupo.setId(result.getInt(1));
            }

            sql.setLength(0);
            sql.append(" INSERT INTO USUARIO_GRUPO ");
            sql.append("    (ID_GRUPO, ID_USUARIO, DATA_INGRESSO) ");
            sql.append(" VALUES(?, ?, CURRENT_DATE) ");

            preparedStatement = conexao.prepareStatement(sql.toString());

            i = 1;
            preparedStatement.setInt(i++, grupo.getId());
            preparedStatement.setInt(i++, grupo.getUsuario().getId());

            int retorno = preparedStatement.executeUpdate();

            conexao.commit();
            return retorno;
        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new SQLException("Erro: DAOGrupo.inserir \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

    @Override
    public int entrar(final Grupo grupo, final Usuario usuario) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" INSERT INTO USUARIO_GRUPO ");
            sql.append("    (ID_GRUPO, ID_USUARIO, DATA_INGRESSO) ");
            sql.append(" VALUES(?, ?, CURRENT_DATE) ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, grupo.getId());
            preparedStatement.setInt(i++, usuario.getId());

            int retorno = preparedStatement.executeUpdate();
            return retorno;
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOGrupo.entrar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

    @Override
    public int sair(final Grupo grupo, final Usuario usuario) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" DELETE FROM GRUPO_USUARIO_MENSAGEM ");
            sql.append("  WHERE ID_GRUPO = ? ");
            sql.append("    AND ID_USUARIO = ? ");

            conexao = abrirConexao();
            conexao.setAutoCommit(false);
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, grupo.getId());
            preparedStatement.setInt(i++, usuario.getId());

            preparedStatement.executeUpdate();

            sql.setLength(0);
            sql.append(" DELETE FROM USUARIO_GRUPO ");
            sql.append("  WHERE ID_GRUPO = ? ");
            sql.append("    AND ID_USUARIO = ? ");

            preparedStatement = conexao.prepareStatement(sql.toString());
            i = 1;
            preparedStatement.setInt(i++, grupo.getId());
            preparedStatement.setInt(i++, usuario.getId());

            int retorno = preparedStatement.executeUpdate();

            conexao.commit();
            return retorno;
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOGrupo.sair \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

    @Override
    public int atualizar(final Grupo grupo) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" UPDATE GRUPO ");
            sql.append("    SET NOME = ? ");
            sql.append("  WHERE ID = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, grupo.getNome().trim().toUpperCase());
            preparedStatement.setInt(i++, grupo.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOGrupo.atualizar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

    @Override
    public int remover(final Grupo grupo) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" DELETE FROM USUARIO_GRUPO ");
            sql.append("  WHERE ID_GRUPO = ? ");

            conexao = abrirConexao();
            conexao.setAutoCommit(false);
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, grupo.getId());

            preparedStatement.executeUpdate();

            sql.setLength(0);
            sql.append(" DELETE FROM GRUPO ");
            sql.append("  WHERE ID = ? ");

            preparedStatement = conexao.prepareStatement(sql.toString());

            i = 1;
            preparedStatement.setInt(i++, grupo.getId());

            int retorno = preparedStatement.executeUpdate();

            conexao.commit();
            return retorno;
        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new SQLException("Erro: DAOGrupo.remover \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }
}
