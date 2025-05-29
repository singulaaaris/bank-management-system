package com.bank.repository;

import com.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByTimestampBetween(Date startDate, Date endDate);
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.account.customer.id = :customerId " +
            "AND t.transactionType IN ('Withdrawal', 'Transfer Out') " +
            "AND FUNCTION('MONTH', t.timestamp) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', t.timestamp) = FUNCTION('YEAR', CURRENT_DATE)")
    double sumSpentThisMonthByCustomerId(@Param("customerId") Long customerId);
    List<Transaction> findByAccountIdAndTimestampBetween(Long accountId, Date start, Date end);


}