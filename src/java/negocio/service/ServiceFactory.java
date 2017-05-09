/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.service;

import negocio.excecao.ControleException;

/**
 *
 * @author Camila
 */
public class ServiceFactory {

    public static final byte LOGIN = 0;
    public static final byte USUARIO = 1;

    private static LoginService loginService;
    private static UsuarioService usuarioService;

    public static Object criarService(final byte id) throws ControleException {
        try {
            switch (id) {
                case LOGIN:
                    if (loginService == null) {
                        loginService = new LoginService();
                    }
                    return loginService;

                case USUARIO:
                    if (usuarioService == null) {
                        usuarioService = new UsuarioService();
                    }
                    return usuarioService;

                default:
                    throw new ControleException("Erro ao criar service");
            }
        } catch (ControleException e) {
            throw e;
        } catch (Exception e) {
            throw new ControleException("Erro: ServiceFactory.criarService");
        }
    }

}
