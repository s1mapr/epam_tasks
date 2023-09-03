package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, PagingAndSortingRepository<Order, Integer> {
    Optional<Order> getOrderById(int id);
    List<Order> getOrdersByUserId(int userId, Pageable pageable);
    List<Order> getOrdersByUserId(int userId);
    void deleteOrderByGiftCertificateId(int giftCertificateId);
}
