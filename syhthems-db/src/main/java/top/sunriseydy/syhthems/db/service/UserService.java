package top.sunriseydy.syhthems.db.service;

import top.sunriseydy.syhthems.db.model.User;

/**
 * @author SunriseYDY
 * @date 2019-03-26 15:30
 */
public interface UserService extends BaseService<User> {
    /**
     * 根据用户名检测用户是否存在
     *
     * @param username
     * @return
     */
    Boolean existsWithUsername(String username);

    /**
     * 根据用户名来查询用户
     *
     * @param username
     * @return
     */
    User selectByUsername(String username);

    /**
     * 注册用户
     *
     * @param newUser
     * @return
     */
    User registerUser(User newUser);

    /**
     * 更新用户中的登录相关字段
     *
     * @param user
     * @return
     */
    int updateLogin(User user);

    /**
     * 处理用户的登出
     */
    void userLogout(String username);
}
