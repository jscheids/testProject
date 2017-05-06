package edu.wctc.jls.MyEcomApp.repository;

import edu.wctc.jls.MyEcomApp.entity.Wine;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
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
    
     @Query("SELECT w FROM Wine w WHERE w.wineId = :id")
    public abstract List<Wine> searchByWineId(@Param("id") Integer id);
    
}

