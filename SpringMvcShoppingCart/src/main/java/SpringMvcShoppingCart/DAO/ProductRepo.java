package SpringMvcShoppingCart.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import SpringMvcShoppingCart.Entity.Product;

@Repository
public interface ProductRepo extends PagingAndSortingRepository<Product, Integer>
{	 
	@Query(value="SELECT * FROM Products WHERE Category =:category", nativeQuery=true)
	List<Product> findBycategory(@Param(value="category") String category);

	@Query(value="SELECT * FROM ( select pro.*, rownum r FROM products pro ) WHERE r >= :pageid-1 AND r <:total AND Category = :category", nativeQuery=true)
	List<Product> findByBookCategory(@Param(value="pageid") int pageid, @Param(value="total") int total, @Param(value="category") String category);
	
	
}
