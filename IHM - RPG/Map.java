import java.util.Random;

public class Map {
    char[][] grid;
    int size;

    public Map(int size) {
        this.size = size;
        grid = new char[size][size];
        initialize();
    }

    private void initialize() {
        Random rand = new Random();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                grid[i][j] = rand.nextDouble() < 0.2 ? (rand.nextBoolean() ? 'M' : 'O') : ' ';

        grid[0][0] = 'D';
        grid[size - 1][size - 1] = 'S';
    }

    public void display(int positionx, int positiony) {
        System.out.println("\nCarte du Donjon");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == positionx && j == positiony)
                    System.out.print("J ");
                else
                    System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public char getCell(int x, int y) {
        return grid[x][y];
    }

    public void clearCell(int x, int y) {
        grid[x][y] = ' ';
    }
}
