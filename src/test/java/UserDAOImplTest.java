import com.revature.DAO.UserDAO;
import com.revature.DAOImpl.UserDAOImpl;
import com.revature.Model.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class UserDAOImplTest {

    private Connection connectionMock;
    private PreparedStatement preparedStatementMock;
    private ResultSet resultSetMock;
    private UserDAO userDAO;

    @Before
    public void setup() throws SQLException {
        connectionMock = mock(Connection.class);
        preparedStatementMock = mock(PreparedStatement.class);
        resultSetMock = mock(ResultSet.class);

        userDAO = new UserDAOImpl(connectionMock);
    }

    @Test
    public void testCreateUser() throws SQLException {
        User user = new User("John Doe", "1234567890", "123 Main St", "john@example.com", "hashedPassword");

        // Mocking PreparedStatement
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(1); // Assuming one row is affected

        // Performing the test
        userDAO.createUser(user);

        // Verifying that the PreparedStatement was called with the correct parameters
        verify(preparedStatementMock).setString(1, user.getName());
        verify(preparedStatementMock).setString(2, user.getPhoneNumber());
        verify(preparedStatementMock).setString(3, user.getAddress());
        verify(preparedStatementMock).setString(4, user.getEmail());
        verify(preparedStatementMock).setString(5, user.getHashedPassword());
        verify(preparedStatementMock).executeUpdate();
    }

    @Test
    public void testLoginUser() throws SQLException {
        String userEmail = "john@example.com";
        String userPassword = "password123";

        // Mocking PreparedStatement and ResultSet
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true); // Assuming user exists
        when(resultSetMock.getString("password")).thenReturn("$2a$10$RmWdYkXYb/9zjYhvwE4KNeEva3z3ZveVK34WxS1HyOlC0Lh.Cgl3u"); // Hashed password

        // Performing the test
        boolean result = userDAO.loginUser(userEmail, userPassword);

        // Verifying that the PreparedStatement was called with the correct parameters
        verify(preparedStatementMock).setString(1, userEmail);
        verify(preparedStatementMock).executeQuery();

        // Verifying the result
        assertTrue(result);
    }
}
