/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jls.MyEcomApp.entity;

import edu.wctc.jls.MyEcomApp.exeption.InvalidParameterException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * helper class to wine class to search to see if an image file exists in the images folder
 * @author Jennifer
 */
public class ImageFileHelper {
    private String wineImgUrl; 
    private final String FILE =  "C:\\Users\\Jennifer\\Documents\\NetBeansProjects\\jls-MyEcomApp\\src\\main\\webapp\\images";
    private final String DEFAULT_LOGO = "logo.png";

    public ImageFileHelper(String wineImgUrl) {
        this.wineImgUrl = wineImgUrl;
         //wineImgFileChecker(wineImgUrl);
    }
    /**
     * private helper method for checking if a img file at brought back from the database really exists 
     * @param wineImgUrl the wine img url being requested from the database 
     * @return a string 
     */
   public final String  wineImgFileChecker(String wineImgUrl){
       if(wineImgUrl.isEmpty() || wineImgUrl == null){
           throw new InvalidParameterException(); 
                   }
    File file = new File(FILE);
    
    
    List<File> list = new ArrayList<File>();
    List<String> fileNames = getImgFiles(file, list);
    
    
     Set<String> set = new HashSet<String>(fileNames);
  if (set.contains(wineImgUrl))
  {
 
  }
         else wineImgUrl = DEFAULT_LOGO; 

   return wineImgUrl; 
}
   /**
    * a helper method to wineImgFileChecker to get the String list of file names that exist
    * @param rootfile - the file to be checked for the img url
    * @param list list of files 
    * @return a list of strings of file names 
    * @throws InvalidParameterException
    */
 private  List<String> getImgFiles(File rootfile, List<File> list)
{       
    if(rootfile == null || list == null)
    { throw new InvalidParameterException(); 
    }
    
    File[] files; 
   
    List<String> fileNames = new ArrayList<String>(); 
   
     
        files = rootfile.listFiles();
  
 
    for (File file : files)
    {
        if(file.isDirectory())
        {
            getImgFiles(file, list);
        }
        else
        {
           
                list.add(file);
                fileNames.add(file.getName());
               // System.out.println(file.getName());
                
            
        }
    }
        return fileNames; 
       
    
}
/**
 * getter for wineImgURL
 * @return String of wine img url value 
 */
    public String getWineImgUrl() {
        return wineImgUrl;
    }
/**
 * wine img url setter
 * @param wineImgUrl 
 * @throws InvalidParamterException 
 */
    public void setWineImgUrl(String wineImgUrl) throws InvalidParameterException  {
        if(wineImgUrl.isEmpty() || wineImgUrl == null){
            throw new InvalidParameterException(); 
        }
        this.wineImgUrl = wineImgUrl;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.wineImgUrl);
        hash = 23 * hash + Objects.hashCode(this.FILE);
        hash = 23 * hash + Objects.hashCode(this.DEFAULT_LOGO);
        return hash;
    }

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
        final ImageFileHelper other = (ImageFileHelper) obj;
        if (!Objects.equals(this.wineImgUrl, other.wineImgUrl)) {
            return false;
        }
        if (!Objects.equals(this.FILE, other.FILE)) {
            return false;
        }
        if (!Objects.equals(this.DEFAULT_LOGO, other.DEFAULT_LOGO)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ImageFileHelper{" + "wineImgUrl=" + wineImgUrl + ", FILE=" + FILE + ", DEFAULT_LOGO=" + DEFAULT_LOGO + '}';
    }
 
}
