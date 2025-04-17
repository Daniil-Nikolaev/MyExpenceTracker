package by.aston.myexpencetracker.Mapper;

import by.aston.myexpencetracker.Dto.CategoryDto;
import by.aston.myexpencetracker.Dto.TransactionDto;
import by.aston.myexpencetracker.Entity.Category;
import by.aston.myexpencetracker.Entity.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapper {
    private static TransactionMapper transactionMapper;
    private final CategoryMapper categoryMapper=CategoryMapper.getInstance();

    private TransactionMapper() {};
    public static TransactionMapper getInstance() {
        if (transactionMapper == null) {
            transactionMapper = new TransactionMapper();
        }
        return transactionMapper;
    }

    public TransactionDto convertToDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setDate(transaction.getDate());
        List<CategoryDto> categories = transaction.getCategories().stream().map(categoryMapper::convertToDto).collect(Collectors.toList());
        transactionDto.setCategories(categories);
        return transactionDto;
    }

    public Transaction convertToEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setDescription(transactionDto.getDescription());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate());
        List<Category> categories=transactionDto.getCategories().stream().map(categoryMapper::convertToEntity).collect(Collectors.toList());
        transaction.setCategories(categories);
        return transaction;
    }
}
