package web.servlet;

import constant.Constant;
import domain.Category;
import domain.Product;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import service.ProductService;
import utils.BeanFactory;
import utils.UUIDUtils;
import utils.UploadUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/4.
 * 添加商品servlet，由于用了multipart所以不能用BaseServlet获取method等字段
 */
public class AddProductServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//保存图片和商品信息
			Map<String, Object> map = new HashMap<>();
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
			List<FileItem> fileitems = upload.parseRequest(request);
			for (FileItem fileitem : fileitems) {
				String key = fileitem.getFieldName();
				if (fileitem.isFormField()) {	//普通字段
					map.put(key, fileitem.getString("utf-8"));
				} else {	//文件
					String name = fileitem.getName();
					String realName = UploadUtils.getRealName(name);
					String uuidName = UploadUtils.getUUIDName(realName);
					String dir = UploadUtils.getDir();
					InputStream is = fileitem.getInputStream();
					
					String productPath = this.getServletContext().getRealPath("/products");
					File dirFile = new File(productPath, dir);
					if (!dirFile.exists()) {
						dirFile.mkdirs();
					}
					OutputStream os = new FileOutputStream(new File(dirFile, uuidName));
					IOUtils.copy(is, os);

					os.close();
					is.close();
					fileitem.delete();
					map.put(key, "products" + dir + "/" + uuidName);
				}
			}

			//封装product
			Product product = new Product();
			map.put("pid", UUIDUtils.getId());
			map.put("pdate", new Date());
			map.put("pflag", Constant.PRODUCT_IS_UP);
			BeanUtils.populate(product, map);
			Category category = new Category();
			category.setCid((String) map.get("cid"));
			product.setCategory(category);
			
			//调用serivce
			ProductService service = (ProductService) BeanFactory.getBean("ProductService");
			service.addProduct(product);
			
			//重定向
			response.sendRedirect(request.getContextPath() + "adminProduct?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "添加商品失败");
			request.getRequestDispatcher("jsp/msg.jsp").forward(request, response);
		} 
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
