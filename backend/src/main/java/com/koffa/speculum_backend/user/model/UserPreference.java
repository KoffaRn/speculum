package com.koffa.speculum_backend.user.model;

import com.koffa.speculum_backend.common.models.Place;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Embedded
    private Place home;
    @ElementCollection
    @CollectionTable(name = "favorite_places", joinColumns = @JoinColumn(name = "user_pref_id"))
    private List<Place> favoritePlaces;

}
