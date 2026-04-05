package controle;

import controle.controller.DispositivoController;
import controle.controller.RegistroController;
import controle.model.Dispositivo;
import controle.model.TipoDispositivo;

/**
 * Ponto de entrada da aplicação — demonstra o uso do sistema de controle remoto.
 */
public class Main {

    public static void main(String[] args) {

        RegistroController registro   = new RegistroController();
        DispositivoController ctrl    = new DispositivoController();

        // ── Cadastro ──────────────────────────────────────────────────────────
        registro.cadastrar("tv01",  "Smart TV Sala",     TipoDispositivo.TELEVISAO);
        registro.cadastrar("ac01",  "Ar-Condicionado",   TipoDispositivo.AR_CONDICIONADO);
        registro.cadastrar("som01", "Caixa de Som",      TipoDispositivo.SOM);
        registro.cadastrar("lmp01", "Lampada Escritorio",TipoDispositivo.ILUMINACAO);

        System.out.println("\n=== Dispositivos cadastrados ===");
        for (Dispositivo d : registro.listarTodos()) {
            System.out.println("  " + d);
        }

        // ── Operações ─────────────────────────────────────────────────────────
        System.out.println("\n=== Operações ===");
        ctrl.ligar("tv01");
        ctrl.ajustarNivel("tv01", 40);

        ctrl.ligar("ac01");
        ctrl.ajustarNivel("ac01", 22);

        ctrl.ligar("som01");
        ctrl.ajustarNivel("som01", 70);

        ctrl.desligar("ac01");

        // ── Estado final ──────────────────────────────────────────────────────
        System.out.println("\n=== Estado Final ===");
        for (Dispositivo d : registro.listarTodos()) {
            System.out.println("  " + d);
        }

        // ── Singleton: mesma instância ────────────────────────────────────────
        System.out.println("\n=== Verificação Singleton ===");
        var g1 = controle.singleton.GerenciadorDispositivos.getInstancia();
        var g2 = controle.singleton.GerenciadorDispositivos.getInstancia();
        System.out.println("g1 == g2 ? " + (g1 == g2));
    }
}
