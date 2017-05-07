package edu.wctc.jls.MyEcomApp.controller;

import edu.wctc.jls.MyEcomApp.entity.Wine;
import edu.wctc.jls.MyEcomApp.entity.DateHelper;
import edu.wctc.jls.MyEcomApp.service.WineService;
import edu.wctc.jls.MyEcomApp.exeption.InvalidParameterException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet/Controller which is the "C" in MCV. Facilitates the communication
 * between the view and model.
 *
 * @author Jennifer
 */
@WebServlet(name = "WineController", urlPatterns = {"/WineController"})
public class WineController extends HttpServlet {

    private static final String REQ_TYPE = "requestType";

    // page variables   
    private static final String HOME_PAGE = "/index.jsp";
    private static final String WINE_LIST_PAGE = "/wineList.jsp";
    private static final String ADD_WINE_PAGE = "/addWine.jsp";
    private static final String EDIT_WINE_PAGE = "/editWine.jsp";
    private static final String WINE_SEARCH_PAGE = "/wineSearch.jsp";

    //these are the values "grabbed" from the page
    private static final String WINE_ID = "wineId";
    private static final String WINE_NAME = "wineName";
    private static final String DATE_ADDED = "dateAdded";
    private static final String WINE_PRICE = "winePrice";
    private static final String WINE_ID_CBX = "wineId";
    private static final String WINE_LIST = "wines";
    private static final String WINE_IMG_URL = "wineImgUrl";

    //database variables 
    private static final String WINE_EDIT_ID = "editWine";

    //different types of requests that may come in from the UI
    private static final String WINE_LIST_REQ = "wineList";
    private static final String ADD_WINE_REQ = "addWine";
    private static final String EDIT_WINE_REQ = "editWine";
    private static final String SAVE_REQ = "saveWine";
    private static final String DELETE_WINE_REQ = "deleteWine";
    private static final String CANCEL_REQ = "cancel";
    private static final String SEARCH_REQ = "search";
    private static final String HOME_REQ = "home";
    private static final String ENTER_SEARCH_REQ = "enterSearch";

    //error/validation messagining
    private static final String ERROR_MSG_KEY = "errMsg";
    private static final String MISSING_INPUT_MSG = "Please ensure all fields have values before saving the record.";
    private static final long serialVersionUID = 1L;

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

        HttpSession session = request.getSession();
        ServletContext ctx = request.getServletContext();
        // default initilization for destination var
        String destination = HOME_PAGE;

        String req_Action = request.getParameter(REQ_TYPE);

        Wine wine = null;

        try {

            switch (req_Action) {

                case WINE_LIST_REQ:

                    try {
                        List<Wine> wines = wineService.findAll();
                        destination = WINE_LIST_PAGE;
                        request.setAttribute(WINE_LIST, wines);

                    } catch (InvalidParameterException e) {
                        request.setAttribute(ERROR_MSG_KEY, e.getMessage());
                        destination = HOME_PAGE;
                    }
                    break;

                case DELETE_WINE_REQ:
                    destination = WINE_LIST_PAGE;
                    String[] wineToDelete = request.getParameterValues(WINE_ID_CBX);
                    if (wineToDelete != null) {
                        try {
                            for (String id : wineToDelete) {

                                wine = wineService.findById(id);
                                wineService.remove(wine);

                            }
                            refreshResults(request, wineService);
                        } catch (InvalidParameterException | SQLException | ClassNotFoundException e) {
                            request.setAttribute(ERROR_MSG_KEY, e.getMessage());
                            destination = WINE_LIST_PAGE;

                        }
                    }

                    break;

                case ADD_WINE_REQ:
                    destination = ADD_WINE_PAGE;
                    break;

                case EDIT_WINE_REQ:
                    destination = EDIT_WINE_PAGE;
                    String id = request.getParameter(WINE_EDIT_ID);
                    try {
                        // Wine wine = wineService.find(new Integer(id));
                        wine = wineService.findById(id);
                        request.setAttribute(WINE_ID, wine.getWineId());
                        request.setAttribute(WINE_NAME, wine.getWineName());
                        request.setAttribute(WINE_PRICE, wine.getWinePrice());
                        request.setAttribute(DATE_ADDED, wine.getDateAdded());
                        request.setAttribute(WINE_IMG_URL, wine.getWineImgUrl());
                    } catch (InvalidParameterException e) {
                        request.setAttribute(ERROR_MSG_KEY, e.getMessage());
                        destination = WINE_LIST_PAGE;
                    }

                    break;

                case CANCEL_REQ:

                    try {
                        this.refreshResults(request, wineService);
                    } catch (ClassNotFoundException | SQLException ex) {
                        request.setAttribute(ERROR_MSG_KEY, ex.getMessage());
                    } catch (InvalidParameterException ex) {
                        request.setAttribute(ERROR_MSG_KEY, ex.getMessage());
                        destination = WINE_LIST_PAGE;

                    }
                    break;

                case SAVE_REQ:
                    destination = WINE_LIST_PAGE;
                    try {

                        String wineName = request.getParameter(WINE_NAME);
                        String dateAdded = request.getParameter(DATE_ADDED);
                        String winePrice = request.getParameter(WINE_PRICE);
                        String req_id = request.getParameter(WINE_ID);
                        String wineImgUrl = request.getParameter(WINE_IMG_URL);

// if id is not there, it must be a new
//consider moving date to wine class- doesn't belong in controller imo. Look at midterm to see what I did. 
                        if (req_id == null || req_id.isEmpty()) {
                            DateHelper dh = new DateHelper();
                            String date = dh.getCurrentDate();

                            // this is validation that prevents empty vaules from being put in at the UI. Useful if JS is turned off and Jquery validation doesn't run  
                            if (wineName.isEmpty() || wineName == null || winePrice.isEmpty() || winePrice == null || wineImgUrl.isEmpty() || wineImgUrl == null) {
                                request.setAttribute(ERROR_MSG_KEY, MISSING_INPUT_MSG);
                                destination = ADD_WINE_PAGE;
                                break;

                            }

                            wine = new Wine(0);
                            wine.setWineName(wineName);

                            // need to move, for now just doing it here to get it done
                            BigDecimal bigDecimalValue = new BigDecimal(winePrice);
                            wine.setWinePrice(bigDecimalValue);
                            wine.setWineImgUrl(wineImgUrl);
                            wine.setDateAdded(new Date());
                            wineService.edit(wine);
                        } else {
                            //else it is an "edit" request bc it has an id
                            wine = wineService.findById(req_id);
                            wine.setWineName(wineName);

                            // need to move, for now just doing it here to get it done
                            BigDecimal bigDecimalValue = new BigDecimal(winePrice);
                            wine.setWinePrice(bigDecimalValue);
                            wine.setWineImgUrl(wineImgUrl);
                            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

                            try {
                                Date date = format.parse(dateAdded);
                                wine.setDateAdded(date);
                            } catch (ParseException ex) {
                                Logger.getLogger(WineController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            //server side validation in event of client side validation failure
                            if (wineName.isEmpty() || wineName == null || winePrice.isEmpty() || winePrice == null || wineImgUrl.isEmpty() || wineImgUrl == null) {
                                request.setAttribute(ERROR_MSG_KEY, MISSING_INPUT_MSG);
                                break;
                            }

                            wineService.edit(wine);
                        }
                        refreshResults(request, wineService);

                    } catch (InvalidParameterException | SQLException | ClassNotFoundException e) {
                        request.setAttribute(ERROR_MSG_KEY, e.getMessage());
                        destination = WINE_LIST_PAGE;

                    }

                    break;

                case SEARCH_REQ:
                    List<Wine> wines = null;
                    String wineSearchMinPrice;
                    String wineSearchMaxPrice;
                    String wineId = request.getParameter("wineSearchId");

                    String wineName = request.getParameter("wineSearchName");
                    wineSearchMinPrice = request.getParameter("wineSearchMinPrice");
                    wineSearchMaxPrice = request.getParameter("wineSearchMaxPrice");
                    if (wineId.equals("All")) {
                        if ((!(wineName.isEmpty())) && (wineSearchMaxPrice.isEmpty()) && (wineSearchMinPrice.isEmpty())) {
                            wines = wineService.searchByName(wineName);
                        } 
                           else if ((!(wineSearchMinPrice.isEmpty())) && (!(wineSearchMinPrice.isEmpty())) && wineName.isEmpty()) {
                                wines = wineService.searchByWinePrice(wineSearchMinPrice, wineSearchMaxPrice);
                            }
                       
                         else   if ((!(wineSearchMinPrice.isEmpty())) && (!(wineSearchMinPrice.isEmpty())) && (!(wineName.isEmpty()))) {
                                 wines= wineService.searchByWineNameAndPrice(wineName, wineSearchMinPrice, wineSearchMaxPrice); 
                            }
                        
                        
                         else   if (wineSearchMinPrice.isEmpty() && wineSearchMinPrice.isEmpty() && wineName.isEmpty()) {
                                wines= wineService.findAll(); 
                            }
                        }
                        
                     else  if (!(wineId.equals("All"))) {
                            if ((!(wineName.isEmpty())) && wineSearchMaxPrice.isEmpty()) {
                                Integer wId = new Integer(wineId);
                                wines = wineService.searchByWineIdAndName(wId, wineName);
                            }
                       
                          else  if ((!(wineSearchMaxPrice.isEmpty())) && (!(wineSearchMinPrice.isEmpty())) && wineName.isEmpty()) {
                                Integer wId = new Integer(wineId);
                                 wines = wineService.searchByWineIdAndPrice(wId, wineSearchMinPrice, wineSearchMaxPrice);
                            }
                       
                         else   if ((!(wineSearchMaxPrice.isEmpty())) && (!(wineSearchMinPrice.isEmpty())) && (!(wineName.isEmpty()))) {
                                Integer wId = new Integer(wineId);
                                 wines = wineService.searchByWineIdAndNameAndPrice(wId,wineName, wineSearchMinPrice, wineSearchMaxPrice);
                            }
                       
                          else  if ((wineSearchMaxPrice.isEmpty()) && (wineSearchMinPrice.isEmpty()) && (wineName.isEmpty())) {
                                Integer wId = new Integer(wineId);
                                wines= wineService.searchByWineId(wId); 
                            }
                            }
                            
                           


                    request.setAttribute(WINE_LIST, wines);
                    destination = WINE_LIST_PAGE;
                    break;

                case ENTER_SEARCH_REQ:

                    wines = wineService.findAll();
                    destination = WINE_LIST_PAGE;
                    request.setAttribute(WINE_LIST, wines);
                    destination = WINE_SEARCH_PAGE;
                    break;

                case HOME_REQ:
                    destination = HOME_PAGE;
                    break;

                default:
                    destination = HOME_PAGE;
                    break;

            }
        } catch (InvalidParameterException | NumberFormatException e) {
            request.setAttribute(ERROR_MSG_KEY, e.getMessage());
        }

        RequestDispatcher view
                = request.getRequestDispatcher(response.encodeURL(destination));

        view.forward(request, response);
    }

    /**
     * helper method to refresh the wine list after adding, editing, or deleting
     * a wine
     *
     * @param request the request that was made (add, edit, delete)
     * @param wineService (wine service class to actually complete the request)
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    private void refreshResults(HttpServletRequest request, WineService wineService)
            throws ClassNotFoundException, SQLException, InvalidParameterException {
        if (request == null || wineService == null) {
            throw new InvalidParameterException();
        }
        List<Wine> wines = wineService.findAll();
        request.setAttribute(WINE_LIST, wines);
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
