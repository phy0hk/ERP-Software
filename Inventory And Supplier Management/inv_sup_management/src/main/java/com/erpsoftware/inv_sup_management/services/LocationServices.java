package com.erpsoftware.inv_sup_management.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Locations;
import com.erpsoftware.inv_sup_management.repo.LocationsRepository;
import com.erpsoftware.inv_sup_management.security.ApiException;
import com.erpsoftware.inv_sup_management.services.Interfaces.LocationServicesInterface;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class LocationServices implements LocationServicesInterface {

    private final ObjectMapper mapper;
    private final LocationsRepository locationsRepository;
    private final RedisManager Cache;

    public LocationServices(LocationsRepository locationsRepository) {
        this.locationsRepository = locationsRepository;
        this.mapper = new ObjectMapper();
        this.Cache = new RedisManager();
    }

    @Override
    public List<Locations> getAllLocations() {
        try {
            String key = "location:all";
            String cacheData = Cache.getData(key);
            if (cacheData != null) {
                return mapper.readValue(cacheData, new TypeReference<List<Locations>>() {
                });
            }
            List<Locations> locations = locationsRepository.findAll();
            String json = mapper.writeValueAsString(locations);
            Cache.setData(key, json);
            return locations;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Internal Server Error", 500);
        }
    }

    @Override
    public List<Locations> getAllLocationsByParentId(Integer id) {
        try {
            String key = "location:parent" + id;
            String cacheData = Cache.getData(key);
            if (cacheData != null) {
                return mapper.readValue(cacheData, new TypeReference<List<Locations>>() {
                });
            }
            List<Locations> locations = locationsRepository.findAllByParentId(id);
            String json = mapper.writeValueAsString(locations);
            Cache.setData(key, json);
            return locations;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Internal Server Error", 500);
        }
    }

    @Override
    public Locations getLocationInfo(Integer id) {
        try {
            String key = "location:detail" + id;
            String cacheData = Cache.getData(key);
            if (cacheData != null) {
                return mapper.readValue(cacheData, new TypeReference<Locations>() {
                });
            }
            Locations location = locationsRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
            String json = mapper.writeValueAsString(location);
            Cache.setData(key, json);
            return location;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Internal Server Error", 500);
        }
    }

    @Override
    public Locations saveLocation(Locations location) {
        Cache.removeKeys("location");
        Locations newLocation = locationsRepository.save(location);
        return newLocation;
    }

}
