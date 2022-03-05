package note.utils.sms;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.AllArgsConstructor;
import note.utils.ConstantUtil;
import note.utils.RedisUtil;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static note.utils.sms.SmsUtil.NOTE_SIGN;


/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: 腾讯短信服务类
 * @Date: 2022/1/16 上午 9:19
 **/
@Service
@AllArgsConstructor
public class SMSService {

    private final RedisUtil redisUtil;

    /**
     * 指定模板ＩＤ发送短信
     *
     * @param number 用户手机号
     * @param mark   0 -- 注册验证   1 -- 修改密码验证
     * @return OK 成功  null 失败
     */
    public String sendSms(String number, Integer mark) {
        try {
            // 验证码随机数
            String code = PhoneRandomBuilder.randomBuilder();
            /*验证码存入缓存*/
            redisUtil.set(ConstantUtil.USER_PHONE_CODE + number, code);
            /*设置过期时间 300s*/
            redisUtil.expire(ConstantUtil.USER_PHONE_CODE + number, 300);

            String[] params = {code};
            SmsSingleSender smsSingleSender = new SmsSingleSender(SmsUtil.APPID, SmsUtil.APP_KEY);
            if (mark == 0) {
                SmsSingleSenderResult result = smsSingleSender.sendWithParam("86", number,
                        SmsUtil.LOGIN, params, NOTE_SIGN, "", "");
                // 签名参数未提供或者为空时，会使用默认签名发送短信
                System.out.print(result);
                return result.errMsg;
            } else if (mark == 1) {
                SmsSingleSenderResult result = smsSingleSender.sendWithParam("86", number,
                        SmsUtil.UPDATE_PASSWORD, params, NOTE_SIGN, "", "");
                // 签名参数未提供或者为空时，会使用默认签名发送短信
                System.out.print(result);
                return result.errMsg;
            } else if (mark == 2) {
                SmsSingleSenderResult result = smsSingleSender.sendWithParam("86", number,
                        SmsUtil.UPDATE_PHONE, params, NOTE_SIGN, "", "");
                // 签名参数未提供或者为空时，会使用默认签名发送短信
                System.out.print(result);
                return result.errMsg;
            }
        } catch (HTTPException | JSONException | IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
