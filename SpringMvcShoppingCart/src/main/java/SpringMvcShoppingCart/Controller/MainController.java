package SpringMvcShoppingCart.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SpringMvcShoppingCart.DAO.AccountDAO;
import SpringMvcShoppingCart.DAO.OrderDAO;
import SpringMvcShoppingCart.DAO.ProductDAO;
import SpringMvcShoppingCart.Entity.Account;
import SpringMvcShoppingCart.Entity.Product;
import SpringMvcShoppingCart.Model.CartInfo;
import SpringMvcShoppingCart.Model.CustomerInfo;
import SpringMvcShoppingCart.Model.ProductInfo;
import SpringMvcShoppingCart.Utils.Utils;
import SpringMvcShoppingCart.Validator.CustomerInfoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
@Controller
@Transactional // Enable Hibernate Transaction.
@EnableWebMvc  // Need to use RedirectAttributes
public class MainController {
 
    @Autowired
    private OrderDAO orderDAO;
 
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private AccountDAO accountDAO;

 
    @Autowired
    private CustomerInfoValidator customerInfoValidator;
 
    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
 
        // For Cart Form.
        // (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
        if (target.getClass() == CartInfo.class) {
 
        }
        // For Customer Form.
        // (@ModelAttribute("customerForm") @Validated CustomerInfo
        // customerForm)
        else if (target.getClass() == CustomerInfo.class) {
            dataBinder.setValidator(customerInfoValidator);
        }
 
    }
 
    @RequestMapping("/403")
    public String accessDenied() {
        return "/403";
    }
 
    @RequestMapping("/")
    public String home() {
        return "index";
    }
 
    // Product List page.
    /*@RequestMapping({ "/productList" })
    public String listProductHandler(Model model, @RequestParam(value="name",defaultValue= "") String likeName,
    		@RequestParam(value="page",defaultValue="1") int page) 
    {
        final int maxResult = 5;
        final int maxNavigationPage = 10;
        
        PaginationResult<ProductInfo> result = productDAO.queryProducts(page, maxResult, maxNavigationPage, likeName);
 
        model.addAttribute("paginationProducts", result);
        return "productList";
    }*/
    
    @RequestMapping({ "/productList" })
    public String listProductHandler(Model model, @RequestParam(value="name",defaultValue= "") String likeName,@RequestParam(value="page",defaultValue="1") int page) 
    {
        final int maxResult = 5;
        final int maxNavigationPage = 10;
        
        Page<Product> result= (Page<Product>) productDAO.queryProducts(page, maxResult, maxNavigationPage, likeName);
        model.addAttribute("paginationProducts", result);
        return "productList";
    }
    
    @RequestMapping(value="/viewBook/{pageid}")    
    public String edit(@PathVariable int pageid,Model m){    
        int total=pageid+10;    
		/*
		 * if(pageid==1){} else{ pageid=(pageid-1)*total+1; }
		 */
        List<Product> list=productDAO.getProductByPage(pageid,total);
        m.addAttribute("msg", list);  
        return "viewBook";    
    }    
    
    @RequestMapping(value="/ChildrensBooks/{catogeryName}")    
    public String getChildrensBooks(@PathVariable String catogeryName,Model m){    
        
        List<Product> listofBooks = productDAO.getProductByCatogery(catogeryName);
        m.addAttribute("children", listofBooks);  
        return "childrensBook";    
    }    
    
    @RequestMapping(value="/ChildrensBooksByPage/{pageid}")    
    public String getChildrensBooksByPage(@PathVariable int pageid,Model m){    
        
    	int total=pageid+10;    
    	String category="Childrens Books";
        List<Product> list=productDAO.getBooksByPage(pageid, total, category);
        m.addAttribute("children", list);  
       
        return "childrensBook";    
    }    
    
    @RequestMapping(value="/BiographiesBooks/{biographies}")    
    public String getBiographiesBooks(@PathVariable("biographies") String catogeryName,Model m){    
        List<Product> listofBooks = productDAO.getProductByCatogery(catogeryName);
        m.addAttribute("biographies", listofBooks);  
        return "BiographiesBooks";    
    }    
    
    @RequestMapping(value="/BiographiesBooksByPage/{pageid}")    
    public String getBiographiesBooksByPage(@PathVariable int pageid,Model m){    
        
    	int total=pageid+10;    
    	String category="Biographies";
        List<Product> list=productDAO.getBooksByPage(pageid, total, category);
        m.addAttribute("biographies", list);  
 
        return "BiographiesBooks";    
    }    
    
    @RequestMapping(value="/LiteratureBooks/{literature}")    
    public String getLiteratureBooks(@PathVariable("literature") String catogeryName,Model m){    
        List<Product> listofBooks = productDAO.getProductByCatogery(catogeryName);
        m.addAttribute("literature", listofBooks);  
        return "LiteratureBooks";    
    }    
    
    @RequestMapping(value="/LiteratureBooksByPage/{pageid}")    
    public String getLiteratureBooksByPageByPage(@PathVariable int pageid,Model m){    
        
    	int total=pageid+10;    
    	String category="Literature";
        List<Product> list=productDAO.getBooksByPage(pageid, total, category);
        m.addAttribute("literature", list);  
 
        return "LiteratureBooks";    
    }
    
    @RequestMapping(value="/SCI_FIBooks/{sci-fi}")    
    public String getSCI_FIBooks(@PathVariable("sci-fi") String catogeryName, Model m){    
        List<Product> listofBooks = productDAO.getProductByCatogery(catogeryName);
        m.addAttribute("sci_fi", listofBooks);  
        return "Sci_Fi";    
    }    
    
    @RequestMapping(value="/SCI_FIBooksByPage/{pageid}")    
    public String getSCI_FIByPageByPage(@PathVariable int pageid,Model m){    
        
    	int total=pageid+10;    
    	String category="Sci-Fi";
        List<Product> list=productDAO.getBooksByPage(pageid, total, category);
        m.addAttribute("sci_fi", list);  
 
        return "Sci_Fi";    
    }    
    
    @RequestMapping({ "/buyProduct" })
    public String listProductHandler(HttpServletRequest request, Model model, @RequestParam(value = "code", defaultValue = "") String code) 
    {
        Product product = null;
        if (code != null && code.length() > 0) 
        {
            product = productDAO.findProduct(code);
        }
        
        if (product != null) 
        {
        	CartInfo cartInfo = Utils.getCartInSession(request); // Cart info stored in Session.
 
            ProductInfo productInfo = new ProductInfo(product);
 
            cartInfo.addProduct(productInfo, 1);
        }
        return "redirect:/shoppingCart"; // Redirect to shoppingCart page.
    }
 
    @RequestMapping({ "/shoppingCartRemoveProduct" })
    public String removeProductHandler(HttpServletRequest request, Model model, @RequestParam(value = "code", defaultValue = "") String code) 
    {
        Product product = null;
        if (code != null && code.length() > 0) 
        {
            product = productDAO.findProduct(code);
        }
        if (product != null) 
        { 
            CartInfo cartInfo = Utils.getCartInSession(request); // Cart Info stored in Session.
 
            ProductInfo productInfo = new ProductInfo(product);
 
            cartInfo.removeProduct(productInfo);
 
        }
        return "redirect:/shoppingCart"; // Redirect to shoppingCart page.
    }
 
    // POST: Update quantity of products in cart.
    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
    public String shoppingCartUpdateQty(HttpServletRequest request, Model model, @ModelAttribute("cartForm") CartInfo cartForm) 
    {
        CartInfo cartInfo = Utils.getCartInSession(request);
        cartInfo.updateQuantity(cartForm);
        
        return "redirect:/shoppingCart"; // Redirect to shoppingCart page.
    }
 
    // GET: Show Cart
    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
    public String shoppingCartHandler(HttpServletRequest request, Model model) 
    {
        CartInfo myCart = Utils.getCartInSession(request);
  
        model.addAttribute("cartForm", myCart);
        return "shoppingCart";
    }
 
    // GET: Enter customer information.
    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
    public String shoppingCartCustomerForm(HttpServletRequest request, Model model) 
    {
        CartInfo cartInfo = Utils.getCartInSession(request);
       
        if (cartInfo.isEmpty())  // Cart is empty.
        {     
            return "redirect:/shoppingCart"; // Redirect to shoppingCart page.
        }
 
        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        if (customerInfo == null) 
        {
            customerInfo = new CustomerInfo();
        }
 
        model.addAttribute("customerForm", customerInfo);
 
        return "shoppingCartCustomer";
    }
 
    // POST: Save customer information.
    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
    public String shoppingCartCustomerSave(HttpServletRequest request, Model model, @ModelAttribute("customerForm") @Validated CustomerInfo customerForm, //
            BindingResult result, final RedirectAttributes redirectAttributes) 
    {
        if (result.hasErrors()) 
        {
            customerForm.setValid(false);
            return "shoppingCartCustomer"; // Forward to reenter customer info.
        }
 
        customerForm.setValid(true);
        CartInfo cartInfo = Utils.getCartInSession(request);
 
        cartInfo.setCustomerInfo(customerForm);

        return "redirect:/shoppingCartConfirmation"; // Redirect to Confirmation page.
    }
 
    // GET: Review Cart to confirm.
    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
    public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) 
    {
        CartInfo cartInfo = Utils.getCartInSession(request);
        
        if (cartInfo.isEmpty()) // Cart have no products.
        {  
            return "redirect:/shoppingCart";  // Redirect to shoppingCart page.
        } 
        else if (!cartInfo.isValidCustomer()) 
        { 
            return "redirect:/shoppingCartCustomer"; // Enter customer info.
        }
 
        return "shoppingCartConfirmation";
    }
 
    // POST: Send Cart (Save).
    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
    // Avoid UnexpectedRollbackException (See more explanations)
    @Transactional(propagation = Propagation.NEVER)
    public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) 
    {
        CartInfo cartInfo = Utils.getCartInSession(request);
        
        if (cartInfo.isEmpty())  // Cart have no products.
        {
            return "redirect:/shoppingCart"; // Redirect to shoppingCart page.
        } 
        
        else if (!cartInfo.isValidCustomer()) 
        {
            return "redirect:/shoppingCartCustomer";  // Enter customer info.
        }
        
        try 
        {
            orderDAO.saveOrder(cartInfo);
        } 
        catch (Exception e) 
        {
            return "shoppingCartConfirmation"; // Need: Propagation.NEVER?
        }
        
        Utils.removeCartInSession(request);  // Remove Cart In Session.
           
        Utils.storeLastOrderedCartInSession(request, cartInfo); // Store Last ordered cart to Session.
 
        return "redirect:/shoppingCartFinalize";    // Redirect to successful page.
    }
 
    @RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
    public String shoppingCartFinalize(HttpServletRequest request, Model model) 
    {
        CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);
 
        if (lastOrderedCart == null) {
            return "redirect:/shoppingCart";
        }
       
        return "shoppingCartFinalize";
    }

	@RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam("code") String code) throws IOException 
    {
        Product product = null;
        if (code != null) 
        {
            product = this.productDAO.findProduct(code);
        }
        if (product != null && product.getImage() != null) 
        {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }
    
    /*.....Create Account...*/
    
    //GET: Enter Account information.
    @RequestMapping(value = { "/createAccount" }, method = RequestMethod.GET)
    public String createAccount(HttpServletRequest request, Model model) 
    {
    	model.addAttribute("customerAccount", new Account()); 
        return "createAccount";
    }
    
    //POST: Save customer information.
    @RequestMapping(value = { "/saveCustomerAccount" }, method = RequestMethod.POST)
    public String saveCustomerAccount(HttpServletRequest request, Model model, @ModelAttribute("customerAccount") @Validated Account account, BindingResult result, final RedirectAttributes redirectAttributes) 
    {
        if (result.hasErrors()) 
        {
            return "createAccount"; // Forward to reenter Account info.
        }
        try 
        {
        	accountDAO.saveCustomerAccount(account);
        } 
        catch (Exception e) 
        {   
            String message = e.getMessage(); // Need: Propagation.NEVER?
            model.addAttribute("message", message);
          
            return "createAccount"; // Show product form.
        }
        return "accountCreatedSuccessfully";
    }
}
