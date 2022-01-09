package note.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: Shiro - MD5加密类
 * @Date: 2022/1/9 下午 2:59
 **/

public class ShiroMD5 {
    public static Object setMD5(String username, String password) {
        String hashAlgorithName = "MD5";
        int hashIterations = 1024; /*加密次数*/
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        return new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
    }
}
