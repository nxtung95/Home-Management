package com.tungnx.home.controller;

import com.tungnx.home.dto.RoomInfoResponseDto;
import com.tungnx.home.service.RoomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/app/client/")
public class RoomInfoController {
	private final RoomInfoService roomInfoService;

	@Autowired
	public RoomInfoController(RoomInfoService roomInfoService) {
		this.roomInfoService = roomInfoService;
	}

	@Resource
	private HttpServletRequest request;

	@RequestMapping(value = "/infor", method = RequestMethod.GET)
	public String infor(Model model) {
		HttpSession session = request.getSession(false);
		int userId = session != null ? (int) session.getAttribute("userId") : 0;
		RoomInfoResponseDto roomInfoResponse = roomInfoService.getRoomInfo(userId);
		model.addAttribute("roomInfo", roomInfoResponse);
		model.addAttribute("stayDate", roomInfoResponse.getStudentResponseDtoSet().stream().findFirst().get().getStayDate());
		return "client/infor";
	}
}
