import java.util.Arrays;
import java.util.Scanner;
import java.util.*;

class FSM {

  public enum Estados {
    SCAN,
    CONNECT,
    COPY,
    ATTACK,
    REPORTED
  }

  private String ip_externa = "";
  private String dato       = "";
  private String ip_interna = "";
  private Estados estado    = Estados.SCAN;

  public FSM(String ip) {
    ip_externa = ip;
  }

  static public FSM DetectarScan(String tokens[]) {
    if ( "scan".equals(tokens[2]) ) {
       System.out.println("  Scan detectado");
       FSM nuevoCaso = new FSM(tokens[3]);
       return nuevoCaso;
    }
    return null;
  }
  public boolean done() {
    return (Estados.REPORTED  == estado );
  }

  public void procesar( String tokens[]) {
    switch (estado) {
      case SCAN:
        if ( "conn".equals(tokens[2]) ) {
          System.out.println("  Connection detectado");
          dato = tokens[5];
          estado = Estados.CONNECT;
        }
      break;
      case CONNECT:
        if ( "copy".equals(tokens[2]) ) {
          if ( dato.equals(tokens[5]) ) {
            System.out.println("  Internal copy detectado");
            ip_interna = tokens[4];
            estado = Estados.COPY;
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
            estado = Estados.REPORTED;
          }
        }
      break;
      default:

    }
  }

  public static void main(String[] args)     {

    Scanner scanner = new Scanner(System.in);

    List<FSM> casos = new ArrayList<FSM>();

    ListIterator<FSM> iteradorCasos = casos.listIterator();

    while (scanner.hasNext()) {

      String tokens[] = scanner.nextLine().split(" ");
      System.out.println(Arrays.asList(tokens));

      System.out.println("  Evaluando si es un nuevo caso ");
      FSM actual = FSM.DetectarScan(tokens);

      if (null != actual) {
        casos.add(actual);
      } else {
        System.out.println("  Examinando casos conocidos ");
        while ( iteradorCasos.hasNext()) {
          actual = iteradorCasos.next();
          actual.procesar(tokens);
        }
      }
      iteradorCasos = casos.listIterator();

      while ( iteradorCasos.hasNext()) {
        actual = iteradorCasos.next();
        if (actual.done() ) {

          break;
        }
      }
      if (actual == null ) {
        System.out.println("   Eliminado caso, hay " + casos.size());
        casos.remove(actual);
      }
      iteradorCasos = casos.listIterator();
    }

    scanner.close();
  }
}
