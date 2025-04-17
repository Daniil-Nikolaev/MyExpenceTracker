package by.aston.myexpencetracker.Controller;

import by.aston.myexpencetracker.Dto.UserDto;
import by.aston.myexpencetracker.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserController {

    private static UserController userController;
    private final UserService userService=UserService.getInstance();
    private final ObjectMapper objectMapper=new ObjectMapper();


    private UserController(){};
    public static UserController getInstance(){
        if(userController == null){
            userController = new UserController();
        }
        return userController;
    }

    public void getAll(HttpServletResponse response) throws IOException {
        List<UserDto> all = userService.getAll();

        if(all.isEmpty()){
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }else{
            String json=objectMapper.writeValueAsString(all);
            response.setContentType("application/json");
            response.getWriter().write(json);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    public void getById(int id,HttpServletResponse response)throws IOException{
        Optional<UserDto> user = userService.getById(id);

        if(user.isPresent()){
            String json=objectMapper.writeValueAsString(user.get());
            response.setContentType("application/json");
            response.getWriter().write(json);
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    public void addUser(UserDto userDto){
        userService.addUser(userDto);
    }

    public void updateUser(UserDto UserDto,int id){
        userService.updateUser(UserDto,id);
    }

    public void deleteUser(int id){
        userService.deleteUser(id);
    }
}