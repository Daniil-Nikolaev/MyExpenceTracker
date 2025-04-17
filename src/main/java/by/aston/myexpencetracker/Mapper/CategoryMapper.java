package by.aston.myexpencetracker.Mapper;

import by.aston.myexpencetracker.Dto.CategoryDto;
import by.aston.myexpencetracker.Entity.Category;

public class CategoryMapper {
    private static CategoryMapper categoryMapper;

    private CategoryMapper() {}

    public static CategoryMapper getInstance() {
        if (categoryMapper == null) {
            categoryMapper = new CategoryMapper();
        }
        return categoryMapper;
    }

    public CategoryDto convertToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    public Category convertToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }
}
