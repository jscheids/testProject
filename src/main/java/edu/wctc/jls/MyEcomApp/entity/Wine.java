package edu.wctc.jls.MyEcomApp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.validation.constraints.*;

/**
 * note on declaring methods final and validating parameters- in Lecture 17 at
 * the 1:00 mark, instructor says that declaring methods final causes problems
 * with JPA and will return to it in the future. For validation, says to use
 * annotation. - Do not recall going over this at all.
 * https://docs.oracle.com/cd/E19798-01/821-1841/gkahq/index.html ? Used example
 * of not null on wine Name. Added import to do this. Could do patterns for
 * regex on some columns- but that is out of the scope of time I currently have.
 * 5/14/2017 Entity annotation allows JPA to find the classes Table annotation
 * is optional- ids the name of the table that this class maps to. There incase
 * someone changes table name in db XML root element- optional. Can be used for
 * restful services Named queries- are default provided by wizard. Not necessary
 * Seriablaize- for memory clearing.
 *
 * @author Jennifer
 */
@Entity
@Table(name = "wine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wine.findAll", query = "SELECT w FROM Wine w")
    , @NamedQuery(name = "Wine.findByWineId", query = "SELECT w FROM Wine w WHERE w.wineId = :wineId")
    , @NamedQuery(name = "Wine.findByWineName", query = "SELECT w FROM Wine w WHERE w.wineName = :wineName")
    , @NamedQuery(name = "Wine.findByWineImgUrl", query = "SELECT w FROM Wine w WHERE w.wineImgUrl = :wineImgUrl")
    , @NamedQuery(name = "Wine.findByDateAdded", query = "SELECT w FROM Wine w WHERE w.dateAdded = :dateAdded")
    , @NamedQuery(name = "Wine.findByWinePrice", query = "SELECT w FROM Wine w WHERE w.winePrice = :winePrice")})
public class Wine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "wine_id")
    @NotNull
    private Integer wineId;
    @Size(max = 80)
    @Column(name = "wine_name")
    @NotNull
    private String wineName;
    @Size(max = 80)
    @Column(name = "wine_img_url")
    @NotNull
    private String wineImgUrl;
    @Column(name = "date_added")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dateAdded;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "wine_price")
    @NotNull
    private BigDecimal winePrice;

    //must have default empty construcotr
    public Wine() {
    }

    /**
     * Must have constructor that uses the primary key. Validation provided by
     * not null annotation above on declaration
     *
     * @param wineId id of the wine
     */
    public Wine(Integer wineId) {
        this.wineId = wineId;
    }

    /**
     * getter for wine id
     *
     * @return integer value of wine id
     */
    public Integer getWineId() {
        return wineId;
    }

    /**
     * setter for wine id- method validation provided by not null annotation
     * above on declaration
     *
     * @param wineId id of wine
     */
    public void setWineId(Integer wineId) {
        this.wineId = wineId;
    }

    /**
     * getter for wine name
     *
     * @return String wineName
     */
    public String getWineName() {
        return wineName;
    }

    /**
     * setter for wineName method validation provided by not null annotation
     * above on declaration
     *
     * @param wineName name of wine
     */

    public void setWineName(String wineName) {
        this.wineName = wineName;
    }

    /**
     * getter for wine img url. Helper calls in there stopped working after
     * transition to spring/jpa.
     *
     * @return String of the wine image url
     */
    public String getWineImgUrl() {
        //ImageFileHelper imgHelper = new ImageFileHelper(wineImgUrl);
        // wineImgUrl = imgHelper.wineImgFileChecker(wineImgUrl);
        return wineImgUrl;
    }

    /**
     * setter for wine img url. method validation provided by not null
     * annotation above on declaration
     *
     * @param wineImgUrl String imgurl value
     */
    public void setWineImgUrl(String wineImgUrl) {
        this.wineImgUrl = wineImgUrl;
    }

    /**
     * getter for date added
     *
     * @return Date- the date added
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * setter for date added for wine. method validation provided by not null
     * annotation above on declaration. Also temporal annotation.
     *
     * @param dateAdded Date- Date added
     */
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * getter for wine price.
     *
     * @return BIg Decimal- big decimal value of wineprice
     */
    public BigDecimal getWinePrice() {
        return winePrice;
    }

    /**
     * setter for winePrice.- Should consider pattern/ other regex annotations
     * for validation if time allows. method validation provided by not null
     * annotation above on declaration
     *
     * @param winePrice wineprice- BigDecimal
     */
    public void setWinePrice(BigDecimal winePrice) {
        this.winePrice = winePrice;
    }

    /**
     * Default Overriden hashCode
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wineId != null ? wineId.hashCode() : 0);
        return hash;
    }

    /**
     * default overriden equals
     *
     * @param object
     * @return boolean
     */
    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Wine)) {
            return false;
        }
        Wine other = (Wine) object;
        if ((this.wineId == null && other.wineId != null) || (this.wineId != null && !this.wineId.equals(other.wineId))) {
            return false;
        }
        return true;
    }

    /**
     * Standard overriden toString method
     *
     * @return string value of class
     */
    @Override
    public String toString() {
        return "edu.wctc.jls.MyEcomApp.model.Wine[ wineId=" + wineId + " ]";
    }

}
