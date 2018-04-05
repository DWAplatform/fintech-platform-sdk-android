package com.fintechplatform.ui.enterprise.db.enterprise;

import com.fintechplatform.ui.db.PlatformDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = PlatformDB.class)
public class Enterprise extends BaseModel {
    @PrimaryKey
    @Column
    public String id;
    @Column
    public String name;
    @Column
    public String type;
    @Column
    public String email;
    @Column
    public String telephone;
    @Column
    public String address;
    @Column
    public String postalCode;
    @Column
    public String city;
    @Column
    public String country;
    @Column
    public String legalRapresentativeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLegalRapresentativeId() {
        return legalRapresentativeId;
    }

    public void setLegalRapresentativeId(String legalRapresentativeId) {
        this.legalRapresentativeId = legalRapresentativeId;
    }
}
