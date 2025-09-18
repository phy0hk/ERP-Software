package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.entity.Locations;
import com.erpsoftware.inv_sup_management.security.AuthGuard;
import com.erpsoftware.inv_sup_management.services.Interfaces.LocationServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.StatusResponder;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AuthGuard
@RequestMapping("/api/v1")
public class LocationController {

    private final LocationServicesInterface locationServices;

    public LocationController(LocationServicesInterface locationService) {
        this.locationServices = locationService;
    }

    @GetMapping("/location/{id}")
    public StatusResponder<Locations> getLocation(@PathVariable Integer id) {
        try {
            Locations location = locationServices.getLocationInfo(id);
            return new StatusResponder<>("ok", location);
        } catch (Exception e) {
            return new StatusResponder<>("bad", null);
        }
    }

    @GetMapping("/locations")
    public StatusResponder<List<Locations>> getLocations() {
        try {
            List<Locations> locations = locationServices.getAllLocations();
            return new StatusResponder<>("ok", locations);
        } catch (Exception e) {
            return new StatusResponder<>("bad", null);
        }
    }

    @GetMapping("/locations/parent/{id}")
    public StatusResponder<List<Locations>> getMethodName(@PathVariable Integer id) {
        try {
            List<Locations> locations = locationServices.getAllLocationsByParentId(id);
            return new StatusResponder<>("ok", locations);
        } catch (Exception e) {
            return new StatusResponder<>("bad", null);
        }
    }

    @PostMapping("/location")
    public StatusResponder<Locations> createNewLocation(@RequestBody Locations entity) {
        try {
            Locations location = locationServices.saveLocation(entity);
            return new StatusResponder<>("ok", location);
        } catch (Exception e) {
            return new StatusResponder<>("bad", null);
        }
    }

    // @PostMapping("/locations")
    // public StatusResponder<Locations> createNewLocations(@RequestBody Locations entity) {
    //     try {
    //         Locations location = locationServices.saveLocation(entity);
    //         return new StatusResponder<>("ok", location);
    //     } catch (Exception e) {
    //         return new StatusResponder<>("bad", null);
    //     }
    // }

    @PutMapping("/location")
    public StatusResponder<Locations> updateLocation(@RequestBody Locations entity) {
        try {
            Locations location = locationServices.saveLocation(entity);
            return new StatusResponder<>("ok", location);
        } catch (Exception e) {
            e.printStackTrace();
            return new StatusResponder<>("bad", null);
        }
    }

}
