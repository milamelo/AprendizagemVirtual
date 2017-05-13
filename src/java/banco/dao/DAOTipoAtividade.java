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
import negocio.entidade.TipoAtividade;

/**
 *
 * @author Camila
 */
public class DAOTipoAtividade extends Conexao {
    
    public DAOTipoAtividade(){
        
    }
    
    public List<TipoAtividade> listar(final TipoAtividade tipoAtividade) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<TipoAtividade> tipoAtividades = new ArrayList<>();

        try {
            sql = new StringBuilder();
            sql.append(" SELECT * ");
            sql.append("   FROM TIPO_ATIVIDADE TA ");
            sql.append("  WHERE 1 = 1 ");
            if (tipoAtividade != null && tipoAtividade.getDescricao() != null && !tipoAtividade.getDescricao().trim().isEmpty()) {
                sql.append(" AND TRIM(DESCRICAO) ILIKE ? ");
            }
            sql.append(" ORDER BY DESCRICAO ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            if (tipoAtividade != null && tipoAtividade.getDescricao() != null && !tipoAtividade.getDescricao().trim().isEmpty()) {
                preparedStatement.setString(i++, "%" + tipoAtividade.getDescricao().trim().toUpperCase() + "%");
            }
            
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TipoAtividade tipo = new TipoAtividade();
                tipo.setId(resultSet.getInt("ID"));
                tipo.setDescricao((resultSet.getString("DESCRICAO")).trim());
                tipoAtividades.add(tipo);
            }

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOTipoAtividade.listar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return tipoAtividades;
    }

    public int inserir(final TipoAtividade tipoAtividade) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" INSERT INTO TIPO_ATIVIDADE ");
            sql.append("    (DESCRICAO) ");
            sql.append(" VALUES(?) ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            preparedStatement.setString(i++, tipoAtividade.getDescricao().trim().toUpperCase());
            
            int retorno = preparedStatement.executeUpdate();
            result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                tipoAtividade.setId(result.getInt(1));
            }

            return retorno;
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOTipoAtividade.inserir \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

    public int atualizar(final TipoAtividade tipoAtividade) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" UPDATE TIPO_ATIVIDADE ");
            sql.append("    SET DESCRICAO = ? ");
            sql.append(" WHERE ID = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, tipoAtividade.getDescricao().trim().toUpperCase());
            preparedStatement.setInt(i++, tipoAtividade.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOTipoAtividade.atualizar \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

    public int remover(final TipoAtividade tipoAtividade) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" DELETE FROM TIPO_ATIVIDADE ");
            sql.append(" WHERE ID = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, tipoAtividade.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOTipoAtividade.remover \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

    public boolean existeDescricao(final TipoAtividade tipoAtividade) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean retorno = false;

        try {
            sql = new StringBuilder();
            sql.append("    SELECT * ");
            sql.append("      FROM TIPO_ATIVIDADE TA ");
            sql.append("     WHERE DESCRICAO = ? ");
            sql.append("       AND ID <> ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setString(i++, tipoAtividade.getDescricao().trim().toUpperCase());
            preparedStatement.setInt(i++, tipoAtividade.getId() == null ? 0 : tipoAtividade.getId());

            resultSet = preparedStatement.executeQuery();
            retorno = resultSet.next();

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOTipoAtividade.existeNome \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }

    public int countUsuariosComTipoAtividade(final TipoAtividade tipoAtividade) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int retorno = 0;

        try {
            sql = new StringBuilder();
            sql.append("     SELECT COUNT(*) TOTAL ");
            sql.append("       FROM TIPO_ATIVIDADE TA ");
            sql.append(" INNER JOIN ATIVIDADE A ON A.ID_TIPO_ATIVIDADE = TA.ID ");
            sql.append("      WHERE TA.ID = ? ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, tipoAtividade.getId());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                retorno = resultSet.getInt("TOTAL");
            }

        } catch (SQLException e) {
            throw new SQLException("Erro: DAOTipoAtividade.countAtividadesComTipoAtividade \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return retorno;
    }
    
}
