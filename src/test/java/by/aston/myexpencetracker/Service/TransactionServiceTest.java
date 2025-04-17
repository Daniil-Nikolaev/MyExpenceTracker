package by.aston.myexpencetracker.Service;

import by.aston.myexpencetracker.Dto.TransactionDto;
import by.aston.myexpencetracker.Entity.Transaction;
import by.aston.myexpencetracker.Mapper.TransactionMapper;
import by.aston.myexpencetracker.Repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    private final int userId = 1;
    private final int transactionId = 10;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionService(transactionRepository, transactionMapper);
    }

    @Test
    void testAddTransaction() {
        TransactionDto dto = new TransactionDto("Обед", new BigDecimal("100.00"), LocalDate.now(), Collections.emptyList());
        Transaction entity = new Transaction(0, "Обед", new BigDecimal("100.00"), LocalDate.now(), Collections.emptyList());

        when(transactionMapper.convertToEntity(dto)).thenReturn(entity);

        transactionService.addTransaction(dto, userId);

        verify(transactionMapper).convertToEntity(dto);
        verify(transactionRepository).save(entity, userId);
    }

    @Test
    void testGetAllTransactions() {
        List<Transaction> entities = List.of(
                new Transaction(1, "Обед", new BigDecimal("100.00"), LocalDate.now(), Collections.emptyList()),
                new Transaction(2, "Ресторан", new BigDecimal("100.00"), LocalDate.now(), Collections.emptyList())
        );

        List<TransactionDto> dtos = List.of(
                new TransactionDto("Обед", new BigDecimal("100.00"), LocalDate.now(), Collections.emptyList()),
                new TransactionDto("Ресторан",new BigDecimal("100.00"), LocalDate.now(), Collections.emptyList())
        );

        when(transactionRepository.findAll(userId)).thenReturn(entities);
        when(transactionMapper.convertToDto(any())).thenReturn(dtos.get(0), dtos.get(1));

        List<TransactionDto> result = transactionService.getAllTransactions(userId);

        assertEquals(2, result.size());
        verify(transactionRepository).findAll(userId);
        verify(transactionMapper, times(2)).convertToDto(any());
    }

    @Test
    void testGetTransactionById() {
        Transaction entity = new Transaction(1, "Обед",  new BigDecimal("100.00"), LocalDate.now(), Collections.emptyList());
        TransactionDto dto = new TransactionDto("Обед",  new BigDecimal("100.00"), LocalDate.now(), Collections.emptyList());

        when(transactionRepository.findByTransactionId(transactionId, userId)).thenReturn(Optional.of(entity));
        when(transactionMapper.convertToDto(entity)).thenReturn(dto);

        Optional<TransactionDto> result = transactionService.getTransaction(transactionId, userId);

        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
        verify(transactionRepository).findByTransactionId(transactionId, userId);
        verify(transactionMapper).convertToDto(entity);
    }

    @Test
    void testDeleteTransaction() {
        transactionService.deleteTransaction(transactionId, userId);

        verify(transactionRepository).delete(transactionId, userId);
    }

    @Test
    void testUpdateTransaction() {
        TransactionDto dto = new TransactionDto("Обед", new BigDecimal("100.00"), LocalDate.now(), Collections.emptyList());
        Transaction entity = new Transaction(0, "Обед", new BigDecimal("100.00"), LocalDate.now(), Collections.emptyList());

        when(transactionMapper.convertToEntity(dto)).thenReturn(entity);

        transactionService.updateTransaction(dto, transactionId, userId);

        verify(transactionMapper).convertToEntity(dto);
        verify(transactionRepository).update(entity, transactionId, userId);
    }
}
