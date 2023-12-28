import com.revature.DAOImpl.UserDAOImpl;
import com.revature.Model.Plan;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.User.MainApplication;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.slf4j.Logger;




import java.sql.SQLException;

import java.util.Scanner;


import static org.mockito.Mockito.*;



public class MainApplicationTest {

    @Mock
    private UserDAOImpl userDAOMock;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginUserSuccess() throws SQLException {
        // Arrange
        MainApplication mainApp = new MainApplication();
        mainApp.logger = mock(Logger.class);

        when(userDAOMock.loginUser(anyString(), anyString())).thenReturn(true);

        // Act
        mainApp.loginUser(userDAOMock, new Scanner(System.in));

        // Assert
        assertTrue(mainApp.loggedIn);
    }

    @Test
    public void testLoginUserFailure() throws SQLException {
        // Arrange
        MainApplication mainApp = new MainApplication();
        mainApp.logger = mock(Logger.class);

        when(userDAOMock.loginUser(anyString(), anyString())).thenReturn(false);

        // Act
        mainApp.loginUser(userDAOMock, new Scanner(System.in));

        // Assert
        assertFalse(mainApp.loggedIn);
    }

    @Test
    public void testSubscribeToPlan() throws SQLException {
        // Arrange
        MainApplication mainApp = new MainApplication();
        mainApp.logger = mock(Logger.class);

        Plan plan = new Plan(/* initialize plan */);
        when(userDAOMock.getPlanById(anyInt())).thenReturn(plan);
        when(userDAOMock.getServiceIdForPlan(anyInt())).thenReturn(1);
        when(userDAOMock.getUserIdByEmail(anyString())).thenReturn(1);
        when(userDAOMock.subscribeToPlan(anyInt(), anyInt(), any(), any())).thenReturn(true);

        // Act
        mainApp.subscribeToPlan(userDAOMock, new Scanner(System.in));

        // Assert
        // Add assertions based on the expected behavior after subscribing to a plan
    }



}
