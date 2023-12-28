import com.revature.DAO.UserDAO;
import com.revature.DAOImpl.UserDAOImpl;
import com.revature.Model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.mockito.MockitoAnnotations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.mindrot.jbcrypt.BCrypt;
public class UserDAOImplTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private UserDAO userDAO;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        // Mocking the DriverManager.getConnection call
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        userDAO = new UserDAOImpl(mockConnection);
    }

    @Test
    public void testUpdateName() throws SQLException {
        // Assuming that the update is successful (rowsAffected > 0)
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Perform the test
        userDAO.updateName("test@example.com", "NewName");

        // Verify that the appropriate methods were called
        verify(mockPreparedStatement).setString(1, "NewName");
        verify(mockPreparedStatement).setString(2, "test@example.com");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testLoginUser() throws SQLException {
        // Assuming a user with the given email exists and the password is correct
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("password")).thenReturn(BCrypt.hashpw("password123", BCrypt.gensalt()));

        assertTrue(userDAO.loginUser("test@example.com", "password123"));

        // Verify that the appropriate methods were called
        verify(mockPreparedStatement).setString(1, "test@example.com");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testLoginUserInvalidPassword() throws SQLException {
        // Assuming a user with the given email exists but the password is incorrect
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("password")).thenReturn(BCrypt.hashpw("password456", BCrypt.gensalt()));

        assertFalse(userDAO.loginUser("test@example.com", "password123"));

        // Verify that the appropriate methods were called
        verify(mockPreparedStatement).setString(1, "test@example.com");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testCreateUser() throws SQLException {
        // Assuming that the user creation is successful (rowsAffected > 0)
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Perform the test
        User user = new User("John Doe", "1234567890", "123 Main St", "john@example.com", "password123");
        userDAO.createUser(user);

        // Verify that the appropriate methods were called
        verify(mockPreparedStatement).setString(1, "John Doe");
        verify(mockPreparedStatement).setString(2, "1234567890");
        verify(mockPreparedStatement).setString(3, "123 Main St");
        verify(mockPreparedStatement).setString(4, "john@example.com");
        verify(mockPreparedStatement).setString(5, anyString());  // Password hashing, so any string is fine
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdatePhoneNumber() throws SQLException {
        // Assuming that the update is successful (rowsAffected > 0)
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Perform the test
        userDAO.updatePhoneNumber("test@example.com", "9876543210");

        // Verify that the appropriate methods were called
        verify(mockPreparedStatement).setString(1, "9876543210");
        verify(mockPreparedStatement).setString(2, "test@example.com");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateAddress() throws SQLException {
        // Assuming that the update is successful (rowsAffected > 0)
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Perform the test
        userDAO.updateAddress("test@example.com", "456 Broad St");

        // Verify that the appropriate methods were called
        verify(mockPreparedStatement).setString(1, "456 Broad St");
        verify(mockPreparedStatement).setString(2, "test@example.com");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testChangePassword() throws SQLException {
        // Assuming that the password change is successful (rowsAffected > 0)
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Perform the test
        assertTrue(userDAO.changePassword("test@example.com", "newPassword123"));

        // Verify that the appropriate methods were called
        verify(mockPreparedStatement).setString(1, anyString());  // Password hashing, so any string is fine
        verify(mockPreparedStatement).setString(2, "test@example.com");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testRequestForgottenPassword() throws SQLException {
        // Assuming that the password change is successful (rowsAffected > 0)
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Perform the test
        assertTrue(userDAO.requestForgottenPassword("test@example.com"));

        // Verify that the appropriate methods were called
        verify(mockPreparedStatement).setString(1, anyString());  // Password generation, so any string is fine
        verify(mockPreparedStatement).setString(2, "test@example.com");
        verify(mockPreparedStatement).executeUpdate();
    }


}