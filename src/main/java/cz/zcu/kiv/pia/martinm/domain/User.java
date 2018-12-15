package cz.zcu.kiv.pia.martinm.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Date: 06.12.2018
 *
 * @author Martin Matas
 */
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "User")
public class User implements UserDetails, DataTransferObject<Integer> {

    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";

    @Id
    @Column(nullable = false, name = "ID")
        @ToString.Include
        @EqualsAndHashCode.Include
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private int id;
    @Column(nullable = false, name = "FirstName")
        private String firstName;
    @Column(nullable = false, name = "LastName")
        private String lastName;
    @Column(nullable = false, name = "BirthNumber")
        private String birthNumber;
    @Column(nullable = false, name = "BirthDate")
        private Date birthDate;

    @Column(nullable = false, name = "Email")
        private String email;
    @Column(nullable = false, name = "MobileNumber")
        private String mobileNumber;
    @Column(nullable = false, name = "Street")
        private String street;
    @Column(nullable = false, name = "HouseNumber")
        private int houseNumber;
    @Column(nullable = false, name = "ZipCode")
        private int zipCode;
    @Column(nullable = false, name = "City")
        private String city;

    @Column(nullable = false, name = "Login")
        private String login;
    @Column(nullable = false, name = "Password")
        private String password;

    /*
        Spring Security
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(ROLE_USER));
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

    public int getHouseNumber() {
        return houseNumber;
    }

    public int getZipCode() {
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
        return login;
    }
}
