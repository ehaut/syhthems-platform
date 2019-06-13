package top.sunriseydy.syhthems.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.sunriseydy.syhthems.common.enums.ResultEnum;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;
import top.sunriseydy.syhthems.db.model.DataStream;
import top.sunriseydy.syhthems.db.model.Device;
import top.sunriseydy.syhthems.db.model.Product;
import top.sunriseydy.syhthems.db.service.DataStreamService;
import top.sunriseydy.syhthems.db.service.DeviceService;
import top.sunriseydy.syhthems.db.service.ProductService;
import top.sunriseydy.syhthems.web.vo.ProductVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-05-23 23:13
 */
@RestController
@RequestMapping(value = "/web/api/product")
public class ProductController extends BaseController {
    @Autowired
    ProductService productService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    DataStreamService dataStreamService;

    @GetMapping
    public ResultVO getCurrentUserProductsWithDevices() {
        List<ProductVO> productVOs = new ArrayList<>();
        List<Product> products = productService.selectByUser(super.getCustomUserDetails().getUser());
        for (Product product : products) {
            List<Device> devices= deviceService.selectByProduct(product.getProductId());
            List<DataStream> dataStreams = dataStreamService.selectByProductId(product.getProductId());
            ProductVO productVO = new ProductVO();
            productVO.setProduct(product);
            productVO.setDevices(devices);
            productVO.setDataStreams(dataStreams);
            productVOs.add(productVO);
        }
        return ResultUtils.success(productVOs);
    }

    @GetMapping("/{pid}")
    public ResultVO getProductWithDevice(@PathVariable(required = true, name = "pid") Long productId) {
        Product product = productService.selectByPrimaryKey(productId);
        if (product == null) {
            return ResultUtils.error(ResultEnum.PRODUCT_NOT_FOUND);
        }
        ProductVO productVO = new ProductVO();
        productVO.setProduct(product);
        List<Device> devices = deviceService.selectByProduct(productId);
        productVO.setDevices(devices);
        List<DataStream> dataStreams = dataStreamService.selectByProductId(productId);
        productVO.setDataStreams(dataStreams);
        return ResultUtils.success(productVO);
    }

    @PostMapping
    public ResultVO addProduct(@Validated Product product) {
        return ResultUtils.success(productService.addProduct(product));
    }

    @PatchMapping("/{pid}")
    public ResultVO updateProduct(@PathVariable(required = true, name = "pid") Long productId,
                                  @Validated Product product) {
        product.setProductId(productId);
        productService.updateByPrimaryKeySelectiveWithVersion(product);
        return ResultUtils.success();
    }

    @DeleteMapping("/{pid}")
    public ResultVO deleteProduct(@PathVariable(required = true, name = "pid") Long productId) {
        productService.deleteProductByPrimaryKey(productId);
        return ResultUtils.success();
    }
}
