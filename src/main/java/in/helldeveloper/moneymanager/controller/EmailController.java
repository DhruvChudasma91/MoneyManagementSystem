package in.helldeveloper.moneymanager.controller;

import in.helldeveloper.moneymanager.entity.ProfileEntity;
import in.helldeveloper.moneymanager.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final ExcelService excelService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final EmailService emailService;
    private final ProfileService profileService;

    @GetMapping("/income-excel")
    public ResponseEntity<String> emailIncomeExcel() throws IOException {
        ProfileEntity profile = profileService.getCurrentProfile();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        excelService.writeIncomesToExel(byteArrayOutputStream, incomeService.getCurrentMonthIncomesForCurrentUser());
        emailService.sendEmailWithAttachment(profile.getEmail(), "Your Income Excel Report", "Please find attached your income repost", byteArrayOutputStream.toByteArray(), "income.xlsx");

        return ResponseEntity.ok("Email Sent Successfully");
    }

    @GetMapping("/expense-excel")
    public ResponseEntity<String> emailExpenseExcel() throws IOException {
        ProfileEntity profile = profileService.getCurrentProfile();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        excelService.writeExpensesToExel(byteArrayOutputStream, expenseService.getCurrentMonthExpensesForCurrentUser());
        emailService.sendEmailWithAttachment(profile.getEmail(), "Your Expense Excel Report", "Please find attached your expense repost", byteArrayOutputStream.toByteArray(), "expense.xlsx");

        return ResponseEntity.ok("Email Sent Successfully");
    }

}
