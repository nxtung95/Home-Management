package com.tungnx.home.controller;

import com.tungnx.home.dto.TotalElectricPriceChartDto;
import com.tungnx.home.dto.TotalPriceChartResponseDto;
import com.tungnx.home.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app")
public class TotalElectricPriceChartController {
	@Autowired
	private PriceService priceService;

	@RequestMapping(value = "client/totalElectricPriceChart")
	public String showChart(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		int userId = session != null ? (int) session.getAttribute("userId") : 0;
		Map<String, List<TotalElectricPriceChartDto>> totalPriceChartList = priceService.getTotalElectricPriceByYearAndMonth(userId);
		model.addAttribute("totalPriceChartList", totalPriceChartList);
		return "client/client_electric_price_chart";
	};
}
