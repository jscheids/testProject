package edu.wctc.jls.MyEcomApp.listeners;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * The session Listener class responds to sessionCreate and sessionDestroyed
 * events. Used here to track how many "hits," or sessions have been created.
 *
 * @author Jennifer
 */
public class MySessionListener implements HttpSessionListener {

    private static int hitCount = 0;

    /**
     * Automatically called by server when a user connects to the application.
     * Currently used to keep track of the total number of hits for the
     * application, where a 'hit' is defined as application access by one user.
     *
     * @param se - automatically triggered when user connects to application.
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ++hitCount;
        ServletContext ctx = se.getSession().getServletContext();

        synchronized (ctx) {
            ctx.setAttribute("hitCount", hitCount);
        }

    }
// Method not currently used

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }

    /**
     * returns the "hitCount"
     *
     * @return int
     */
    public static int getHitCount() {
        return hitCount;
    }

}
