import java.awt.*;

public class Game {
    final Player player;
    final Ball ball;
    Level level;
    boolean isPlaying = false;
    int score = 0;
    int totalBricks = 48;

    public Game(Player player, Ball ball) {
        this.player = player;
        this.ball = ball;
        this.level = new Level(4, 12);
    }

    void checkState(GameDelegate delegate) {
        if (totalBricks <= 0) {
            isPlaying = false;
            ball.xDirection = 0;
            ball.yDirection = 0;
            delegate.didWin(this);
        }

        if (ball.y > 570) {
            isPlaying = false;
            ball.xDirection = 0;
            ball.yDirection = 0;
            delegate.didLose(this);
        }
    }

    void update() {
        if (!isPlaying) { return; }

        if (new Rectangle(
            ball.x, ball.y, 20, 20
        ).intersects(new Rectangle(
            player.x, 550, 30, 8
        ))) {
            ball.yDirection = -ball.yDirection;
            ball.xDirection = -2;
        } else if (new Rectangle(
            ball.x, ball.y, 20, 20
        ).intersects(new Rectangle(
            player.x + 70, 550, 30, 8
        ))) {
            ball.yDirection = -ball.yDirection;
            ball.xDirection = ball.xDirection + 1;
        } else if (new Rectangle(
            ball.x, ball.y, 20, 20
        ).intersects(new Rectangle(
            player.x + 30, 550, 40, 8
        ))) {
            ball.yDirection = -ball.yDirection;
        }

        // check map collision with the ball
        A:
        for (int i = 0; i < level.map.length; i++) {
            for (int j = 0; j < level.map[0].length; j++) {
                if (level.map[i][j] > 0) {
                    //scores++;
                    int brickX = j * level.brickWidth + 80;
                    int brickY = i * level.brickHeight + 50;
                    int brickWidth = level.brickWidth;
                    int brickHeight = level.brickHeight;

                    Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle ballRect = new Rectangle(ball.x, ball.y, 20, 20);

                    if (ballRect.intersects(rect)) {
                        level.setBrickValue(0, i, j);
                        score += 5;
                        totalBricks--;

                        // when ball hit right or left of brick
                        if (ball.x + 19 <= rect.x || ball.x + 1 >= rect.x + rect.width) {
                            ball.xDirection = -ball.xDirection;
                        }
                        // when ball hits top or bottom of brick
                        else {
                            ball.yDirection = -ball.yDirection;
                        }

                        break A;
                    }
                }
            }
        }

        ball.x += ball.xDirection;
        ball.y += ball.yDirection;

        if (ball.x < 0) {
            ball.xDirection = -ball.xDirection;
        }
        if (ball.y < 0) {
            ball.yDirection = -ball.yDirection;
        }
        if (ball.x > 670) {
            ball.xDirection = -ball.xDirection;
        }
    }

    void restart() {
        isPlaying = true;
        ball.x = 120;
        ball.y = 350;
        ball.xDirection = -1;
        ball.yDirection = -2;
        player.x = 310;
        score = 0;
        totalBricks = 21;
        level = new Level(3, 7);
    }

    void moveLeft() {
        if (player.x < 10) {
            player.x = 10;
        } else {
            isPlaying = true;
            player.x -= 20;
        }
    }

    void moveRight() {
        if (player.x >= 600) {
            player.x = 600;
        } else {
            isPlaying = true;
            player.x += 20;
        }
    }
}
