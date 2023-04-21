import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int WATER = 1;
  public static final int EARTH = 2;
  public static final int FIRE = 3;
  public static final int AIR = 4;

  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols) {
    String[] names;
    names = new String[5];
    names[EMPTY] = "Empty";
    names[WATER] = "Water";
    names[EARTH] = "Earth";
    names[FIRE] = "Fire";
    names[AIR] = "Air";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int[numRows][numCols];
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool) {
    grid[row][col] = tool;

  }

  //copies each element of grid into the display
  public void updateDisplay() {
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (grid[row][col] == EMPTY) {
          display.setColor(row, col, Color.BLACK);
        } else if (grid[row][col] == WATER) {
          display.setColor(row, col, Color.BLUE);
        } else if (grid[row][col] == EARTH) {
          display.setColor(row, col, new Color(50, 15, 18));
        } else if (grid[row][col] == FIRE) {
          display.setColor(row, col, Color.RED);
        } else if (grid[row][col] == AIR) {
          display.setColor(row, col, Color.CYAN);
        }
      }
    }
  }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step() {
    int row = (int)(Math.random() * grid.length);
    int col = (int)(Math.random() * grid[0].length);
    if (grid[row][col] != EMPTY) {
      if (grid[row][col] == WATER) {
        waterBehavior(row, col);
      } else if (grid[row][col] == EARTH) {
        earthBehavior(row, col);
      } else if (grid[row][col] == FIRE) {
        fireBehavior(row, col);
      } else if (grid[row][col] == AIR) {
        airBehavior(row, col);
      }
    }
  }

  private void waterBehavior(int row, int col) {
    // if space below is empty, move there
    int direction = 0;
    if (row + 1 < grid.length && grid[row + 1][col] == EMPTY) {
      direction = 1;
      grid[row + 1][col] = WATER;
      grid[row][col] = EMPTY;
    }
    // randomly move left or right otherwise
    else if (row + 1 < grid.length && grid[row + 1][col] != EMPTY) {
      int random = (int)(Math.random() * 2);
      if (random == 0 && col - 1 >= 0 && grid[row][col - 1] == EMPTY) {
        direction = 2;
        grid[row][col - 1] = WATER;
        grid[row][col] = EMPTY;
      } else if (random == 1 && col + 1 < grid[0].length && grid[row][col + 1] == EMPTY) {
        direction = 3;
        grid[row][col + 1] = WATER;
        grid[row][col] = EMPTY;
      }
    }

    // water has a random chance of turning into air
    int random2 = (int)(Math.random() * 10000);
    if (random2 == 0) {
      if (direction == 0) {
        grid[row][col] = AIR;
      } else if (direction == 1) {
        grid[row + 1][col] = AIR;
      } else if (direction == 2) {
        grid[row][col - 1] = AIR;
      } else if (direction == 3) {
        grid[row][col + 1] = AIR;
      }
    }
  }

  private void fireBehavior(int row, int col){
    int random = (int)(Math.random() * 50);
    if (random == 0) {
      grid[row][col] = EMPTY;
    }
    else {
        int random2 = (int)(Math.random() * 4);
        if (random2 == 0 && row - 1 >= 0) {
          if (grid[row - 1][col] == EMPTY) {
            grid[row - 1][col] = FIRE;
            grid[row][col] = EMPTY;
          } else if (grid[row - 1][col] == WATER) {
            grid[row - 1][col] = AIR;
            grid[row][col] = EMPTY;
          }
        } else if (random2 == 1 && row + 1 < grid.length) {
          if (grid[row + 1][col] == EMPTY) {
            grid[row + 1][col] = FIRE;
            grid[row][col] = EMPTY;
          } else if (grid[row + 1][col] == WATER) {
            grid[row + 1][col] = AIR;
            grid[row][col] = EMPTY;
          }
        } else if (random2 == 2 && col - 1 >= 0) {
          if (grid[row][col - 1] == EMPTY) {
            grid[row][col - 1] = FIRE;
            grid[row][col] = EMPTY;
          } else if (grid[row][col - 1] == WATER) {
            grid[row][col - 1] = AIR;
            grid[row][col] = EMPTY;
          }
        } else if (random2 == 3 && col + 1 < grid[0].length) {
          if (grid[row][col + 1] == EMPTY) {
            grid[row][col + 1] = FIRE;
            grid[row][col] = EMPTY;
          } else if (grid[row][col + 1] == WATER) {
            grid[row][col + 1] = AIR;
            grid[row][col] = EMPTY;
          }
        }
      else {
        grid[row][col] = EMPTY;
      }
    }
  }

  private void earthBehavior(int row, int col) {
    // if space below is empty, move there
    if (row + 1 < grid.length && grid[row + 1][col] == EMPTY) {
      grid[row + 1][col] = EARTH;
      grid[row][col] = EMPTY;
    }
    // if space below is water or air move there
    else if (row + 1 < grid.length && (grid[row + 1][col] == WATER || grid[row + 1][col] == AIR)) {
      int temp = grid[row + 1][col];
      grid[row + 1][col] = EARTH;
      grid[row][col] = temp;
    }
  }

    private void airBehavior(int row, int col) {
        // air should move in a random direction
        int direction = 0;
        int random = (int)(Math.random() * 6);
        if (random < 3 && row - 1 >= 0) {
          direction = 1;
            int temp = grid[row - 1][col];
            grid[row - 1][col] = AIR;
            grid[row][col] = temp;
        } else if (random == 3 && row + 1 < grid.length && grid[row + 1][col] == EMPTY) {
            direction = 2;
          grid[row + 1][col] = AIR;
            grid[row][col] = EMPTY;
        } else if (random == 4 && col - 1 >= 0 && grid[row][col - 1] == EMPTY) {
            direction = 3;
          grid[row][col - 1] = AIR;
            grid[row][col] = EMPTY;
        } else if (random == 5 && col + 1 < grid[0].length && grid[row][col + 1] == EMPTY) {
          direction = 4;
          grid[row][col + 1] = AIR;
            grid[row][col] = EMPTY;
        }

        // air has a random chance of turning into water
      int random2 = (int)(Math.random() * 10000);
      if (random2 == 0) {
        if (direction == 0) {
          grid[row][col] = WATER;
        } else if (direction == 1) {
          grid[row - 1][col] = WATER;
        } else if (direction == 2) {
          grid[row + 1][col] = WATER;
        } else if (direction == 3) {
          grid[row][col - 1] = WATER;
        } else if (direction == 4) {
          grid[row][col + 1] = WATER;
        }
      }
    }
  
  //do not modify
  public void run() {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
