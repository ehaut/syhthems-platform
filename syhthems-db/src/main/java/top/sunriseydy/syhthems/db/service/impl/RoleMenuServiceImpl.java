package top.sunriseydy.syhthems.db.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.sunriseydy.syhthems.db.model.RoleMenu;
import top.sunriseydy.syhthems.db.service.RoleMenuService;

/**
 * @author SunriseYDY
 * @date 2019-03-26 17:31
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenu> implements RoleMenuService {
}
