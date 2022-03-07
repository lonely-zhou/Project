package note.config;

import cn.dev33.satoken.jwt.StpLogicJwtForStyle;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: sa-token配置类
 * @Date: 2022/3/6 下午 1:06
 **/
@Configuration
public class SaTokenConfigure {
    /**
     * Sa-Token 整合 jwt (Style模式)
     * @return StpLogicJwtForStateless
     */
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForStyle();
    }
}
