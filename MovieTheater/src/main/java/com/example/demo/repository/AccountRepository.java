package com.example.demo.repository;

import com.example.demo.entity.Account;
import com.example.demo.enums.UserRoleEnums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountId(Long accountId);

    @Query("SELECT a FROM Account a WHERE a.username = :identifier OR a.email = :identifier OR a.phoneNumber = :identifier")
    Optional<Account> findByUsernameOrEmailOrPhoneNumber(@Param("identifier") String identifier);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByIdentityCard(String identityCard);

    // Member management queries
    List<Account> findByAccountRole(UserRoleEnums role);

    org.springframework.data.domain.Page<Account> findByAccountRole(UserRoleEnums role, org.springframework.data.domain.Pageable pageable);

    @Query("SELECT a FROM Account a WHERE a.accountRole = :role AND " +
            "(LOWER(a.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(a.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(a.phoneNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Account> findMembersBySearchTerm(@Param("role") UserRoleEnums role,
                                          @Param("searchTerm") String searchTerm,
                                          Pageable pageable);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.accountRole = :role")
    long countByAccountRole(@Param("role") UserRoleEnums role);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.accountRole = :role AND a.status = :status")
    long countByAccountRoleAndStatus(@Param("role") UserRoleEnums role, @Param("status") Boolean status);
}

