
package es.codeurjc.ais.tictactoe;

import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardUnitaryTest {
    
    TicTacToeGame tttGame;
    
    @Before
    public void setUp() {
        this.tttGame = new TicTacToeGame();
        tttGame.addPlayer(new Player(0, "X", "Fran"));
        tttGame.addPlayer(new Player(1, "O", "Ana"));
    }
    
    @After
    public void tearDown() {
    }
    
    
    //Test que comprueba que gana el primero que pone su marca
    @Test
    public void testGetCellsIfWinner_primeroGana() {
        // Given
        /*
            Como cada vez que marcamos, cambiamos de turno, si queremos que el
            jugador 0 marque las casillas {0,1,2} tendríamos que hacerlo 
            intercalándolo con el jugador 1, como se muestra a continuación:
        */
        tttGame.mark(0); //Turno jugador 0
        tttGame.mark(3); //Turno jugador 1
        tttGame.mark(1); //Turno jugador 0
        tttGame.mark(5); //Turno jugador 1
        tttGame.mark(2); //Turno jugador 0
        
        int[] expResult = {0, 1, 2};
        
        // When
        WinnerResult result = tttGame.checkWinner();
        
        // Then
        assertArrayEquals(expResult, result.pos);
        
    }
    
    
    //Test que comprueba que pierde el primero que pone su marca
    @Test
    public void testGetCellsIfWinner_primeroPierde() {
        // Given
        
        tttGame.mark(1); //Turno jugador 0
        tttGame.mark(0); //Turno jugador 1
        tttGame.mark(2); //Turno jugador 0
        tttGame.mark(4); //Turno jugador 1
        tttGame.mark(5); //Turno jugador 0
        tttGame.mark(8); //Turno jugador 1
        
        int[] expResult = {0, 4, 8};
        
        // When
        /*
            Estamos usando checkWinner() porque este hace uso de getCellsIfWinner(…),
            y nos devuelve un objeto en el que podemos obtener la linea ganadora
            accediendo al atributo "pos" (result.pos)
        */
        WinnerResult result = tttGame.checkWinner();
        
        // Then
        assertArrayEquals(expResult, result.pos);
        
    }
    
    
    // Test que comprueba que hay un empate
    @Test
    public void testCheckDraw() {
        // Given
        boolean expectedDraw = true;
        
        tttGame.mark(0); //Turno jugador 0
        tttGame.mark(1); //Turno jugador 1
        tttGame.mark(2); //Turno jugador 0
        tttGame.mark(5); //Turno jugador 1
        tttGame.mark(3); //Turno jugador 0
        tttGame.mark(6); //Turno jugador 1
        tttGame.mark(4); //Turno jugador 0
        tttGame.mark(8); //Turno jugador 1
        tttGame.mark(7); //Turno jugador 0
        
        // When
        boolean isDraw = tttGame.checkDraw();
        
        // Then
        assertEquals(expectedDraw, isDraw);
    }
    
}
