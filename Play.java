import java.util.*;
import graphics.*;

public class Play {
  private Troop troopCurrent;
  private Graphics g;
  private Scanner sc = new Scanner(System.in);

  public Play(BattleRoyale round) {
    g = new Graphics(Picture.mergePicture(Soldier.getBattleRoyale()));
    g.print();

    troopCurrent = round.getTroop1(); // El ejercito 1 empezara por defecto    
    init(round);
  }

  private void init(BattleRoyale round) {
    String option;
    boolean start = true;

    while(start) {
      //Logica rapida para terminar el juego
      if(round.getTroop1().getTroop().size() == 0) {
        System.out.println("\n\t>>> " + round.getTroop2().getPlayer() + ", has ganado la partida,eres muy god");
        //System.exit(0);
        break;
      }

      if(round.getTroop2().getTroop().size() == 0) {
        System.out.println("\n\t>>> " + round.getTroop1().getPlayer() + ", has ganado la partida, eres muy god");
        //System.exit(0);
        break;
      }

      System.out.println("\n---> Tropa de " + troopCurrent.getPlayer());
      System.out.println("1. Moverse\n" + "2. Informacion\n" + "3. Abandonar juego");
      System.out.print("Seleccione una opcion: "); option = sc.nextLine();
      
      switch(option) {
        case "1" :
          state(soldierCurrent(), round); //Ojo, el estado es imporntante
          change(round); break;
        case "2" : 
          troopCurrent.information(); break;
        case "3" : 
          start = false; break; //Volvemos al menu principal
        default : 
          System.out.println("Opcion no valida"); break;
      }
    }
  }

  public void change(BattleRoyale round) {
    if(troopCurrent == round.getTroop1()) 
      troopCurrent = round.getTroop2();
    else
      troopCurrent = round.getTroop1();
  }

  private Soldier soldierCurrent() {
    int x, y;
    Soldier soldierCurrent;
    //Verificamos si el soldado de las coordenadas dadas, exite.
    do {
      System.out.println("\n---> " + troopCurrent.getPlayer() + ", elige tu guerrero a mover: ");
      System.out.print("Fila: "); x = sc.nextInt();
      System.out.print("Columna: "); y = sc.nextInt();
      //Leemos el bufer para que sea consumido.
      sc.nextLine();
      soldierCurrent = troopCurrent.selectSoldier(x, y);
    } while (soldierCurrent == null);

    return soldierCurrent;
  }

  public void state(Soldier soldier, BattleRoyale round) {
    boolean start = true;

    while(start) {
      System.out.println("\n---> " + troopCurrent.getPlayer() + ", aventurate a ganar");
      System.out.println("1. Atacar\n" + "2. Adquirir velocidad\n" + "3. Informacion");
      System.out.print("Seleccione una opcion: "); String option = sc.nextLine();

      switch(option) {
        case "1" :
          int[] victimPosition = soldier.attack();
          if(victimPosition == null)
            break;
          //El ataque se esta ejecutando, ahora el atacante debe recibir un respuesta de la
          //victima, por ello cambiamos a la otra tropa.
          change(round);
          // Una vez que cambiamos, debemos buscar a la victima, entonces aqui se usa
          // la posicion de la victima para encontrarla.
          Soldier victim = troopCurrent.selectSoldier(victimPosition[0], victimPosition[1]);
          //Cuando ya tenemos a la victima, tiene opciones para responder, dependiendo de 
          //sus habilidades.
          victim.beAttacked(troopCurrent.getPlayer(), soldier);
          optionBeAttacked(victim, soldier, round);
          start = false; break;
        case "2" :
          soldier.gainSpeed();
          g.update(Picture.mergePicture(Soldier.getBattleRoyale())); g.print();
          start = false; break;
        case "3" :
          System.out.println(soldier.toString()); break;
        default : 
          System.out.println("Opcion no valida."); break;
      }
    }
  }

  private void optionBeAttacked(Soldier victim, Soldier attacker, BattleRoyale round) {
    //Todas las opciones como respuesta al atque  dependen de la probabilidad de ganar y
    //la velocidad de la victima.
    double win = victim.probalityWinning(attacker);
    int x = victim.getPosition()[0];
    int y = victim.getPosition()[1];

    if(win < 50.00 && victim.getSpeed() < 2) {
      //La victima muere y el atacante se mueve a su posicion. En la representacion la
      //victima es eliminida redibujando la representacion del atacante.
      troopCurrent.removeSoldier(victim.didHonor(attacker).getName());
      System.out.println("\nTu guerrero ha muerto luchando con honor hasta el final");
      attacker.move(x, y);
      g.update(Picture.mergePicture(Soldier.getBattleRoyale())); g.print();
    } else {
      String option = null;
      if(win > 50.00 && victim.getSpeed() >= 2) { 
        boolean start = true;

        while(start) {
          System.out.println("\n1. Enfrentar\n2. Escapar\n3. Informacion");
          System.out.print("Seleccione un opcion: "); option = sc.nextLine();
          switch(option) {
            //Si enfrenta, entonces el atacador cambia al estado de victima, esta logica
            //lo realiza el metodo instantAttack.
            case "1" :
              instantAttack(attacker, victim, round); 
              g.update(Picture.mergePicture(Soldier.getBattleRoyale())); g.print();
              start = false; break;
            case "2" :
              victim.escape(troopCurrent.getPlayer());
              attacker.move(x, y);
              g.update(Picture.mergePicture(Soldier.getBattleRoyale())); g.print();
              start = false; break;
            case "3" :
              System.out.println(victim.toString()); break;
            default :
              System.out.println("Opcion no valida."); break;
          }
        }
      }

      if(win > 50.00 && victim.getSpeed() < 2) { 
        boolean start = true;

        while(start) {
          System.out.println("\n1. Enfrentar\n2. Informacion");
          System.out.print("Seleccione un opcion: "); option = sc.nextLine();
          switch(option) {
            case "1" :
              instantAttack(attacker, victim, round);
              g.update(Picture.mergePicture(Soldier.getBattleRoyale())); g.print();
              start = false; break;
            case "2" : 
              System.out.println(victim.toString()); break;
            default :
              System.out.println("Opcion no valida."); break;
          }
        } 
      } else if(win < 50.00) {
        boolean start = true;

        while(start) {
          System.out.println("\n1. Morir con honor\n2. Escapar\n3. Informacion");
          System.out.print("Seleccione un opcion: "); option = sc.nextLine();
          switch(option) {
            case "1" :
              troopCurrent.removeSoldier(victim.didHonor(attacker).getName());
              System.out.println("\nTu guerrero ha muerto luchando con honor hasta el final");
              attacker.move(x, y);
              g.update(Picture.mergePicture(Soldier.getBattleRoyale())); g.print();
              start = false; break;
            case "2" :
              victim.escape(troopCurrent.getPlayer());
              attacker.move(x, y);
              g.update(Picture.mergePicture(Soldier.getBattleRoyale())); g.print();
              start = false; break;
            case "3" :
              System.out.println(victim.toString()); break;
            default :
              System.out.println("Opcion no valida."); break;
          }
        }
      }
    }
  }
  
  private void instantAttack(Soldier victim, Soldier attacker, BattleRoyale round) {
    int x = victim.getPosition()[0];
    int y = victim.getPosition()[1];
    change(round);

    boolean start = true;
    while(start) {
      System.out.println("\n---> " + troopCurrent.getPlayer() + ", fue mala idea atacar");
      if(victim.getSpeed() < 2) {
        System.out.println("\nTu guerrero ha muerto luchando con honor hasta el final");
        troopCurrent.removeSoldier(victim.didHonor(attacker).getName());
        Soldier.getBattleRoyale()[x][y] = BattleRoyale.getCell();
        g.update(Picture.mergePicture(Soldier.getBattleRoyale())); g.print();
        break;
      }

      System.out.println("\n1. Escapar\n2. Morir con honor");
      System.out.print("Seleccione una opcion: "); String option = sc.nextLine();
      switch(option) {
        case "1" :
          victim.escape(troopCurrent.getPlayer());
          g.update(Picture.mergePicture(Soldier.getBattleRoyale())); g.print();
          start = false; break;
        case "2" :
          System.out.println("\nTu guerrero ha muerto luchando con honor hasta el final");
          troopCurrent.removeSoldier(victim.didHonor(attacker).getName());
          Soldier.getBattleRoyale()[x][y] = BattleRoyale.getCell();
          g.update(Picture.mergePicture(Soldier.getBattleRoyale())); g.print();
          start = false;
        default :
          System.out.println("Opcion no valida"); break;
      }
    }
  }
}
