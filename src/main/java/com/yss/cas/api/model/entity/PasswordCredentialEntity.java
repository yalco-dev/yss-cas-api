package com.yss.cas.api.model.entity;

import com.yss.cas.api.model.CredentialType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(CredentialType.Values.PASSWORD)
public class PasswordCredentialEntity extends UserAccountCredentialsEntity {

    @Column(nullable = false)
    private String password;

    @Override
    public CredentialType getType() {
        return CredentialType.PASSWORD;
    }

}
