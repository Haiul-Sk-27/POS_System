package com.StoreHub.StoreHub.pos.system.repository;

import com.StoreHub.StoreHub.pos.system.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {

    Store findByStoreAdminId(Long adminId);
}
