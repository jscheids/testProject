package edu.wctc.jls.MyEcomApp.repository;

import edu.wctc.jls.MyEcomApp.entity.Wine;
import java.io.Serializable;
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
}

