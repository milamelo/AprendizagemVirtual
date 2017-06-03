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
import java.util.ArrayList;
import java.util.List;
import negocio.entidade.Usuario;

/**
 *
 * @author Camila
 */
public class DAORanking extends Conexao {

    public DAORanking() {
    }

    public List<Usuario> listarRanking(final int limite) throws Exception {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            sql = new StringBuilder();
            sql.append("    SELECT U.NOME, U.PONTUACAO_ACUMULADA, ");
            sql.append("           M.NOME MEDALHA_NOME ");
            sql.append("      FROM USUARIO U ");
            sql.append(" LEFT JOIN MEDALHA M ON M.ID = U.ID_MEDALHA ");
            sql.append("  ORDER BY PONTUACAO_ACUMULADA DESC ");
            if (limite != 0) {
                sql.append(" LIMIT ? ");
            }

            conexao = abrirConexao();
            preparedStatement = conexao.prepareStatement(sql.toString());

            int i = 1;
            if (limite != 0) {
                preparedStatement.setInt(i++, limite);
            }

            resultSet = preparedStatement.executeQuery();
            int posicao = 1;
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(posicao++);
                usuario.setNome((resultSet.getString("NOME")).trim());
                usuario.setPontuacaoAcumulada(resultSet.getDouble("PONTUACAO_ACUMULADA"));
                usuario.getMedalha().setNome(resultSet.getString("MEDALHA_NOME") == null ? "" : resultSet.getString("MEDALHA_NOME").trim());
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new SQLException("Erro: DAORanking.listarRanking \n" + e.getMessage());
        } finally {
            fecharPreparedStatement(preparedStatement);
            fecharResultSet(resultSet);
            fecharConexao(conexao);
        }

        return usuarios;
    }

}
