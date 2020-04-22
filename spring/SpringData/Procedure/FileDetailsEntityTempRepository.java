import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.List;
@Transactional
public interface FileDetailsEntityTempRepository extends CrudRepository<xyzEntity, Long>,FileDetailsEntityTempRepositoryCustom {
}
