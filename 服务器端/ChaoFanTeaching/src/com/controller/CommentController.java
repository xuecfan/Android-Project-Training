package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.CommentService;

@RestController
public class CommentController {

	@Resource
	private CommentService commentService;
	
	@RequestMapping(value="/Comment",produces="text/html;charset=UTF-8")
	public String addcomment(@RequestParam("option") String option,@RequestParam(value="commenter1Id",required=false) String user,
			@RequestParam(value="messageId",required=false) Integer messageId,
			@RequestParam(value="commenter2Id",required=false) String objuser,@RequestParam(value="commentingContent",required=false) String content,
			@RequestParam(value="isOnTime",required=false) String time,@RequestParam(value="teachingQuality",required=false) String quality) {
		System.out.println(user+objuser);		
		return this.commentService.addcomment(option, user, objuser, content, time, quality,messageId);
		
	}
	
	@RequestMapping(value="/userComment",produces="text/html;charset=UTF-8")
	public String lookusercomment(@RequestParam("user")String user) {
		return this.commentService.lookusercomment(user);
	}
	
	@RequestMapping(value="/objuserComment",produces="text/html;charset=UTF-8")
	public String lookobjusercomment(@RequestParam("objuser")String objuser) {
		System.out.println(objuser);
		return this.commentService.lookobjusercomment(objuser);
	}
	
	@RequestMapping(value="/avgComment",produces="text/html;charset=UTF-8")
	public String avg(@RequestParam("objuser")String objuser) {
		System.out.println(objuser);
		return this.commentService.avg(objuser);
	}
	
}
