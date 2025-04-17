package by.aston.myexpencetracker.Servlet;

import by.aston.myexpencetracker.Controller.TransactionController;
import by.aston.myexpencetracker.Dto.TransactionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/transactions/*")
public class TransactionServlet extends HttpServlet {
    private final TransactionController transactionController=TransactionController.getInstance();
    private final ObjectMapper objectMapper=new ObjectMapper();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        int userId=Integer.parseInt(request.getParameter("userId"));

        if (pathInfo == null || pathInfo.equals("/")) {
            transactionController.getAll(response,userId);
        } else {
            int transactionId = Integer.parseInt(pathInfo.substring(1));
            transactionController.getById(response,transactionId,userId);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId=Integer.parseInt(request.getParameter("userId"));
        objectMapper.registerModule(new JavaTimeModule());
        TransactionDto transactionDto=objectMapper.readValue(request.getReader(), TransactionDto.class);
        transactionController.addTransaction(transactionDto,userId);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId=Integer.parseInt(request.getParameter("userId"));
        int transactionId = Integer.parseInt(request.getPathInfo().substring(1));
        transactionController.deleteTransaction(transactionId,userId);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId=Integer.parseInt(request.getParameter("userId"));
        int transactionId = Integer.parseInt(request.getPathInfo().substring(1));
        objectMapper.registerModule(new JavaTimeModule());
        TransactionDto transactionDto=objectMapper.readValue(request.getReader(), TransactionDto.class);
        transactionController.updateTransaction(transactionDto,transactionId,userId);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
