package controle.controller;

import controle.model.Dispositivo;
import controle.singleton.GerenciadorDispositivos;

public class DispositivoController {

    private static final int NIVEL_MIN = 0;
    private static final int NIVEL_MAX = 100;

    private final GerenciadorDispositivos gerenciador;

    public DispositivoController() {
        this.gerenciador = GerenciadorDispositivos.getInstancia();
    }

    public void ligar(String id) {
        Dispositivo d = gerenciador.buscar(id);
        d.setLigado(true);
        System.out.printf("[CTRL] %s ligado.%n", d.getNome());
    }

    public void desligar(String id) {
        Dispositivo d = gerenciador.buscar(id);
        d.setLigado(false);
        d.setNivel(0);
        System.out.printf("[CTRL] %s desligado.%n", d.getNome());
    }


    public void ajustarNivel(String id, int nivel) {
        if (nivel < NIVEL_MIN || nivel > NIVEL_MAX)
            throw new IllegalArgumentException(
                    "Nível deve estar entre " + NIVEL_MIN + " e " + NIVEL_MAX + ".");

        Dispositivo d = gerenciador.buscar(id);
        if (!d.isLigado())
            throw new IllegalStateException("Dispositivo '" + d.getNome() + "' está desligado.");

        d.setNivel(nivel);
        System.out.printf("[CTRL] %s -> nível ajustado para %d.%n", d.getNome(), nivel);
    }

    public Dispositivo consultarEstado(String id) {
        return gerenciador.buscar(id);
    }
}
