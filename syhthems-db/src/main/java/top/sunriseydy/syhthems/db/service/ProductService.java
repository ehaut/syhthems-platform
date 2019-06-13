package top.sunriseydy.syhthems.db.service;

import top.sunriseydy.syhthems.db.model.Product;
import top.sunriseydy.syhthems.db.model.User;

import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-05-21 15:24
 */
public interface ProductService extends BaseService<Product> {
    /**
     * 根据用户名查询用户产品列表
     * @param user
     * @return List<Product> if haven't product, then return null
     */
    List<Product> selectByUser(User user);

    /**
     * 添加产品
     * @param product
     * @return
     */
    Product addProduct(Product product);

    /**
     * 删除产品
     * @param productId
     */
    void deleteProductByPrimaryKey(Long productId);
}
