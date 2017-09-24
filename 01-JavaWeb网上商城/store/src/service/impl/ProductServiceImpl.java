package service.impl;

import dao.ProductDao;
import dao.impl.ProductDaoImpl;
import domain.PageBean;
import domain.Product;
import service.ProductService;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */
public class ProductServiceImpl implements ProductService {
	
	@Override
	public List<Product> findHot() throws Exception {
		//查询热门商品
		ProductDao dao = new ProductDaoImpl();
		return dao.findHot();
	}

	@Override
	public List<Product> findNew() throws Exception {
		//查询最新商品
		ProductDao dao = new ProductDaoImpl();
		return dao.findNew();
	}

	@Override
	public Product getById(String pid) throws Exception {
		//单个商品详情
		ProductDao dao = new ProductDaoImpl();
		return dao.getById(pid);
	}

	@Override
	public PageBean<Product> findByPage(int pageNumber, int pageSize, String cid) throws Exception {
		//分页展示商品
		ProductDao dao = new ProductDaoImpl();
		//1.创建pagebean
		PageBean<Product> pageBean = new PageBean<Product>(pageNumber, pageSize);
		
		//2.设置当前页数据
		List<Product> list = dao.findByPage(pageBean, cid);
		pageBean.setData(list);
		
		//3.设置总记录数
		int totalRecord = dao.getTotalRecord(cid);
		pageBean.setTotalRecord(totalRecord);
		
		return pageBean;
	}

}
