package com.x75f.installer.Utils;

import com.google.api.client.util.Key;

import java.io.Serializable;

/**
 * Created by Jaspinder Kaur on 03-06-2016.
 */
public class UsersData implements Serializable {

    private String id;

    private String username;

    private String user_type;

    private String emailAddr;

    private double lng;

    private String address;

    private String city;

    private String zipcode;

    private String state;

    private String country;

    private String cloudServer;

    private String installedVersion;

    private String installedDate;

    private String installerEmail;

    private String installedTier;

    private double lat;

    private String ccuName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }


    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCloudServer() {
        return cloudServer;
    }

    public void setCloudServer(String cloudServer) {
        this.cloudServer = cloudServer;
    }

    public String getInstalledVersion() {
        return installedVersion;
    }

    public void setInstalledVersion(String installedVersion) {
        this.installedVersion = installedVersion;
    }

    public String getInstalledDate() {
        return installedDate;
    }

    public void setInstalledDate(String installedDate) {
        this.installedDate = installedDate;
    }

    public String getInstallerEmail() {
        return installerEmail;
    }

    public void setInstallerEmail(String installerEmail) {
        this.installerEmail = installerEmail;
    }

    public String getInstalledTier() {
        return installedTier;
    }

    public void setInstalledTier(String installedTier) {
        this.installedTier = installedTier;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCcuName() {
        return ccuName;
    }

    public void setCcuName(String ccuName) {
        this.ccuName = ccuName;
    }

    public void UsersData() {

    }

    public UsersData(String id, String username, String user_type, String emailAddr, double lng, String address, String city, String zipcode, String state, String country, String cloudServer, String installedVersion, String installedDate, String installerEmail, String installedTier, double lat, String ccuName) {
        this.id = id;
        this.username = username;
        this.user_type = user_type;
        this.emailAddr = emailAddr;
        this.lng = lng;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.state = state;
        this.country = country;
        this.cloudServer = cloudServer;
        this.installedVersion = installedVersion;
        this.installedDate = installedDate;
        this.installerEmail = installerEmail;
        this.installedTier = installedTier;
        this.lat = lat;
        this.ccuName = ccuName;
    }
}
