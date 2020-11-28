package com.mockit.MockServer.repository;

import com.mockit.MockServer.entity.MockRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MockServiceRepository extends JpaRepository<MockRequest, Integer> {

    Optional<MockRequest> findByMockId(String mockId);

    @Transactional
    void deleteByMockId(String mockId);

    @Transactional
    @Modifying
    @Query("UPDATE MockRequest SET delay = :delay where mock_id = :mockId")
    int updateMockServiceDelay(@Param("mockId") String mockId, @Param("delay") Integer delay);

    @Transactional
    @Modifying
    @Query("UPDATE MockRequest SET response_status = :responseStatus where mock_id = :mockId")
    int updateMockServiceResponseStatus(@Param("mockId") String mockId, @Param("responseStatus") String responseStatus);
}
