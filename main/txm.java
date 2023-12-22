import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MuonDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/quanlythuvien";
    private static final String USER = "root";
    private static final String PASSWORD = "MatKhauCuaBan";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void themMuon(String soPhieuMuon, String maDocGia, String ngayMuon) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO muon (so_phieu_muon, ma_doc_gia, ngay_muon) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, soPhieuMuon);
            preparedStatement.setString(2, maDocGia);
            preparedStatement.setString(3, ngayMuon);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void suaMuon(String soPhieuMuon, String maDocGia, String ngayMuon) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE muon SET ma_doc_gia = ?, ngay_muon = ? WHERE so_phieu_muon = ?")) {

            preparedStatement.setString(1, maDocGia);
            preparedStatement.setString(2, ngayMuon);
            preparedStatement.setString(3, soPhieuMuon);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void xoaMuon(String soPhieuMuon) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM muon WHERE so_phieu_muon = ?")) {

            preparedStatement.setString(1, soPhieuMuon);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void xuatMuon() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM muon");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String soPhieuMuon = resultSet.getString("so_phieu_muon");
                String maDocGia = resultSet.getString("ma_doc_gia");
                String ngayMuon = resultSet.getString("ngay_muon");

                System.out.println("Số phiếu mượn: " + soPhieuMuon);
                System.out.println("Mã đọc giả: " + maDocGia);
                System.out.println("Ngày mượn: " + ngayMuon);
                System.out.println("--------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MuonDAO muonDAO = new MuonDAO();

        // Thêm phiếu mượn
        muonDAO.themMuon("PM001", "DG001", "2023-01-01");

        // Sửa phiếu mượn
        muonDAO.suaMuon("PM001", "DG002", "2023-02-01");

        // Xóa phiếu mượn
        muonDAO.xoaMuon("PM001");

        // Xuất danh sách phiếu mượn
        muonDAO.xuatMuon();
    }
}
