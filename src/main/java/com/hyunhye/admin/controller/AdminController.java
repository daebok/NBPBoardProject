package com.hyunhye.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hyunhye.admin.service.AdminService;
import com.hyunhye.board.model.CategoryModel;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	public AdminService adminService;

	@RequestMapping("admin")
	public String admin(Model model) throws Exception {
		List<CategoryModel> list = adminService.categoryListAll();
		model.addAttribute("categoryList", list);
		return "admin/admin";
	}

	@RequestMapping(value = "categoryAdd", method = {RequestMethod.POST, RequestMethod.GET})
	public String categoryAdd(@ModelAttribute CategoryModel categoryModel) throws Exception {
		adminService.categoryAdd(categoryModel);
		return "redirect:/admin/admin";
	}

	@RequestMapping(value = "categoryDelete", method = {RequestMethod.POST, RequestMethod.GET})
	public String categoryDelete(@ModelAttribute CategoryModel categoryModel) throws Exception {
		System.out.println("categoryDelete");
		adminService.categoryDelete(categoryModel);
		return "redirect:/admin/admin";
	}

	@RequestMapping(value = "categoryModify", method = {RequestMethod.POST, RequestMethod.GET})
	public String categoryModify(@ModelAttribute CategoryModel categoryModel) throws Exception {
		adminService.categoryModify(categoryModel);
		return "redirect:/admin/admin";
	}
}
