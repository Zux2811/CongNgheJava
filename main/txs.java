import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuanLyThuVienDAO {
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/Quanlythuvien";
        String username = "root";
        String password = "123456789";
        return DriverManager.getConnection(jdbcURL, username, password);
    }

    // Thêm sách
    public void themSach(String maSach, String tenSach, String tenTacGia, String maNXB, double giaVon) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO sach (ma_sach, ten_sach, ten_tac_gia, ma_nxb, gia_von) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, maSach);
                statement.setString(2, tenSach);
                statement.setString(3, tenTacGia);
                statement.setString(4, maNXB);
                statement.setDouble(5, giaVon);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xem danh sách sách
    public void xemDanhSachSach() {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM sach";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println("Mã sách: " + resultSet.getString("ma_sach"));
                        System.out.println("Tên sách: " + resultSet.getString("ten_sach"));
                        System.out.println("Tên tác giả: " + resultSet.getString("ten_tac_gia"));
                        System.out.println("Mã NXB: " + resultSet.getString("ma_nxb"));
                        System.out.println("Giá vốn: " + resultSet.getDouble("gia_von"));
                        System.out.println("------------------------");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Sửa thông tin sách
    public void suaThongTinSach(String maSach, String tenSach, String tenTacGia, String maNXB, double giaVon) {
        try (Connection connection = getConnection()) {
            String sql = "UPDATE sach SET ten_sach = ?, ten_tac_gia = ?, ma_nxb = ?, gia_von = ? WHERE ma_sach = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, tenSach);
                statement.setString(2, tenTacGia);
                statement.setString(3, maNXB);
                statement.setDouble(4, giaVon);
                statement.setString(5, maSach);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa sách
    public void xoaSach(String maSach) {
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM sach WHERE ma_sach = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, maSach);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        QuanLyThuVienDAO dao = new QuanLyThuVienDAO();

        // Thêm sách
        dao.themSach("S001", "Java Programming", "John Doe", "NXB001", 150000);

        // Xem danh sách sách
        dao.xemDanhSachSach();

        // Sửa thông tin sách
        dao.suaThongTinSach("S001", "Java Programming (Updated)", "John Doe", "NXB001", 180000);

        // Xem danh sách sách sau khi sửa
        dao.xemDanhSachSach();

        // Xóa sách
        dao.xoaSach("S001");

        // Xem danh sách sách sau khi xóa
        dao.xemDanhSachSach();
    }
}
