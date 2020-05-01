import java.util.Arrays;
import java.util.Scanner;


class SimpleFSM {

  public enum Estados {
    SEARCH,
    SCAN,
    CONNECT,
    COPY,
    ATTACK
  }


  public static void main(String[] args)     {
    Scanner scanner = new Scanner(System.in);
    Estados estado = Estados.SEARCH;

    String ip_externa = "";
    String dato = "";
    String ip_interna = "";

    while (scanner.hasNext()) {

      String tokens[] = scanner.nextLine().split(" ");
      // 0 date
      // 1 time
      // 2 type
      // 3 src
      // 4 dst
      // 5 data

      System.out.println(Arrays.asList(tokens));

      switch (estado) {
        case SEARCH:
          if ( "scan".equals(tokens[2]) ){
            System.out.println("  Scan detectado");
            estado = Estados.SCAN;
            ip_externa = tokens[3];
          }
        break;
        case SCAN:
          if ( "conn".equals(tokens[2]) ) {
            System.out.println("  Connection detectado");
            estado = Estados.CONNECT;
            dato = tokens[5];
          }
 
        break;
        case CONNECT:
          if ( "copy".equals(tokens[2]) ) {
            if ( dato.equals(tokens[5]) ) {
              System.out.println("  Internal copy detectado");
              estado = Estados.COPY;
              ip_interna = tokens[4];
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
            }
          }
        break;
        default:

        break;

      }

    }
    scanner.close();
  }
}
