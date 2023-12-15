import java.util.*;

public class QuickStrategy {
  private static final Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    QuickStrategy start = new QuickStrategy();

    while(true) {
      start.mainMenu();
      String option = sc.nextLine();
      switch (option) {
        case "1" :
          Play action1 = new Play(start.login());
          break;
        case "2" :
          start.customGame(start.login());
          break;
        case "3" : System.exit(0); break;
        default : System.out.println("Opcion no valida."); break;
      }
    }
  }

  private void mainMenu() {
    System.out.println("\n---------------Quick Strategy--------------");
    System.out.println("1. Juego rapido\n" + "2. Juego personalizado\n" + "3. Salir");
    System.out.print("Eliga una opcion: ");
  }

  private BattleRoyale login() {
    System.out.println("\nBienvenidos pseudoespecialistas en estrategia!\n");
    System.out.print("Jugador 1: "); String j1 = sc.nextLine();
    System.out.println(j1 + ", tu tropa es verde\n");
    System.out.print("Jugador 2: "); String j2 = sc.nextLine();
    System.out.println(j2 + ", tu tropa es anaranjada\n");
    return new BattleRoyale(j1, j2);
    
  }

  private void customGame(BattleRoyale previousBattle) {
    boolean start = true;
    while(start) {
      System.out.println("1. Personalizar tropa de " + previousBattle.getTroop1().getPlayer() +
          "\n2. Personalizar tropa de " + previousBattle.getTroop2().getPlayer() +
          "\n3. Jugar\n4. Salir");
      System.out.print("Eliga una opcion: "); String option = sc.nextLine();
      switch (option) {
        case "1" :
          optionCustomBattle(previousBattle.getTroop1()); break;
        case "2" :
          optionCustomBattle(previousBattle.getTroop2()); break;
        case "3" :
          Play action = new Play(previousBattle); break;
        case "4" :
          System.exit(0); break;
        default:
          System.out.println("Opcion no valida."); break;
      }
    }
  }

  private void optionCustomBattle(Troop troop) {
    boolean start = true;
    while(start) {
      System.out.println("\n1. Crear guerrero\n2. Eliminar guerrero\n3. Clonar guerrero\n" +
          "4. Modificar guerrero\n5. Comparar guerreros\n6. Intercambiar guerreros\n" +
          "7. Ver guerrero\n8. Ver tropa\n9. Sumar niveles\n10. volver");
      System.out.print("Eliga una opcion: "); String option = sc.nextLine();

      switch (option) {
        case "1":
          troop.createSoldier(); break;
        case "2":
          troop.deleteSoldier(); break;
        case "3":
          troop.cloneSoldier(); break;
        case "4":
        case "5":
        case "6":
        case "7":
          if(troop.showSoldier() != null)
            System.out.println(troop.showSoldier().toString());
          else
            System.out.println("No existe ese guerrero.");
          break;
        case "8":
          troop.information(); break;
        case "9":
        case "10":
          start = false; break;
        default:
          System.out.println("Opcion no valida"); break;
      }
    }
  }
}
