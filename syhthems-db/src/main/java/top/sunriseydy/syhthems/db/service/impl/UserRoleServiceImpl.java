package top.sunriseydy.syhthems.db.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.sunriseydy.syhthems.db.model.UserRole;
import top.sunriseydy.syhthems.db.service.UserRoleService;

/**
 * @author SunriseYDY
 * @date 2019-03-26 17:30
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService {
}
