package cz.zcu.kiv.pia.martinm.internetbanking.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
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

    public User () {

    }

    /**
     * Creates new user.
     *
     * @param firstName - user's first name
     * @param lastName - user's last name
     * @param birthNumber - user's birth number
     * @param email - user's email
     */
    public User(String firstName, String lastName, String birthNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthNumber = birthNumber;
        this.email = email;
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
    @Column(name = "BirthDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date birthDate;

    /**
     * user's email address.
     */
    @Column(nullable = false, name = "Email")
        private String email;

    /**
     * User's mobile phone number.
     */
    @Column(name = "MobileNumber")
        private String mobileNumber;

    /**
     * User's street address.
     */
    @Column(name = "Street")
        private String street;

    /**
     * User's house number.
     */
    @Column(name = "HouseNumber")
        private String houseNumber;

    /**
     * User's zip code.
     */
    @Column(name = "ZipCode")
        private String zipCode;

    /**
     * User's city of living.
     */
    @Column(name = "City")
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
     * User enabled.
     */
    @Column(nullable = false, name = "Enabled")
        private Boolean enabled = true;

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
    public boolean isEnabled() {
        return enabled;
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

    /**
     * Returns composed address from street, houseNumber, zipCode and city. If any of these attributes is null,
     * method returns empty string.
     *
     * @return address or empty string
     */
    @Transient
    public String getAddress() {
        if (!(isNullOrEmpty(street) && isNullOrEmpty(houseNumber) && isNullOrEmpty(zipCode) && isNullOrEmpty(city))) {
            return getStreet() + " " + getHouseNumber() + ", " + getZipCode() + " " + getCity();
        }
        return "";
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public Boolean getEnabled() {
        return enabled;
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

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set user's address.
     *
     * @param street - street
     * @param houseNumber - house number
     * @param zipCode - zip code
     * @param city - cite
     */
    public void setAddress(String street, String houseNumber, String zipCode, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    /**
     * Determines if given string is null or empty. If any of both is true,
     * method returns true.
     *
     * @param str - any string
     * @return true if given string is null or empty
     */
    private boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

}
