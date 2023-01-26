package kr.co.concurrency.concurrencytest.service;

import kr.co.concurrency.concurrencytest.aop.annotation.CheckLock;
import kr.co.concurrency.concurrencytest.domain.entity.Test;
import kr.co.concurrency.concurrencytest.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TestService {
    private final TestRepository testRepository;
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @CheckLock
    public void increase(long id) {
        Optional<Test> testOptional = testRepository.findById(id);
        Test test = testOptional.get();
        test.plus();
    }
    @Transactional(rollbackFor = Exception.class)
    @CheckLock
    public void decrease(long id) {
        Optional<Test> testOptional = testRepository.findById(id);
        Test test = testOptional.get();
        test.minus();
    }
}
