import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {

    public static void main(String[] args) throws Exception {

        String s = "select * from Statement, Account where Statement.account_id = Account.id";
        String preparedStatment = "select * from (select account_id, CDEC(val(amount)) AS dec_amount, Format(CDate(Replace(datefield, \".\", \"-\")), \"dd/mm/yyyy\") AS formatted_date from Statement) as q1" +
            " where CDate(formatted_date) BETWEEN ? AND ? AND CDec(dec_amount) >= ? AND  CDec(dec_amount) <= ?";
        String databaseURL = "jdbc:ucanaccess:///Users/sqasem/IdeaProjects/BM-test/target/classes/accountsdb.accdb";

        Connection connection = DriverManager.getConnection(databaseURL);

        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement = connection.prepareStatement(preparedStatment);
        preparedStatement.setString(1, "2012-01-13");
        preparedStatement.setString(2, "2012-01-13");
        preparedStatement.setString(3, "651.993429533135");
        preparedStatement.setString(4, "651.993429533135");
        ResultSet resultSet = statement.executeQuery(s);

        while (resultSet.next()) {
//            System.out.println(resultSet.getString("ID") + "  "+ resultSet.getString("account_id")+ "  "+ resultSet.getDate("datefield")
//                + "  "+ resultSet.getString("amount"));

            System.out.println(resultSet.getString("account_id") + " " + resultSet.getString("account_number"));
//            System.out.println(resultSet.getString(1));
//            System.out.println(resultSet.getString(1));
        }
    }
}
