package soundsys;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class Audience {

    @Pointcut("execution(** soundsys.CDPlayer.playCd(..))")
    public void cdplaylog(){}

    @Before("cdplaylog()")
    public void CDConsoleLog(){
        System.out.println("--------CD STARTS PLAYING MERGED--------");
        System.out.println("--------TEST 2--------");
        System.out.println("--------MAIN--------");

    }

    @After("cdplaylog()")
    public void CDConsoleLogAfter(){
        System.out.println("--------CD ENDS--------");
    }

    @AfterThrowing("cdplaylog()")
    public void CDConsoleLogThrow(){
        System.out.println("--------CD ERROR--------");
    }

    @Around("cdplaylog()")
    public void watchAround(ProceedingJoinPoint jp){
        try{
            System.out.println("--------CD STARTS PLAYING--------");
            jp.proceed();
            System.out.println("--------CD ENDS--------");
        }
        catch(Throwable e){
            System.out.println("--------CD ERROR--------");
        }
    }
}
