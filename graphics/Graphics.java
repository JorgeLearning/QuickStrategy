package graphics;

import javax.swing.*;
/**
 * This class let you see an interpretation of a String array
 * as an graphics image in a window.
 */
public class Graphics{
  private JFrame frame;
  private GPicture gp;
  /**
    * The constructors needs an instance of Picture
    * @param pic a String array that will be interpreted as an graphics
    * image
  * */
  public Graphics(Picture pic){
    frame = new JFrame("Quick Strategy");
    gp = new GPicture(pic);
    frame.add(gp);
    frame.setSize(gp.getWidth() + 5, gp.getHeight() + 40);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
    * Shows the imagen in a JFrame window.
  */
  public void print(){
    frame.repaint();
    frame.setVisible(true);
  }

  public void update(Picture newPic) {
    frame.remove(gp);
    gp = new GPicture(newPic);
    frame.add(gp);
  }
}
