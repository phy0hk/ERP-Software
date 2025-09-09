package com.erpsoftware.inv_sup_management.services.Interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Locations;

@Service
public interface LocationServicesInterface {
    List<Locations> getAllLocations();
    List<Locations> getAllLocationsByParentId(Integer id);
    Locations getLocationInfo(Integer id);
    Locations saveLocation(Locations location);
}
