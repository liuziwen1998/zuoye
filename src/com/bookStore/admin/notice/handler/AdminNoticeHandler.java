package com.bookStore.admin.notice.handler;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookStore.admin.notice.service.IAdminNoticeService;
import com.bookStore.commons.beans.Notice;
import com.bookStore.utils.PageModel;

@Controller
@RequestMapping("/admin/notice")
public class AdminNoticeHandler {

	@Autowired
	private IAdminNoticeService adminNoticeService;
	
	@RequestMapping("/findListNotice.do")
	public String findListNotice(@RequestParam(defaultValue="1")Integer pageIndex,Model model){
		PageModel pageModel = new PageModel();
		pageModel.setPageIndex(pageIndex);
		int count = adminNoticeService.findNoticeCount();
		pageModel.setRecordCount(count);
		List<Notice> notices = adminNoticeService.findListNotice(pageModel);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("notices", notices);
		return "/admin/notices/list.jsp";
	}
	
	@RequestMapping("/addNotice.do")
	private String addNotice(Notice notice){
		
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		notice.setN_time(time);
		
		int count = adminNoticeService.addNotice(notice);
		
		return "/admin/notice/findListNotice.do";
	}
	
	@RequestMapping("/findNoticeById.do")
	public String findNoticeById(Integer id, Model model){
		Notice notice = adminNoticeService.findNoticeById(id);
		model.addAttribute("notice", notice);
		return "/admin/notices/edit.jsp";
	}
	
	@RequestMapping("/editNotice.do")
	public String editNotice(Notice notice){
		/*System.out.println(notice.getN_id());*/
		
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		notice.setN_time(time);
		int count = adminNoticeService.modifyNotice(notice);
		return "/admin/notice/findListNotice.do";
	}
	
	@RequestMapping("/removeNoticeById.do")
	public String removeNoticeById(Integer id){
		int count = adminNoticeService.removeNoticeById(id);
		return "/admin/notice/findListNotice.do";
	}
	
	
	
	
	
	
}
