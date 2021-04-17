import { Alpr } from "../model/alpr.model";
import { Vehicle } from "../model/vehicle.model";

export const check = async (img: any):Promise<Vehicle> => {
    let rAlpr:Alpr = await alpr(img);

    return find(rAlpr.results[0].plate);
}

const alpr = async (img: any): Promise<Alpr> => {

    return new Alpr();
}

const find = async (plate:string):Promise<Vehicle> => {
    return new Vehicle();
}