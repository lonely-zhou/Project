package note.utils.sms;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: 短信验证码生成
 * @Date: 2022/1/16 下午 2:08
 **/

public class PhoneRandomBuilder {

    public static String randomBuilder() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            result.append(Math.round(Math.random() * 9));
        }

        return result.toString();

    }
}
