import java.sql.*;

public class DataBaseHandler {

    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + configs.dbHost + ":" + configs.dbPort + "/" + configs.dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, configs.dbUser, configs.dbPass);
        return dbConnection;
    }



    public void signUpUser(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USERS_NAME + "," + Const.USERS_PASSWORD + "," + Const.USERS_EMAIL + ")" + "VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getName());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getEmail());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public ResultSet getUser(User user) {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_EMAIL + " = ? AND " + Const.USERS_PASSWORD + " = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getEmail());
            prSt.setString(2, user.getPassword());
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }

    public void UpdateBalance(User user) throws SQLException, ClassNotFoundException {
        String update = "UPDATE " + Const.USER_TABLE + " SET " + Const.USERS_BALANCE + " = " + Const.USERS_BALANCE + " + ? WHERE " + Const.USERS_EMAIL + " = ?";
        //String update = "UPDATE " + Const.USER_TABLE + " SET " + Const.USERS_BALANCE + " = ? WHERE " + Const.USERS_EMAIL + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setDouble(1,user.getBalance());
            prSt.setString(2, user.getEmail());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
