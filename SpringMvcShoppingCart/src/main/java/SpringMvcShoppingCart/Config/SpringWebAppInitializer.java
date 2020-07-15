package SpringMvcShoppingCart.Config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
 
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
 
/*
 * This onStartup(ServletContext servletContext) is internally called by spring class named SpringServletContainerInitializer like: initializer.onStartup(servletContext);

 * This class is used to add spring dispatcher servlet and add file upload settings to the servlet, filter, etc..
 * Here we register our ApplicationContextConfig in DispatcherServlet and we have set it to initialized first by dispatcher servlet 
 * Like: dispatcher.setLoadOnStartup(1); this will loads on the servlet at the time of deployment or server
 * 1 indicates that first of all the @bean method in ApplicationContextConfig will be created by IOC 
 * then other classes which is annotated by @Controller, @Service, etc.. 
 */
public class SpringWebAppInitializer implements WebApplicationInitializer 
{ 
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException 
    {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(ApplicationContextConfig.class);
 
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
         
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(appContext);
 
        servletContext.addListener(contextLoaderListener);
         
        //Filter.
        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");
    }
}
