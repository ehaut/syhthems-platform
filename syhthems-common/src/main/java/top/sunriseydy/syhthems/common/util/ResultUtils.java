package top.sunriseydy.syhthems.common.util;

import top.sunriseydy.syhthems.common.enums.ResultEnum;
import top.sunriseydy.syhthems.common.vo.ResultVO;

/**
 * 返回结果封装的工具类
 *
 * @author SunriseYDY
 * @date 2019-03-08 18:30
 */
public class ResultUtils {

    /**
     * 构建一个成功的返回结果 使用方法：<code>ResultUtils.<T>success(data)</code>
     *
     * @param data 传入的数据
     * @param <T>  传入的 data 的类型
     * @return ResultVO 返回结果类
     */
    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<>(ResultEnum.SUCCESS.getKey(), ResultEnum.SUCCESS.getValue(), data);
    }

    public static <T> ResultVO<T> success() {
        return success(null);
    }

    /**
     * 构建一个错误的返回结果 使用方法：<code>ResultUtils.<T>success(code, message, data?)</code>
     *
     * @param data 传入的数据
     * @param <T>  T 为传入的 data 的类型
     * @return ResultVO 返回结果类
     */
    public static <T> ResultVO<T> error(Integer code, String message, T data) {
        return new ResultVO<>(code, message, data);
    }

    public static <T> ResultVO<T> error(Integer code, String message) {
        return error(code, message, null);
    }

    public static <T> ResultVO<T> error(ResultEnum resultEnum) {
        return error(resultEnum.getKey(), resultEnum.getValue());
    }

    public static <T> ResultVO<T> error(ResultEnum resultEnum, T data) {
        return error(resultEnum.getKey(), resultEnum.getValue(), data);
    }

    public static <T> ResultVO<T> error(T data) {
        return error(ResultEnum.BASE_ERROR, data);
    }

    public static <T> ResultVO<T> error() {
        return error(ResultEnum.BASE_ERROR);
    }

}
