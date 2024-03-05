import java.awt.*;

public class Level {
    public int[][] bricks;
    int rows;
    int columns;

    public Level(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.bricks = new int[rows][columns];

        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                bricks[i][j] = 1;
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        bricks[row][col] = value;
    }

    public Pair<Integer, Integer> getBrickDimensions() {
        return new Pair<>(
            540 / columns,
            150 / rows
        );
    }
}
