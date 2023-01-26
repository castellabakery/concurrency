package kr.co.concurrency.concurrencytest.controller;

import kr.co.concurrency.concurrencytest.adapter.CountAdapter;
import kr.co.concurrency.concurrencytest.enumerate.TestEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private final CountAdapter countAdapter;

    public TestController(CountAdapter countAdapter) {
        this.countAdapter = countAdapter;
    }

    @GetMapping("/increase")
    public void increase() throws Exception {
        countAdapter.checkAndUpdate(TestEnum.INCREASE, 1L);
    }

    @GetMapping("/decrease")
    public void decrease() throws Exception {
        countAdapter.checkAndUpdate(TestEnum.DECREASE,1L);
    }
}
