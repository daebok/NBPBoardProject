package com.hyunhye.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hyunhye.admin.service.AdminService;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.user.model.UserModel;

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

	/* 카테고리 관리 */
	@RequestMapping("category")
	public String category(Model model) throws Exception {
		List<CategoryModel> list = adminService.categoryListAll();
		model.addAttribute("categoryList", list);
		return "admin/categoryManage";
	}

	@RequestMapping(value = "categoryAdd", method = {RequestMethod.POST, RequestMethod.GET})
	public String categoryAdd(@ModelAttribute CategoryModel categoryModel) throws Exception {
		adminService.categoryAdd(categoryModel);
		return "redirect:/admin/category";
	}

	@RequestMapping(value = "categoryDelete", method = {RequestMethod.POST, RequestMethod.GET})
	public String categoryDelete(@ModelAttribute CategoryModel categoryModel) throws Exception {
		System.out.println("categoryDelete");
		adminService.categoryDelete(categoryModel);
		return "redirect:/admin/category";
	}

	@RequestMapping(value = "categoryModify", method = {RequestMethod.POST, RequestMethod.GET})
	public String categoryModify(@ModelAttribute CategoryModel categoryModel) throws Exception {
		adminService.categoryModify(categoryModel);
		return "redirect:/admin/category";
	}

	/* 회원 관리 */
	@RequestMapping("user")
	public String user(Model model) throws Exception {
		List<UserModel> list = adminService.userListAll();
		model.addAttribute("userList", list);
		return "admin/userManage";
	}

	@RequestMapping("userModify")
	public ResponseEntity<String> userModify(@ModelAttribute UserModel userModel) throws Exception {
		adminService.userModify(userModel);

		return new ResponseEntity<String>("user modified", HttpStatus.OK);
	}

	@RequestMapping("userDelete")
	public ResponseEntity<String> userDelete(@ModelAttribute UserModel userModel) throws Exception {
		adminService.userDelete(userModel);

		return new ResponseEntity<String>("user deleted", HttpStatus.OK);
	}
}