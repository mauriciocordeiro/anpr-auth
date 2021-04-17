import { Result } from "./result.model";

export class Alpr {
    imgHeight!:number;
    imgWidth!:number;
    processingTimeMillis!:number;
    results!: Array<Result>;
}