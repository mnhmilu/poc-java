import com.mysql.cj.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.file.remote.session.DelegatingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
@SpringBootTest
@RunWith(SpringRunner.class)
public class SFTPOutboundTest {
//    @Autowired
//    private BankWiseSftpConfig.UploadGateway gateway;
//
//    @Autowired
//    private DelegatingSessionFactory<?> sessionFactory;;
//
//
//
//    @Test
//    public void testUpload() throws IOException {
//
//        //gateway.upload(Paths.get("/Users/macbook/workspace/test.txt").toFile());//gateway.download();
//
//        String resourceName = "test.txt";
//        ClassLoader classLoader = getClass().getClassLoader();
//        File file1 = new File(classLoader.getResource(resourceName).getFile());
//
//        String resourceName2 = "test2.txt";
//        ClassLoader classLoader2 = getClass().getClassLoader();
//        File file2 = new File(classLoader2.getResource(resourceName2).getFile());
//
//
//        // sending file to server one by key
//
//        try {
//            this.sessionFactory.setThreadKey("one"); // use factory "one" , use uniqe key
//            gateway.upload(file1);
//
//        }
//        finally {
//            this.sessionFactory.clearThreadKey();
//        }
//
//        // sending file to server tow by key
//
//        try {
//
//            this.sessionFactory.setThreadKey("two"); // use factory "two", use uniqe key
//            gateway.upload(file2);
//
//
//        }
//        finally {
//            this.sessionFactory.clearThreadKey();
//        }
//
//    }
 }
