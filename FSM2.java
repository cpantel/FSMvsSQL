import java.util.Arrays;
import java.util.Scanner;
import java.util.*;

class FSM2 {

  public enum Estados {
    SEARCH,
    SCAN,
    CONNECT,
    COPY,
    ATTACK
  }

  public enum Resultados {
    CHANGE,
    NOCHANGE,
    SKIP,
    EMPTY,
    DONE
  }

  private String ip_externa = "";
  private String dato       = "";
  private String ip_interna = "";
  private Estados estado    = Estados.SEARCH;

  public Resultados procesar( String tokens[]) {
    Resultados resultado = Resultados.NOCHANGE;

    switch (estado) {
      case SEARCH:
        if ( "scan".equals(tokens[2]) ) {
          System.out.println("  Scan detectado");
          ip_externa = tokens[3];
          estado = Estados.SCAN;
          resultado = Resultados.CHANGE;
        }
      break;
      case SCAN:
        if ( "conn".equals(tokens[2]) ) {
          System.out.println("  Connection detectado");
          dato = tokens[5];
          estado = Estados.CONNECT;
          resultado = Resultados.CHANGE;
        }
      break;
      case CONNECT:
        if ( "copy".equals(tokens[2]) ) {
          if ( dato.equals(tokens[5]) ) {
            System.out.println("  Internal copy detectado");
            ip_interna = tokens[4];
            estado = Estados.COPY;
            resultado = Resultados.CHANGE;
          }
        }
      break;
      case COPY:
        if ("copy".equals(tokens[2]) ) {
          System.out.println("  ATTACK posible...");
          if ( dato.equals(tokens[5]) && ip_interna.equals( tokens[3]) ) {
            System.out.println("  ATTACK detectado");
            System.out.println("    Origen            : " + ip_externa);
            System.out.println("    Nodo comprometido : " + ip_interna);
            System.out.println("    Dato              : " + dato);
            System.out.println("    Colector          : " + tokens[3]);
            estado = Estados.SEARCH;
            resultado = Resultados.DONE;
          }
        }
      break;
      default:
        resultado = Resultados.SKIP;
    }
    return resultado;
  }

  public static void main(String[] args)     {
    Resultados resultado = Resultados.EMPTY;

    Scanner scanner = new Scanner(System.in);

    List<FSM2> casos = new ArrayList<FSM2>();

    ListIterator<FSM2> iteradorCasos = casos.listIterator();

    while (scanner.hasNext()) {

      // leer linea y partirla en campos
      String tokens[] = scanner.nextLine().split(" ");
      System.out.println(Arrays.asList(tokens));

      System.out.println("  Examinando casos conocidos ");

      resultado = Resultados.EMPTY;
      FSM2 actual = null;
      while ( iteradorCasos.hasNext() && resultado != Resultados.SKIP) {
          actual = iteradorCasos.next();
          resultado = actual.procesar(tokens);
          System.out.println("       " + resultado);
        }
      System.out.println( resultado);

          if ( resultado == Resultados.DONE) {
            System.out.println("   Eliminado caso, " + casos.size());
            casos.remove(actual);

          }


      if ( resultado == Resultados.EMPTY) {
        System.out.println("  Creando nuevo caso");
        FSM2 fsm = new FSM2();
        resultado = fsm.procesar(tokens);
        if ( resultado != Resultados.SKIP ) {
          System.out.println("    Agregando caso" );
          casos.add(fsm);
        }

      }
      System.out.println( resultado);
      iteradorCasos = casos.listIterator();
    }

    scanner.close();
  }
}
