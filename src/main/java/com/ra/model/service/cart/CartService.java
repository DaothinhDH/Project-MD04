package com.ra.model.service.cart;

import com.ra.model.entity.CartItem;
import com.ra.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private HttpSession session;
    List<CartItem> cartItems = new ArrayList<>();

    public List<CartItem> getCartItems() {
        // ktra co thi lay ko co thi thoi
        cartItems = session.getAttribute("cart") != null ? (List<CartItem>) session.getAttribute("cart") : new ArrayList<>();
        return cartItems;
    }

    public void addToCart(CartItem item) {
        CartItem oldCartItem = findCartItemByProduct(item.getProduct());
        if (oldCartItem != null){
                oldCartItem.setQuantity(oldCartItem.getQuantity() + item.getQuantity());
        }else {
            // day item vao cart
            cartItems.add(item);
        }
        // luu vao session
        session.setAttribute("cart", cartItems);
    }

    public void update(Integer quantity, Integer productId) {
    }

    public void delete(Integer id) {
        List<CartItem> cartItems = getCartItems();
        cartItems.removeIf(item -> item.getProduct().getProductId() == id);
        session.setAttribute("cart",cartItems);
    }
    public CartItem findCartItemByProduct(Product product){
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId() == product.getProductId()){
                return cartItem;
            }
        }
        return null;
    }
}
