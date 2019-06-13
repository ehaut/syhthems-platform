package top.sunriseydy.syhthems.db.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.sunriseydy.syhthems.db.model.DeviceDataStream;
import top.sunriseydy.syhthems.db.service.DeviceDataStreamService;

/**
 * @author SunriseYDY
 * @date 2019-05-23 17:09
 */
@Service
@CacheConfig(cacheNames = "DeviceDataStreamServiceImpl")
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class DeviceDataStreamServiceImpl extends BaseServiceImpl<DeviceDataStream> implements DeviceDataStreamService {
}
