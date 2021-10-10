package com.tungnx.home.controller;

import com.tungnx.home.dto.PriceResponseDto;
import com.tungnx.home.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/app")
public class DashboardController {
    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
    
    public static int count = 1;

    @Autowired
    private PriceService priceService;

    @GetMapping("/client/dashboard")
    public String showClientDashboard(HttpServletRequest request, Model model) {
        log.info("Thread " + Thread.currentThread().getName() + ", count = " + count++);
        try {
            HttpSession session = request.getSession(false);
            int userId = session != null ? (int) session.getAttribute("userId") : 0;

            List<PriceResponseDto> priceList = priceService.getAllPriceFirstView(userId, 1, 6);
            model.addAttribute("priceList", priceList);
        } catch (Exception e) {
            log.error("Error showClientDashboard: " + e);
        }
        return "client/client_dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard() {
        return "admin/admin_dashboard";
    }
}
