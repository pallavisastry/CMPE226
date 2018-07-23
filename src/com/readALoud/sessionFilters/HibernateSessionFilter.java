package com.readALoud.sessionFilters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.context.internal.ManagedSessionContext;

import com.readALoud.utils.HibernateUtil;


@WebFilter(description = "Hibernate Session Holder", urlPatterns = { "/HibernateSessionFilter" })
public class HibernateSessionFilter implements Filter {

	private static Logger log=Logger.getLogger(HibernateSessionFilter.class);
    private SessionFactory sf;
    //Session currentSession;

    public HibernateSessionFilter() {
     
    }
    public void destroy() {
	
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		try{
			log.debug("<<<Starting a database transaction>>>");
			sf.getCurrentSession().beginTransaction();
            
            
            // Call the next filter (continue request processing)
            chain.doFilter(request, response);
 
            // Commit and cleanup
            log.debug("<<<Committing the database transaction>>>");
            sf.getCurrentSession().getTransaction().commit();
	
		}catch(StaleObjectStateException staleEx){
			log.error("error!!");
			throw staleEx;
		}catch(Throwable ex){
			//roll back
			ex.printStackTrace();
			try{
				if(sf.getCurrentSession().getTransaction().isActive()){
					log.info("trying to roll back!!");
					sf.getCurrentSession().getTransaction().rollback();
				}
			}catch(Throwable rbEx){
					log.error("roll back transaction exception!!",rbEx);
				}
				throw new ServletException(ex);
			}
			finally{
				 log.error("Cleanup after exception!");
				 log.debug("Closing Session after exception");
	              sf.getCurrentSession().close();
			}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("Initialzing filter");
		log.info("Obtaining SessionFactory from static HibernateUtil singleton");
		
		sf=HibernateUtil.getSessionFactory();
	}
}
