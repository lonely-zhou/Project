package note.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: 全局异常捕获
 * @Date: 2022/1/26 下午 10:27
 **/

@Slf4j
@RestControllerAdvice
public class GlobalExceptionUtil {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public NoteResultUtil exception(RuntimeException e) {
        log.error("运行时异常: " + e.getCause());
        return NoteResultUtil.error(Integer.valueOf(HttpStatus.BAD_REQUEST.toString().split(" ")[0]),e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = AuthorizationException.class)
    public NoteResultUtil authorizationException(AuthorizationException e) {
        log.error("授权异常: " + e.getMessage());
        return NoteResultUtil.error(Integer.valueOf(HttpStatus.BAD_REQUEST.toString().split(" ")[0]),e.getMessage());
    }

}
