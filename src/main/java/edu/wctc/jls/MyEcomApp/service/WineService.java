package edu.wctc.jls.MyEcomApp.service;

import edu.wctc.jls.MyEcomApp.entity.Wine;
import edu.wctc.jls.MyEcomApp.exeption.InvalidParameterException;
import edu.wctc.jls.MyEcomApp.repository.WineRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring-enabled service class that delegates work to different Spring managed
 * repository objects via the use of spring annotations to perform dependency
 * injection. Also uses annotations for transactions. Uses SLF4j to provide
 * logging features.
 *
 * @author jennifer
 */
@Service
@Transactional(readOnly = true)
public class WineService {

    private transient final Logger LOG = LoggerFactory.getLogger(WineService.class);

    @Inject
    private WineRepository wineRepo;

    @Inject
    private NotificationEmailSender noticeEmailSender;

// default constructor is mandatory
    public WineService() {
    }

    /**
     * Calls the Wine Repositorys' find all method
     *
     * @return List all wines in the db
     */
    public List<Wine> findAll() {
        return wineRepo.findAll();
    }

    /**
     * Retrieve a single wine by id
     *
     * @param id the id of the wine being searched
     * @return a single wine object
     * @throws InvalidParameterException if id is null
     */
    public Wine findById(String id) throws InvalidParameterException {
        if (id.isEmpty() || id == null) {
            throw new InvalidParameterException();
        }
        return wineRepo.findOne(new Integer(id));
    }

    /**
     * Spring performs a transaction with readonly=false. This guarantees a
     * rollback if something goes wrong.
     * Removes wine 
     * Also used email sender service to send an email when a wine is deleted. with some of the information about the deleted information. 
     * 
     * @param wine the wine object that is being removed
     */
    @Transactional
    public void remove(Wine wine) {
        LOG.debug("Deleting wine: " + wine.getWineName());
        wineRepo.delete(wine);
        noticeEmailSender.sendDeleteEmail("Wine", wine.getWineName(), wine.getWinePrice().toString());
    }

    /**
     * Spring performs a transaction with readonly=false. This guarantees a
     * rollback if something goes wrong.
     * edit a wine. Also calls the notice email sender service to update admin with pre-edit information for easy reference. 
     * @param wine wine object to be edited
     * @param previousWineName previous wineName
     * @param previousWinePrice previous winePrice
     * @return Wine- wine object that has been edited.
     * @throws InvalidParameterException if any params are null or empty
     */
    @Transactional
    public Wine edit(Wine wine, String previousWineName, String previousWinePrice) throws InvalidParameterException {
        if (wine == null || previousWineName.isEmpty() || previousWineName == null || previousWinePrice.isEmpty() || previousWinePrice == null) {
            throw new InvalidParameterException();
        }

        Wine editedWine = wineRepo.saveAndFlush(wine);
        noticeEmailSender.sendEditEmail("Wine", previousWineName, previousWinePrice);
        return editedWine;
    }

    /**
     * Custom method to search by wine id and wine name together. 
     * @param id id to be included in the search
     * @param wineName name to be included in the search 
     * @return a list of wine objects that match the search 
     * @throws InvalidParameterException if any params are null or empty
     */
    public List<Wine> searchByWineIdAndName(Integer id, String wineName) throws InvalidParameterException {
        if (id == null ||wineName.isEmpty() || wineName == null) {
            throw new InvalidParameterException();
        }
        return wineRepo.searchByWineIdAndName(id, wineName);
    }

    /**
     * Custom method to search by wineName
     * @param wineName- name or beginning part of the name to be searched for. 
     * @return a list of wine objects  that match the search 
     * @throws InvalidParameterException if any params are null or empty
     */
    public List<Wine> searchByName(String wineName) throws InvalidParameterException {
        if (wineName.isEmpty() || wineName == null) {
            throw new InvalidParameterException();
        }
        return wineRepo.searchByName(wineName);
    }

    /**
     * Method to search by wine id
     * @param id id to be searched 
     * @return a list of wine objects that match the id (should only ever be one as id is pk)
     * @throws InvalidParameterException if any params are null or empty
     */
    public List<Wine> searchByWineId(Integer id) throws InvalidParameterException {
        if (id == null) {
            throw new InvalidParameterException();
        }
        return wineRepo.searchByWineId(id);
    }

    /**
     * Custom method to search by a price range.
     * @param minPrice- the lower value price for the query 
     * @param maxPrice - the highr value price for the query 
     * @return a list of wine objects that match the search 
     * @throws InvalidParameterException if any params are null or empty
     */
    public List<Wine> searchByWinePrice(String minPrice, String maxPrice) throws InvalidParameterException {
        if (minPrice.isEmpty() || minPrice == null || maxPrice.isEmpty() || maxPrice == null) {
            throw new InvalidParameterException();
        }
        BigDecimal minPriceDec = new BigDecimal(minPrice);
        BigDecimal maxPriceDec = new BigDecimal(maxPrice);
        return wineRepo.searchByPrice(minPriceDec, maxPriceDec);
    }

    /**
     * Custom method  to search by the id, name, and price range provided by the searcher. 
     * @param id id of the wine to be searched 
     * @param wineName the name (or beginning part of the name) to be included in the search 
     * @param minPrice  the lower value price for the query
     * @param maxPrice  the higher value price for the query
     * @return  a list of wine objects that match the search 
     * @throws InvalidParameterException if any params are null or empty
     */
    public List<Wine> searchByWineIdAndNameAndPrice(Integer id, String wineName, String minPrice, String maxPrice) throws InvalidParameterException {
        if (id == null || wineName.isEmpty() || wineName == null || minPrice.isEmpty() || minPrice == null || maxPrice.isEmpty() || maxPrice == null) {
            throw new InvalidParameterException();
        }
        BigDecimal minPriceDec = new BigDecimal(minPrice);
        BigDecimal maxPriceDec = new BigDecimal(maxPrice);
        return wineRepo.searchByWineIdAndNameAndPrice(id, wineName, minPriceDec, maxPriceDec);
    }

    /**
     * Custom method to search by the combination of wine id and price range. 
     * @param id id of the wine to be searched 
     * @param minPrice  the lower value price for the query
     * @param maxPrice the higher value price for the query
     * @return a list of wine objects that match the search 
     * @throws InvalidParameterException if any params are null or empty
     */
    public List<Wine> searchByWineIdAndPrice(Integer id, String minPrice, String maxPrice) throws InvalidParameterException {
        if (id == null || minPrice.isEmpty() || minPrice == null || maxPrice.isEmpty() || maxPrice == null) {
            throw new InvalidParameterException();
        }
        BigDecimal minPriceDec = new BigDecimal(minPrice);
        BigDecimal maxPriceDec = new BigDecimal(maxPrice);
        return wineRepo.searchByWineIdAndPrice(id, minPriceDec, maxPriceDec);
    }

    /**
     * Custom method to search for a wine by the combination of name and price range. 
     * @param wineName name of the wine (or beginning part of the name) to be included in the search 
     * @param minPrice the lower value price for the query
     * @param maxPrice the lower value price for the query
     * @return a list of wine objects that match the search 
     * @throws InvalidParameterException if any params are null or empty
     */
    public List<Wine> searchByWineNameAndPrice(String wineName, String minPrice, String maxPrice) throws InvalidParameterException {
        if (wineName.isEmpty() || wineName == null || minPrice.isEmpty() || minPrice == null || maxPrice.isEmpty() || maxPrice == null) {
            throw new InvalidParameterException();
        }
        BigDecimal minPriceDec = new BigDecimal(minPrice);
        BigDecimal maxPriceDec = new BigDecimal(maxPrice);
        return wineRepo.searchByWineNameAndPrice(wineName, minPriceDec, maxPriceDec);

    }

    /**
     * Standard overriden hashcode
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.wineRepo);
        hash = 59 * hash + Objects.hashCode(this.noticeEmailSender);
        return hash;
    }

    /**
     * Standard overridden equals
     *
     * @param obj instantiated object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WineService other = (WineService) obj;
        if (!Objects.equals(this.wineRepo, other.wineRepo)) {
            return false;
        }
        if (!Objects.equals(this.noticeEmailSender, other.noticeEmailSender)) {
            return false;
        }
        return true;
    }

    /**
     * Standard overridden toString
     *
     * @return String- string of class values
     */
    @Override
    public String toString() {
        return "WineService{" + "LOG=" + LOG + ", wineRepo=" + wineRepo + ", noticeEmailSender=" + noticeEmailSender + '}';
    }

}
