package top.sunriseydy.syhthems.common.vo;

import lombok.Data;

/**
 * 返回的结果视图类
 *
 * @author SunriseYDY
 * @date 2019-03-08 18:25
 */
@Data
public class ResultVO<T> {

    /**
     * 是否成功标志
     * <p> true - 成功</p>
     * <p> false - 失败</p>
     */
    private Boolean success;

    /**
     * <p>返回的状态码</p>
     *     <li>0 - 成功</li>
     *     <li>其他为错误码 在 {@link top.sunriseydy.syhthems.common.enums.ResultEnum} 中定义</li>
     */
    private Integer code;

    /**
     * 返回的消息
     * <p>在 {@link top.sunriseydy.syhthems.common.enums.ResultEnum} 中定义</p>
     */
    private String message;

    /**
     * 返回的数据
     */
    private T data;

    public ResultVO(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultVO(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public ResultVO() {
    }
}
