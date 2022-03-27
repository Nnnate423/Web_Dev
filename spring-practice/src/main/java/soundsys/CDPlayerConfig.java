package soundsys;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class CDPlayerConfig {

    @Bean(name = "sgt")
    public CompactDisc sgt(){
        return new Sgt();
    }

    @Bean(name = "cdPlayer")
    public CDPlayer cdPlayer(CompactDisc cd){
        return new CDPlayer(cd);
    }

    @Bean
    public Audience audience(){
        return new Audience();
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(CDPlayerConfig.class);
        System.out.println("-------BRANCH 1 MARK--------");
        Sgt cd = (Sgt) context.getBean("sgt");
        CDPlayer cdPlayer = (CDPlayer) context.getBean("cdPlayer");
        cdPlayer.playCd();
    }
}
