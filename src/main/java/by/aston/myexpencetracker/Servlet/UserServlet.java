package by.aston.myexpencetracker.Servlet;

import by.aston.myexpencetracker.Controller.UserController;
import by.aston.myexpencetracker.Dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    private final UserController userController=UserController.getInstance();
    private final ObjectMapper mapper=new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            userController.getAll(resp);
        } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                userController.getById(id, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto=mapper.readValue(req.getReader(), UserDto.class);

        userController.addUser(userDto,resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getPathInfo().substring(1));

        userController.deleteUser(id,resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserDto userDto = mapper.readValue(req.getReader(), UserDto.class);
        int id = Integer.parseInt(req.getPathInfo().substring(1));

        userController.updateUser(userDto,id,resp);
    }
}