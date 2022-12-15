package com.Application.FoodToSave.Models.DTOs;

public class LogarDto {

    private String cpfCnpj,password;



    public LogarDto(String cpfCnpj, String password) {
        this.cpfCnpj = cpfCnpj;
        this.password = password;
    }

    public LogarDto() {}


    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
