import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocGiaDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/quanlythuvien";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123456789";

    public void themDocGia(DocGia docGia) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO docgia (TenDocGia, DienThoai, DiaChi) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, docGia.getTenDocGia());
                preparedStatement.setString(2, docGia.getDienThoai());
                preparedStatement.setString(3, docGia.getDiaChi());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void capNhatDocGia(DocGia docGia) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "UPDATE docgia SET TenDocGia = ?, DienThoai = ?, DiaChi = ? WHERE MaDocGia = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, docGia.getTenDocGia());
                preparedStatement.setString(2, docGia.getDienThoai());
                preparedStatement.setString(3, docGia.getDiaChi());
                preparedStatement.setInt(4, docGia.getMaDocGia());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void xoaDocGia(int maDocGia) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "DELETE FROM docgia WHERE MaDocGia = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, maDocGia);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DocGia> layDanhSachDocGia() {
        List<DocGia> danhSachDocGia = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM docgia";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        DocGia docGia = new DocGia();
                        docGia.setMaDocGia(resultSet.getInt("MaDocGia"));
                        docGia.setTenDocGia(resultSet.getString("TenDocGia"));
                        docGia.setDienThoai(resultSet.getString("DienThoai"));
                        docGia.setDiaChi(resultSet.getString("DiaChi"));
                        danhSachDocGia.add(docGia);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachDocGia;
    }
}
