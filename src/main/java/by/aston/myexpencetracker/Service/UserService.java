package by.aston.myexpencetracker.Service;

import by.aston.myexpencetracker.Dto.UserDto;
import by.aston.myexpencetracker.Entity.User;
import by.aston.myexpencetracker.Mapper.UserMapper;
import by.aston.myexpencetracker.Repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private static UserService userService;
    private  UserRepository userRepository=UserRepository.getInstance();
    private  UserMapper userMapper=UserMapper.getInstance();

    private UserService() {};
    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public List<UserDto> getAll(){
        List<User> users=userRepository.findAll();
        return users.stream().
                map(userMapper::convertToDto).
                collect(Collectors.toList());
    }

    public Optional<UserDto> getById(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        Optional<User> user=userRepository.findById(id);
        if (user.isPresent()){
            Optional<UserDto> userDto=Optional.of(userMapper.convertToDto(user.get()));
            return userDto;
        }else{
            return Optional.empty();
        }
    }

    public void addUser(UserDto userDto) {
        if(userDto.getName() == null && userDto.getPassword() == null && userDto.getBalance() == null){
            throw new IllegalArgumentException("Invalid request");
        }

        User user=userMapper.convertToEntity(userDto);
        userRepository.save(user);
    }

    public void updateUser(UserDto userDto,Integer id) {
        if(userDto.getName() == null && userDto.getPassword() == null && userDto.getBalance() == null){
            throw new IllegalArgumentException("Invalid request");
        }
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        User user=userMapper.convertToEntity(userDto);
        userRepository.update(user,id);
    }

    public void deleteUser(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        userRepository.delete(id);
    }
}