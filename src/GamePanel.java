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

    public void paint(Graphics g) {
        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        game.level.draw((Graphics2D) g);

        // borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // the scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + game.score, 590, 30);

        // the paddle
        g.setColor(Color.green);
        g.fillRect(game.player.x, 550, 100, 8);

        // the ball
        g.setColor(Color.yellow);
        g.fillOval(game.ball.x, game.ball.y, 20, 20);

        game.update(new GameDelegate() {
            @Override
            public void didWin(Game game) {
                g.setColor(Color.RED);
                g.setFont(new Font("serif",Font.BOLD, 30));
                g.drawString("You Won", 260,300);

                g.setColor(Color.RED);
                g.setFont(new Font("serif",Font.BOLD, 20));
                g.drawString("Press (Enter) to Restart", 230,350);
            }

            @Override
            public void didLose(Game game) {
                g.setColor(Color.RED);
                g.setFont(new Font("serif",Font.BOLD, 30));
                g.drawString("Game Over, Scores: " + game.score, 190,300);

                g.setColor(Color.RED);
                g.setFont(new Font("serif",Font.BOLD, 20));
                g.drawString("Press (Enter) to Restart", 230,350);
            }
        });

        g.dispose();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.moveRight();
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            game.moveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!game.isPlaying) {
                game.restart();
                repaint();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
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
