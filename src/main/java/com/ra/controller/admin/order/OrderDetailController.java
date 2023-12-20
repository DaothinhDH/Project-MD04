package com.ra.controller.admin.order;

import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Product;
import com.ra.model.service.order.OrderService;
import com.ra.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrderDetailController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @RequestMapping("/order/order-details/{id}")
   public String OrderDetail(@PathVariable("id") Integer id, Model model) {
       List <OrderDetail> orderDetailList = orderService.showOrderDetailsByOrderId(id);
        Order order = orderService.findById(id);
        model.addAttribute("order",order);
       model.addAttribute("orderDetail", orderDetailList);
       return "admin/order/orderDetail";
   }
}
