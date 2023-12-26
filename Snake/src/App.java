import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Kích thước bảng trò chơi
        int boardWidth = 600;
        int boardHeight = 600;

        // Tạo khung trò chơi
        JFrame frame = createGameFrame(boardWidth, boardHeight);

        // Nhập tên người chơi
        String playerName = getPlayerName();

        // Tạo đối tượng trò chơi
        Game game = new Game(boardWidth, boardHeight, playerName);

        // Bắt đầu trò chơi
        startGame(frame, game);
    }

    // Phương thức tạo JFrame cho trò chơi
    private static JFrame createGameFrame(int width, int height) {
        JFrame frame = new JFrame("Snake Game");
        frame.setVisible(true); // Hiển thị khung trò chơi
        frame.setSize(width, height); // Đặt kích thước của khung trò chơi
        frame.setLocationRelativeTo(null); // Đặt vị trí của khung trò chơi ở giữa màn hình
        frame.setResizable(false); // Không cho phép thay đổi kích thước khung trò chơi
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kết thúc chương trình khi đóng khung trò chơi
        return frame;
    }

    // Phương thức nhận tên người chơi từ người dùng
    private static String getPlayerName() {
        String playerName = JOptionPane.showInputDialog("Nhập tên của bạn:"); // Hiển thị hộp thoại để nhập tên
        return (playerName == null || playerName.trim().isEmpty()) ? "Người chơi" : playerName;
        // Trả về tên người chơi hoặc giá trị mặc định nếu người chơi không nhập tên
    }

    // Phương thức bắt đầu trò chơi
    private static void startGame(JFrame frame, Game game) {
        frame.add(game); // Thêm đối tượng trò chơi vào khung trò chơi
        frame.pack(); // Đóng gói lại khung trò chơi để nó hiển thị đúng kích thước của đối tượng trò chơi
        game.requestFocus(); // Yêu cầu trò chơi lấy trọng tâm và nhận sự kiện từ bàn phím
    }
}
