import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.file.remote.session.DelegatingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
@Configuration
@Slf4j
public class BankWiseSftpConfig {
    @Autowired
    private BankSftpConfigService bankSftpConfigService;
    @Bean
    public DelegatingSessionFactory<LsEntry> sessionFactory() {
        Iterable<BankSftpConfigEntity> banklist = bankSftpConfigService.getAll();
        Map<Object, SessionFactory<LsEntry>> factories = new LinkedHashMap<>();
        for(BankSftpConfigEntity bank:banklist)
        {
            DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
            factory.setHost(bank.getSftpHost());
            factory.setUser(bank.getSftpUser());
            factory.setPort(bank.getSftpPort());
            factory.setPassword(bank.getSftpPass());
            factory.setAllowUnknownKeys(true);
            factories.put(bank.getBankName(), factory); //TODO: check
        }
        // use the first SF as the default
        return new DelegatingSessionFactory<LsEntry>(factories, factories.values().iterator().next());
    }
    @ServiceActivator(inputChannel = "toSftp")
    @Bean
    public SftpMessageHandler handler() {
        SftpMessageHandler handler = new SftpMessageHandler(sessionFactory());
        handler.setRemoteDirectoryExpression(new LiteralExpression("upload"));
        return handler;
    }
    @MessagingGateway
    public interface UploadGateway {
        @Gateway(requestChannel = "toSftp")
        void upload(File file);
    }
}
