package kr.co.concurrency.concurrencytest.domain.dto;

import kr.co.concurrency.concurrencytest.domain.entity.Test;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestDto {
    private long id;
    private int count;

    @Builder(builderClassName = "create", builderMethodName = "create")
    public TestDto(long id, int count){
        this.id = id;
        this.count = count;
    }

    public Test update(TestDto testDto){
        return Test
                .builder()
                .id(testDto.getId())
                .count(testDto.getCount())
                .build();
    }
}
