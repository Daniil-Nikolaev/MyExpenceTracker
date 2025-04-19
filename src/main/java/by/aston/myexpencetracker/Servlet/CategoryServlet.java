package by.aston.myexpencetracker.Servlet;

import by.aston.myexpencetracker.Controller.CategoryController;
import by.aston.myexpencetracker.Dto.CategoryCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/categories/*")
public class CategoryServlet extends HttpServlet {
    private final CategoryController categoryController =CategoryController.getInstance();
    private final ObjectMapper mapper=new ObjectMapper();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        categoryController.getAll(response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryCreateDto categoryCreateDto=mapper.readValue(request.getReader(), CategoryCreateDto.class);

        categoryController.addCategory(categoryCreateDto,response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getPathInfo().substring(1));

        categoryController.deleteCategory(id,response);
    }
}
