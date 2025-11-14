//Alumna: García Herrera Valeria

package Main;
import java.math.BigInteger;
import java.util.Hashtable;

	public class FibonacciThreadsBigIntegerMemo implements Runnable {

	    // La tabla hash estática para la memoria. Key: posición (BigInteger), Value: resultado (BigInteger)
	    private static final Hashtable<BigInteger, BigInteger> sucFib = new Hashtable<>();
	    
	    static {
	        // Inicialización de los casos base f(0)=0 y f(1)=1.
	        sucFib.put(BigInteger.ZERO, BigInteger.ZERO);
	        sucFib.put(BigInteger.ONE, BigInteger.ONE);
	    }

	    private int num;
	    private BigInteger fi; // La posición n a calcular

	    public FibonacciThreadsBigIntegerMemo(int n, BigInteger f) {
	        this.num = n;
	        this.fi = f;
	    }

	    @Override
	    public void run() {
	        System.out.println("Starte # " + num);
	        BigInteger res = fibonacci(fi); // Llama a la función memoizada
	        System.out.println("Abschlussverfahren: " + num + " - " + "fibonacci(" + fi + ") = " + res);
	    }

	    /**
	     * Función de Fibonacci con Memoria.
	     * @param n La posición a calcular (BigInteger).
	     * @return El resultado de f(n) (BigInteger).
	     */
	    
	    public BigInteger fibonacci(BigInteger n) {
	        
	        // Acción 1: Consultar la memoria (get(key) y containsKey)
	        if (sucFib.containsKey(n)) {
	            return sucFib.get(n);
	        }

	        // Lógica: f(n) = f(n-1) + f(n-2)
	        BigInteger res = fibonacci(n.subtract(BigInteger.ONE))
	                         .add(fibonacci(n.subtract(BigInteger.TWO)));
	        
	        // Acción 2: Guardar en la memoria (put(key, value))
	        sucFib.put(n, res);
	        
	        return res;
	    }

	    public static void main(String[] args) {
	        // Ejemplo de uso con Threads para probar concurrencia
	        Thread[] threads = new Thread[10];

	        for (int i = 0; i < 10; i++) {
	            // Genera posiciones grandes aleatorias (ej. hasta 50, que antes fallaba)
	            long position = (long)(Math.random() * 50) + 1; 
	            
	            threads[i] = new Thread(
	                new FibonacciThreadsBigIntegerMemo(i, BigInteger.valueOf(position))
	            );
	        }

	        // Inicia todos los hilos
	        for (int i = 0; i < 10; i++) {
	            threads[i].start();
	        }
	    }
	}

