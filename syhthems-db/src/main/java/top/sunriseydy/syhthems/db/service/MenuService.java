package top.sunriseydy.syhthems.db.service;

import top.sunriseydy.syhthems.db.model.Menu;
import top.sunriseydy.syhthems.db.model.Role;

import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-03-26 17:05
 */
public interface MenuService extends BaseService<Menu> {
    /**
     * 根据角色list查询menu
     *
     * @param roles {@link Role}
     * @return {@link Menu}
     */
    List<Menu> selectByRoles(List<Role> roles);

    /**
     * 根据菜单代码查询菜单
     *
     * @param menuCode
     * @return
     */
    Menu selectByMenuCode(String menuCode);

    /**
     * 根据菜单父ID查询菜单
     *
     * @param parentId
     * @return
     */
    List<Menu> selectByParentId(Long parentId);

    /**
     * 根据权限来查询菜单
     *
     * @param permission
     * @return
     */
    List<Menu> selectByPermission(String permission);

}
