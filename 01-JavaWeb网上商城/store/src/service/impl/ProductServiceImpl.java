package service.impl;

import dao.ProductDao;
import domain.PageBean;
import domain.Product;
import service.ProductService;
import utils.BeanFactory;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */
public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> findHot() throws Exception {
		//查询热门商品
		//解耦合，不再使用new ProductDaoImpl();
		ProductDao dao = (ProductDao) BeanFactory.getBean("ProductDao");
		return dao.findHot();
	}

	@Override
	public List<Product> findNew() throws Exception {
		//查询最新商品
		ProductDao dao = (ProductDao) BeanFactory.getBean("ProductDao");
		return dao.findNew();
	}

	@Override
	public Product getById(String pid) throws Exception {
		//单个商品详情
		ProductDao dao = (ProductDao) BeanFactory.getBean("ProductDao");
		return dao.getById(pid);
	}

	@Override
	public PageBean<Product> findByPage(int pageNumber, int pageSize, String cid) throws Exception {
		//分页展示商品
		ProductDao dao = (ProductDao) BeanFactory.getBean("ProductDao");
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

	@Override
	public void addProduct(Product product) throws Exception {
		//添加商品
		ProductDao dao = (ProductDao) BeanFactory.getBean("ProductDao");
		dao.addProduct(product);
	}

	@Override
	public List<Product> findAll() throws Exception {
		//查询所有商品信息
		ProductDao dao = (ProductDao) BeanFactory.getBean("ProductDao");
		return dao.findAll();
	}

	@Override
	public void delete(String pid) throws Exception {
		ProductDao dao = (ProductDao) BeanFactory.getBean("ProductDao");
		//删除商品
		dao.delete(pid);
	}

	@Override
	public void editProduct(Product product) throws Exception {
		//添加商品
		ProductDao dao = (ProductDao) BeanFactory.getBean("ProductDao");
		dao.editProduct(product);
	}

}
