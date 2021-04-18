/**
 * Required External Modules and Interfaces
 */
import express, { Request, Response } from "express";
import { Vehicle } from "../model/vehicle.model";
import * as VehicleService from "../service/vehicle.service";

const multer = require('multer');
const upload = multer({ dest: 'uploads/' });

/**
 * Router Definition
 */
export const vehicleRoute = express.Router();

/**
 * Controller Definitions
 */
vehicleRoute.post("/", upload.single('img'), async (req: Request, res: Response) => {
    try {

        console.log('body: ', req.body)
        console.log('file: ', req.file)

        const vehicle:Vehicle = await VehicleService.check(req.file);
        res.status(200).json(vehicle);
    } catch (e) {
        console.error(e);
        res.status(500).send(e.message);
    }
});
