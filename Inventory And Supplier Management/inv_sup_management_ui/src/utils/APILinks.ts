const hostname:string = window.location.hostname
const port:number = 8080;
const url:string = `http://${hostname}:${port}`;

export function getAllLocationsURL():string{
    return url+"/api/v1/locations"
}
