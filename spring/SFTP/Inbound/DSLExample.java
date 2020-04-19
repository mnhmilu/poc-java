import com.jcraft.jsch.ChannelSftp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.dsl.Sftp;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import java.io.File;
@Configuration
@EnableIntegration
public class SftpJavaApplication {
    @Bean
    public IntegrationFlow sftpInboundFlow() {
        return IntegrationFlows
                .from(Sftp.inboundAdapter(this.sftpSessionFactory())
                                .preserveTimestamp(true)
                                .remoteDirectory("/upload/")
                                .regexFilter(".*\\.csv$")
                                .localDirectory(new File("/Users/macbook/workspace/temp/")),
                        e -> e.id("sftpInboundAdapter")
                                .autoStartup(true)
                                .poller(Pollers.fixedDelay(5000))).channel(myChannel())
                .handle(m -> System.out.println("*************************************** Test ********************************"+m.getPayload()))
                .get();
    }
    @Bean
    public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost("0.0.0.0");
        factory.setPort(22);
        factory.setUser("foo");
        factory.setPassword("pass");
        factory.setAllowUnknownKeys(true);
        return new CachingSessionFactory<ChannelSftp.LsEntry>(factory);
    }
}





