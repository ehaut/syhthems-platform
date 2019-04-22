package top.sunriseydy.syhthems.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import top.sunriseydy.syhthems.db.mapper.RoleMenuMapper;
import top.sunriseydy.syhthems.db.model.Menu;
import top.sunriseydy.syhthems.db.model.Role;
import top.sunriseydy.syhthems.db.model.RoleMenu;
import top.sunriseydy.syhthems.db.service.MenuService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SunriseYDY
 * @date 2019-03-26 17:23
 */
@Service
@CacheConfig(cacheNames = "MenuServiceImpl")
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Override
    public List<Menu> selectByRoles(List<Role> roles) {
        Assert.notEmpty(roles, "用户角色不能为空");
        Example roleMenuExample = new Example(RoleMenu.class);
        roleMenuExample.createCriteria().andIn(RoleMenu.ROLE_ID,
                roles.stream().map(Role::getRoleId).collect(Collectors.toList()));
        List<RoleMenu> roleMenus = roleMenuMapper.selectByExample(roleMenuExample);
        if (CollectionUtils.isEmpty(roleMenus)) {
            return null;
        }
        Example menuExample = new Example(Menu.class);
        menuExample.createCriteria().andIn(Menu.MENU_ID,
                roleMenus.stream().map(RoleMenu::getMenuId).distinct().collect(Collectors.toList()));
        return super.selectByExample(menuExample);
    }

    @Override
    public Menu selectByMenuCode(String menuCode) {
        Assert.hasText(menuCode, "菜单代码不能为空");
        Example menuExample = new Example(Menu.class);
        menuExample.createCriteria().andEqualTo(Menu.MENU_CODE, menuCode);
        return super.selectOneByExample(menuExample);
    }

    @Override
    public List<Menu> selectByParentId(Long parentId) {
        Assert.notNull(parentId, "父菜单id不能为空");
        Example menuExample = new Example(Menu.class);
        menuExample.createCriteria().andEqualTo(Menu.PARENT_ID, parentId);
        return super.selectByExample(menuExample);
    }

    @Override
    public List<Menu> selectByPermission(String permission) {
        Assert.hasText(permission, "权限不能为空");
        Example menuExample = new Example(Menu.class);
        menuExample.createCriteria().andEqualTo(Menu.PERMISSION, permission);
        return super.selectByExample(menuExample);
    }
}
