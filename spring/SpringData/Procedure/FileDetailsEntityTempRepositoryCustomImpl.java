import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
public class FileDetailsEntityTempRepositoryCustomImpl implements FileDetailsEntityTempRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    public String updateBankWiseReportValue(String bankName, String slotNo) {
       return em.createStoredProcedureQuery("updateBankWiseReportValue")
                .registerStoredProcedureParameter(1,String.class, ParameterMode.IN)
                .setParameter(1,bankName)
                .registerStoredProcedureParameter(2,String.class, ParameterMode.IN)
                .setParameter(2,slotNo)
                .registerStoredProcedureParameter(3,String.class,ParameterMode.OUT)
                .getOutputParameterValue(3).toString();
    }
}
