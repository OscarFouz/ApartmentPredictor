package com.example.apartment_predictor;

import jakarta.persistence.Entity;

@Entity // Indicates that this class is an entity in the data model
public class Review {
    private int idReview;
    private int idApartmentReview;
    private int idReviewerReview;
    private String texto;
    private int valor;

    //Constructors
    public Review() {
    }

    public Review(int idReview, int idApartmentReview, int idReviewerReview, String texto, int valor) {
        this.idReview = idReview;
        this.idApartmentReview = idApartmentReview;
        this.idReviewerReview = idReviewerReview;
        this.texto = texto;
        this.valor = valor;
    }

    //Setters
    public void setIdApartmentReview(int idApartmentReview) {this.idApartmentReview = idApartmentReview; }

    public void setIdReviewerReview(int idReviewerReview) {this.idReviewerReview = idReviewerReview; }

    public void setTexto(String texto) {this.texto = texto; }

    public void setValor(int valor) {this.valor = valor; }

    //Getters

    public int getIdReview() {return idReview; }

    public int getIdApartmentReview() {return idApartmentReview; }

    public int getIdReviewerReview() {return idReviewerReview; }

    public String getTexto() {return texto; }

    public int getValor() {return valor; }

    @Override
    public String toString() {
        return "Review{" +
                "idReview=" + idReview +
                ", idApartmentReview=" + idApartmentReview +
                ", idReviewerReview=" + idReviewerReview +
                ", texto='" + texto + '\'' +
                ", valor=" + valor +
                '}';
    }
}
