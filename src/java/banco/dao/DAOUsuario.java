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
import java.util.logging.Level;
import java.util.logging.Logger;
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
        Connection conexao;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

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
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, e);
            throw new SQLException("Erro: DAOUsuario.logar \n" + e.getMessage());
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

        final Medalha medalha = new Medalha();
        if (rs.getInt("ID_MEDALHA") != 0) {
            medalha.setId(rs.getInt("ID_MEDALHA"));
            medalha.setNome(rs.getString("NOME_MEDALHA"));
            medalha.setPontuacaoNecessaria(rs.getBigDecimal("PONTUACAO_NECESSARIA"));
        }
        usuario.setMedalha(medalha);

        return usuario;
    }
}
