import java.util.*;
import graphics.*;

public class Soldier {
  private String name;
  private int hp;
  private int attack;
  private int defense;
  private int speed;
  private int[] position = new int[2];
  private Picture soldier;
  private static Picture[][] battleRoyale; //Cada soldado conoce el mismo campo de batalla 
  private Random random = new Random();
  private Scanner sc = new Scanner(System.in);
  
  public Soldier(String name) {
    this.name = name;
    this.hp = generator();
    this.attack = generator();
    this.defense = generator();
    this.speed = 2;
    this.soldier = createSoldier(hp);
  }

  public Soldier(String name, int hp, int attack, int defense, int speed, int[] position) {
    this.name = name;
    this.hp = hp;
    this.attack = attack;
    this.defense = defense;
    this.speed = speed;
    this.position = position;
    this.soldier = createSoldier(hp);
  }

  private int generator() {
    return random.nextInt(5) + 1;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setHp(int hp){
    this.hp = hp;
  }

  public void setAttack(int attack) {
    this.attack = attack;
  } 

  public void setDefense(int defense) {
    this.defense = defense;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }
  
  public void setPosition(int x, int y) {
    position[0] = x;
    position[1] = y;
  }

  private Picture createSoldier(int hp) {
    switch(hp) {
      case 1 :
        return Picture.soldier1();
      case 2 :
        return Picture.soldier2();
      case 3 :
        return Picture.soldier3();
      case 4 :
        return Picture.soldier4();
      default :
        return Picture. soldier5();
    }
  }

  public void setSoldier(Picture soldier) {
    this.soldier = soldier;
  }

  public static void setBattleRoyale(Picture[][] royale) {
    battleRoyale = royale;
  }

  public String getName(){
    return name;
  }

  public int getHp(){
    return hp;
  }

  public int getAttack() {
    return attack;
  }

  public int getDefense() {
    return defense;
  }

  public int getSpeed() {
    return speed;
  }
  
  public int[] getPosition() {
    return position;
  }

  public Picture getSoldier() {
    return soldier;
  }

  public static Picture[][] getBattleRoyale() {
    return battleRoyale;
  }

  @Override
  public String toString(){
    return ("\nName: " + name + "\nhp: " + hp + "\nAttack: " + attack +
        "\nDefense: " + defense + "\nSpeed: " + speed + "\nRow: " + position[0] +
        "\nColumn: " + position[1] + "\n");
  }

  public void move(int x, int y) { 
    battleRoyale[x][y] = soldier.superponer(battleRoyale[x][y]);
    battleRoyale[position[0]][position[1]] = BattleRoyale.getCell();
    position[0] = x;
    position[1] = y;
  }

  //Cuando el soldado ataca, es necesario siempre tener presente quien es la victima,
  //por ello devuelve la posicion de la victima
  public int[] attack() {
    int[] victim = new int[2];

    boolean start = true;
    while(start) {
      System.out.println("\n1. Saborea a tu victima.\n2. Volver");
      System.out.print("Elige un opcion: "); String option = sc.nextLine();

      switch(option) {
        case "1" :
          System.out.print("\nFila: "); victim[0] = sc.nextInt();
          System.out.print("Columna: "); victim[1] = sc.nextInt();
          sc.nextLine();
          //Verificamos que realmente se este atacando a un oponente
          if(battleRoyale[victim[0]][victim[1]] != BattleRoyale.getCell())
            //Aca si es importante saber la velocidad del atacante, pues si la velocidad es
            //menor de la que puede alcanzar, entonces no puede atacar a esa victima
            if(speed - calculateSpeed(victim[0], victim[1]) >= 0) {
              speed = speed - calculateSpeed(victim[0], victim[1]); 
              start = false; break;
            } else
              System.out.println("\nNo te emociones, aun no tienes suficiente velocidad");
          else
            System.out.println("\nNo hay ninguna victima aqui.");
          break;
        case "2" :
          victim = null;
          start = false; break;
        default :
          System.out.println("Opcion no valida."); break;
      }
    }
    return victim;
  }
  
  public void gainSpeed() {
    while(true) {
      System.out.println("\nRecuerda que ganas velocidad acumulando celdas visitadas.");
      System.out.print("Fila: "); int x = sc.nextInt();
      System.out.print("Columna: "); int y = sc.nextInt();
      //Consumir el bufer
      //sc.nextLine();

      if(battleRoyale[x][y] == BattleRoyale.getCell()) {
        // Siempre va a poder moverse, pero no siempre adquiere velocidad, su maxima velocidad
        // que puede tener es de 20 celdas, visitadas
        if(speed + calculateSpeed(x, y) <= 20) {
          speed = speed + calculateSpeed(x, y);
        }
        move(x, y);
        break;
      } else 
        System.out.println("\nNo puedes atacar, solo estas adquiriendo velocidad");
    }
  }

  private int calculateSpeed(int x, int y) {
    x = Math.absExact(x - position[0]);
    y = Math.absExact(y - position[1]);
    return x + y;
  }

  public void beAttacked(String player, Soldier attacker) {
    int x = attacker.getPosition()[0];
    int y = attacker.getPosition()[1];
    System.out.println("\n---> " + player + ", tu guerrero de la fila " + position[0] +
        " y columna " + position[1] + " esta siendo atacado por el guerrero de de la fila " +
        x + " y columna " + y);
    System.out.println(attacker.toString());
    System.out.println(String.format("\nTienes %.2f de probabilidad para ganar", probalityWinning(attacker)));
  }

  public void escape(String player) {
    while(true) {
      System.out.println("\n" + player + ", escapa lo mas lejos que puedas");
      System.out.print("Fila: "); int x = sc.nextInt();
      System.out.print("Columna: "); int y = sc.nextInt();

      // Cuando quiere escapar, gasta el doble de velocidad
      if(battleRoyale[x][y] == BattleRoyale.getCell() && calculateSpeed(x, y) * 2 <= getSpeed()) {
        //La victima logra escapar pero para ello gasta el doble de su velocidad para atacar.
        speed -= calculateSpeed(x, y) * 2;
        move(x, y);
        break;
      } else
        System.out.println("\nNo puedes escapar ahi, estas loco.");
    }
  }

  public Soldier didHonor(Soldier attacker) {
    if(attacker.getHp() < 5)
      attacker.setHp(attacker.getHp() + 1);
    if(attacker.getAttack() < 5)
      attacker.setAttack(attacker.getAttack() + 1);
    if(attacker.getDefense() < 5)
      attacker.setDefense(attacker.getDefense() + 1);
    return this;
  }

  public double probalityWinning(Soldier attacker) {
    double victimPower = hp + attack + defense;
    double attackerPower = attacker.getHp() + attacker.getAttack() + attacker.getDefense();
    return (victimPower * 100) / (victimPower + attackerPower);
  }
}
