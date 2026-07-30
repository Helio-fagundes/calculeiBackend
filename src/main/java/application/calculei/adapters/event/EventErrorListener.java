package application.calculei.adapters.event;

import application.calculei.adapters.event.dto.SystemErrorWarning;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class EventErrorListener {

    private final JavaMailSender mailSender;

    public EventErrorListener(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Value("${spring.mail.username}")
    private String email;

    private final Map<String, Long> emailCache = new ConcurrentHashMap<>();
    private static final long COOLDOWN_TIME = 5L * 60 * 1000;

    @Async
    @EventListener
    public void listenerSystemErrorWarning(SystemErrorWarning event) {

        Exception exception = event.ex();
        String router = event.request().getRequestURI();
        String method = event.request().getMethod();

        String cacheKey = method + "_" + router + "_" + exception.getMessage();
        long agora = System.currentTimeMillis();

        if (emailCache.containsKey(cacheKey)) {
            long ultimoEnvio = emailCache.get(cacheKey);
            if (agora - ultimoEnvio < COOLDOWN_TIME) {
                return;
            }
        }
        emailCache.put(cacheKey, agora);

        emailSend(event);
    }

    private void emailSend(SystemErrorWarning event) {
        log.warn("Sending system error warnings to email\nSubject: System Error Warning\nMethod: {}\nRequest: {}",
                event.request().getMethod(), event.request().getRequestURI());

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(email);

            helper.setTo(email);

            helper.setSubject("🚨 CRITICAL: Erro 500 em " + event.request().getMethod() + " " + event.request().getRequestURI());

            String htmlTemplate = gerarHtmlDoErro(event.ex(), event.request().getRequestURI(), event.request().getMethod());

            helper.setText(htmlTemplate, true);

            mailSender.send(message);

        } catch (Exception mailException) {
            log.error("Falha ao enviar e-mail de alerta: {}", mailException.getMessage());
        }
    }

    private String gerarHtmlDoErro(Exception ex, String rota, String metodo) {
        StringBuilder stackTraceStr = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            stackTraceStr.append(element.toString()).append("\n");
        }

        return """
    <!DOCTYPE html>
    <html>
    <head>
        <style>
            body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
            .card { background: white; border-radius: 8px; padding: 20px; border-top: 5px solid #d9534f; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
            .title { font-size: 20px; color: #d9534f; font-weight: bold; margin-bottom: 15px; }
            .badge { background: #d9534f; color: white; padding: 3px 8px; border-radius: 4px; font-size: 12px; }
            .info-table { width: 100%%; margin-bottom: 20px; border-collapse: collapse; }
            .info-table td { padding: 10px; border-bottom: 1px solid #eee; }
            .label { font-weight: bold; color: #555; width: 25%%; }
            .stack { background: #272822; color: #f8f8f2; padding: 15px; border-radius: 5px; font-family: monospace; font-size: 13px; overflow-x: auto; white-space: pre-wrap; }
        </style>
    </head>
    <body>
        <div class="card">
            <div class="title">🚨 Alerta de Sistema: Erro Interno <span class="badge">HTTP 500</span></div>
            <table class="info-table">
                <tr><td class="label">Endpoint:</td><td><strong>%s</strong> %s</td></tr>
                <tr><td class="label">Exceção:</td><td style="color: #c9302c;">%s</td></tr>
                <tr><td class="label">Mensagem:</td><td>%s</td></tr>
            </table>
            <div style="font-weight: bold; margin-bottom: 8px; color: #333;">Stack Trace:</div>
            <div class="stack">%s</div>
        </div>
    </body>
    </html>
    """.formatted(metodo, rota, ex.getClass().getName(), ex.getMessage(), stackTraceStr.toString());
    }
}
