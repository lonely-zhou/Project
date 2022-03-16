package note.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: 统一封装结果
 * @Date: 2022/1/26 下午 10:09
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteResultUtil {

    private Integer code;
    private String msg;
    private Object data;

    public static NoteResultUtil success() {
        return new NoteResultUtil(200, "成功");
    }

    public static NoteResultUtil success(String msg, Object data) {
        return new NoteResultUtil(200, msg, data);
    }

    public static NoteResultUtil success(Object data) {
        return new NoteResultUtil(200, "成功", data);
    }

    public static NoteResultUtil error(Integer code, String msg) {
        return new NoteResultUtil(code, msg);
    }

    public static NoteResultUtil error(String msg) {
        return new NoteResultUtil(500, msg);
    }

    public NoteResultUtil(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
