package com.kbannach.funds.calculator.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@EqualsAndHashCode(of = {"name", "kind"})
@ToString
public class Fund {

    public enum Kind {
        PL, FOREIGN, MONETARY
    }

    private static AtomicLong sequence = new AtomicLong();

    public Fund(String name, Kind kind) {
        this.id = sequence.incrementAndGet();
        this.name = name;
        this.kind = kind;
    }

    private Long id;
    private String name;
    private Kind kind;
}
