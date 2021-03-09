package com.raunak.dss;

public class patient_data {
    private String patient_name, patient_gender, patient_disease, patient_address, disease_stage,patient_age, patient_phone, suggested_medicine;

    public patient_data(){
    }

    public patient_data(String patient_name,String patient_gender, String patient_disease,
                        String patient_address, String disease_stage,String patient_age, String patient_phone, String suggested_medicine){
        this.patient_name = patient_name;
        this.patient_gender = patient_gender;
        this.patient_disease = patient_disease;
        this.patient_address = patient_address;
        this.disease_stage = disease_stage;
        this.patient_age = patient_age;
        this.patient_phone = patient_phone;
        this.suggested_medicine = suggested_medicine;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_gender(){
        return patient_gender;
    }

    public void setPatient_gender(String patient_gender) {
        this.patient_gender = patient_gender;
    }

    public String getPatient_disease() {
        return patient_disease;
    }

    public void setPatient_disease(String patient_disease) {
        this.patient_disease = patient_disease;
    }

    public String getPatient_address() {
        return patient_address;
    }

    public void setPatient_address(String patient_address) {
        this.patient_address = patient_address;
    }

    public String getDisease_stage() {
        return disease_stage;
    }

    public void setDisease_stage(String disease_stage) {
        this.disease_stage = disease_stage;
    }

    public String getPatient_age() {
        return patient_age;
    }

    public void setPatient_age(String patient_age) {
        this.patient_age = patient_age;
    }

    public String getPatient_phone() {

        return patient_phone;
    }
    public void setPatient_phone(String patient_phone) {
        this.patient_phone = patient_phone;
    }

    public String getSuggested_medicine() {
        return suggested_medicine;
    }

    public void setSuggested_medicine(String suggested_medicine) {
        this.suggested_medicine = suggested_medicine;
    }
}
