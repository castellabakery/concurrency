package kr.co.concurrency.concurrencytest.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private int count;

    @Builder
    public Test(long id, int count) {
        this.id = id;
        this.count = count;
    }

    public void plus() {
        this.count++;
    }

    public void minus() {
        this.count--;
    }
}
