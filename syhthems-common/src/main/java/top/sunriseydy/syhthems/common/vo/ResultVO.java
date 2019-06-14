package top.sunriseydy.syhthems.common.vo;

import lombok.Data;

/**
 * 返回的结果对象
 *
 * @author SunriseYDY
 * @date 2019-03-08 18:25
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String message;

    private T data;

    public ResultVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVO() {
    }
}
