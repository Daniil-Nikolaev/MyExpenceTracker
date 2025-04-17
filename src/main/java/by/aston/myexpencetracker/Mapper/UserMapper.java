package by.aston.myexpencetracker.Mapper;

import by.aston.myexpencetracker.Dto.UserDto;
import by.aston.myexpencetracker.Entity.User;

public class UserMapper {
    private static UserMapper userMapper;

    private UserMapper() {}
    public static UserMapper getInstance() {
        if (userMapper == null) {
            userMapper = new UserMapper();
        }
        return userMapper;
    }

    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        userDto.setBalance(user.getBalance());
        return userDto;
    }

    public User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setBalance(userDto.getBalance());
        return user;
    }

}