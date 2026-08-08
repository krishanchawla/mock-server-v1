package com.mockit.mockserver.repository;

import com.mockit.mockserver.entity.MockRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import java.util.Optional;

public interface MockServiceRepository extends JpaRepository<MockRequest, Integer> {

    Optional<MockRequest> findByMockId(String mockId);

    @Transactional
    void deleteByMockId(String mockId);

    @Transactional
    @Modifying
    @Query("UPDATE MockRequest SET delay = :delay where mockId = :mockId")
    int updateMockServiceDelay(@Param("mockId") String mockId, @Param("delay") Integer delay);

    @Transactional
    @Modifying
    @Query("UPDATE MockRequest SET responseStatus = :responseStatus where mockId = :mockId")
    int updateMockServiceResponseStatus(@Param("mockId") String mockId, @Param("responseStatus") String responseStatus);
}
