package edu.wctc.jls.MyEcomApp.service;

import edu.wctc.jls.MyEcomApp.entity.Wine;
import edu.wctc.jls.MyEcomApp.repository.WineRepository;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring-enabled service class that delegates work to different Spring managed repository objects via the use of spring annotations to perform dependency injection.
 * Also uses annotations for transactions.
 * Uses SLF4j to provide logging features.
 * @author jennifer 
 */
@Service
@Transactional(readOnly = true)
public class WineService {

    private transient final Logger LOG = LoggerFactory.getLogger(WineService.class);

    @Inject
    private WineRepository wineRepo;

     @Inject 
    private DeleteNotificationEmailSender deleteNoticeEmailSender; 
  
    public WineService() {
    }

    public List<Wine> findAll() {
        return wineRepo.findAll();
    }
/**
 * Retrieve a single wine by id
 * @param id the id of the wine being searched 
 * @return a single wine object 
 */
    public Wine findById(String id) {
        return wineRepo.findOne(new Integer(id));
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param wine the wine object that is being removed 
     */
    @Transactional
    public void remove(Wine wine ) {
        LOG.debug("Deleting wine: " + wine.getWineName());
        wineRepo.delete(wine);
          deleteNoticeEmailSender.sendEmail("Wine", wine.getWineName());
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param wine wine object to be edited
     */
    @Transactional
    public Wine edit(Wine wine) {
        return wineRepo.saveAndFlush(wine);  
    }
    /**
     * 
     * @param id
     * @param wineName 
     * @return 
     */
      public List<Wine> searchByWineIdAndName(Integer id, String wineName) {
        return wineRepo.searchByWineIdAndName(id, wineName);        
    }
/**
 * 
 * @param wineName
 * @return 
 */
    public List<Wine> searchByName(String wineName) {
        return wineRepo.searchByName(wineName);        
    }
    /**
     * 
     * @param id
     * @return 
     */
     public List<Wine> searchByWineId(Integer id) {
        return wineRepo.searchByWineId(id);
    }
     public List<Wine> searchByWinePrice(String minPrice, String maxPrice) {
         BigDecimal minPriceDec = new BigDecimal(minPrice);
     BigDecimal maxPriceDec = new BigDecimal(maxPrice);
        return wineRepo.searchByPrice(minPriceDec, maxPriceDec);  
}
     public List <Wine> searchByWineIdAndNameAndPrice(Integer id, String wineName, String minPrice, String maxPrice){
         BigDecimal minPriceDec = new BigDecimal(minPrice);
     BigDecimal maxPriceDec = new BigDecimal(maxPrice);
     return wineRepo.searchByWineIdAndNameAndPrice(id, wineName, minPriceDec, maxPriceDec);
     }
     
   public List <Wine> searchByWineIdAndPrice(Integer id, String minPrice, String maxPrice){
         BigDecimal minPriceDec = new BigDecimal(minPrice);
     BigDecimal maxPriceDec = new BigDecimal(maxPrice);
     return wineRepo.searchByWineIdAndPrice(id, minPriceDec, maxPriceDec);
     }  
   
   public List <Wine> searchByWineNameAndPrice(String wineName, String minPrice, String maxPrice){
       BigDecimal minPriceDec = new BigDecimal(minPrice);
     BigDecimal maxPriceDec = new BigDecimal(maxPrice);
     return wineRepo.searchByWineNameAndPrice(wineName, minPriceDec, maxPriceDec);
   
}

}