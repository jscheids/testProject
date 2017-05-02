package edu.wctc.jls.MyEcomApp.controller;

import edu.wctc.jls.MyEcomApp.entity.Wine;
import edu.wctc.jls.MyEcomApp.entity.DateHelper;
import edu.wctc.jls.MyEcomApp.service.WineService;
import edu.wctc.jls.exeption.InvalidParameterException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
    private static final String WINE_NAME_COL = "wine_name";
    private static final String DATE_COL = "date_added";
    private static final String WINE_PRICE_COL = "wine_price";
    private static final String WINE_IMG_URL_COL = "wine_img_url";

    //different types of requests that may come in from the UI
    private static final String WINE_LIST_REQ = "wineList";
    private static final String ADD_WINE_REQ = "addWine";
    private static final String EDIT_WINE_REQ = "editWine";
    private static final String SAVE_REQ = "saveWine";
    private static final String DELETE_WINE_REQ = "deleteWine";
    private static final String CANCEL_REQ = "cancel";
    private static final String HOME_REQ = "home";

    //error/validation messagining
    private static final String ERROR_MSG_KEY = "errMsg";
    private static final String MISSING_INPUT_MSG = "Please ensure all fields have values before saving the record.";

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

        try {

            switch (req_Action) {

                case WINE_LIST_REQ:

                    List<Wine> wines = new ArrayList<>();
                    try {
                        wines = wineService.findAll();
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
                                //wineService.deleteById(id);
                               Wine wine = wineService.findById(id); 
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
                        Wine wine = wineService.findById(id);
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

                        String winePrice = request.getParameter(WINE_PRICE);
                        String req_id = request.getParameter(WINE_ID);
                        String wineImgUrl = request.getParameter(WINE_IMG_URL);
                        
// if id is not there, it must be a new
                        if (req_id == null || req_id.isEmpty()) {
                            DateHelper dh = new DateHelper();
                            String date = dh.getCurrentDate();

                            List<String> colNames = new ArrayList<>();

                            // this is validation that prevents empty vaules from being put in at the UI. Useful if JS is turned off and Jquery validation doesn't run  
                            if (wineName.isEmpty() || wineName == null || winePrice.isEmpty() || winePrice == null || wineImgUrl.isEmpty() || wineImgUrl == null) {
                                request.setAttribute(ERROR_MSG_KEY, MISSING_INPUT_MSG);
                                destination = ADD_WINE_PAGE;
                                break;

                            }
                            colNames.add(WINE_NAME_COL);
                            colNames.add(DATE_COL);
                            colNames.add(WINE_PRICE_COL);
                            colNames.add(WINE_IMG_URL_COL);
                            List<Object> colValues = new ArrayList<>();
                            colValues.add(wineName);
                            colValues.add(date);
                            colValues.add(winePrice);
                            colValues.add(wineImgUrl);
                            Wine newWine = new Wine(0);
                            newWine.setWineName(wineName);
                            
                            // need to move, for now just doing it here to get it done
                            BigDecimal bigDecimalValue= new BigDecimal(winePrice);
                            newWine.setWinePrice(bigDecimalValue);
                            newWine.setWineImgUrl(wineImgUrl);
                            newWine.setDateAdded(new Date());
                            //wineService.addNewWine(wineName, winePrice, wineImgUrl);
                        } else {
                            //else it is an "edit" request bc it has an id
                            List<String> colNamesEdit = new ArrayList<>();
                            colNamesEdit.add(WINE_NAME_COL);
                            colNamesEdit.add(WINE_PRICE_COL);
                            colNamesEdit.add(WINE_IMG_URL_COL);
                            List<Object> colValuesEdit = new ArrayList<>();
                            //server side validation in event of client side validation failure
                            if (wineName.isEmpty() || wineName == null || winePrice.isEmpty() || winePrice == null || wineImgUrl.isEmpty() || wineImgUrl == null) {
                                request.setAttribute(ERROR_MSG_KEY, MISSING_INPUT_MSG);
                                break;
                            }

                            colValuesEdit.add(wineName);
                            colValuesEdit.add(winePrice);
                            colValuesEdit.add(wineImgUrl);
                            wineService.editWine(req_id, wineName, winePrice, wineImgUrl);
                        }
                        refreshResults(request, wineService);

                    } catch (InvalidParameterException | SQLException | ClassNotFoundException e) {
                        request.setAttribute(ERROR_MSG_KEY, e.getMessage());
                        destination = WINE_LIST_PAGE;

                    }

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
