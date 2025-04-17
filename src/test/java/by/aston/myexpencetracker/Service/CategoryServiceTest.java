package by.aston.myexpencetracker.Service;

import by.aston.myexpencetracker.Dto.CategoryCreateDto;
import by.aston.myexpencetracker.Dto.CategoryDto;
import by.aston.myexpencetracker.Entity.Category;
import by.aston.myexpencetracker.Mapper.CategoryCreateMapper;
import by.aston.myexpencetracker.Mapper.CategoryMapper;
import by.aston.myexpencetracker.Repository.CategoryRepository;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockedStatic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryServiceTest {

    private MockedStatic<CategoryRepository> mockedRepo;
    private MockedStatic<CategoryMapper> mockedMapper;
    private MockedStatic<CategoryCreateMapper> mockedCreateMapper;

    private CategoryRepository mockRepository;
    private CategoryMapper mockMapper;
    private CategoryCreateMapper mockCreateMapper;

    private CategoryService categoryService;

    @BeforeAll
    void init() {
        mockRepository = mock(CategoryRepository.class);
        mockMapper = mock(CategoryMapper.class);
        mockCreateMapper = mock(CategoryCreateMapper.class);

        mockedRepo = mockStatic(CategoryRepository.class);
        mockedMapper = mockStatic(CategoryMapper.class);
        mockedCreateMapper = mockStatic(CategoryCreateMapper.class);

        mockedRepo.when(CategoryRepository::getInstance).thenReturn(mockRepository);
        mockedMapper.when(CategoryMapper::getInstance).thenReturn(mockMapper);
        mockedCreateMapper.when(CategoryCreateMapper::getInstance).thenReturn(mockCreateMapper);

        categoryService = CategoryService.getInstance();
    }

    @AfterAll
    void tearDown() {
        mockedRepo.close();
        mockedMapper.close();
        mockedCreateMapper.close();
    }

    @Test
    void getAll() {
        Category category1 = new Category(1, "Еда");
        Category category2 = new Category(2, "Транспорт");
        when(mockRepository.findAll()).thenReturn(Arrays.asList(category1, category2));
        when(mockMapper.convertToDto(category1)).thenReturn(new CategoryDto(1, "Еда"));
        when(mockMapper.convertToDto(category2)).thenReturn(new CategoryDto(2, "Транспорт"));

        List<CategoryDto> result = categoryService.getAll();

        assertEquals(2, result.size());
        assertEquals("Еда", result.get(0).getName());
        assertEquals("Транспорт", result.get(1).getName());
    }

    @Test
    void addCategory() {
        CategoryCreateDto createDto = new CategoryCreateDto("Еда");
        Category category = new Category(0, "Еда");

        when(mockCreateMapper.convertToEntity(createDto)).thenReturn(category);

        categoryService.addCategory(createDto);

        verify(mockCreateMapper, times(1)).convertToEntity(createDto);
        verify(mockRepository, times(1)).save(category);
    }

    @Test
    void deleteCategory() {
        int id = 3;

        categoryService.deleteCategory(id);

        verify(mockRepository, times(1)).delete(id);
    }
}
