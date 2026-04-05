package controle.controller;

import controle.model.Dispositivo;
import controle.model.TipoDispositivo;
import controle.singleton.GerenciadorDispositivos;

import java.util.Collection;

public class RegistroController {

    private final GerenciadorDispositivos gerenciador;

    public RegistroController() {
        this.gerenciador = GerenciadorDispositivos.getInstancia();
    }
    
    public Dispositivo cadastrar(String id, String nome, TipoDispositivo tipo) {
        Dispositivo d = new Dispositivo(id, nome, tipo);
        gerenciador.registrar(d);
        System.out.printf("[REG] Dispositivo '%s' cadastrado com sucesso.%n", nome);
        return d;
    }
    
    public void remover(String id) {
        gerenciador.remover(id);
        System.out.printf("[REG] Dispositivo '%s' removido.%n", id);
    }
    
    public Collection<Dispositivo> listarTodos() {
        return gerenciador.listarTodos();
    }
}
