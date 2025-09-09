package com.erpsoftware.inv_sup_management.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Locations;
import com.erpsoftware.inv_sup_management.repo.LocationsRepository;
import com.erpsoftware.inv_sup_management.services.Interfaces.LocationServicesInterface;

@Service
public class LocationServices implements LocationServicesInterface{

    private final LocationsRepository locationsRepository;
    public LocationServices(LocationsRepository locationsRepository){
        this.locationsRepository = locationsRepository;
    }


    @Override
    public List<Locations> getAllLocations() {
        List<Locations> locations = locationsRepository.findAll();
        return locations;
    }

    @Override
    public List<Locations> getAllLocationsByParentId(Integer id) {
        List<Locations> locations = locationsRepository.findAllByParentId(id);
        return locations;
    }

    @Override
    public Locations getLocationInfo(Integer id) {
        Locations location = locationsRepository.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
        return location;
    }

    @Override
    public Locations saveLocation(Locations location) {
        Locations newLocation = locationsRepository.save(location);
        return newLocation;
    }
    
}
