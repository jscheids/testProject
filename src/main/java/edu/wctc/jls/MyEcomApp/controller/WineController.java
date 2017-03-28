/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jls.MyEcomApp.controller;

import edu.wctc.jls.MyEcomApp.model.Wine;
import edu.wctc.jls.MyEcomApp.model.WineDao;
import edu.wctc.jls.MyEcomApp.model.WineService;
import edu.wctc.jls.MyEcomApp.model.DateHelper;
import edu.wctc.jls.MyEcomApp.model.DbAccessor;
import edu.wctc.jls.MyEcomApp.model.IWineDao;
import edu.wctc.jls.MyEcomApp.model.MySqlDbAccessor;
import edu.wctc.jls.exeption.InvalidParameterException;
import java.io.IOException;
import java.io.PrintWriter;
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
    private static final String WINE_TABLE_NAME = "wine";
    private static final String WINE_ID_COL = "wine_id";
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
    private static final int MAX_TABLE_ROWS = 100;
    private static final String ERROR_MSG_KEY = "errMsg";
    private static final String MISSING_INPUT_MSG = "Please ensure all fields have values before saving the record.";
    
    
    private static final String DRIVER_CLASS_KEY = "db.driver.class";
    private static final String DATABASE_URL_KEY = "db.url";
    private static final String DATABASE_USERNAME_KEY = "db.username";
    private static final String DATABASE_PASSWORD_KEY = "db.password";
    private static final String DATABASE_JNDI_NAME_KEY = "connPoolName";
     private static final String DAO_CLASS_NAME_KEY = "wineDao"; 
    private static final String DB_STRATEGY_CLASS_NAME_KEY = "dbStrategy";

    // db config init params from web.xml
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbStrategyClassName;
    private String daoClassName;
    private String jndiName;
    

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
            //connection pool
            WineService wineService = injectDependenciesAndGetWineService();

            switch (req_Action) {

                case WINE_LIST_REQ:

                  
                    List<Wine> wines = new ArrayList<>(); 
                    try {
                      wines = wineService.retrieveWineList(WINE_TABLE_NAME, MAX_TABLE_ROWS);
                      
                    } catch (InvalidParameterException | SQLException |ClassNotFoundException e ) {
                        request.setAttribute(ERROR_MSG_KEY, e.getMessage());
                        destination = HOME_PAGE;
                    }
                      destination = WINE_LIST_PAGE;
                      request.setAttribute(WINE_LIST, wines);
                    break;

                case DELETE_WINE_REQ:
                    destination = WINE_LIST_PAGE;
                    String[] wineToDelete = request.getParameterValues(WINE_ID_CBX);
                    if (wineToDelete != null) {
                        try {
                            for (String id : wineToDelete) {
                                wineService.deleteWineById(WINE_TABLE_NAME, WINE_ID_COL, id);
                            }
                        } catch (InvalidParameterException | SQLException | ClassNotFoundException e) {
                            request.setAttribute(ERROR_MSG_KEY, e.getMessage());
                            destination = WINE_LIST_PAGE;

                        }
                    }

                    refreshResults(request, wineService);
                    break;

                case ADD_WINE_REQ:
                    destination = ADD_WINE_PAGE;
                    break;

                case EDIT_WINE_REQ:
                    destination = EDIT_WINE_PAGE;
                    String id = request.getParameter(WINE_EDIT_ID);
                    try {
                        Wine wine = wineService.retrieveWine(WINE_TABLE_NAME, WINE_ID_COL, id);
                        request.setAttribute(WINE_ID, wine.getWineID());
                        request.setAttribute(WINE_NAME, wine.getWineName());
                        request.setAttribute(WINE_PRICE, wine.getWinePrice());
                        request.setAttribute(DATE_ADDED, wine.getDateAdded());
                        request.setAttribute(WINE_IMG_URL, wine.getWineImgUrl());
                    } catch (InvalidParameterException | SQLException | ClassNotFoundException e) {
                        request.setAttribute(ERROR_MSG_KEY, e.getMessage());
                        destination = WINE_LIST_PAGE;

                    }
                    break;
                    case CANCEL_REQ:
                    this.refreshResults(request, wineService);
                    destination = WINE_LIST_PAGE;
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
                           // if(wineName.isEmpty() || wineName == null){
                           //     wineName = "no name";
                           // }
                           
                             if(wineName.isEmpty() || wineName == null || winePrice.isEmpty() || winePrice == null || wineImgUrl.isEmpty() || wineImgUrl == null){
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
                            wineService.addNewWine(WINE_TABLE_NAME,
                                    colNames, colValues);
                        } else {
                            //else it is an "edit" request bc it has an id
                            List<String> colNamesEdit = new ArrayList<>();
                            colNamesEdit.add(WINE_NAME_COL);
                            colNamesEdit.add(WINE_PRICE_COL);
                            colNamesEdit.add(WINE_IMG_URL_COL);
                            List<Object> colValuesEdit = new ArrayList<>();
                                 if(wineName.isEmpty() || wineName == null || winePrice.isEmpty() || winePrice == null || wineImgUrl.isEmpty() || wineImgUrl == null){
                                request.setAttribute(ERROR_MSG_KEY, MISSING_INPUT_MSG);
                        destination = EDIT_WINE_PAGE;
                        break;

                            }
                            colValuesEdit.add(wineName);
                            colValuesEdit.add(winePrice);
                            colValuesEdit.add(wineImgUrl);
                            wineService.updateWineById(WINE_TABLE_NAME, colNamesEdit,
                                    colValuesEdit, WINE_ID_COL, req_id);
                        }

                    } catch (InvalidParameterException | SQLException | ClassNotFoundException e) {
                        request.setAttribute(ERROR_MSG_KEY, e.getMessage());
                        destination = WINE_LIST_PAGE;

                    }

                    refreshResults(request, wineService);

                    break;

                case HOME_REQ:

                    destination = HOME_PAGE;
                    break;

                default:

                    destination = HOME_PAGE;
                    break;

            }
        } catch (InvalidParameterException e) {
            request.setAttribute(ERROR_MSG_KEY, e.getMessage());
        } catch (SQLException e) {
            request.setAttribute(ERROR_MSG_KEY, e.getMessage());
        } catch (Exception e) {
            request.setAttribute(ERROR_MSG_KEY, e.getMessage());

        }

        RequestDispatcher view
                = request.getRequestDispatcher(response.encodeURL(destination));
        view.forward(request, response);
    }

    /*
        This is the helper method.  Liskov Substitution Principle and Java Reflection to
         instantiate DBStrategy based on the class name retrieved
        from web.xml
     */
    private WineService injectDependenciesAndGetWineService() throws Exception {

        Class dbClass = Class.forName(dbStrategyClassName);
        //  Java reflection instanntiatea the DBStrategy object
        DbAccessor db = (DbAccessor) dbClass.newInstance();

        // Use Liskov Substitution Principle and Java Reflection to
        // instantiate the chosen DAO based on the class name retrieved above.
        // This one is trickier because the available DAO classes have
        // different constructor params
        IWineDao wineDao = null;
        Class daoClass = Class.forName(daoClassName);
        Constructor constructor = null;

        //for the non-pooled AuthorDao
        try {
            constructor = daoClass.getConstructor(new Class[]{
                DbAccessor.class, String.class, String.class, String.class, String.class
            });
        } catch (NoSuchMethodException nsme) {
            // do nothing, the exception means that there is no such constructor,
            // so code will continue executing below
        }

        // constructor will be null if using connectin pool dao because the
        // constructor has a different number and type of arguments
        if (constructor != null) {
            // conn pool NOT used so constructor has these arguments
            Object[] constructorArgs = new Object[]{
                db, driverClass, url, userName, password
            };
            wineDao = (IWineDao) constructor
                    .newInstance(constructorArgs);

        } else {
            /*
             this is for the  connection pool version. 
             Gets tjhe JNDI name of the Glassfish connection pool
             and then Java Reflection is used  to create the needed
             objects based on the servlet init params
             */
            Context ctx = new InitialContext();
            //for pcs only
            DataSource ds = (DataSource) ctx.lookup(jndiName);
            //for macs only 
            //Context envCtx = (Context) ctx.lookup("java:comp/env");
            // DataSource ds = (DataSource) envCtx.lookup(jndiName);
            constructor = daoClass.getConstructor(new Class[]{
                DataSource.class, DbAccessor.class
            });
            Object[] constructorArgs = new Object[]{
                ds, db
            };

            wineDao = (IWineDao) constructor
                    .newInstance(constructorArgs);
        }

        return new WineService(wineDao);
    }

    /**
     * helper method to refresh the wine list after adding, editing, or deleting
     * a wine
     *
     * @param request the request that was made (add, edit, delete)
     * @param wineService (wine service class to actually complete the request)
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void refreshResults(HttpServletRequest request, WineService wineService)
            throws ClassNotFoundException, SQLException {
        List<Wine> wines = wineService.retrieveWineList(
                WINE_TABLE_NAME, MAX_TABLE_ROWS);
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
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {

        driverClass = getServletContext()
                .getInitParameter(DRIVER_CLASS_KEY);
        url = getServletContext()
                .getInitParameter(DATABASE_URL_KEY);
        userName = getServletContext()
                .getInitParameter(DATABASE_USERNAME_KEY);
        password = getServletContext()
                .getInitParameter(DATABASE_PASSWORD_KEY);
        

        dbStrategyClassName = getServletContext().getInitParameter(DB_STRATEGY_CLASS_NAME_KEY);
        daoClassName = getServletContext().getInitParameter(DAO_CLASS_NAME_KEY);
        jndiName = getServletContext().getInitParameter(DATABASE_JNDI_NAME_KEY);

    }

}
