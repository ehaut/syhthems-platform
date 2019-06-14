package top.sunriseydy.syhthems.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.sunriseydy.syhthems.common.exception.ServiceException;
import top.sunriseydy.syhthems.db.model.CustomDeviceUserDetails;
import top.sunriseydy.syhthems.db.model.DataStream;
import top.sunriseydy.syhthems.db.model.Device;
import top.sunriseydy.syhthems.db.model.Product;
import top.sunriseydy.syhthems.db.service.DataStreamService;
import top.sunriseydy.syhthems.db.service.DeviceService;
import top.sunriseydy.syhthems.db.service.ProductService;

import java.util.Collections;
import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-05-28 17:13
 */
@Service
public class CustomDeviceUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    DeviceService deviceService;

    @Autowired
    ProductService productService;

    @Autowired
    DataStreamService dataStreamService;

    @Override
    @Cacheable(cacheNames = "DeviceUserDetails",
            unless = "#username == null",
            cacheManager = "JDKCacheManager")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Device device;
        try {
            device = deviceService.selectByPrimaryKey(Long.valueOf(username));
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("用户名必须为设备ID");
        }
        if (device == null) {
            throw new UsernameNotFoundException("该设备不存在");
        }
        if (device.getProductId() == null) {
            throw new ServiceException("设备没有绑定产品");
        }
        Product product = productService.selectByPrimaryKey(device.getProductId());
        if (product == null) {
            throw new ServiceException("设备没有绑定产品");
        }
        // List<DataStream> dataStreams = dataStreamService.selectByDeviceId(device.getDeviceId());
        List<DataStream> dataStreams = dataStreamService.selectByProductId(product.getProductId());
        return new CustomDeviceUserDetails(username,
                device.getDeviceSecret(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_DEVICE")), product, device, dataStreams);
    }
}
