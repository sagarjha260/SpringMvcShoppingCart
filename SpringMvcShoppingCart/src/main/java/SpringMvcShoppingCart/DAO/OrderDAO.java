package SpringMvcShoppingCart.DAO;

import java.util.List;

import SpringMvcShoppingCart.Model.CartInfo;
import SpringMvcShoppingCart.Model.OrderDetailInfo;
import SpringMvcShoppingCart.Model.OrderInfo;
import SpringMvcShoppingCart.Model.PaginationResult;
 
public interface OrderDAO {
 
    public void saveOrder(CartInfo cartInfo);
 
    public PaginationResult<OrderInfo> listOrderInfo(int page,
            int maxResult, int maxNavigationPage);
    
    public OrderInfo getOrderInfo(String orderId);
    
    public List<OrderDetailInfo> listOrderDetailInfos(String orderId);
 
}
