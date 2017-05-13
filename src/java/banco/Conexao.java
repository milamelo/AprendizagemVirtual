/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Camila
 */
public class Conexao {

    private final String DRIVER = "org.postgresql.Driver";
    private final String URL = "jdbc:postgresql://localhost:5432/aprendizagemvirtual";
    private final String USER = "postgres";
    private final String PASSWORD = "password";
    protected StringBuilder sql;

    public StringBuilder getSql() {
        return sql;
    }

    public void setSql(StringBuilder sql) {
        this.sql = sql;
    }

    protected Connection abrirConexao() throws Exception {
        Connection conexao = null;
        try {
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Erro: Conexao.abrirConexao \n" + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erro: Conexao.abrirConexao: Falha conexão com o banco \n" + e.getMessage());
        }
        return conexao;
    }

    protected void fecharConexao(final Connection conexao) throws SQLException {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            throw new SQLException("Erro: Conexao.fecharConexao \n" + e.getMessage());
        }
    }
    
    protected void fecharPreparedStatement(final PreparedStatement preparedStatement) throws SQLException {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new SQLException("Erro: Conexao.fecharPreparedStatement \n" + e.getMessage());
        }
    }
    
    protected void fecharResultSet(final ResultSet resultSet) throws SQLException {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new SQLException("Erro: Conexao.fecharResultSet \n" + e.getMessage());
        }
    }

}
