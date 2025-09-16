module com.example.poo_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.persistence;

    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires javafx.graphics;
    requires java.desktop;


    opens com.example.poo_project to javafx.fxml;
    exports com.example.poo_project;
    opens com.example.entity;

    exports com.example.entity;
    exports com.example.service;
    exports com.example.dao;
    exports com.example.controller;
    opens com.example.controller to javafx.fxml;
}