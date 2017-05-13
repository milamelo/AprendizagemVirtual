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
    public static final byte MEDALHA = 2;
    public static final byte TIPO_ATIVIDADE = 3;

    private static LoginService loginService;
    private static UsuarioService usuarioService;
    private static MedalhaService medalhaService;
    private static TipoAtividadeService tipoAtividadeService;

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

                case MEDALHA:
                    if (medalhaService == null) {
                        medalhaService = new MedalhaService();
                    }
                    return medalhaService;

                case TIPO_ATIVIDADE:
                    if (tipoAtividadeService == null) {
                        tipoAtividadeService = new TipoAtividadeService();
                    }
                    return tipoAtividadeService;

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
