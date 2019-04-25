import java.sql.*;

public class Sql {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL="jdbc:mysql://localhost:3306/UA?useSSL=false";
    static final String DB_URL = "jdbc:mysql://localhost:3306/UA?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
    static final String USER = "root";
    static final String PASS = "a123456";
    static Connection con = null;
    static Statement st = null;
    static ResultSet rs = null;

    /*    public static void main(String[] args){
            Establish();
            InputData(172054201,123);
        }*/
    public static void Establish() {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("连接数据库！");


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void InputData(int num, int cod) {
        try {
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            st = con.createStatement();
            String test = "INSERT INTO register values ('" + num + "', '" + cod + "')";
            st.executeUpdate(test);
            System.out.println("数据传输成功！");
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean Search(int num, int co) {
        try {
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM register WHERE account ='" + num + "' AND code ='" + co + "'");
            rs = st.executeQuery("SELECT * FROM register");
//            if(rs.first()){
//                rs.close();
//                st.close();
//                con.close();
//                return true;
//            }
            while (rs.first()) {
                int x = rs.getInt(1);
                int y = rs.getInt(2);
                if (num == x || co == y) {
                    rs.close();

                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

