package kr.co.concurrency.concurrencytest.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Order(1)
@Component
@Slf4j
public class CheckLockAdvisor {

    private final RedissonClient redissonClient;
    public CheckLockAdvisor(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Around("@annotation(kr.co.concurrency.concurrencytest.aop.annotation.CheckLock)")
    public Object processCustomAnnotation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object proceedReturnValue = null;
        final String lockName = "like:lock";
        final RLock lock = redissonClient.getLock(lockName);

        try {
            log.info("Wait the Lock...");
            if(!lock.tryLock(10, 1, TimeUnit.SECONDS)) {
//                throw new Exception("Failed to get the Lock !");
                return null;
            }
            log.info("You got the Lock !");
            proceedReturnValue = proceedingJoinPoint.proceed();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            if(lock != null && lock.isLocked()){
                lock.unlock();
            }
            log.info("Process Complete.");
        }

//        try {
//            if(lock.tryLock(30, 30, TimeUnit.SECONDS)) {
//                try {
//                    log.info("You got the Lock !");
//                    proceedReturnValue = proceedingJoinPoint.proceed();
//                } catch(Exception e){
//                    e.printStackTrace();
//                } finally {
//                    if(lock != null && lock.isLocked()){
//                        lock.unlock();
//                    }
//                    log.info("Lock is Down.");
//                }
//            }
//        } catch(InterruptedException e){
//            Thread.currentThread().interrupt();
//        }

        return proceedReturnValue;
    }
}
