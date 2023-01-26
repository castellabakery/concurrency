package kr.co.concurrency.concurrencytest;

import kr.co.concurrency.concurrencytest.adapter.CountAdapter;
import kr.co.concurrency.concurrencytest.enumerate.TestEnum;
import kr.co.concurrency.concurrencytest.repository.TestRepository;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class ConcurrencyTestApplicationTests {

    @Autowired
    private CountAdapter countAdapter;
    @Autowired
    private TestRepository testRepository;

    @Before
    public void initialize() {
        kr.co.concurrency.concurrencytest.domain.entity.Test test = kr.co.concurrency.concurrencytest.domain.entity.Test.builder().id(1L).count(0).build();
        testRepository.save(test);
    }

    @Test
    @DisplayName("Increase Test")
    void 증가테스트() throws InterruptedException {
        int threadCount = 10000;
        //멀티스레드 이용 ExecutorService : 비동기를 단순하게 처리할 수 있도록 해주는 java api
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        //다른 스레드에서 수행이 완료될 때 까지 대기할 수 있도록 도와주는 API - 요청이 끝날때 까지 기다림
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                        try {
                            countAdapter.checkAndUpdate(TestEnum.INCREASE, 1L);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    }
            );
        }

        latch.await();

        kr.co.concurrency.concurrencytest.domain.entity.Test test = testRepository.findById(1L).orElseThrow();

        // 기대값 = 100
        assertThat(test.getCount()).isEqualTo(10000);
    }

    @Test
    @DisplayName("Decrease Test")
    void 감소테스트() throws InterruptedException {
        int threadCount = 19000;
        //멀티스레드 이용 ExecutorService : 비동기를 단순하게 처리할 수 있도록 해주는 java api
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        //다른 스레드에서 수행이 완료될 때 까지 대기할 수 있도록 도와주는 API - 요청이 끝날때 까지 기다림
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                        try {
                            countAdapter.checkAndUpdate(TestEnum.DECREASE, 1L);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    }
            );
        }

        latch.await();

        kr.co.concurrency.concurrencytest.domain.entity.Test test = testRepository.findById(1L).orElseThrow();

        // 기대값 = 100
        assertThat(test.getCount()).isEqualTo(0);
    }

}
