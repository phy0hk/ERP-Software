package com.erpsoftware.inv_sup_management.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erpsoftware.inv_sup_management.entity.Locations;

@Repository
public interface LocationsRepository extends JpaRepository<Locations,Integer>{
    List<Locations> findAllByParentId(Integer parentId);
}
