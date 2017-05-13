/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jls.MyEcomApp.controller;

import edu.wctc.jls.MyEcomApp.entity.Wine;
import edu.wctc.jls.MyEcomApp.service.WineService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Jennifer
 */
@WebServlet(name = "AjaxWineController", urlPatterns = {"/AjaxWineController"})
public class AjaxWineController extends HttpServlet {
 private static final String ACTION_PARAM = "action";
    private static final String ID_PARAM = "wineId";
    private static final String LIST_ACTION = "list";
    private static final String FIND_ONE_ACTION = "findone";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    //private static final String SEARCH_ACTION = "search";
    private static final String HOME_PAGE = "/quickList.jsp";
     private static final String REQ_TYPE = "requestType";
    
    private WineService wineService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
      
      // set default destination
        String destination = HOME_PAGE;
        
         

        // Attempt to get QueryString parameters, may not always be available
        String action = request.getParameter(ACTION_PARAM);
        String wineId = request.getParameter(ID_PARAM);

        try {
            switch (action) {
                case LIST_ACTION:
                    
                    refreshWineList(request, response);
                    break;

                case FIND_ONE_ACTION: {
              
                    Wine wine = wineService.findById(wineId);
                    JsonObjectBuilder builder = Json.createObjectBuilder()
                            .add("wineId", wine.getWineId())
                            .add("name", wine.getWineName())
                            .add("price", wine.getWinePrice())
                            .add("dateAdded", wine.getDateAdded().toString());
                            

                    JsonObject wineJson = builder.build();

                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    out.write(wineJson.toString());
                    out.flush();
                    break;
                }

                case DELETE_ACTION: {
                    PrintWriter out = response.getWriter();
                    Wine wineToDelete = wineService.findById(wineId);
                    wineService.remove(wineToDelete);
                    response.setContentType("application/json; charset=UTF-8");
                    response.setStatus(200);
                    out.write("{\"success\":\"true\"}");
                    out.flush();
                    break;
                }

                case UPDATE_ACTION: {
                    PrintWriter out = response.getWriter();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = request.getReader();
                    try {
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } finally {
                        br.close();
                    }

                    String payload = sb.toString();
                    JsonReader reader = Json.createReader(new StringReader(payload));
                    JsonObject wineJson = reader.readObject();

                    // create new entity and populate
                    Wine wine = new Wine();
                    wineId = wineJson.getString("wineId");
                    Integer id = (wineId == null || wineId.isEmpty()) ? null : Integer.valueOf(wineId);
                    
                    
                    BigDecimal winePrice = new BigDecimal(wineJson.getString("price"));
                    wine.setWinePrice(winePrice);
                
                   
                   
                   String wineDateAdded = (wineJson.getString("dateAdded")); 
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm d, yyyy", Locale.ENGLISH);
LocalDate date = LocalDate.parse(wineDateAdded, formatter);
 Date wineDate = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());                 
                   
 
 wine.setWineId(id);
 wine.setWineName(wineJson.getString("name"));
 wine.setWinePrice(winePrice);
 
                    wine.setDateAdded(wineDate);
                  

                    wineService.edit(wine, "", "");

                    response.setContentType("application/json");
                    response.setStatus(200);
                    out.write("{\"success\":\"true\"}");
                    out.flush();
                    break;
                }

              /**  case SEARCH_ACTION:
                    JsonObjectBuilder builder = null;
                    JsonObject wineJson = null;
                    String searchKey = request.getParameter("searchKey");
                    List<Wine> wines = wineService.searchForWineByAny(searchKey);
                    // Only return first match or nothing if none found
                    if(!wines.isEmpty()) {
                        Wine wine = wines.get(0);
                        builder = Json.createObjectBuilder()
                            .add("wineId", wine.getWineId())
                            .add("name",wine.getWineName())
                            .add("price", wine.getWinePrice())
                            .add("dateAdded", wine.getDateAdded())
                            .add("imgUrl", wine.getWineImgUrl());
                       wineJson = builder.build();
                    }
                    
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    if(builder == null) {
                        out.write("{}");
                    } else {
                        out.write(wineJson.toString());
                    }
                    out.flush();
                    break;
            }
            **/
            }
        } catch (IOException | NumberFormatException e) {
            // Error messages will appear on the destination page if present
            request.setAttribute("errMessage", e.getMessage());

            // Just in case it's some other exception not predicted
        } catch (Exception e2) {
            // Error messages will appear on the destination page if present
            request.setAttribute("errMessage", e2.getMessage());
        }

  //  }
      //  else {destination= HOME_PAGE;}
    }
    
    
       private void refreshWineList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Wine> wines = wineService.findAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        wines.forEach((wine) -> {
            jsonArrayBuilder.add(
                    Json.createObjectBuilder()
                    .add("wineId", wine.getWineId())
                    .add("name", wine.getWineName())
                    .add("price", wine.getWinePrice().toString())
                    .add("dateAdded", wine.getDateAdded().toString())
               
            );
        });

        JsonArray winesJson = jsonArrayBuilder.build();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.write(winesJson.toString());
        out.flush();
    } 
    
    


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
/**
     * init method works for either connection pool or standard connections
     * update on changing to JPA (lecture 17)- cleared out body of this method
     * as previous connection info no longer needed. Retaining in event of
     * future need.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sctx);
        wineService = (WineService) ctx.getBean("wineService");
    }
}
