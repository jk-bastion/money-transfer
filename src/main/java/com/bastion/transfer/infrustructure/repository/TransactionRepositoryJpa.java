package com.bastion.transfer.infrustructure.repository;

import com.bastion.transfer.infrustructure.repository.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepositoryJpa extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByFromAccountId(long fromAccountId);
}
