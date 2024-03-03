import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    private final Game game;
    private final Timer timer;

    public GamePanel() {
        int delay = 8;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        game = new Game(new Player(), new Ball(), new Level(4, 12));
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {
        // background
        graphics.setColor(Color.black);
        graphics.fillRect(1, 1, 692, 592);

        game.level.draw((Graphics2D) graphics);

        // borders
        graphics.setColor(Color.yellow);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(691, 0, 3, 592);

        // the scores
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("" + game.score, 590, 30);

        // the paddle
        graphics.setColor(Color.green);
        graphics.fillRect(game.player.x, 550, 100, 8);

        // the ball
        graphics.setColor(Color.yellow);
        graphics.fillOval(game.ball.x, game.ball.y, 20, 20);

        game.update(new GameDelegate() {
            @Override
            public void didWin(Game game) {
                graphics.setColor(Color.RED);
                graphics.setFont(new Font("serif",Font.BOLD, 30));
                graphics.drawString("You Won", 260,300);

                graphics.setColor(Color.RED);
                graphics.setFont(new Font("serif",Font.BOLD, 20));
                graphics.drawString("Press (Enter) to Restart", 230,350);
            }

            @Override
            public void didLose(Game game) {
                graphics.setColor(Color.RED);
                graphics.setFont(new Font("serif",Font.BOLD, 30));
                graphics.drawString("Game Over, Scores: " + game.score, 190,300);

                graphics.setColor(Color.RED);
                graphics.setFont(new Font("serif",Font.BOLD, 20));
                graphics.drawString("Press (Enter) to Restart", 230,350);
            }
        });

        graphics.dispose();
    }

    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                game.moveRight();
                break;
            case KeyEvent.VK_LEFT:
                game.moveLeft();
                break;
            case KeyEvent.VK_ENTER:
                if (!game.isPlaying) {
                    game.restart();
                    repaint();
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void actionPerformed(ActionEvent event) {
        timer.start();
        if (game.isPlaying) {
            if (new Rectangle(game.ball.x, game.ball.y, 20, 20).intersects(new Rectangle(game.player.x, 550, 30, 8))) {
                game.ball.yDirection = -game.ball.yDirection;
                game.ball.xDirection = -2;
            } else if (new Rectangle(game.ball.x, game.ball.y, 20, 20).intersects(new Rectangle(game.player.x + 70, 550, 30, 8))) {
                game.ball.yDirection = -game.ball.yDirection;
                game.ball.xDirection = game.ball.xDirection + 1;
            } else if (new Rectangle(game.ball.x, game.ball.y, 20, 20).intersects(new Rectangle(game.player.x + 30, 550, 40, 8))) {
                game.ball.yDirection = -game.ball.yDirection;
            }

            // check map collision with the ball
            A:
            for (int i = 0; i < game.level.map.length; i++) {
                for (int j = 0; j < game.level.map[0].length; j++) {
                    if (game.level.map[i][j] > 0) {
                        //scores++;
                        int brickX = j * game.level.brickWidth + 80;
                        int brickY = i * game.level.brickHeight + 50;
                        int brickWidth = game.level.brickWidth;
                        int brickHeight = game.level.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(game.ball.x, game.ball.y, 20, 20);

                        if (ballRect.intersects(rect)) {
                            game.level.setBrickValue(0, i, j);
                            game.score += 5;
                            game.totalBricks--;

                            // when ball hit right or left of brick
                            if (game.ball.x + 19 <= rect.x || game.ball.x + 1 >= rect.x + rect.width) {
                                game.ball.xDirection = -game.ball.xDirection;
                            }
                            // when ball hits top or bottom of brick
                            else {
                                game.ball.yDirection = -game.ball.yDirection;
                            }

                            break A;
                        }
                    }
                }
            }

            game.ball.x += game.ball.xDirection;
            game.ball.y += game.ball.yDirection;

            if (game.ball.x < 0) {
                game.ball.xDirection = -game.ball.xDirection;
            }
            if (game.ball.y < 0) {
                game.ball.yDirection = -game.ball.yDirection;
            }
            if (game.ball.x > 670) {
                game.ball.xDirection = -game.ball.xDirection;
            }

            repaint();
        }
    }
}
