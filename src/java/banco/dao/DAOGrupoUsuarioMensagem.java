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
import negocio.entidade.GrupoUsuarioMensagem;

/**
 *
 * @author Camila
 */
public class DAOGrupoUsuarioMensagem extends Conexao {

    public DAOGrupoUsuarioMensagem() {

    }

    public void consultarMensagens(final GrupoUsuarioMensagem grupoUsuarioMensagem) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sql = new StringBuilder();
            sql.append("     SELECT GUM.ID, GUM.DATA_INCLUSAO, GUM.MENSAGEM, ");
            sql.append("            U.ID USUARIO_ID, U.NOME USUARIO_NOME ");
            sql.append("       FROM GRUPO_USUARIO_MENSAGEM GUM ");
            sql.append(" INNER JOIN USUARIO_GRUPO UG ON UG.ID_GRUPO = GUM.ID_GRUPO ");
            sql.append("                            AND UG.ID_USUARIO = GUM.ID_USUARIO ");
            sql.append(" INNER JOIN USUARIO U ON U.ID = UG.ID_USUARIO ");
            sql.append("      WHERE GUM.ID_GRUPO = ? ");
            sql.append("   ORDER BY GUM.DATA_INCLUSAO ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            preparedStatement.setInt(i++, grupoUsuarioMensagem.getGrupo().getId());

            resultSet = preparedStatement.executeQuery();
            GrupoUsuarioMensagem grupoUsuMsg;
            grupoUsuarioMensagem.getGrupo().getGrupoUsuarioMensagem().clear();
            while (resultSet.next()) {
                grupoUsuMsg = new GrupoUsuarioMensagem();
                grupoUsuMsg.setId(resultSet.getInt("ID"));
                grupoUsuMsg.setMensagem(resultSet.getString("MENSAGEM"));
                grupoUsuMsg.setDataInclusao(resultSet.getObject("DATA_INCLUSAO", LocalDateTime.class));
                grupoUsuMsg.getUsuario().setId(resultSet.getInt("USUARIO_ID"));
                grupoUsuMsg.getUsuario().setNome(resultSet.getString("USUARIO_NOME"));
                grupoUsuMsg.setGrupo(grupoUsuarioMensagem.getGrupo());
                grupoUsuarioMensagem.getGrupo().getGrupoUsuarioMensagem().add(grupoUsuMsg);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOGrupoUsuarioMensagem.consultarMensagens \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }
    }

    public int inserirMensagem(final GrupoUsuarioMensagem grupoUsuarioMensagem) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            sql = new StringBuilder();
            sql.append(" INSERT INTO GRUPO_USUARIO_MENSAGEM ");
            sql.append("    (ID_GRUPO, ID_USUARIO, MENSAGEM, DATA_INCLUSAO) ");
            sql.append(" VALUES(?, ?, ?, CURRENT_TIMESTAMP) ");

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            preparedStatement.setInt(i++, grupoUsuarioMensagem.getGrupo().getId());
            preparedStatement.setInt(i++, grupoUsuarioMensagem.getUsuario().getId());
            preparedStatement.setString(i++, grupoUsuarioMensagem.getMensagem().trim().toUpperCase());

            int retorno = preparedStatement.executeUpdate();
            result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                grupoUsuarioMensagem.setId(result.getInt(1));
            }

            return retorno;
        } catch (SQLException e) {
            throw new SQLException("Erro: DAOGrupoUsuarioMensagem.inserirMensagem \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(result);
            fecharConexao(conexao);
        }
    }

}
