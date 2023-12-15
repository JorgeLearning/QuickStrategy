import java.util.*;
import graphics.*; 

public class BattleRoyale {
  private Picture[][] battleRoyale;
  private Troop troop1; 
  private Troop troop2; 
  private static final Picture cell = Picture.table();
  private Random random = new Random();

  public BattleRoyale(String player1, String player2) {
    /* Los soldados se incializan con un color por defecto, en esta ocasion
     * como solo tenemos dos tropas diferentes, entonces una se inicializara con
     * el color por defecto, pero la otra si tendremos que establecer su color
     */
    troop1 = new Troop(player1);
    troop2 = new Troop(player2);
    troop2.setColor(); 
    setField(10, 10);
    positionSoldiers();
  }

  private void setField(int r, int c) {
    battleRoyale = new Picture[r][c];
    for(int i = 0; i < r; i++)
      for(int j = 0; j < c; j++)
        battleRoyale[i][j] = cell;
  }

  private void positionSoldiers() {
    int i = 0, t1 = 0, t2 = 0;
    int len1 = troop1.getTroop().size();
    int len2 = troop2.getTroop().size();

    while(i < len1 + len2) {
      int x = random.nextInt(10);
      int y = random.nextInt(10);
      if(battleRoyale[x][y] == cell) {
        if(random.nextBoolean() && t1 < len1) {
          battleRoyale[x][y] = troop1.getTroop().get(t1).getSoldier().superponer(battleRoyale[x][y]);
          troop1.getTroop().get(t1).setPosition(x, y);
          t1++; i++;
        } else if (t2 < len2) {
          battleRoyale[x][y] = troop2.getTroop().get(t2).getSoldier().superponer(battleRoyale[x][y]);
          troop2.getTroop().get(t2).setPosition(x, y);
          t2++; i++;
        }
      }
    }

    /*Como el atributo battleRoyale es static, se refleja en todos los soldados y asi todos ellos
     * conocen el campo de batalla, esto es muy importante.
     */
    Soldier.setBattleRoyale(battleRoyale);
  }

  public Troop getTroop1() {
    return troop1;
  }

  public Troop getTroop2() {
    return troop2;
  }

  // Esta celda es especial para hacer comparaciones por referencia, sin importar el contenido
  public static Picture getCell() {
    return cell;
  }
}
