package cz.zcu.kiv.pia.martinm.internetbanking.controller.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
public class UserDto {

    @NotEmpty
    @Size(max = 20)
    private String firstName;

    @NotEmpty
    @Size(max = 30)
    private String lastName;

    @NotEmpty
    @Size(max = 20)
    private String birthNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthDate;

    @NotEmpty
    @Size(max = 50)
    private String email;

    @Size(max = 16)
    private String mobileNumber;

    @Size(max = 30)
    private String street;

    @Size(max = 5)
    private String houseNumber;

    @Size(max = 6)
    private String zipCode;

    @Size(max = 30)
    private String city;

    private Integer turingTestId;

    @NotNull
    private Integer turingTestAnswer;

    @NotNull
    @AssertTrue
    private Boolean terms_conditions;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthNumber() {
        return birthNumber;
    }

    public void setBirthNumber(String birthNumber) {
        this.birthNumber = birthNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getTerms_conditions() {
        return terms_conditions;
    }

    public void setTerms_conditions(Boolean terms_conditions) {
        this.terms_conditions = terms_conditions;
    }

    public Integer getTuringTestAnswer() {
        return turingTestAnswer;
    }

    public void setTuringTestAnswer(Integer turingTestAnswer) {
        this.turingTestAnswer = turingTestAnswer;
    }

    public Integer getTuringTestId() {
        return turingTestId;
    }

    public void setTuringTestId(Integer turingTestId) {
        this.turingTestId = turingTestId;
    }
}
