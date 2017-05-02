package edu.wctc.jls.MyEcomApp.service;

import edu.wctc.jls.MyEcomApp.entity.Wine;
import edu.wctc.jls.MyEcomApp.repository.WineRepository;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a special Spring-enabled service class that delegates work to 
 * various Spring managed repository objects using special spring annotations
 * to perform dependency injection, and special annotations for transactions.
 * It also uses SLF4j to provide logging features.
 * 
 * Don't confuse the Spring @Respository annotation with the repository
 * classes (AuthorRepository, BookRespository). The annotation here is used 
 * solely to tell Spring to translate any exception messages into more
 * user friendly text. Any class annotated that way will do that.
 * 
 * @author jennifer 
 */
@Service
@Transactional(readOnly = true)
public class WineService {

    private transient final Logger LOG = LoggerFactory.getLogger(WineService.class);

    @Inject
    private WineRepository wineRepo;

  
    public WineService() {
    }

    public List<Wine> findAll() {
        return wineRepo.findAll();
    }

    public Wine findById(String id) {
        return wineRepo.findOne(new Integer(id));
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param wine 
     */
    @Transactional
    public void remove(Wine wine ) {
        LOG.debug("Deleting wine: " + wine.getWineName());
        wineRepo.delete(wine);
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param wine 
     */
    @Transactional
    public Wine edit(Wine wine) {
        return wineRepo.saveAndFlush(wine);
    }
}

