package note.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: MD5加密类
 * @Date: 2022/1/27 下午 2:18
 **/

public class Md5Util {
    public static Object setMd5(String username, String password) {
        String hashAlgorithmName = "MD5";
        /*加密次数*/
        int hashIterations = 1024;
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        return new SimpleHash(hashAlgorithmName, password, credentialsSalt, hashIterations);
    }
}
