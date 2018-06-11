package com.bookStore.client.products.handler;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookStore.client.products.service.IProductService;
import com.bookStore.commons.beans.Notice;
import com.bookStore.commons.beans.Product;
import com.bookStore.utils.PageModel;

@Controller
@RequestMapping("/client/products")
public class ProductHandler {
	@Autowired
	private IProductService productService;
	
	
	@RequestMapping("/findProductByCategory.do")
	public String findProductByCategory(HttpServletRequest request ,@RequestParam(defaultValue="1")Integer pageIndex, Model model, String category ) throws UnsupportedEncodingException{
		
		if(category != null) {
			category = new String(category.getBytes("ISO-8859-1"), "UTF-8");
		}
		PageModel pageModel = new PageModel();
		pageModel.setPageIndex(pageIndex);
		Product product = new Product();
		product.setCategory(category);
		
		int count = productService.findProductCount(product);
		pageModel.setRecordCount(count);
		List<Product> products = productService.findProducts(product, pageModel);
		model.addAttribute("category", category);
		model.addAttribute("products", products);
		model.addAttribute("pageModel", pageModel);
		return "/client/product_list.jsp";
	}
	
	@RequestMapping("/findProductByName.do")
	public String findProductByName(@RequestParam(defaultValue="1")Integer pageIndex, String name, Model model) throws UnsupportedEncodingException{
		if(name != null) {
			name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
		}
		PageModel pageModel = new PageModel();
		pageModel.setPageIndex(pageIndex);
		Product product = new Product();
		product.setName(name);
		int count = productService.findProductCount(product);
		pageModel.setRecordCount(count);
		List<Product> products = productService.findProducts(product, pageModel);
		model.addAttribute("product", product);
		model.addAttribute("products", products);
		model.addAttribute("pageModel", pageModel);
		return "/client/product_search_list.jsp";
	}
	
	@RequestMapping("/findProductById.do")
	public String findProductById(String id, Model model){
		Product product = productService.findProductById(id);
		model.addAttribute("p", product);
		
		return "/client/info.jsp";
	}
	
	@RequestMapping("/showIndex.do")
	public String showIndex(Model model){
		Notice notice = productService.findRecentNotice();
		model.addAttribute("notice", notice);
		List<Product> products = productService.findWeekHostProduct();
		model.addAttribute("products", products);
		return "/client/index.jsp";
	}

}
