package note.recordAndShare.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
  @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description:
 * @Date: 2022/1/9 下午 12:38
 */

/**
 * 自定义响应数据结构
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 503: 不具备角色功能
 * 555：异常抛出信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteResult {
    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static NoteResult success() {
        return new NoteResult(200, "success");
    }

    public static NoteResult errorMsg(String msg) {
        return new NoteResult(500, msg);
    }

    public NoteResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
