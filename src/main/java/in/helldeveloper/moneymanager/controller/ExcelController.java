package in.helldeveloper.moneymanager.controller;

import in.helldeveloper.moneymanager.service.ExcelService;
import in.helldeveloper.moneymanager.service.ExpenseService;
import in.helldeveloper.moneymanager.service.IncomeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    @GetMapping("/download/income")
    public void downloadIncomeExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spredsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=income.xlsx");
        excelService.writeIncomesToExel(response.getOutputStream(), incomeService.getCurrentMonthIncomesForCurrentUser());
    }

    @GetMapping("/download/expense")
    public void downloadExpenseExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spredsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=expense.xlsx");
        excelService.writeExpensesToExel(response.getOutputStream(), expenseService.getCurrentMonthExpensesForCurrentUser());
    }



}
