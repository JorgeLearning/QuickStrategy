import java.util.*;
import graphics.*;

public class Troop {
  private List<Soldier> troop = new ArrayList<>();
  private String player;
  public enum Color {GREEN, ORANGE}
  private Color color;
  private Random random = new Random();
  private final Scanner sc = new Scanner(System.in);

  public Troop(String player) {
    int n = random.nextInt(10) + 1;
    for(int i = 0; i < n; i++)
      troop.add(new Soldier("Soldier" + i));
    this.player = player;
    color = Color.GREEN;
  }

  public void setColor() {
    for(Soldier i : troop)
      i.setSoldier(i.getSoldier().invertir());
    color = Color.ORANGE;
  }

  public List<Soldier> getTroop() {
    return troop;
  }
  
  public String getPlayer() {
    return player;
  }

  public Soldier selectSoldier(int x, int y) {
    for(Soldier i : troop) {
      int[] position = i.getPosition();
      if(position[0] == x && position[1] == y)
        return i;
    }
    return null;
  }

  public void information() {
    System.out.println("\n---> " + player + ", tus guerreros son los siguientes:");
    for(Soldier i : troop)
      System.out.println(i.toString());
  }

  public void createSoldier() {
      System.out.print("\nIngrese nombre del guerrero: "); String name = sc.nextLine();
      System.out.print("Ingrese vida del guerrero: "); int hp = sc.nextInt();
      System.out.print("Ingrese ataque del guerrero: "); int attack = sc.nextInt();
      System.out.print("Ingrese defensa del guerrero: "); int defense= sc.nextInt();
      System.out.print("Ingrese velocidad del guerrero: "); int speed = sc.nextInt();
      int[] position = new int[2];

    while(true) {
      System.out.println("Ingrese posicion del guerrero:  "); 
      System.out.print("Fila: "); position[0] = sc.nextInt();
      System.out.print("Columna: "); position[1] = sc.nextInt();
      sc.nextLine();

      if(troop.size() < 10 && Soldier.getBattleRoyale()[position[0]][position[1]] == BattleRoyale.getCell()) {
        Soldier newSoldier = new Soldier(name, hp, attack, defense, speed, position);
        if(color == Color.ORANGE) {
          //System.out.println("Si es verdadero que el color sea green");
          newSoldier.setSoldier(newSoldier.getSoldier().invertir());
        }
        troop.add(newSoldier);
        Soldier.getBattleRoyale()[position[0]][position[1]] = newSoldier.getSoldier().superponer(Soldier.getBattleRoyale()[position[0]][position[1]]);
        break;
      } else
        System.out.println("Un guerrero ya se encuentra en esta posicion o tu tropa esta llena");
    }
  }

  public void removeSoldier(String victim) {
    for(int i = 0; i < troop.size(); i++)
      if(victim.equals(troop.get(i).getName())) {
        troop.remove(i); break;
      }
  }

  public void deleteSoldier() {
    Soldier delete = showSoldier();
    int x = delete.getPosition()[0];
    int y = delete.getPosition()[1];
    Soldier.getBattleRoyale()[x][y] = BattleRoyale.getCell();
    troop.remove(delete);
  }

  //x y y es la posicion del guerrero el cual queremos clonar y posicion es la posicion donde
  //el guerrero clonado se ubicara.
  public void cloneSoldier() {
    int[] positions = new int[4];
    System.out.println("Ingrese la posicion del guerrero a clonar: ");
    System.out.print("Fila: "); positions[0] = sc.nextInt();
    System.out.print("Columna: "); positions[1]= sc.nextInt();

    while(true) {
      System.out.println("Ingrese la posicion del futuro guerrero: ");
      System.out.print("Fila: "); positions[2] = sc.nextInt();
      System.out.print("Columna: "); positions[3] = sc.nextInt();
      if(troop.size() < 10 && Soldier.getBattleRoyale()[positions[2]][positions[3]] == BattleRoyale.getCell()) {
        Soldier cloned = selectSoldier(positions[0], positions[1]);
        cloned.setPosition(positions[2], positions[3]);
        troop.add(cloned);
        Soldier.getBattleRoyale()[positions[2]][positions[3]] = cloned.getSoldier().superponer(Soldier.getBattleRoyale()[positions[2]][positions[3]]);
        break;
      } else
        System.out.println("Un guerrero ya se encuentra en esta posicion.");
    }
  }

  public Soldier showSoldier() {
    System.out.print("Ingrese el nombre correcto  del guerrero: "); String name = sc.nextLine();
    for(Soldier i : troop)
      if(name.equals(i.getName()))
        return i;
    return null;
  }
}
