package com.bookStore.admin.product.handler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.bookStore.admin.product.service.IAdminProductService;
import com.bookStore.commons.beans.Product;
import com.bookStore.commons.beans.ProductList;
import com.bookStore.utils.IdUtils;

@Controller
@RequestMapping("/admin/products")
public class AdminProductHandler {
	
	@Autowired
	private IAdminProductService adminProductService;
	
	
	//商品信息查询
	@RequestMapping("/listProduct.do")
	public String listProduct(Model model){
		List<Product> products = adminProductService.findProduct(); //调用service层的方法查询商品信息
		model.addAttribute("products", products);  //将查询到的商品信息放入到model域中
		
		return "/admin/products/list.jsp";
	}
	
	//按图书类别查询
	@RequestMapping("/findProductByManyCondition.do")
	public String findProductByManyCondition(Product product, String minprice, String maxprice, Model model){
		List<Product> products = adminProductService.findProductByManyCondition(product, minprice, maxprice);
		model.addAttribute("products", products);  //将查询到的商品信息放入model域中
		model.addAttribute("product", product);  //将查询条件放到model域中
		model.addAttribute("minprice", minprice);  //将之前输入的查询最小价格放到model中
		model.addAttribute("maxprice", maxprice);  //将之前输入的查询最小大价格放到model中
		
		return "/admin/products/list.jsp";
	}
	
	
	//添加图书
	@RequestMapping("/addProduct.do")
	public String addProduct(MultipartFile upload, Product product, HttpServletRequest request) throws IllegalStateException, IOException{
		//获取存放图书图片的绝对路径
		String path = request.getSession().getServletContext().getRealPath("/productImg");
		File file = new File(path);
		if(!file.exists()){        //file.exists(), 如果路径存在返回true
			file.mkdirs();  //如果路径不存在则新建路径
		}
		String filename = IdUtils.getUUID() + "-" +upload.getOriginalFilename();  //定义图书图片文件名
		String imgurl = path + File.separatorChar + filename;  //定义图书图片路径
		upload.transferTo(new File(imgurl));  //保存图片
		product.setId(IdUtils.getUUID());    //设置保存的图书的id
		product.setImgurl("/productImg/" + filename);  //设置图书图片的路径
		
		int count = adminProductService.addProduct(product);  //将新的图书信息保存到数据库中
		return "/admin/products/listProduct.do";
		
	}
	
	//根据id查询图书信息
	@RequestMapping("/findProductById.do")
	public String findProductById(String id, Model model){
		Product product = adminProductService.findProductById(id);  //查询图书信息
		
		model.addAttribute("p", product);  //将查询到的图书信息保存到model域中
		
		return "/admin/products/edit.jsp";
		
	}
	
	//修改图书信息
	@RequestMapping("/editProduct.do")
	public String editProduct(Product product, MultipartFile upload, HttpSession session) throws IllegalStateException, IOException{
		//判断是否插入新的图片
		if(!upload.isEmpty()){
			String path = session.getServletContext().getRealPath("/productImg");  //获取图片的路径
			Product target = adminProductService.findProductById(product.getId());  //查询要修改的图书信息
			File targetfile = new File(session.getServletContext().getRealPath("/") + target.getImgurl());  //获取当前图书的图片路径
			if(targetfile.exists()){  
				targetfile.delete();  //如果图片路径存在则将其删除
			}
			String filename = IdUtils.getUUID() + "-" + upload.getOriginalFilename();  //定义新图片的名称
			String imgurl = path + File.separatorChar + filename;  //定义新图片的路径信息
			upload.transferTo(new File(imgurl));  //保存图片
			product.setImgurl("/productImg/" + filename);  //将新图片的路径保存到product
		}
		int count = adminProductService.modifyProduct(product);  //将新图片的信息保存到数据库中
		return "/admin/products/listProduct.do";
	}
	
	//删除图书
	@RequestMapping("/deleteProduct.do")
	public String deleteProduct(String id, HttpSession session){
		//根据id删除图书信息
		Product product = adminProductService.findProductById(id);
		//获取将要删除图书的图片路径
	    File targetfile = new File(session.getServletContext().getRealPath("/") + product.getImgurl());
	    if(targetfile.exists()){
	    	targetfile.delete();  //如果图片存在则将其删除
	    }
	    int count = adminProductService.removeProduct(id);  //根据id删除数据库中图书的信息
	    return "/admin/products/listProduct.do";
	}
	
	//销售榜单下载
	@RequestMapping("/download.do")
	public void download(String year, String month, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException{
		//根据年月查询图书信息
		List<ProductList> plist = adminProductService.findProductList(year, month);
		//定义csv文件名
		String filename = year +"年" + month + "月销售榜单.csv";
		response.setHeader("Content-Disposition", "attachment;filename="+processFileName(request, filename));
		//设置文件名的编码
		/*response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gbk"),"ISO-8859-1"));*/  
		response.setContentType(session.getServletContext().getMimeType(filename));
		//设置输出文件内容的编码
		response.setCharacterEncoding("GBK");
		PrintWriter out = response.getWriter();
		
		//输出文件内容
	    out.println("商品名称,商品销量");
	    for(int i=0; i<plist.size(); i++){
	    	ProductList pl = plist.get(i);
	    	out.println(pl.getName() + "," + pl.getSalnum());
	    }
	    out.flush();
	    out.close();
	}

	
	
	
	
	//设置编码
	public String processFileName(HttpServletRequest request, String fileNames){
		String codedFilename = null;
		try{
			String agent = request.getHeader("USER-AGENT");
			if(null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 !=agent.indexOf("Trident")){  //IE
				String name = java.net.URLEncoder.encode(fileNames, "UTF-8");
				
				codedFilename = name;
			}else if(null != agent && -1 != agent.indexOf("Mozilla")){  //火狐，Chrome等
				codedFilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return codedFilename;
	}
	
	
	
	
	
	
	
	
	
}
