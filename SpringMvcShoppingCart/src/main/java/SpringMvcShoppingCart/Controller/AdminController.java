package SpringMvcShoppingCart.Controller;

import java.util.List;

import SpringMvcShoppingCart.DAO.OrderDAO;
import SpringMvcShoppingCart.DAO.ProductDAO;
import SpringMvcShoppingCart.Model.OrderDetailInfo;
import SpringMvcShoppingCart.Model.OrderInfo;
import SpringMvcShoppingCart.Model.PaginationResult;
import SpringMvcShoppingCart.Model.ProductInfo;
import SpringMvcShoppingCart.Validator.ProductInfoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@Controller
@Transactional  // Enable Hibernate Transaction.
@EnableWebMvc   // Need to use RedirectAttributes
public class AdminController 
{ 
	Logger logger= LoggerFactory.getLogger("com.plantplaces");
	
    @Autowired
    private OrderDAO orderDAO;
 
    @Autowired
    private ProductDAO productDAO;
 
    @Autowired
    private ProductInfoValidator productInfoValidator;
 
    
    @Autowired
    private ResourceBundleMessageSource messageSource; // Configurated In ApplicationContextConfig.
 
    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
 
        if (target.getClass() == ProductInfo.class) {
            dataBinder.setValidator(productInfoValidator);
            // For upload Image.
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }
 
    // GET: Show Login Page
    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login(Model model) 
    {
    	logger.info("Hi Users!!!");
        return "login";  
    }
 
    @RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
    public String accountInfo(Model model) 
    {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   
        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }
 
    @RequestMapping(value = { "/orderList" }, method = RequestMethod.GET)
    public String orderList(Model model, @RequestParam(value = "page", defaultValue = "1") String pageStr) 
    {
        int page = 1;
        try 
        {
            page = Integer.parseInt(pageStr);
        } 
        catch (Exception e) 
        {
        	
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;
 
        PaginationResult<OrderInfo> paginationResult = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
 
        model.addAttribute("paginationResult", paginationResult);
        return "orderList";
    }
 
    // GET: Show product. (Create product)
    @RequestMapping(value = { "/product" }, method = RequestMethod.GET)
    public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) 
    {
        ProductInfo productInfo = null;
 
        if (code != null && code.length() > 0) 
        {
            productInfo = productDAO.findProductInfo(code);
        }
        if (productInfo == null) 
        {
            productInfo = new ProductInfo();
            productInfo.setNewProduct(true);
        }
        model.addAttribute("productForm", productInfo);
        return "product";
    }
 
    // POST: Save product (On click Submit or reset while creating product)
    @RequestMapping(value = { "/product" }, method = RequestMethod.POST)
    @Transactional(propagation = Propagation.NEVER) // Avoid UnexpectedRollbackException (See more explanations)
    public String productSave(Model model, @ModelAttribute("productForm") @Validated ProductInfo productInfo, BindingResult result, final RedirectAttributes redirectAttributes) 
    {
        if (result.hasErrors()) 
        {
            return "product";
        }
        try 
        {
            productDAO.save(productInfo);
        } 
        catch (Exception e) 
        {   
            String message = e.getMessage(); // Need: Propagation.NEVER?
            model.addAttribute("message", message);
            
            return "product"; // Show product form.
        }
        return "redirect:/productList"; //Return to the 1st page with created product on top
    }
 
    @RequestMapping(value = { "/order" }, method = RequestMethod.GET)  // View product from Ordered List
    public String orderView(Model model, @RequestParam("orderId") String orderId) 
    {
        OrderInfo orderInfo = null;
        if (orderId != null) 
        {
            orderInfo = this.orderDAO.getOrderInfo(orderId);
        }
        if (orderInfo == null) {
            return "redirect:/orderList";
        }
        
        List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
        orderInfo.setDetails(details);
 
        model.addAttribute("orderInfo", orderInfo);
 
        return "order";
    }
    
}