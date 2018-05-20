
package es.codeurjc.ais.tictactoe;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SystemTest {
    
    protected WebDriver driver1; //Navegador 1
    protected WebDriver driver2; //Navegador 2
    
    @BeforeClass
    public static void setUpClass() {
        ChromeDriverManager.getInstance().setup();
        WebApp.start();
    }
    
    @AfterClass
    public static void tearDownClass() {
        WebApp.stop();
    }
    
    @Before
    public void setUp() {
        driver1 = new ChromeDriver();
        driver2 = new ChromeDriver();
        
        //Indicamos a los navegadores el puerto por el que deben escuchar
        this.configurarNavegadores();
    }
    
    @After
    public void tearDown() {
        if(driver1 != null) {
            driver1.quit();
        }
        if(driver2 != null) {
            driver2.quit();
        }
    }
    
    @Test
    public void testGanaJugador1() {
        
        String nickname1 = "Fran";
        String nickname2 = "Ana";
        
        //Elegimos el nombre de usuario para el jugador 1
        this.insertarNombre(1, nickname1);
        
        //Elegimos el nombre de usuario para el jugador 2
        this.insertarNombre(2, nickname2);
        
        //Esperamos a que el tablero se haga visible para poder encontrar los elementos.
        // Si no hacemos esto, intentará encontrarlos sin que estén y dará un error.
        this.crearTiempoEspera(1, 10); // Navegador 1, espera 10 segundos
        this.crearTiempoEspera(2, 10); // Navegador 2, espera 10 segundos
        
        //Marcamos la celda 0 (jugador 1)
        driver1.findElement(By.id("cell-0")).click();
        //Marcamos la celda 1 (jugador 2)
        driver2.findElement(By.id("cell-1")).click();
        //Marcamos la celda 2 (jugador 1)
        driver1.findElement(By.id("cell-2")).click();
        //Marcamos la celda 3 (jugador 2)
        driver2.findElement(By.id("cell-3")).click();
        //Marcamos la celda 4 (jugador 1)
        driver1.findElement(By.id("cell-4")).click();
        //Marcamos la celda 5 (jugador 2)
        driver2.findElement(By.id("cell-5")).click();
        //Marcamos la celda 8 (jugador 1)
        driver1.findElement(By.id("cell-8")).click();
        
        // Tras marcar estas celdas, al haber sido el jugador 1 el que empezó
        // a marcar, habría ganado con la línea {0, 4, 8}, y debería salir un
        // alert de javascript con el mensaje del ganador.
        
        // El texto que debería salir es:
        String textoEsperado = nickname1 + " wins! " + nickname2 + " looses.";
        
        //Esperamos  segundos para dar tiempo a que aparezca el alert
        this.crearTiempoEspera(1, 10);
        
        //Recogemos el mensaje para comprobar que es lo que debería salir
        String textoGanador1Alert = driver1.switchTo().alert().getText();
        
        //Probamos que el texto del alert es el que se esperaba
        assertEquals("El mensaje debería ser: \' "+ nickname1 +" wins! " + nickname2 +" looses. \'",
        textoEsperado,
        textoGanador1Alert
        );
    }
    
    @Test
    public void testGanaJugador2() {
        
        String nickname1 = "Fran";
        String nickname2 = "Ana";
        
        //Elegimos el nombre de usuario para el jugador 1
        this.insertarNombre(1, nickname1);
        
        //Elegimos el nombre de usuario para el jugador 2
        this.insertarNombre(2, nickname2);
        
        //Esperamos a que el tablero se haga visible para poder encontrar los elementos.
        // Si no hacemos esto, intentará encontrarlos sin que estén y dará un error.
        this.crearTiempoEspera(1, 10); // Navegador 1, espera 10 segundos
        this.crearTiempoEspera(2, 10); // Navegador 2, espera 10 segundos
        
        //Marcamos la celda 0 (jugador 1)
        driver1.findElement(By.id("cell-0")).click();
        //Marcamos la celda 1 (jugador 2)
        driver2.findElement(By.id("cell-1")).click();
        //Marcamos la celda 2 (jugador 1)
        driver1.findElement(By.id("cell-2")).click();
        //Marcamos la celda 4 (jugador 2)
        driver2.findElement(By.id("cell-4")).click();
        //Marcamos la celda 5 (jugador 1)
        driver1.findElement(By.id("cell-5")).click();
        //Marcamos la celda 7 (jugador 2)
        driver2.findElement(By.id("cell-7")).click();
        
        // Tras marcar estas celdas, el jugador 2 habría ganado con la 
        // línea {1, 4, 7}, y debería salir un
        // alert de javascript con el mensaje del ganador.
        
        // El texto que debería salir es:
        String textoEsperado = nickname2 + " wins! " + nickname1 + " looses.";
        
        //Esperamos 10 segundos para dar tiempo a que aparezca el alert
        this.crearTiempoEspera(2, 10);
        
        //Recogemos el mensaje para comprobar que es lo que debería salir
        String textoGanador2Alert = driver2.switchTo().alert().getText();
        
        //Probamos que el texto del alert es el que se esperaba
        assertEquals("El mensaje debería ser: \' "+ nickname2 +" wins! " + nickname1 +" looses. \'",
        textoEsperado,
        textoGanador2Alert
        );
    }
    
    @Test
    public void testEmpate() {
        
        String nickname1 = "Fran";
        String nickname2 = "Ana";
        
        //Elegimos el nombre de usuario para el jugador 1
        this.insertarNombre(1, nickname1);
        
        //Elegimos el nombre de usuario para el jugador 2
        this.insertarNombre(2, nickname2);
        
        //Esperamos a que el tablero se haga visible para poder encontrar los elementos.
        // Si no hacemos esto, intentará encontrarlos sin que estén y dará un error.
        this.crearTiempoEspera(1, 10); // Navegador 1, espera 10 segundos
        this.crearTiempoEspera(2, 10); // Navegador 2, espera 10 segundos
        
        //Marcamos la celda 0 (jugador 1)
        driver1.findElement(By.id("cell-0")).click();
        //Marcamos la celda 1 (jugador 2)
        driver2.findElement(By.id("cell-1")).click();
        //Marcamos la celda 2 (jugador 1)
        driver1.findElement(By.id("cell-2")).click();
        //Marcamos la celda 3 (jugador 2)
        driver2.findElement(By.id("cell-3")).click();
        //Marcamos la celda 4 (jugador 1)
        driver1.findElement(By.id("cell-4")).click();
        //Marcamos la celda 6 (jugador 2)
        driver2.findElement(By.id("cell-6")).click();
        //Marcamos la celda 5 (jugador 1)
        driver1.findElement(By.id("cell-5")).click();
        //Marcamos la celda 8 (jugador 2)
        driver2.findElement(By.id("cell-8")).click();
        //Marcamos la celda 7 (jugador 1)
        driver1.findElement(By.id("cell-7")).click();
        
        // Tras marcar estas celdas, se produciría un empate, ya que hemos completado
        // el tablero sin tener ninguna linea ganadora. Debería salir un
        // alert de javascript con el mensaje del empate.
        
        // El texto que debería salir es:
        String textoEsperado = "Draw!";
        
        // Esperamos 10 segundos para dar tiempo a que aparezca el alert
        this.crearTiempoEspera(1, 10);
        
        // Recogemos el mensaje para comprobar que es lo que debería salir
        // Podemos usar tanto el driver1 como driver2, ya que en los dos navegadores
        // saldría el mismo mensaje.
        String textoEmpate = driver1.switchTo().alert().getText();
        
        //Probamos que el texto del alert es el que se esperaba
        assertEquals("El mensaje debería ser: \'Draw!\'",
        textoEsperado,
        textoEmpate
        );
    }
    
    private void configurarNavegadores() {
        //Indicamos al driver que escuche en el puerto 8080 de localhost
        driver1.get("http://localhost:8080/");
        driver2.get("http://localhost:8080/");
    }
    
    private void insertarNombre(int navegador, String nickname) {
        switch(navegador) {
            case 1:
                driver1.findElement(By.id("nickname")).sendKeys(nickname);
                driver1.findElement(By.id("startBtn")).click();
                break;
            case 2:
                driver2.findElement(By.id("nickname")).sendKeys(nickname);
                driver2.findElement(By.id("startBtn")).click();
                break;
        }
    }
    
    private void crearTiempoEspera(int navegador, int segundos) {
        switch(navegador) {
            case 1:
                driver1.manage().timeouts().implicitlyWait(segundos, TimeUnit.SECONDS);
                break;
            case 2:
                driver2.manage().timeouts().implicitlyWait(segundos, TimeUnit.SECONDS);
                break;
        }
    }
}
