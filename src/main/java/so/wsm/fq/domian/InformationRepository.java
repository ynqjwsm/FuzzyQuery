package so.wsm.fq.domian;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long>{

    List<Information> findTop20ByNameInChineseContainingOrNameInEnglishContaining(String chsLike, String engLike);
    List<Information> findTop20ByEnbIdIs(Integer enbId);

    Information findByEnbId(Integer enbId);

}