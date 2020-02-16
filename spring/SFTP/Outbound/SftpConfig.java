package com.bkash.template.webapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.*;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizingMessageSource;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.Message;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.io.File;


@Configuration
public class SftpConfig {

    @Value("${sftp.host:x.x.x.x}")
    private String sftpHost;
    @Value("${sftp.port:22}")
    private int sftpPort;
    @Value("${sftp.user:vanguard}")
    private String sftpUser;
    @Value("${sftp.privateKey:#{null}}")
    private Resource sftpPrivateKey;
    @Value("${sftp.privateKeyPassphrase:}")
    private String sftpPrivateKeyPassphrase;
    @Value("${sftp.password:xxxx}")
    private String sftpPasword;
   @Value("${sftp.remote.directory:/}")
   private String sftpRemoteDirectory;

    @Value("${sftp.remote.directory.download:/sptp/input}")
    private String sftpRemoteDirectoryDownload;
    @Value("${sftp.local.directory.download:${java.io.tmpdir}/localDownload}")
    private String sftpLocalDirectoryDownload;
    @Value("${sftp.remote.directory.download.filter:*.*}")
    private String sftpRemoteDirectoryDownloadFilter;




    @Bean
    public SessionFactory<LsEntry> sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost("10.x.x.x");
        factory.setPort(22);
        factory.setUser("vanguard");

        if (sftpPrivateKey != null) {
           factory.setPrivateKey(sftpPrivateKey);
            factory.setPrivateKeyPassphrase(sftpPrivateKeyPassphrase);
        } else {
            factory.setPassword("xxxx");
        }
        factory.setAllowUnknownKeys(true);
      

        return new CachingSessionFactory<LsEntry>(factory);
    }
    @Bean
    @ServiceActivator(inputChannel = "toSftpChannel")
    public MessageHandler handler() {
        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
        handler.setRemoteDirectoryExpression(new LiteralExpression(sftpRemoteDirectory));
        handler.setFileNameGenerator(new FileNameGenerator() {
            @Override
            public String generateFileName(Message<?> message) {
                if (message.getPayload() instanceof File) {
                    return ((File) message.getPayload()).getName();
                } else {
                    throw new IllegalArgumentException("File expected as payload.");
                }
            }
        });
        return handler;
    }
    @MessagingGateway
    public interface UploadGateway {
        @Gateway(requestChannel = "toSftpChannel")
        void upload(File file);
    } 


}
