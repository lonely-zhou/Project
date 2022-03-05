package note.utils;

import com.upyun.FormUploader;
import com.upyun.Params;
import com.upyun.Result;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: 又拍云工具类
 * @Date: 2022/2/12 上午 10:37
 **/

public class UpYunUtil {
    public static Result testSync(byte[] datas, String filename, String username) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //初始化uploader
        FormUploader uploader = new FormUploader("lonelyzhou-img", "lonelyzhou", "cyp39jK5r8LJGTuX71hixV8KGMRE5Udc");

        //初始化参数组 Map
        final Map<String, Object> paramsMap = new HashMap<String, Object>();

        //添加 SAVE_KEY 参数
        //filename为文件名(例如：12345.jpg)
        String userAvatarFolder = "/note/userAvatar/" + username + "/";
        paramsMap.put(Params.SAVE_KEY, userAvatarFolder + filename);

        //添加同步上传作图参数 X_GMKERL_THUMB
        //限定图片宽度为 300px、锐化、压缩质量 80、存储为 png 格式（参数不区分先后顺序）
        paramsMap.put(Params.X_GMKERL_THUMB, "/fw/300/unsharp/true/quality/80/format/png");
        //打印结果
        Result result = null;
        result = uploader.upload(paramsMap, datas);
        return result;
    }
}