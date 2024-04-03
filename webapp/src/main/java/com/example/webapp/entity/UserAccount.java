package com.example.webapp.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

@Data
@Entity
@Table(name = "useraccount")
@Transactional
public class UserAccount implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private UUID id;

  @Column(unique = true)
  private String username;

  private String password;

  private String firstName;

  private String lastName;

  private Boolean isVerfied = false;

  @Column(unique = true)
  private String token;

  private LocalDateTime tokenExpTime;

  private String link;

  @CreationTimestamp
  @Column(name = "account_created")
  private LocalDateTime accountCreated;

  @UpdateTimestamp
  @Column(name = "account_updated")
  private LocalDateTime accountUpdated;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
