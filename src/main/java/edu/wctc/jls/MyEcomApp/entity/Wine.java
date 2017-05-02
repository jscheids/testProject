
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

/**
 * note on declaring methods final and validating parameters- 
 * in Lecture 17 at the 1:00 mark, instructor says that declaring final problems with JPA and will return to it in the future. 
 * For validation, says to use annotation. 
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
    private Integer wineId;
    @Size(max = 80)
    @Column(name = "wine_name")
    private String wineName;
    @Size(max = 80)
    @Column(name = "wine_img_url")
    private String wineImgUrl;
    @Column(name = "date_added")
    @Temporal(TemporalType.DATE)
    private Date dateAdded;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "wine_price")
    private BigDecimal winePrice;

    public Wine() {
    }

    public Wine(Integer wineId) {
        this.wineId = wineId;
    }

    public Integer getWineId() {
        return wineId;
    }

    public void setWineId(Integer wineId) {
        this.wineId = wineId;
    }

    public String getWineName() {
        return wineName;
    }

    public void setWineName(String wineName) {
        this.wineName = wineName;
    }

    public String getWineImgUrl() {
        //ImageFileHelper imgHelper = new ImageFileHelper(wineImgUrl);
       // wineImgUrl = imgHelper.wineImgFileChecker(wineImgUrl);
        return wineImgUrl;
    }

    public void setWineImgUrl(String wineImgUrl) {
        this.wineImgUrl = wineImgUrl;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public BigDecimal getWinePrice() {
        return winePrice;
    }

    public void setWinePrice(BigDecimal winePrice) {
        this.winePrice = winePrice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wineId != null ? wineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wine)) {
            return false;
        }
        Wine other = (Wine) object;
        if ((this.wineId == null && other.wineId != null) || (this.wineId != null && !this.wineId.equals(other.wineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.jls.MyEcomApp.model.Wine[ wineId=" + wineId + " ]";
    }
    
}
