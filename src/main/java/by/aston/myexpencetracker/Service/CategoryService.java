package by.aston.myexpencetracker.Service;

import by.aston.myexpencetracker.Dto.CategoryCreateDto;
import by.aston.myexpencetracker.Dto.CategoryDto;
import by.aston.myexpencetracker.Entity.Category;
import by.aston.myexpencetracker.Mapper.CategoryCreateMapper;
import by.aston.myexpencetracker.Mapper.CategoryMapper;
import by.aston.myexpencetracker.Repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {
    private static CategoryService categoryService;
    private final CategoryRepository categoryRepository=CategoryRepository.getInstance();
    private final CategoryMapper categoryMapper=CategoryMapper.getInstance();
    private final CategoryCreateMapper categoryCreateMapper=CategoryCreateMapper.getInstance();


    private CategoryService() {};
    public static CategoryService getInstance() {
        if (categoryService == null) {
            categoryService = new CategoryService();
        }
        return categoryService;
    }

    public List<CategoryDto> getAll(){
        List<Category> categories=categoryRepository.findAll();
        return categories.stream().
                map(categoryMapper::convertToDto).
                collect(Collectors.toList());
    }

    public void addCategory(CategoryCreateDto categoryCreateDto) {
        if(categoryCreateDto.getName()==null){
            throw new IllegalArgumentException("Invalid request");
        }
        Category category=categoryCreateMapper.convertToEntity(categoryCreateDto);
        categoryRepository.save(category);
    }

    public void deleteCategory(Integer id) {
        if(id==null){
            throw new IllegalArgumentException("id cannot be null ");
        }
        categoryRepository.delete(id);
    }
}
