package SpringMvcShoppingCart.DAO.Impl;

import org.springframework.data.domain.Pageable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import SpringMvcShoppingCart.DAO.ProductDAO;
import SpringMvcShoppingCart.DAO.ProductRepo;
import SpringMvcShoppingCart.Entity.Product;
import SpringMvcShoppingCart.Model.PaginationResult;
import SpringMvcShoppingCart.Model.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
 
// Transactional for Hibernate
@Transactional
public class ProductDAOImpl implements ProductDAO {
 
    @Autowired
    private SessionFactory entityManagerFactory;
    
    @Autowired
    private ProductRepo productRepo;
    
	/*
	 * While using JdbcTemplate spring internally map datasource JdbcTemplate.class
	 * Like: public JdbcTemplate(DataSource dataSource) { setDataSource(dataSource);
	 * afterPropertiesSet(); }
	 */
    @Autowired
    JdbcTemplate template;    
 
    @Override
    public Product findProduct(String code) 
    {
        Session session = entityManagerFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Product.class);
        crit.add(Restrictions.eq("code", code));
        return (Product) crit.uniqueResult();
    }
 
    @Override
    public ProductInfo findProductInfo(String code) {
        Product product = this.findProduct(code);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getCode(), product.getName(), product.getCategory() ,product.getPrice());
    }
 
    @Override
    public void save(ProductInfo productInfo) 
    {
        String code = productInfo.getCode();
        
        Product product = null;
        boolean isNew = false;
        
        if (code != null) 
        {
            product = this.findProduct(code);
        }
        if (product == null) 
        {
            isNew = true;
            product = new Product();
            product.setCreateDate(new Date());
        }
        product.setCode(code);
        product.setName(productInfo.getName());
        product.setPrice(productInfo.getPrice());
        product.setCategory(productInfo.getCategory());
 
        if (productInfo.getFileData() != null) 
        {
            byte[] image = productInfo.getFileData().getBytes();
            if (image != null && image.length > 0) 
            {
                product.setImage(image);
            }
        }
        if (isNew)
        {
            this.entityManagerFactory.getCurrentSession().persist(product);
        }
        this.entityManagerFactory.getCurrentSession().flush();  // If error in DB, Exceptions will be thrown out immediately
    }
 
   /* @Override
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage, String likeName) {
    	
    	String sql = "Select new " + ProductInfo.class.getName() + "(p.code, p.name, p.price) " + " from "+ Product.class.getName() + " p ";
    	
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }
        sql += " order by p.createDate desc ";
        
        Session session = sessionFactory.getCurrentSession();
 
        Query query = session.createQuery(sql);
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
    }*/
    
    @SuppressWarnings("unchecked")
	@Override
    public Page<Product> queryProducts(int page, int maxResult, int maxNavigationPage, String likeName)
    {    
        Pageable paging = (Pageable) PageRequest.of(page, maxResult);
        
        Page<Product> pagedResult = (Page<Product>) productRepo.findAll(paging);
        
        return pagedResult;
    }

	@Override
	public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 @Override
     public List<Product> getProductByPage(int pageid, int total) 
     {
    	String sql="SELECT * from ( select pro.*, rownum r from products pro) where r >= "+(pageid-1) +" and r < "+total;
    	return template.query(sql, new RowMapper<Product>()
    	{
    		@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
    			
    			Product product=new Product();
    			product.setCode(rs.getString("Code"));
				product.setImage(rs.getBytes("Image"));
				product.setName(rs.getString("Name"));
				product.setPrice(rs.getFloat("Price"));
				product.setCreateDate(rs.getDate("Create_Date"));
				
				return product;
			}
    	} );  		
	 }

	 @Override
	 public List<Product> getProductByCatogery(String category) 
	 {
		 List<Product> listProducts= productRepo.findBycategory(category);
		 return  listProducts;
	 }

	@Override
	public List<Product> getBooksByPage(int pageid, int total, String category) {
		List<Product> listProducts= productRepo.findByBookCategory(pageid, total, category);
		return  listProducts;
	}

}
