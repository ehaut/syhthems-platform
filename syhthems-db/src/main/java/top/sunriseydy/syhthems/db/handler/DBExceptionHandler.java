package top.sunriseydy.syhthems.db.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tk.mybatis.mapper.version.VersionException;
import top.sunriseydy.syhthems.common.constants.BaseConstants;
import top.sunriseydy.syhthems.common.enums.ResultEnum;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;

import java.sql.SQLException;

/**
 * 处理数据库乐观锁版本号异常
 *
 * @author SunriseYDY
 * @date 2019-03-19 18:29
 */
@Slf4j
@RestControllerAdvice
@Order(BaseConstants.DEFAULT_ORDER - 10)
public class DBExceptionHandler {
    /**
     * 数据库乐观锁版本号验证失败异常处理
     *
     * @param e {@link VersionException}
     * @return ResultVO
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({VersionException.class})
    public ResultVO versionExceptionHandler(final VersionException e) {
        log.error("-----> 数据库版本号验证异常：" + e.getMessage(), e);
        return ResultUtils.error(ResultEnum.DATABASE_OPERATION_ERROR);
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResultVO databaseExceptionHandler(final Throwable e) {
        log.error("-----> 数据库异常：" + e.getMessage(), e);
        return ResultUtils.error(ResultEnum.DATABASE_ERROR);
    }
}
