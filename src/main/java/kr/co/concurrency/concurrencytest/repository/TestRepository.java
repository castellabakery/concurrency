package kr.co.concurrency.concurrencytest.repository;

import kr.co.concurrency.concurrencytest.domain.entity.Test;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Test, Long> {

}
