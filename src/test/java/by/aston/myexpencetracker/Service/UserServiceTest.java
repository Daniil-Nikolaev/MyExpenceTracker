package by.aston.myexpencetracker.Service;

import by.aston.myexpencetracker.Dto.UserDto;
import by.aston.myexpencetracker.Entity.User;
import by.aston.myexpencetracker.Mapper.UserMapper;
import by.aston.myexpencetracker.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userService = new UserService(userRepository, userMapper);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User(1, "Петя", "123", new BigDecimal("100.00"));
        User user2 = new User(2, "Вася", "123", new BigDecimal("100.00"));
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.convertToDto(user1)).thenReturn(new UserDto("Петя", "123", new BigDecimal("100.00")));
        when(userMapper.convertToDto(user2)).thenReturn(new UserDto("Вася", "123", new BigDecimal("100.00")));

        List<UserDto> result = userService.getAll();

        assertEquals(2, result.size());
        assertEquals("Петя", result.get(0).getName());
        assertEquals("Вася", result.get(1).getName());
    }

    @Test
    void testGetUserById() {
        int id = 1;
        User user = new User(id, "Петя", "123", new BigDecimal("100.00"));
        UserDto userDto = new UserDto("Петя", "123", new BigDecimal("100.00"));

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.convertToDto(user)).thenReturn(userDto);

        Optional<UserDto> result = userService.getById(id);

        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
    }

    @Test
    void testAddUser() {
        UserDto userDto = new UserDto("Петя", "123", new BigDecimal("100.00"));
        User user = new User(0, "Петя", "123", new BigDecimal("100.00"));

        when(userMapper.convertToEntity(userDto)).thenReturn(user);

        userService.addUser(userDto);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        int id = 1;
        UserDto userDto = new UserDto("Петя", "123", new BigDecimal("100.00"));
        User user = new User(0, "Петя", "123", new BigDecimal("100.00"));

        when(userMapper.convertToEntity(userDto)).thenReturn(user);

        userService.updateUser(userDto, id);

        verify(userRepository, times(1)).update(user, id);
    }

    @Test
    void testDeleteUser() {
        int id = 2;

        userService.deleteUser(id);

        verify(userRepository, times(1)).delete(id);
    }
}
