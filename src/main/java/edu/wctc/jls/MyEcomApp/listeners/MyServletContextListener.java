
package edu.wctc.jls.MyEcomApp.listeners;

import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Servlet Context Listner Class responds to contextInitialized and
 * contextDestroyed events. Used here to get the last date the app was started
 * and shutdown (Hence, has application scope)
 * @author Jennifer
 */
public class MyServletContextListener implements ServletContextListener {

    /**
     * Automatically called by server when application starts up. Currently used
     * to date stamp the event.
     * @param event - automatically triggered when application starts
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        Date date = new Date();
        sc.setAttribute("appStarted", date);
    }

    /**
     * Automatically called by server when application shuts down. Currently
     * used to date stamp the event.
     * @param sce - automatically triggered when application is shutdown
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext cd = sce.getServletContext();
        Date date = new Date();
        cd.setAttribute("appShutown", date);
    }
}
