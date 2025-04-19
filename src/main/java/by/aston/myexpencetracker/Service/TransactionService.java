package by.aston.myexpencetracker.Service;

import by.aston.myexpencetracker.Dto.TransactionDto;
import by.aston.myexpencetracker.Dto.UserDto;
import by.aston.myexpencetracker.Entity.Transaction;
import by.aston.myexpencetracker.Entity.User;
import by.aston.myexpencetracker.Mapper.TransactionMapper;
import by.aston.myexpencetracker.Repository.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionService {
    private static TransactionService transactionService;
    private  TransactionRepository transactionRepository=TransactionRepository.getInstance();
    private  TransactionMapper transactionMapper=TransactionMapper.getInstance();

    private TransactionService() {};
    public static TransactionService getInstance() {
        if (transactionService == null) {
            transactionService = new TransactionService();
        }
        return transactionService;
    }
    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }


    public void addTransaction(TransactionDto transactionDto, Integer userId) {
        if(transactionDto.getAmount()==null&&transactionDto.getDate()==null&&transactionDto.getDescription()==null){
            throw new IllegalArgumentException("Invalid request");
        }
        if(userId==null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Transaction transaction=transactionMapper.convertToEntity(transactionDto);
        transactionRepository.save(transaction,userId);
    }

    public List<TransactionDto> getAllTransactions(Integer userId) {
        if(userId==null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        List<Transaction> transactions=transactionRepository.findAll(userId);
        return transactions.stream().
                map(transactionMapper::convertToDto).
                collect(Collectors.toList());
    }

    public Optional<TransactionDto> getTransaction(Integer transactionId, Integer userId) {
        if (transactionId==null||userId==null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Transaction> transaction=transactionRepository.findByTransactionId(transactionId,userId);
        if(transaction.isPresent()){
            Optional<TransactionDto> transactionDto=Optional.of(transactionMapper.convertToDto(transaction.get()));
            return transactionDto;
        }else{
            return Optional.empty();
        }
    }

    public void deleteTransaction(Integer transactionId, Integer userId) {
        if (transactionId==null||userId==null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        transactionRepository.delete(transactionId,userId);
    }

    public void updateTransaction(TransactionDto transactionDto, Integer transactionId, Integer userId) {
        if(transactionDto.getAmount()==null&&transactionDto.getDate()==null&&transactionDto.getDescription()==null){
            throw new IllegalArgumentException("Invalid request");
        }
        if (transactionId==null||userId==null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Transaction transaction=transactionMapper.convertToEntity(transactionDto);
        transactionRepository.update(transaction,transactionId,userId);
    }
}
