package by.aston.myexpencetracker.Mapper;

import by.aston.myexpencetracker.Dto.CategoryCreateDto;
import by.aston.myexpencetracker.Entity.Category;

public class CategoryCreateMapper {
    private static CategoryCreateMapper categoryCreateMapper;

    private CategoryCreateMapper() {};
    public static CategoryCreateMapper getInstance() {
        if (categoryCreateMapper == null) {
            categoryCreateMapper = new CategoryCreateMapper();
        }
        return categoryCreateMapper;
    }
    public CategoryCreateDto convertToDto(Category category) {
        CategoryCreateDto dto = new CategoryCreateDto();
        dto.setName(category.getName());
        return dto;
    }

    public Category convertToEntity(CategoryCreateDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }
}
