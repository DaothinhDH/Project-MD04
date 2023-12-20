package com.ra.controller.admin.order;

import com.ra.model.entity.Order;
import com.ra.model.entity.User;
import com.ra.model.service.order.OrderService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @RequestMapping("/order")
    public String order(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orderList",orders);
        return "admin/order/order";
    }
    @RequestMapping("/order/{id}")
    public String updateStatus(@PathVariable("id") Integer id,
                               @RequestParam("status") Integer status,
                               Model model){
        Integer newStatus = status;
        boolean updatedStatus = orderService.updateStatus(id,newStatus);
        if (updatedStatus){
            List<Order> orders = orderService.findAll();
            model.addAttribute("orderList",orders);
            return "admin/order/order";
        }
        return null;
    }
}
