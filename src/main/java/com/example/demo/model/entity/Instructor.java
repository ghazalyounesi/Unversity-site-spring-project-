package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rank")
    private Rank rank;

    public enum Rank {
        ASSISTANT, ASSOCIATE, FULL
    }

    public static Instructor createAssistantInstructor(User user) {
        Instructor instructor = new Instructor();
        instructor.setUserId(user.getId());
        instructor.setRank(Rank.ASSISTANT);
        return instructor;
    }

}
