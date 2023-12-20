package com.ra.controller.user;

import com.ra.model.dto.user.UserCheckOutDTO;
import com.ra.model.dto.user.response.UserResponseDTO;
import com.ra.model.entity.CartItem;
import com.ra.model.entity.Order;
import com.ra.model.entity.Product;
import com.ra.model.entity.User;
import com.ra.model.service.cart.CartService;
import com.ra.model.service.order.OrderService;
import com.ra.model.service.product.ProductService;
import com.ra.model.service.user.UserServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private HttpSession session;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserServive userServive;

    @RequestMapping("/cart")
    public String cart(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        float total = 0;
        for (CartItem cartItem : cartItems) {
            total = total + cartItem.getQuantity() * cartItem.getProduct().getPrice();
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        session.setAttribute("cartItems", cartItems);
        session.setAttribute("total", total);
        return "users/cart/cart";
    }

    @PostMapping("/addCart")
    public String addCart(@RequestParam("quantity") Integer quantity, @RequestParam("productId") Integer id) {
        CartItem cartItem = new CartItem();
        Product product = productService.findById(id);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartService.addToCart(cartItem);
        return "redirect:/cart";
    }
    @GetMapping("/deleteCart/{id}")
    public String deleteCart(@PathVariable("id") Integer id){
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");
        for (CartItem cartItem : cartItems) {
           cartService.delete(cartItem.getProduct().getProductId());
           break;
        }
        return "redirect:/cart";
    }
    @GetMapping("/checkout")
    public String checkout(Model model) {
        if (session.getAttribute("user") == null) {
            // chua co dang nhap bat dang nhap
            return "redirect:/cart";
        }
        UserCheckOutDTO checkOutDTO = new UserCheckOutDTO();
        UserResponseDTO user = (UserResponseDTO) session.getAttribute("user");
        checkOutDTO.setFullName(user.getUserName());
        checkOutDTO.setEmail(user.getUserEmail());
        checkOutDTO.setPhone(user.getPhoneNumber());
        checkOutDTO.setAddress(user.getAddress());
        model.addAttribute("checkOutDTO", checkOutDTO);
        return "users/checkout/checkout";
    }

    @PostMapping("/checkout")
    public String handleCheckout(@ModelAttribute("checkOutDTO") UserCheckOutDTO checkOutDTO) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        Order order =new Order();
        UserResponseDTO  orderUser = (UserResponseDTO) session.getAttribute("user");
        int id= orderUser.getUserId();
        User user = userServive.findById(id);
        order.setUser(user);
        order.setAddress(checkOutDTO.getAddress());
        order.setPhone(checkOutDTO.getPhone());
        order.setTotalPrice((Float) session.getAttribute("total"));
        orderService.order(order);
        return "users/cart/thanks";
    }
}

