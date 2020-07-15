package SpringMvcShoppingCart.DAO;

import java.util.List;

import org.springframework.data.domain.Page;

import SpringMvcShoppingCart.Entity.Product;
import SpringMvcShoppingCart.Model.PaginationResult;
import SpringMvcShoppingCart.Model.ProductInfo;
 
public interface ProductDAO {
    
    public Product findProduct(String code);
    
    public ProductInfo findProductInfo(String code) ;
  
    public PaginationResult<ProductInfo> queryProducts(int page,int maxResult, int maxNavigationPage);
    
    public Page<Product> queryProducts(int page,int maxResult, int maxNavigationPage, String likeName);
 
    public void save(ProductInfo productInfo);

	public List<Product> getProductByPage(int pageid, int total);
	
	public List<Product> getProductByCatogery(String category);

	public List<Product> getBooksByPage(int pageid, int total, String category);
    
}
