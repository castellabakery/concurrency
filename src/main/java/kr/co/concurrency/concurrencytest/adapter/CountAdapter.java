package kr.co.concurrency.concurrencytest.adapter;

import kr.co.concurrency.concurrencytest.enumerate.TestEnum;
import kr.co.concurrency.concurrencytest.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class CountAdapter {
    private final TestService testService;

    public CountAdapter(TestService testService) {
        this.testService = testService;
    }

    public boolean checkAndUpdate(TestEnum type, long id) throws Exception {
        if (type == null) {
            return false;
        }
        if (type.equals(TestEnum.INCREASE)) {
            testService.increase(id);
            return true;
        } else if (type.equals(TestEnum.DECREASE)) {
            testService.decrease(id);
            return true;
        } else{
            return false;
        }
    }
}
