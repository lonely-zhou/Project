package note.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForStyle;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description:
 * @Date: 2022/3/8 下午 3:16
 **/
@EnableWebMvc
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForStyle();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = new ArrayList<>();
        patterns.add("/recordAndShare/user/**");
        patterns.add("/recordAndShare/note/getNoteList");
        patterns.add("/recordAndShare/note/selNote");
        patterns.add("/recordAndShare/note/selSearchNote");
        patterns.add("/recordAndShare/note/selLabelValuesList");
        patterns.add("/recordAndShare/feedback/insFeedback");
        patterns.add("/recordAndShare/report-note/insReportNote");
        patterns.add("/recordAndShare/comment/selNoteCommentCount");
        patterns.add("/recordAndShare/comment/selNoteCommentList");
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new SaRouteInterceptor()).addPathPatterns("/**").excludePathPatterns(patterns);
    }
}
