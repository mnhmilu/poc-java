package com.bkash.template.webapp;


import com.bkash.template.webapp.configuration.SftpConfig;
import com.bkash.template.webapp.configuration.SftpInboundConfig;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = { "sftp.port = 10022" })
public class SpringSftpUploadDemoApplicationTests {

    @Autowired
    private SftpConfig.UploadGateway gateway;

    private static EmbeddedSftpServer server;
    private static Path sftpFolder;
    @BeforeClass
    public static void startServer() throws Exception {
        server = new EmbeddedSftpServer();
        server.setPort(10022);
        sftpFolder = Files.createTempDirectory("SFTP_UPLOAD_TEST");
        server.afterPropertiesSet();
        server.setHomeFolder(sftpFolder);
        // Starting SFTP
        if (!server.isRunning()) {
            server.start();
        }
    }
    @Before
    @After
    public void cleanSftpFolder() throws IOException {
      //  Files.walk(sftpFolder).filter(Files::isRegularFile).map(Path::toFile).forEach(File::delete);
    }
    @Test
    public void testUpload() throws IOException {

        gateway.upload(Paths.get("/home/nahid/Downloads/testupload/log.csv").toFile());
        //gateway.download();
    }
    @AfterClass
    public static void stopServer() {
        if (server.isRunning()) {
            server.stop();
        }
    }

}
