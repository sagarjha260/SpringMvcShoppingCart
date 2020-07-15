package SpringMvcShoppingCart.Config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
 
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
 
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter 
{ 
    private static final Charset UTF8 = Charset.forName("UTF-8");
 
	/*
	 * Use for reading or writing to the body of the request or response. 
	 * If no converters are added, default list of converters is registered.
	 */ 
    //Config UTF-8 Encoding.
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) 
    {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", UTF8)));
        converters.add(stringConverter);
 
        // Add other converters ...
    }
    
	/*
	 * Add handlers to serve static resources such as images, js, and, cssfiles from
	 * specific locations under web application root, the classpath,and others.
	 */    
    //Static Resource Config equivalents for <mvc:resources/> tags
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) 
    {
    	//This cache period is to store resource for that period and if request hit with in same period then resource will be fetched from cache. 
    	registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926); 
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
    }
 
	/*
	 * Configure a handler to delegate unhandled requests by forwarding to
	 * theServlet container's "default" servlet. A common use case for this is
	 * whenthe DispatcherServlet is mapped to "/" thus overriding theServlet
	 * container's default handling of static resources.
	 */    
    //equivalent for <mvc:default-servlet-handler/> tag
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) 
    {
        configurer.enable();
    }
}