package com.ra.model.service.order;

import com.ra.model.dao.order.OrderDAO;
import com.ra.model.dao.orderdetail.OrderDetailDAO;
import com.ra.model.entity.CartItem;
import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceIMPL implements OrderService {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Override
    public Boolean order(Order order) {
        try {
            Order newOrder = orderDAO.save(order);
            List<CartItem> itemList = cartService.getCartItems();
            for (CartItem cartItem : itemList) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(newOrder);
                orderDetail.setProduct(cartItem.getProduct());
                orderDetail.setPrice(cartItem.getProduct().getPrice());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetailDAO.save(orderDetail);
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public Order findById(Integer id) {
        return orderDAO.findById(id);
    }

    @Override
    public List<OrderDetail> showOrderDetailsByOrderId(Integer order_id) {
        return orderDetailDAO.findAll(order_id);
    }

    @Override
    public boolean updateStatus(Integer id, Integer status) {
        return orderDAO.updateStatus(id,status);
    }

}
