package com.hes.test.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

public class UserAccountDto implements Dto {

    private Long id;

    @Size(min = 3, max = 16)
    @Pattern(regexp = "\\w+")
    private String userName;

    @Size(min = 3, max = 16)
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String password;

    @Size(min = 1, max = 16)
    @Pattern(regexp = "\\w+")
    private String firstName;

    @Size(min = 1, max = 16)
    @Pattern(regexp = "\\w+")
    private String lastName;

    private RoleDto roleDto;
    private Status status = Status.ACTIVE;
    private LocalDateTime registrationDateTime = LocalDateTime.now();

    public UserAccountDto() {
    }

    @Override
    public String toString() {
        return "UserAccountDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roleDto=" + roleDto +
                ", status=" + status +
                ", registrationDateTime=" + registrationDateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccountDto that = (UserAccountDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public RoleDto getRoleDto() {
        return roleDto;
    }

    public void setRoleDto(RoleDto role) {
        this.roleDto = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
    }
}
