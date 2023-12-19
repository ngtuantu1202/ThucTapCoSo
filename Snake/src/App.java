import javax.swing.*;

// Lớp chính của ứng dụng
public class App {
    public static void main(String[] args) throws Exception {
        
        // Chiều rộng và chiều cao của khung trò chơi
        int boardWidth = 600;
        int boardHeight = 600;
        
        // Tạo một JFrame mới với tiêu đề là "Snake"
        JFrame frame = new JFrame("Snake Game");
        frame.setVisible(true); // Hiển thị cửa sổ
        frame.setSize(boardWidth, boardHeight);  // Đặt kích thước của cửa sổ
        frame.setLocationRelativeTo(null); // Đặt vị trí của cửa sổ ở giữa màn hình
        frame.setResizable(false); // Ngăn chặn khả năng thay đổi kích thước của cửa sổ
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Đặt hành động khi đóng cửa sổ là kết thúc chương trình

         // Tạo đối tượng Game và thêm nó vào cửa sổ
        Game game = new Game(boardWidth, boardHeight);
        frame.add(game);
        frame.pack();  // Thực hiện đóng gói cửa sổ sao cho nó phù hợp với kích thước của Game
        game.requestFocus();  // Yêu cầu trò chơi lấy trọng tâm và nhận sự kiện từ bàn phím
    }
}
