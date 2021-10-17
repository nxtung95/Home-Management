package com.tungnx.home.controller;

import com.tungnx.home.dto.ElectricPriceResponseDto;
import com.tungnx.home.dto.PriceResponseDto;
import com.tungnx.home.entity.ElectricPrice;
import com.tungnx.home.entity.Price;
import com.tungnx.home.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app/client")
public class ElectricPriceController {
	private static final Logger log = LoggerFactory.getLogger(ElectricPriceController.class);

	@Autowired
	private PriceService priceService;

	@RequestMapping(value = "/electricPrice", method = RequestMethod.GET)
	public String showElectricPrice(HttpServletRequest request, Model model) {
		try {
			HttpSession session = request.getSession(false);
			int userId = session != null ? (int) session.getAttribute("userId") : 0;
			Page<ElectricPrice> pricePage = priceService.searchAllElectricPrice(userId, 1, 6, "", "");
			List<ElectricPriceResponseDto> priceList = pricePage
					.stream()
					.map(p -> new ElectricPriceResponseDto(
							p.getImportedDate(),
							p.getPreElectricNum(),
							p.getCurElectricNum(),
							p.getUnitPrice(),
							p.getTotalElectricNum(),
							p.getTotalElectricPrice(),
							p.getNote()
					))
					.collect(Collectors.toList());
			model.addAttribute("priceList", priceList);
			model.addAttribute("totalPrice", pricePage.getTotalElements());
			model.addAttribute("totalPage", pricePage.getTotalPages());
			model.addAttribute("currentPage", 1);
		} catch (Exception e) {
			log.error("Error showElectricPrice" + e);
		}
		return "client/electric_price";
	}

	@RequestMapping(
			value = "/electricPrice",
			method = RequestMethod.GET,
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	@ResponseBody
	public Map<String, Object> searchAllElectricPrice(
			HttpServletRequest request,
			@RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "6") int pageSize,
			@RequestParam(name = "year", required = false, defaultValue = "")  String year,
			@RequestParam(name = "month", required = false, defaultValue = "")  String month
	) {
		log.info("searchAllElectricPrice");
		Map<String, Object> json = new HashMap<>();
		try {
			HttpSession session = request.getSession(false);
			int userId = session != null ? (int) session.getAttribute("userId") : 0;
			Page<ElectricPrice> pricePage = priceService.searchAllElectricPrice(userId, pageNumber, pageSize, year, month);
			List<ElectricPriceResponseDto> priceList = pricePage
					.stream()
					.map(p -> new ElectricPriceResponseDto(
							p.getImportedDate(),
							p.getPreElectricNum(),
							p.getCurElectricNum(),
							p.getUnitPrice(),
							p.getTotalElectricNum(),
							p.getTotalElectricPrice(),
							p.getNote()
					))
					.collect(Collectors.toList());
			json.put("priceList", priceList);
			json.put("totalPrice", pricePage.getTotalElements());
			json.put("totalPage", pricePage.getTotalPages());
			json.put("currentPage", pageNumber);
		} catch (Exception e) {
			log.error("Error searchAllElectricPrice" + e);
		}
		return json;
	}
}
