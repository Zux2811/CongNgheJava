import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryManager {

    private static Connection getConnection() throws SQLException {
        // Kết nối đến cơ sở dữ liệu

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/Quanlydean", "root", "123456789");
    }

    // Hàm thêm sách vào cơ sở dữ liệu
    public static void addBook(String maSach, String tenSach, String tenTacGia, String maNhaXuatBan, double giaVon) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO sach (ma_sach, ten_sach, ten_tac_gia, ma_nha_xuat_ban, gia_von) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, maSach);
                preparedStatement.setString(2, tenSach);
                preparedStatement.setString(3, tenTacGia);
                preparedStatement.setString(4, maNhaXuatBan);
                preparedStatement.setDouble(5, giaVon);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hàm đọc thông tin tất cả sách từ cơ sở dữ liệu
    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM sach";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Book book = new Book(
                                resultSet.getString("ma_sach"),
                                resultSet.getString("ten_sach"),
                                resultSet.getString("ten_tac_gia"),
                                resultSet.getString("ma_nha_xuat_ban"),
                                resultSet.getDouble("gia_von")
                        );
                        books.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Hàm cập nhật thông tin sách trong cơ sở dữ liệu
    public static void updateBook(String maSach, String tenSach, String tenTacGia, String maNhaXuatBan, double giaVon) {
        try (Connection connection = getConnection()) {
            String sql = "UPDATE sach SET ten_sach = ?, ten_tac_gia = ?, ma_nha_xuat_ban = ?, gia_von = ? WHERE ma_sach = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, tenSach);
                preparedStatement.setString(2, tenTacGia);
                preparedStatement.setString(3, maNhaXuatBan);
                preparedStatement.setDouble(4, giaVon);
                preparedStatement.setString(5, maSach);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hàm xóa sách khỏi cơ sở dữ liệu
    public static void deleteBook(String maSach) {
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM sach WHERE ma_sach = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, maSach);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class Book {
        private String maSach;
        private String tenSach;
        private String tenTacGia;
        private String maNhaXuatBan;
        private double giaVon;

        public Book(String maSach, String tenSach, String tenTacGia, String maNhaXuatBan, double giaVon) {
            this.maSach = maSach;
            this.tenSach = tenSach;
            this.tenTacGia = tenTacGia;
            this.maNhaXuatBan = maNhaXuatBan;
            this.giaVon = giaVon;
        }


    }
}
