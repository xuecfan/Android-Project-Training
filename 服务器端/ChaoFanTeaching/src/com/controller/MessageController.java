package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.MessageService;

@RestController
public class MessageController {

	@Resource
	private MessageService messageService;
	
	@RequestMapping(value="/SubmitOrder",produces="text/html;charset=UTF-8")
	public void addmessage(@RequestParam("user") String user,@RequestParam(value="objusername",required=false) String name,
			@RequestParam(value="id",required=false) Integer id,
			@RequestParam(value="grade",required=false) String grade,@RequestParam(value="subject",required=false) String subject,
			@RequestParam(value="date",required=false) String date,@RequestParam(value="time",required=false) String time,
			@RequestParam(value="location",required=false) String locate,@RequestParam(value="length",required=false) String length,
			@RequestParam(value="tel",required=false) String tel,@RequestParam(value="more",required=false) String other,
			@RequestParam(value="pay",required=false) String pay,@RequestParam(value="objuser",required=false) String objuser) {
		System.out.println(id+user+name+grade+subject+date+time+tel);
		this.messageService.addmessage(id,user,objuser, name, grade, subject, date, length, locate, time, pay, tel, other);
	}
	
	@RequestMapping(value="/LookOrder",produces="text/html;charset=UTF-8")
	public String lookmessage(@RequestParam("id")Integer id) {
		return this.messageService.lookmessage(id);	
	}
	
	@RequestMapping(value="/userOrder",produces="text/html;charset=UTF-8")
	public String lookusermessage(@RequestParam("user")String user) {
		return this.messageService.lookusermessage(user);
	}
	
	@RequestMapping(value="/objuserOrder",produces="text/html;charset=UTF-8")
	public String lookobjusermessage(@RequestParam("user")String objuser) {
		return this.messageService.lookobjusermessage(objuser);
	}
	
	@RequestMapping(value="/deleteOrder",produces="text/html;charset=UTF-8")
	public void deletemessage(@RequestParam("id")Integer id) {
		this.messageService.deletemessage(id);
	}
	
	@RequestMapping(value="/editOrder",produces="text/html;charset=UTF-8")
	public void editmessage(@RequestParam("id")Integer id) {
		this.messageService.editmessage(id);
	}
	
	@RequestMapping(value="/edit1Order",produces="text/html;charset=UTF-8")
	public void edit1message(@RequestParam("id")Integer id) {
		this.messageService.edit1message(id);
	}
}
