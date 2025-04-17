package by.aston.myexpencetracker.Controller;

import by.aston.myexpencetracker.Dto.TransactionDto;
import by.aston.myexpencetracker.Service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TransactionController {
    private static TransactionController transactionController;
    private final TransactionService transactionService = TransactionService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();


    private TransactionController() {};

    public static TransactionController getInstance() {
        if (transactionController == null) {
            transactionController = new TransactionController();
        }
        return transactionController;
    }

    public void getAll(HttpServletResponse response, int userId) throws IOException {
        List<TransactionDto> all = transactionService.getAllTransactions(userId);

        if (all.isEmpty()) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String json = objectMapper.writeValueAsString(all);
            response.setContentType("application/json");
            response.getWriter().write(json);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    public void getById(HttpServletResponse response, int transactionId, int userId) throws IOException {
        Optional<TransactionDto> transaction = transactionService.getTransaction(transactionId, userId);

        if (transaction.isPresent()) {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String json = objectMapper.writeValueAsString(transaction.get());
            response.setContentType("application/json");
            response.getWriter().write(json);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void addTransaction(TransactionDto transactionDto,int userId){
        transactionService.addTransaction(transactionDto,userId);
    }

    public void deleteTransaction(int transactionId,int userId){
        transactionService.deleteTransaction(transactionId,userId);
    }

    public void updateTransaction(TransactionDto transactionDto,int transactionId,int userId){
        transactionService.updateTransaction(transactionDto,transactionId,userId);
    }


}
