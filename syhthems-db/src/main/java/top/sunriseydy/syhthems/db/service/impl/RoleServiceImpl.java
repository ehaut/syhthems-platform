package top.sunriseydy.syhthems.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import top.sunriseydy.syhthems.db.mapper.UserRoleMapper;
import top.sunriseydy.syhthems.db.model.Role;
import top.sunriseydy.syhthems.db.model.User;
import top.sunriseydy.syhthems.db.model.UserRole;
import top.sunriseydy.syhthems.db.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SunriseYDY
 * @date 2019-03-26 16:41
 */
@Service
@CacheConfig(cacheNames = "RoleServiceImpl")
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public List<Role> selectByUser(User user) {
        Assert.notNull(user, "用户不能为空");
        Example userRoleExample = new Example(UserRole.class);
        userRoleExample.createCriteria().andEqualTo(UserRole.USER_ID, user.getUserId());
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);
        if (CollectionUtils.isEmpty(userRoles)) {
            return null;
        }

        Example roleExample = new Example(Role.class);
        roleExample.createCriteria().andIn(Role.ROLE_ID,
                userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
        return super.selectByExample(roleExample);
    }

    @Override
    public Role selectByRoleName(String roleName) {
        Assert.hasText(roleName, "角色名不能为空");
        Example roleExample = new Example(Role.class);
        roleExample.createCriteria().andEqualTo(Role.ROLE_NAME, roleName);
        return super.selectOneByExample(roleExample);
    }

    @Override
    public Role selectByRoleCode(String roleCode) {
        Assert.hasText(roleCode, "角色代码不能为空");
        Example roleExample = new Example(Role.class);
        roleExample.createCriteria().andEqualTo(Role.ROLE_CODE, roleCode);
        return super.selectOneByExample(roleExample);
    }
}
