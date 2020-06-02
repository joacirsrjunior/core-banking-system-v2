package br.com.joacirjunior.corebanking.repository;

import br.com.joacirjunior.corebanking.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {

}
