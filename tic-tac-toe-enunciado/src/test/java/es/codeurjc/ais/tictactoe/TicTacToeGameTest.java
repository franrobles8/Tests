
package es.codeurjc.ais.tictactoe;

import es.codeurjc.ais.tictactoe.TicTacToeGame.CellMarkedValue;
import es.codeurjc.ais.tictactoe.TicTacToeGame.Event;
import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;

public class TicTacToeGameTest {
    
    TicTacToeGame tttGame;
    Connection c0, c1;
    Player p0, p1;
    
    @Before
    public void setUp() {
        // Creamos una partida nueva
        this.tttGame = new TicTacToeGame();
        
        //Creamos dos dobles Mock para las conexiones
        c0 = mock(Connection.class);
        c1 = mock(Connection.class);
        
        // Añadimos las conexiones a la partida
        tttGame.addConnection(c0);
        tttGame.addConnection(c1);
        
        //Creamos dos objetos de tipo de la clase Player
        p0 = new Player(0, "X", "Fran");
        p1 = new Player(1, "O", "Ana");
    }
    
    @Test
    public void check_JOIN_GAME_connection_c0 () {
        
        //Capturamos el evento
        doNothing().when(c0).sendEvent(EventType.JOIN_GAME, tttGame.getPlayers());
        
        //Añadimos los jugadores
        this.anadirJugadores();
        
        //Comprobamos que el jugador 1 ha recibido el evento JOIN_GAME
        ArgumentCaptor<Event> argument = ArgumentCaptor.forClass(Event.class);
        verify(c0, times(2)).sendEvent(eq(EventType.JOIN_GAME), argument.capture());
        Object event = argument.getValue();
        CopyOnWriteArrayList<Player> eventPlayers = (CopyOnWriteArrayList<Player>) event;
        assertEquals("El número de jugadores recibidos debería ser dos en el evento JOIN_GAME (conexión 0)", 2, eventPlayers.size());
    }
    
    @Test
    public void check_JOIN_GAME_connection_c1 () {
        
        //Capturamos el evento
        doNothing().when(c1).sendEvent(EventType.JOIN_GAME, tttGame.getPlayers());
        
        //Añadimos los jugadores
        this.anadirJugadores();
        
        //Comprobamos que el jugador 2 ha recibido el evento JOIN_GAME
        ArgumentCaptor<Event> argument = ArgumentCaptor.forClass(Event.class);
        verify(c1, times(2)).sendEvent(eq(EventType.JOIN_GAME), argument.capture());
        Object event = argument.getValue();
        CopyOnWriteArrayList<Player> eventPlayers = (CopyOnWriteArrayList<Player>) event;
        assertEquals("El número de jugadores recibidos debería ser dos en el evento JOIN_GAME (conexión 1)", 2, eventPlayers.size());
    }
    
    @Test
    public void check_SET_TURN_game_ganaJugador1() {
        int turnoActual;
        
        //Capturamos el evento
        doNothing().when(c0).sendEvent(EventType.MARK, 0);
        
        //Añadimos los jugadores
        this.anadirJugadores();
        
        //Comenzamos a marcar
        tttGame.mark(0); // Turno 0 (veces que hemos marcado: 1)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(1); // 1 es el número de llamadas que hemos hecho a la función mark
        //Puesto que es la primera tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 0", 0, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(1); // Turno 1 (veces que hemos marcado: 2)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(2);
        //Puesto que es la segunda tirada, el turno actual debería ser el 1
        assertEquals("El turno actual debería ser del jugador 1", 1, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(2); // Turno 0 (veces que hemos marcado: 3)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(3);
        //Puesto que es la tercera tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 0", 0, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(3); // Turno 1 (veces que hemos marcado: 4)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(4);
        //Puesto que es la cuarta tirada, el turno actual debería ser el 1
        assertEquals("El turno actual debería ser del jugador 1", 1, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(4); // Turno 0 (veces que hemos marcado: 5)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(5);
        //Puesto que es la quinta tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 0", 0, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(5); // Turno 1 (veces que hemos marcado: 6)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(6);
        //Puesto que es la quinta tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 1", 1, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(8); // Turno 0 (veces que hemos marcado: 7)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(7);
        //Puesto que es la quinta tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 0", 0, turnoActual);
        
        // Llegados a este punto, el primer jugador en tirar habría ganado con
        // las posiciones {0,4,8}, y deberíamos comprobar que se envía el evento
        // GAME_OVER con un ganador
        
        this.check_ganador1_GAME_OVER();
        
    }
    
    @Test
    public void check_SET_TURN_game_ganaJugador2() {
        int turnoActual;
        
        //Capturamos el evento
        doNothing().when(c0).sendEvent(EventType.MARK, 0);
        
        //Añadimos los jugadores
        this.anadirJugadores();
        
        //Comenzamos a marcar
        tttGame.mark(0); // Turno 0 (veces que hemos marcado: 1)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(1); // 1 es el número de llamadas que hemos hecho a la función mark
        //Puesto que es la primera tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 0", 0, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(1); // Turno 1 (veces que hemos marcado: 2)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(2);
        //Puesto que es la segunda tirada, el turno actual debería ser el 1
        assertEquals("El turno actual debería ser del jugador 1", 1, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(2); // Turno 0 (veces que hemos marcado: 3)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(3);
        //Puesto que es la tercera tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 0", 0, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(4); // Turno 1 (veces que hemos marcado: 4)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(4);
        //Puesto que es la cuarta tirada, el turno actual debería ser el 1
        assertEquals("El turno actual debería ser del jugador 1", 1, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(5); // Turno 0 (veces que hemos marcado: 5)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(5);
        //Puesto que es la quinta tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 0", 0, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(7); // Turno 1 (veces que hemos marcado: 6)
        
        // Llegados a este punto, el segundo jugador en tirar habría ganado con
        // las posiciones {1,4,7}, y deberíamos comprobar que se envía el evento
        // GAME_OVER con un ganador
        
        this.check_ganador2_GAME_OVER();
        
    }
    
    @Test
    public void check_SET_TURN_game_hayEmpate() {
        int turnoActual;
        
        //Capturamos el evento
        doNothing().when(c0).sendEvent(EventType.MARK, 0);
        
        //Añadimos los jugadores
        this.anadirJugadores();
        
        //Comenzamos a marcar
        tttGame.mark(0); // Turno 0 (veces que hemos marcado: 1)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(1); // 1 es el número de llamadas que hemos hecho a la función mark
        //Puesto que es la primera tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 0", 0, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(1); // Turno 1 (veces que hemos marcado: 2)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(2);
        //Puesto que es la segunda tirada, el turno actual debería ser el 1
        assertEquals("El turno actual debería ser del jugador 1", 1, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(2); // Turno 0 (veces que hemos marcado: 3)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(3);
        //Puesto que es la tercera tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 0", 0, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(3); // Turno 1 (veces que hemos marcado: 4)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(4);
        //Puesto que es la cuarta tirada, el turno actual debería ser el 1
        assertEquals("El turno actual debería ser del jugador 1", 1, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(4); // Turno 0 (veces que hemos marcado: 5)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(5);
        //Puesto que es la quinta tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 0", 0, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(6); // Turno 1 (veces que hemos marcado: 6)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(6);
        //Puesto que es la sexta tirada, el turno actual debería ser el 1
        assertEquals("El turno actual debería ser del jugador 1", 1, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(5); // Turno 0 (veces que hemos marcado: 7)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(7);
        //Puesto que es la séptima tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 0", 0, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(8); // Turno 1 (veces que hemos marcado: 8)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(8);
        //Puesto que es la octava tirada, el turno actual debería ser el 1
        assertEquals("El turno actual debería ser del jugador 1", 1, turnoActual);
        
        //Marcamos el siguiente turno
        tttGame.mark(7); // Turno 0 (veces que hemos marcado: 9)
        
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        turnoActual = verify_turnoActual(9);
        //Puesto que es la novena tirada, el turno actual debería ser el 0
        assertEquals("El turno actual debería ser del jugador 0", 0, turnoActual);
        
        // Llegados a este punto, los dos jugadores habrían empatado y deberíamos 
        // comprobar que se envía el evento
        // GAME_OVER con un empate
        
        this.check_empate_GAME_OVER();
        
    }
    
    public void check_ganador1_GAME_OVER() {
        int[] winnerPos = {0, 4, 8};
        WinnerValue winner = new WinnerValue();
        winner.player = tttGame.getPlayers().get(0); //Queremos comprobar que es el primer jugador
        winner.pos = winnerPos;
        
        doNothing().when(c0).sendEvent(EventType.GAME_OVER, winner);
        
        //Capturamos el evento GAME_OVER y comprobamos sus parámetros
        ArgumentCaptor<Event> argument = ArgumentCaptor.forClass(Event.class);
        verify(c0, times(1)).sendEvent(eq(EventType.GAME_OVER), argument.capture());
        Object event = argument.getValue();
        WinnerValue wv = (WinnerValue) event;
        
        assertEquals("El jugador ganador debería ser el 0", 0, wv.player.getId());
        assertArrayEquals("La línea ganadora debería ser {0, 4, 8}", winnerPos, wv.pos);
        
    }
    
    public void check_ganador2_GAME_OVER() {
        int[] winnerPos = {1, 4, 7};
        WinnerValue winner = new WinnerValue();
        winner.player = tttGame.getPlayers().get(1); //Queremos comprobar que es el segundo jugador
        winner.pos = winnerPos;
        
        doNothing().when(c0).sendEvent(EventType.GAME_OVER, winner);
        
        //Capturamos el evento GAME_OVER y comprobamos sus parámetros
        ArgumentCaptor<Event> argument = ArgumentCaptor.forClass(Event.class);
        verify(c0, times(1)).sendEvent(eq(EventType.GAME_OVER), argument.capture());
        Object event = argument.getValue();
        WinnerValue wv = (WinnerValue) event;
        
        assertEquals("El jugador ganador debería ser el 1", 1, wv.player.getId());
        assertArrayEquals("La línea ganadora debería ser {1, 4, 7}", winnerPos, wv.pos);
        
    }
    
    public void check_empate_GAME_OVER() {
        
        //Cuando hay empate, junto al evento GAME_OVER, pasamos un winner que es null
        doNothing().when(c0).sendEvent(EventType.GAME_OVER, null);
        
        //Capturamos el evento GAME_OVER y comprobamos sus parámetros
        ArgumentCaptor<Event> argument = ArgumentCaptor.forClass(Event.class);
        verify(c0, times(1)).sendEvent(eq(EventType.GAME_OVER), argument.capture());
        Object event = argument.getValue();
        WinnerValue wv = (WinnerValue) event;
        
        assertEquals("Al haber empate, el valor del ganador debería ser null", null, wv);
    }
    
    //Función auxiliar para añadir los jugadores al objecto TicTacToeGame
    private void anadirJugadores() {
        //Añadimos los jugadores
        tttGame.addPlayer(p0);
        tttGame.addPlayer(p1);
    }
    
    //Función auxiliar para verificar el turno actual
    private int verify_turnoActual(int times) {
        //times es el número de veces que hemos llamado a la función mark
        int turnoActual;
        //Comprobamos que se recibe el evento MARK con los parámetros esperados
        ArgumentCaptor<Event> argument = ArgumentCaptor.forClass(Event.class);
        verify(c0, times(times)).sendEvent(eq(EventType.MARK), argument.capture());
        Object event = argument.getValue();
        CellMarkedValue cell = (CellMarkedValue) event;
        turnoActual = cell.player.getId();
        
        return turnoActual;
    }

}
