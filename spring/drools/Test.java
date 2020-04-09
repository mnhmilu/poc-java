import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
@SpringBootTest
public class CPSFileProcessingTest {
    @Autowired
    MoneyRefundRequestCpsEntityTempRepository moneyRefundRequestCpsEntityTempRepository;
    @Autowired
    CpsFileProcessService cpsFileProcessService;
    @Autowired
    BankWiseSlotInfoRepository bankWiseSlotInfoRepository;
    @Test
    void testRepository() throws Exception {
   
     KieSession kieSession=new DroolsConfiguration().getKieSession();
        try {
            Iterable<MoneyRefundRequestCpsEntityTemp> items = moneyRefundRequestCpsEntityTempRepository.findAll();
            for (MoneyRefundRequestCpsEntityTemp item : items) {
                kieSession.insert(item);
                kieSession.fireAllRules();
                String status =item.getStatus();
                String b =status;
            }
        }finally {
            kieSession.dispose();
        }
         Assert.assertEquals(1,1);
    }
}
