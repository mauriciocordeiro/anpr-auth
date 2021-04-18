import { Alpr } from "../model/alpr.model";
import { Vehicle } from "../model/vehicle.model";

var FormData = require('form-data');
var Headers = require('form-data');
var fs = require('fs');
const fetch = require("node-fetch");

const ALPR4J     = 'http://localhost:8080/v1/find';
const VEHICLESPY = 'http://localhost:5001/vehicles';

export const check = async (img: any): Promise<Vehicle> => {
    let rAlpr: Alpr = await alpr(img);

    return find(rAlpr.results[0].plate);
}

const alpr = async (img: any): Promise<Alpr> => {
    
    let formData = new FormData();
    formData.append('image', fs.createReadStream(img.path))

    const resp = await fetch(ALPR4J, {
        method: 'POST',
        body: formData
    });

    console.log(resp)

    if (!resp.ok) {
        throw new Error();
    }

    if(resp.body === null) {
        throw new Error("Not found");
    }

    return Object.assign(new Alpr(), resp.json());
}

const find = async (plate: string): Promise<Vehicle> => {

    const resp = await fetch(VEHICLESPY, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    });

    if (!resp.ok) {
        throw new Error();
    }

    if(resp.body === null) {
        throw new Error("Not found");
    }

    const vehicles: Array<Vehicle> = Object.assign(new Array(), resp.json());
    let v = new Vehicle();
    
    vehicles.forEach(item => {
        if(item.plate == plate) {
            v = item;
        }
    });

    return v;
}