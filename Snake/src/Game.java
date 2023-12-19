import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener{
    // Lớp lưu trữ thông tin về các ô trên bảng trò chơi
    private class Tile {
        int x;
        int y;

        Tile(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
    // Kích thước của bảng trò chơi và kích thước của mỗi ô
    int boardWidth = 600;
    int boardHeight = 600;
    int tileSize =25;

    // Đối tượng đại diện cho đầu rắn
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    // Đối tượng đại diện cho thức ăn
    Tile food;
    Random random;
    // Game Logic
    Timer gameLoop;
    int velocityX; //khai báo vận tốc
    int velocityY;
    boolean gameOver = false;
    int highScore = 0;
    // Constructor của lớp Game
    Game(int boardWidth, int boardHeight)
    {
        // Thiết lập kích thước và màu nền của JPanel
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        // Khởi tạo đầu rắn ở vị trí ban đầu
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();
        // Khởi tạo thức ăn ở một vị trí khác
        food = new Tile(10,10); // Ban đầu, đặt thức ăn ở vị trí (10, 10)
        random  = new Random();
        placeFood(); // Gọi để đặt lại vị trí của thức ăn

        velocityX = 0;
        velocityY = 0;
        gameLoop = new Timer(100, this);  //vận tốc
        gameLoop.start();
    }

    // Phương thức vẽ đồ họa trên JPanel
    public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            draw(g);
        }
    // Phương thức vẽ đối tượng trên bảng trò chơi
    public void draw(Graphics g)
    {
        // //Vẽ lưới x1,y1,x2,y2
        // for(int i=0; i < boardWidth/tileSize; i++)
        // {
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
        //     g.drawLine(0 , i*tileSize, boardWidth, i*tileSize);
        // }

        //Vẽ thức ăn
        g.setColor(Color.RED);
        //g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        // Vẽ đầu rắn
        g.setColor(Color.GREEN);
        //g.fillRect(snakeHead.x * tileSize, snakeHead.y *tileSize, tileSize, tileSize); 
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y *tileSize, tileSize, tileSize, true); // Vẽ đầu rắn tại vị trí (x, y) với kích thước là tileSize
        // Vẽ thân rắn
        for (int i= 0; i < snakeBody.size(); i++) 
        {
            Tile snakePart = snakeBody.get(i);
            // g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        // Điểm số
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("Kết thúc: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
            g.drawString("Điểm số cao nhất: " + String.valueOf(highScore), tileSize - 16, tileSize + 20);
            g.drawString("Nhấn SPACE để chơi lại", tileSize - 16, tileSize + 40);
        } else {
            g.drawString("Điểm số: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
            g.drawString("Điểm số cao nhất: " + String.valueOf(highScore), tileSize - 16, tileSize + 20);
        }
    }
    // Phương thức đặt thức ăn ở vị trí ngẫu nhiên
    public void placeFood()
    {
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
    }

    // Phương thức kiểm tra va chạm giữa hai ô
    public boolean collision(Tile tile1, Tile tile2)
    {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
    // Phương thức di chuyển rắn
    public void move()
    {
        // Ăn mồi
        if (collision(snakeHead, food))
        {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // Di chuyển thân rắn
        for (int i = snakeBody.size()-1; i >= 0; i--)
        {
            Tile snakePart = snakeBody.get(i);
            if (i==0)
            {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else
            {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // Di chuyển đầu rắn
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
        // Kiểm tra va chạm -> END GAME
        for (int i=0; i< snakeBody.size();i++)
        {
            Tile snakePart = snakeBody.get(i);
            // Va chạm với bản thân
            if (collision(snakeHead, snakePart)) 
            {
                gameOver = true;
            }
        }
        // Va chạm với tường
        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight)
        {
            gameOver = true;
        }
        // Cập nhật điểm cao nhất
        if (gameOver) {
            gameLoop.stop();
            updateHighScore();
        }

    }
    // Phương thức được gọi mỗi lần Timer chạy
    @Override
    public void actionPerformed(ActionEvent e) {
        
        move();
        repaint();
        if(gameOver)
        {
            gameLoop.stop();
        }
    }
    // Phương thức xử lý sự kiện khi phím được nhấn
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1)
        {
            velocityX = 0;
            velocityY = -1; 
        } 
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1)
        {
            velocityX = 0;
            velocityY = 1; 
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1)
        {
            velocityX = -1;
            velocityY = 0; 
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1)
        {
            velocityX = 1;
            velocityY = 0; 
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver) 
        {
            restartGame();
        }
    }
    public void restartGame() {
        snakeHead = new Tile(5, 5);
        snakeBody.clear();
        placeFood();
        velocityX = 0;
        velocityY = 0;
        gameOver = false;
        gameLoop.restart();
    }

    public void updateHighScore() {
        if (snakeBody.size() > highScore) {
            highScore = snakeBody.size();
        }
    }
    ///////////////////////////////////Phương thức không cần thiết, được yêu cầu bởi giao diện KeyListener/////////////////////////////////
    @Override
    public void keyTyped(KeyEvent e) {     
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
