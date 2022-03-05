package note.utils.sms;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: 腾讯云短信验证常量
 * @Date: 2022/1/16 上午 9:14
 **/

public class SmsUtil {
    /**
     * 短信应用SDK AppID
     */
    public static final Integer APPID = 1400488314;

    /**
     * 短信应用SDK AppKEY
     */
    public static final String APP_KEY = "0a0ec7fde8c96cb61736c65cce4bc1c8";

    /**
     * 短信模板id（登录注册）
     */
    public static final Integer LOGIN = 1265359;

    /**
     * 短信模板id （密码重置）
     */
    public static final Integer UPDATE_PASSWORD = 1279363;

    /**
     * 短信模板id （更新号码）
     */
    public static final Integer UPDATE_PHONE = 1302204;

    /**
     * 短信签名
     */
    public static final String NOTE_SIGN = "lonelyzhou";
}
