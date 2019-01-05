package cz.zcu.kiv.pia.martinm.internetbanking.controller.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * DTO used for mapping form output
 * to {@link cz.zcu.kiv.pia.martinm.internetbanking.domain.User} entity.
 *
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
public class UserDto {

    /**
     * First name field. Must be filled and max 20 characters long.
     */
    @NotEmpty
    @Size(max = 20)
    private String firstName;

    /**
     * Last name field. Must be filled and max 30 characters long.
     */
    @NotEmpty
    @Size(max = 30)
    private String lastName;

    /**
     * Birth number field. Must be filled and max 20 characters long.
     */
    @NotEmpty
    @Size(max = 20)
    private String birthNumber;

    /**
     * Date of birth field. Can be empty.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthDate;

    /**
     * Email field. Must be filled, match email pattern and max 50 characters long.
     */
    @NotEmpty
    @Email
    @Size(max = 50)
    private String email;

    /**
     * Mobile number field. Can be empty, but when is filled the value must be max 16 characters.
     */
    @Size(max = 16)
    private String mobileNumber;

    /**
     * Street field. Can be empty, but when is filled the value must be max 30 characters.
     */
    @Size(max = 30)
    private String street;

    /**
     * House number field. Can be empty, but when is filled the value must be max 5 characters.
     */
    @Size(max = 5)
    private String houseNumber;

    /**
     * Zip code field. Can be empty, but when is filled the value must be max 6 characters.
     */
    @Size(max = 6)
    private String zipCode;

    /**
     * City field. Can be empty, but when is filled the value must be max 30 characters.
     */
    @Size(max = 30)
    private String city;

    /**
     * Hidden field for ID of turing test.
     */
    private Integer turingTestId;

    /**
     * Turing test answer field. Must be filled with correct answer to given question.
     */
    @NotNull
    private Integer turingTestAnswer;

    /**
     * Customer's agreement with terms and conditions. Must be true.
     */
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
