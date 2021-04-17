/**
 * Required External Modules and Interfaces
 */
import express, { Request, Response } from "express";
import { Vehicle } from "../model/vehicle.model";
import * as VehicleService from "../service/vehicle.service";

/**
 * Router Definition
 */
export const vehicleRoute = express.Router();

/**
 * Controller Definitions
 */
vehicleRoute.post("/", async (req: Request, res: Response) => {
    try {
        console.log('doing things...')
        const vehicle:Vehicle = await VehicleService.check(req.body);
        res.status(200).json(vehicle);
    } catch (e) {
        console.error(e);
        res.status(500).send(e.message);
    }
});
