package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.projections.SalesRepportMinProjection;
import com.devsuperior.dsmeta.projections.SummaryMinProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    //sql raiz
    @Query(nativeQuery = true, value = "SELECT se.name AS sellerName, SUM(sa.amount) AS total " +
            "FROM tb_seller se " +
            "JOIN tb_sales sa ON sa.seller_id = se.id " +
            "WHERE UPPER(name) LIKE UPPER(CONCAT('%',:name,'%')) " +
            "AND sa.date BETWEEN :initialDate AND :finalDate " +
            "GROUP BY se.name")
    List<SummaryMinProjection> searchSummary(LocalDate initialDate, LocalDate finalDate, String name);

    @Query(nativeQuery = true, value = "SELECT sa.id, sa.date, sa.amount, se.name AS sellerName " +
            "FROM tb_sales sa " +
            "JOIN tb_seller se ON sa.seller_id = se.id " +
            "WHERE UPPER(se.name) LIKE UPPER(CONCAT('%',:name,'%')) " +
            "AND sa.date BETWEEN :initialDate AND :finalDate",
            countQuery = "SELECT COUNT(*) FROM tb_sales sa " +
                    "JOIN tb_seller se ON sa.seller_id = se.id " +
                    "WHERE (:name = '' OR UPPER(se.name) LIKE UPPER(CONCAT('%', :name, '%'))) " +
                    "AND sa.date BETWEEN :initialDate AND :finalDate")
    Page<SalesRepportMinProjection> searchSalesRepport(LocalDate initialDate, LocalDate finalDate, String name, Pageable pageable);

//    @Query(nativeQuery = true, value = "SELECT sa.id, sa.date, sa.amount, se.name AS sellerName " +
//            "FROM tb_sales sa " +
//            "JOIN tb_seller se ON sa.seller_id = se.id " +
//            "WHERE UPPER(se.name) LIKE UPPER(CONCAT('%',:name,'%')) " +
//            "AND sa.date BETWEEN :initialDate AND :finalDate")
//    List<SalesRepportMinProjection> searchSalesRepport(LocalDate initialDate, LocalDate finalDate, String name);
}
