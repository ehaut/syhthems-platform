package top.sunriseydy.syhthems.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import top.sunriseydy.syhthems.common.exception.ServiceException;
import top.sunriseydy.syhthems.db.model.Product;
import top.sunriseydy.syhthems.db.model.User;
import top.sunriseydy.syhthems.db.service.*;
import top.sunriseydy.syhthems.db.util.UserUtils;

import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-05-22 21:30
 */
@Service
@CacheConfig(cacheNames = "ProductServiceImpl")
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    @Autowired
    UserUtils userUtils;

    @Autowired
    DeviceService deviceService;

    @Autowired
    DataStreamService dataStreamService;

    @Override
    public List<Product> selectByUser(User user) {
        Assert.notNull(user, "用户不能为空");
        if (user.getUserId() == null) {
            throw new ServiceException("用户ID为空");
        }
        Product product = new Product();
        product.setUserId(user.getUserId());
        // TODO 分页查询
        return super.select(product);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Product addProduct(Product product) {
        Assert.notNull(product, "产品不能为空");
        product.setProductId(null);
        if (super.insertSelective(product) == 0) {
            throw new ServiceException("添加产品失败");
        }
        if (product.getProductId() == null) {
            throw new ServiceException("添加产品失败");
        }
        return product;
    }

    @Override
    public void deleteProductByPrimaryKey(Long productId) {
        Assert.notNull(productId, "产品不能为空");
        // 删除产品表记录
        super.deleteByPrimaryKeyWithVersion(productId);
        // 删除设备
        deviceService.deleteDevicesByProduceId(productId);

        // 删除产品下的数据流模板
        dataStreamService.deleteByProductId(productId);
    }
}
