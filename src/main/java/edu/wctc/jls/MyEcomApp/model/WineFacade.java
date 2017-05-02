
package edu.wctc.jls.MyEcomApp.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Subclass EJB class extends AbstractFacade superclass. 
 * @author Jennifer
 */
@Stateless
public class WineFacade extends AbstractFacade<Wine> {

    @PersistenceContext(unitName = "edu.wctc.jls_jls-MyEcomApp_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
     public WineFacade() {
        super(Wine.class);
    }

    /**
     * Custom delete by id method
     * @param id- String id of wine to be deleted
     * @return int- number of records deleted 
    **/
    public int deleteById(String id){
        Integer iId = Integer.parseInt(id); 
     String jpql= "delete from Wine w where w.wineId= :id";    
       Query q = this.getEntityManager().createQuery(jpql);
       q.setParameter("id",iId); 
       return q.executeUpdate(); 
    }
    /**
     * Custom method created to allow for creation of new wine. 
     * @param name name of wine to be added to wine_store database
     * @return ine 
     */
    public void addNewWine(String name, String winePrice, String wineImgUrl){
   Wine wine = new Wine(); 
   wine.setWineName(name);
   Date dateAdded = new Date(); 
   wine.setDateAdded(dateAdded);
   wine.setWineImgUrl(wineImgUrl);
   wine.setWinePrice(new BigDecimal(winePrice));
   this.create(wine);
  
    }
    
   /**
    * Custom method created to allow for the editing of an existing wine.
    * Requires 2 step process of first finding and then editing a wine. 
    * @param id unique id of the wine to be edited
    * @param name name of wine to be edited
     * @param winePrice price of wine, possibly being edited 
     * @param wineImgUrl wine image URL, possible being edited 
    */
   public void editWine(String id, String name, String winePrice, String wineImgUrl) {
       Wine wine = this.find(Integer.parseInt(id));
       wine.setWineName(name);
       wine.setWinePrice(new BigDecimal(winePrice));
       wine.setWineImgUrl(wineImgUrl);
       this.edit(wine);
       
       
   }
   
   public void addOrUpdate(String id, String name) {
   if (id == null || id.equals('0')) {
       // new record 
   }
   else {
       //update record 
   }
   }
   
}
