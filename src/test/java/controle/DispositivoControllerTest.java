package controle;

import controle.controller.DispositivoController;
import controle.controller.RegistroController;
import controle.exception.DispositivoNaoEncontradoException;
import controle.model.TipoDispositivo;
import controle.singleton.GerenciadorDispositivos;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DispositivoController")
class DispositivoControllerTest {

    private DispositivoController ctrl;
    private RegistroController    registro;

    @BeforeEach
    void setUp() {
        GerenciadorDispositivos.getInstancia().limpar();
        ctrl     = new DispositivoController();
        registro = new RegistroController();
        registro.cadastrar("tv01",  "Smart TV",      TipoDispositivo.TELEVISAO);
        registro.cadastrar("ac01",  "Ar-Cond",       TipoDispositivo.AR_CONDICIONADO);
    }
    
    @Test
    @DisplayName("Deve ligar dispositivo")
    void deveLigarDispositivo() {
        ctrl.ligar("tv01");
        assertTrue(ctrl.consultarEstado("tv01").isLigado());
    }

    @Test
    @DisplayName("Deve desligar dispositivo e zerar nível")
    void deveDesligarDispositivo() {
        ctrl.ligar("tv01");
        ctrl.ajustarNivel("tv01", 50);
        ctrl.desligar("tv01");
        assertFalse(ctrl.consultarEstado("tv01").isLigado());
        assertEquals(0, ctrl.consultarEstado("tv01").getNivel());
    }

    @Test
    @DisplayName("Deve lançar exceção ao ligar dispositivo inexistente")
    void deveLancarExcecao_LigarInexistente() {
        assertThrows(DispositivoNaoEncontradoException.class,
                () -> ctrl.ligar("fantasma"));
    }
    
    @Test
    @DisplayName("Deve ajustar nível quando ligado")
    void deveAjustarNivel() {
        ctrl.ligar("ac01");
        ctrl.ajustarNivel("ac01", 22);
        assertEquals(22, ctrl.consultarEstado("ac01").getNivel());
    }

    @Test
    @DisplayName("Deve aceitar nível nos limites (0 e 100)")
    void deveAceitarNivelNosLimites() {
        ctrl.ligar("ac01");
        assertDoesNotThrow(() -> ctrl.ajustarNivel("ac01", 0));
        assertDoesNotThrow(() -> ctrl.ajustarNivel("ac01", 100));
    }

    @Test
    @DisplayName("Deve lançar exceção ao ajustar nível com dispositivo desligado")
    void deveLancarExcecao_AjusteDispositivoDesligado() {
        assertThrows(IllegalStateException.class,
                () -> ctrl.ajustarNivel("tv01", 30));
    }

    @Test
    @DisplayName("Deve lançar exceção com nível acima de 100")
    void deveLancarExcecao_NivelAcima100() {
        ctrl.ligar("tv01");
        assertThrows(IllegalArgumentException.class,
                () -> ctrl.ajustarNivel("tv01", 101));
    }

    @Test
    @DisplayName("Deve lançar exceção com nível abaixo de 0")
    void deveLancarExcecao_NivelNegativo() {
        ctrl.ligar("tv01");
        assertThrows(IllegalArgumentException.class,
                () -> ctrl.ajustarNivel("tv01", -1));
    }
    
    @Test
    @DisplayName("Deve consultar estado do dispositivo")
    void deveConsultarEstado() {
        var d = ctrl.consultarEstado("tv01");
        assertNotNull(d);
        assertEquals("tv01", d.getId());
    }
}
