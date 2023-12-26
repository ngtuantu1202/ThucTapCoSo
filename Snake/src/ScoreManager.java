import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreManager {
    // Đường dẫn đến tệp tin chứa điểm số cao
    private static final String FILE_PATH = "highscores.txt";
    // Số lượng điểm số cao nhất được hiển thị
    private static final int TOP_SCORES_COUNT = 3;

    // Phương thức lưu điểm số mới
    public static void saveScore(String playerName, int score) {
        // Tải danh sách điểm số từ tệp tin
        List<ScoreEntry> scores = loadScores();
        // Thêm điểm số mới vào danh sách
        scores.add(new ScoreEntry(playerName, score));
        // Sắp xếp danh sách theo thứ tự giảm dần
        Collections.sort(scores, Comparator.reverseOrder());
        // Giới hạn danh sách chỉ còn TOP_SCORES_COUNT phần tử
        scores = scores.subList(0, Math.min(TOP_SCORES_COUNT, scores.size()));

        // Ghi danh sách điểm số vào tệp tin
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ScoreEntry entry : scores) {
                // Ghi từng dòng: "Tên Người Chơi, Điểm Số"
                writer.write(entry.getPlayerName() + "," + entry.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức tải danh sách điểm số từ tệp tin
    public static List<ScoreEntry> loadScores() {
        List<ScoreEntry> scores = new ArrayList<>();

        // Đọc từ tệp tin và xây dựng danh sách điểm số
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String playerName = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    scores.add(new ScoreEntry(playerName, score));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sắp xếp danh sách theo thứ tự giảm dần
        Collections.sort(scores, Comparator.reverseOrder());
        return scores;
    }

    // Lớp lưu trữ thông tin về một điểm số
    public static class ScoreEntry implements Comparable<ScoreEntry> {
        private String playerName;
        private int score;

        // Constructor
        public ScoreEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        // Phương thức truy cập tên người chơi
        public String getPlayerName() {
            return playerName;
        }

        // Phương thức truy cập điểm số
        public int getScore() {
            return score;
        }

        // Phương thức so sánh để sắp xếp
        @Override
        public int compareTo(ScoreEntry other) {
            return Integer.compare(this.score, other.score);
        }
    }
}
