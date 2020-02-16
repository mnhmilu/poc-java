package com.bkash.template.webapp;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = { "sftp.port = 10022", "sftp.remote.directory.download.filter=*.*"})
public class SpringSftpDownloadDemoApplicationTests {
    private static EmbeddedSftpServer server;
    private static Path sftpFolder;
    @Value("${sftp.local.directory.download}")
    private String localDirectoryDownload;
    @BeforeClass
    public static void startServer() throws Exception {
//        server = new EmbeddedSftpServer();
//        server.setPort(10022);
//        sftpFolder = Files.createTempDirectory("SFTP_DOWNLOAD_TEST");
//        server.afterPropertiesSet();
//        server.setHomeFolder(sftpFolder);
//        // Starting SFTP
//        if (!server.isRunning()) {
//            server.start();
//        }
    }
    @Before
    @After
    public void clean() throws IOException {
     //   Files.walk(Paths.get(localDirectoryDownload)).filter(Files::isRegularFile).map(Path::toFile)
             //   .forEach(File::delete);
    }
    @Test
    public void testDownload() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        // Prepare phase
       // Path pathToFile = Files.createTempFile(sftpFolder, "TEST_DOWNLOAD_", ".xxx");

       // Path test1 =tempFiler.getFileName();

        //Path tempFile=Paths.get("/home/nahid/Downloads/testupload/log.csv");

        //Path tempFile = Files.createTempFile( Files.createTempDirectory("home/nahid/Downloads/testupload"), "log", ".csv");

        //String test2 =tempFile2.toFile()..getFileName();

        Path pathToFile = Paths.get("/opt/vanguard/temp-sftp-refund/input/BRAC_BANK_LTD._11Feb2020.pdf");
        //Path path2 =pathToFile.getFileName();



        // Run async task to wait for expected files to be downloaded to a file
        // system from a remote SFTP server
        Future<Boolean> future = Executors.newSingleThreadExecutor().submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Path expectedFile = Paths.get(localDirectoryDownload).resolve(pathToFile.getFileName());
                while (!Files.exists(expectedFile)) {
                    Thread.sleep(200);
                }
                return true;
            }
        });
        // Validation phase
        assertTrue(future.get(10, TimeUnit.SECONDS));
        assertTrue("File Test ",Files.notExists(pathToFile));
    }
    @AfterClass
    public static void stopServer() {
//        if (server.isRunning()) {
//            server.stop();
//        }
    }
}
