package com.tungnx.home.controller;

import com.tungnx.home.dto.CommentResponseDto;
import com.tungnx.home.dto.InsertCommentRequestDto;
import com.tungnx.home.dto.InsertCommentResponseDto;
import com.tungnx.home.service.CommentService;
import com.tungnx.home.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app")
public class CommentController {

	private static final Logger log = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "/comments", method = RequestMethod.GET)
	public String show(HttpServletRequest request, Model model) {
		log.info("GET Comments");
		List<CommentResponseDto> comments = commentService.getAllComments();
		HttpSession session = request.getSession(false);
		if (session != null) {
			model.addAttribute("avatar", session.getAttribute("avatar"));
		}
		model.addAttribute("totalComment", comments.get(0).getTotal());
		model.addAttribute("comments", comments);
		return "comment";
	}

	@RequestMapping(value = "/comments", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public InsertCommentResponseDto insert(HttpServletRequest request, @RequestBody InsertCommentRequestDto insertCommentRequestDto) {
		log.info("Insert Comment");
		HttpSession session = request.getSession(false);
		Timestamp date = new Timestamp(System.currentTimeMillis());
		int commentId = 0;
		int statusCode;
		try {
			int userId = session != null ? (int) session.getAttribute("userId") : 0;
			commentId = commentService.insert(insertCommentRequestDto.getParentLevel(), insertCommentRequestDto.getComment(), userId, date);
			if (commentId > 0) {
				statusCode = HttpStatus.OK.value();
			} else {
				statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			}
		} catch (Exception e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			log.info("Error insert comment: " + e);
		}
		InsertCommentResponseDto commentResponseDto = new InsertCommentResponseDto();
		commentResponseDto.setStatusCode(statusCode);
		Map<String, Object> data = new HashMap<>();
		data.put("commentId", commentId);
		data.put("comment", insertCommentRequestDto.getComment());
		data.put("postedDate", DateUtil.formatDateFromTimestamp(date, "dd-MM-yyyy HH:mm"));
		data.put("avatar", session.getAttribute("avatar"));
		data.put("userName", session.getAttribute("userName"));
		data.put("parentLevel", insertCommentRequestDto.getParentLevel());
		commentResponseDto.setData(data);
		return commentResponseDto;
	}
}
