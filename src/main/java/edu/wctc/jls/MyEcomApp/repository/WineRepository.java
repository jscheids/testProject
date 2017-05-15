package edu.wctc.jls.MyEcomApp.repository;

import edu.wctc.jls.MyEcomApp.entity.Wine;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Wine Repository Class with custom  JPA queries used for searching wines. 
 * @author jennifer
 */
@Repository
public interface WineRepository extends JpaRepository<Wine, Integer>, Serializable {

    @Query("SELECT w.wineName FROM Wine w")
    public Object[] findAllWithNameOnly();

    @Query("SELECT w FROM Wine w WHERE w.wineId = :id AND w.wineName LIKE :wineName%")
    public abstract List<Wine> searchByWineIdAndName(@Param("id") Integer id, @Param("wineName") String wineName);

    @Query("SELECT w FROM Wine w WHERE w.wineName LIKE :wineName%")
    public abstract List<Wine> searchByName(@Param("wineName") String wineName);

    @Query("SELECT w FROM Wine w WHERE w.winePrice BETWEEN :minPriceDec and :maxPriceDec")
    public abstract List<Wine> searchByPrice(@Param("minPriceDec") BigDecimal minPriceDec, @Param("maxPriceDec") BigDecimal maxPriceDec);

    @Query("SELECT w FROM Wine w WHERE w.wineId = :id")
    public abstract List<Wine> searchByWineId(@Param("id") Integer id);

    @Query("SELECT w FROM Wine w WHERE w.wineName LIKE :wineName% and w.winePrice BETWEEN :minPriceDec and :maxPriceDec")
    public abstract List<Wine> searchByWineNameAndPrice(@Param("wineName") String wineName, @Param("minPriceDec") BigDecimal minPriceDec, @Param("maxPriceDec") BigDecimal maxPriceDec);

    @Query("SELECT w FROM Wine w WHERE w.wineId = :id and w.winePrice BETWEEN :minPriceDec and :maxPriceDec")
    public abstract List<Wine> searchByWineIdAndPrice(@Param("id") Integer id, @Param("minPriceDec") BigDecimal minPriceDec, @Param("maxPriceDec") BigDecimal maxPriceDec);

    @Query("SELECT w FROM Wine w WHERE w.wineId = :id and w.wineName LIKE :wineName% and w.winePrice BETWEEN :minPriceDec and :maxPriceDec")
    public abstract List<Wine> searchByWineIdAndNameAndPrice(@Param("id") Integer id, @Param("wineName") String wineName, @Param("minPriceDec") BigDecimal minPriceDec, @Param("maxPriceDec") BigDecimal maxPriceDec);

}
