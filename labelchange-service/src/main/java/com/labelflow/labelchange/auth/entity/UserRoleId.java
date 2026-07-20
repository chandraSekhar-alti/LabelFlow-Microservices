package com.labelflow.labelchange.auth.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserRoleId implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "role_id")
    private Short roleId;
}
