package top.sunriseydy.syhthems.db.service;

import top.sunriseydy.syhthems.db.model.Role;
import top.sunriseydy.syhthems.db.model.User;

import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-03-26 16:41
 */
public interface RoleService extends BaseService<Role> {
    /**
     * 根据用户查询所拥有的角色
     *
     * @param user {@link User}
     * @return {@link Role} 对象List
     */
    List<Role> selectByUser(User user);

    /**
     * 根据角色名称来查询角色
     *
     * @param roleName
     * @return
     */
    Role selectByRoleName(String roleName);

    /**
     * 根据角色代码来查询角色
     *
     * @param roleCode
     * @return
     */
    Role selectByRoleCode(String roleCode);
}
