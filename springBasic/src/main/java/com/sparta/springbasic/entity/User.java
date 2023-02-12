package com.sparta.springbasic.entity;

import lombok.*;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.regex.Pattern;

@Getter
@Entity
@Table(name="users")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

}
