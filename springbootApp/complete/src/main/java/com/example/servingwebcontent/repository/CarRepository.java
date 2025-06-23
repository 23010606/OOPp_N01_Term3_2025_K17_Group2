// filepath: /workspaces/test_oop/springbootApp/complete/src/main/java/com/example/servingwebcontent/repository/CarRepository.java
package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, String> {
    // Có thể khai báo thêm các phương thức truy vấn đặc biệt nếu cần
}