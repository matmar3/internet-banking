package cz.zcu.kiv.pia.martinm.internetbanking.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * User entity.
 *
 * Date: 06.12.2018
 *
 * @author Martin Matas
 */
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "User")
public class User implements UserDetails, DataTransferObject<Integer> {

    /**
     * Defines possible user roles.
     */
    public enum Role {
        CUSTOMER, ADMIN
    }

    /**
     * User's identifier.
     */
    @Id
    @Column(nullable = false, name = "ID")
        @ToString.Include
        @EqualsAndHashCode.Include
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Integer id;

    /**
     * User's first name.
     */
    @Column(nullable = false, name = "FirstName")
        private String firstName;

    /**
     * User's last name.
     */
    @Column(nullable = false, name = "LastName")
        private String lastName;

    /**
     * User's birth number.
     */
    @Column(nullable = false, name = "BirthNumber")
        private String birthNumber;

    /**
     * User's birth date.
     */
    @Column(nullable = false, name = "BirthDate")
        private Date birthDate;

    /**
     * user's email address.
     */
    @Column(nullable = false, name = "Email")
        private String email;

    /**
     * User's mobile phone number.
     */
    @Column(nullable = false, name = "MobileNumber")
        private String mobileNumber;

    /**
     * User's street address.
     */
    @Column(nullable = false, name = "Street")
        private String street;

    /**
     * User's house number.
     */
    @Column(nullable = false, name = "HouseNumber")
        private Integer houseNumber;

    /**
     * User's zip code.
     */
    @Column(nullable = false, name = "ZipCode")
        private Integer zipCode;

    /**
     * User's city of living.
     */
    @Column(nullable = false, name = "City")
        private String city;

    /**
     * User's login name (username).
     */
    @Column(nullable = false, name = "Login")
        private String username;

    /**
     * User's password.
     */
    @Column(nullable = false, name = "Password")
        private String password;

    /**
     * User's roles as authorized user.
     */
    @Column(nullable = false, name = "Role")
        private String role;

    /**
     * User's accounts.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
        private Set<Account> accounts;

    /*
        Spring Security (UserDetails)
     */

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    /*
        Mappings
    */

    @Override
    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthNumber() {
        return birthNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    @Transient
    public String getAddress() {
        return getStreet() + " " + getHouseNumber() + ", " + getZipCode() + " " + getCity();
    }

    public String getStreet() {
        return street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
