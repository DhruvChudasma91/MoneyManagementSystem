package in.helldeveloper.moneymanager.service;

import in.helldeveloper.moneymanager.entity.ProfileEntity;
import in.helldeveloper.moneymanager.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final ProfileRepository profileRepository;
    private final EmailService emailService;
    private final ExpenseService expenseService;

    @Value("${money.manager.frontend.url}")
    private String frontendUrl;
    @Scheduled(cron = "0 0 22 * * *", zone = "Asia/Kolkata")
    public void sendDailyIncomeExpenseReminder() {
        log.info("Job started: sendDailyIncomeExpenseReminder()");
        List<ProfileEntity> profiles = profileRepository.findAll();

        for (ProfileEntity profile : profiles) {

            String body = """
                Hi %s,<br><br>
                This is a friendly reminder to add your incomes and expenses for today in Money Manager.<br><br>
                <a href="%s"
                   style="display:inline-block;padding:10px 20px;background-color:#4CAF50;color:#ffffff;
                          text-decoration:none;border-radius:5px;font-weight:bold;">
                    Go to Money Manager
                </a>
                <br><br>
                Best regards,<br>
                Money Manager Team
                """.formatted(profile.getFullName(), frontendUrl);

            emailService.sendMail(
                    profile.getEmail(),
                    "Daily Reminder: Add your income and expenses",
                    body
            );
        }

        log.info("Job completed: sendDailyIncomeExpenseReminder()");
    }

}
