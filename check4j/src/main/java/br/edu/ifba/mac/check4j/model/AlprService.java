package br.edu.ifba.mac.check4j.model;

public enum AlprService {

    OPENALPR("openalpr"),
    CATEYE("cateye"),
    VRPDR("vrpdr");

    public final String id;

    private AlprService(String id) {
        this.id = id;
    }
    
}
