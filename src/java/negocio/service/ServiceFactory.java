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
    public static final byte GRUPO = 4;
    public static final byte CURSO = 5;
    public static final byte AULA = 6;
    public static final byte ATIVIDADE = 7;
    public static final byte GRUPO_USUARIO_MENSAGEM = 8;
    public static final byte RANKING = 9;

    private static LoginService loginService;
    private static UsuarioService usuarioService;
    private static MedalhaService medalhaService;
    private static TipoAtividadeService tipoAtividadeService;
    private static GrupoService grupoService;
    private static CursoService cursoService;
    private static AulaService aulaService;
    private static AtividadeService atividadeService;
    private static GrupoUsuarioMensagemService grupoUsuarioMensagemService;
    private static RankingService rankingService;

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

                case GRUPO:
                    if (grupoService == null) {
                        grupoService = new GrupoService();
                    }
                    return grupoService;

                case CURSO:
                    if (cursoService == null) {
                        cursoService = new CursoService();
                    }
                    return cursoService;

                case AULA:
                    if (aulaService == null) {
                        aulaService = new AulaService();
                    }
                    return aulaService;

                case ATIVIDADE:
                    if (atividadeService == null) {
                        atividadeService = new AtividadeService();
                    }
                    return atividadeService;

                case GRUPO_USUARIO_MENSAGEM:
                    if (grupoUsuarioMensagemService == null) {
                        grupoUsuarioMensagemService = new GrupoUsuarioMensagemService();
                    }
                    return grupoUsuarioMensagemService;

                case RANKING:
                    if (rankingService == null) {
                        rankingService = new RankingService();
                    }
                    return rankingService;

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
