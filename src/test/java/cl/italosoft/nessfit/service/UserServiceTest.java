package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.Role;
import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.repository.UserRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceTest
{
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setup()
    {
        testUser = new User("207785296","Ignacia","Rivas","Figueroa",
                "207785296",968569627,"ignacia.rivas@alumnos.ucn.cl",
                true, new Role(3));
    }
    
    @Test
    void findByRut()
    {
        when(userRepository.findById(testUser.getRut())).thenReturn(Optional.of(testUser));

        User foundedUser = userService.find(testUser.getRut());

        assertEquals(testUser, foundedUser);
    }


    @Test
    void findByEmail()
    {
        when(userRepository.findById(testUser.getEmail())).thenReturn(Optional.of(testUser));

        User foundedUser = userService.find(testUser.getEmail());

        assertEquals(testUser, foundedUser);
    }

    @Test
    void save()
    {
        when(userRepository.save(testUser)).thenReturn(testUser);

        User savedUser = userService.save(testUser);

        assertEquals(testUser.getRut(), savedUser.getRut());

    }

    @Test
    void saveAndFlush()
    {
        when(userRepository.save(testUser)).thenReturn(testUser);

        User savedUser = userService.save(testUser);

        assertEquals(testUser.getRut(), savedUser.getRut());
    }

    @Test
    void delete()
    {
        userService.delete(testUser.getRut());
        verify(userRepository).deleteById(testUser.getRut());
    }

    @Test
    void list()
    {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++)
        {
            users.add(new User(Integer.toString(i),"","","",
                    "",968569627,"",
                    true, new Role(3)));
        }
        for (User user : users)
        {
            userService.saveAndFlush(user);
        }
        List<User> savedUsers = userService.list();
        for (int i = 0; i <savedUsers.size(); i++)
        {
            assertEquals(savedUsers.get(i).getRut(),Integer.toString(i));
        }

    }

    @Test
    void flush()
    {
        userService.flush();
        verify(userRepository).flush();
    }
}