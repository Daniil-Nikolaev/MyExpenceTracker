package by.aston.myexpencetracker.Controller;

import by.aston.myexpencetracker.Dto.CategoryCreateDto;
import by.aston.myexpencetracker.Dto.CategoryDto;
import by.aston.myexpencetracker.Service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class CategoryController {
    private static CategoryController categoryController;
    private final CategoryService categoryService=CategoryService.getInstance();
    private final ObjectMapper objectMapper=new ObjectMapper();

    private CategoryController() {};
    public static CategoryController getInstance() {
        if (categoryController == null) {
            categoryController = new CategoryController();
        }
        return categoryController;
    }

    public void getAll(HttpServletResponse response) throws IOException {
        List<CategoryDto> all = categoryService.getAll();

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

    public void addCategory(CategoryCreateDto categoryCreateDto,HttpServletResponse response) throws IOException {
        try {
            categoryService.addCategory(categoryCreateDto);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (IllegalArgumentException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid request");
        }
    }

    public void deleteCategory(int id,HttpServletResponse response) throws IOException {
        try {
            categoryService.deleteCategory(id);
            response.setStatus(HttpServletResponse.SC_OK);
        }catch (IllegalArgumentException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("id cannot be null");
        }

    }
}
