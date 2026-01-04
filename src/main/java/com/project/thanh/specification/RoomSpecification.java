package com.project.thanh.specification;

import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.data.jpa.domain.Specification;

import com.project.thanh.domain.Room;

public class RoomSpecification {

    public static Specification<Room> hasCapacity(Integer capacity) {
        return (root, query, builder) -> {
            if (capacity == null) {
                return builder.conjunction(); // không lọc
            }
            return builder.equal(root.get("roomType").get("capacity"), capacity);
        };
    }

    public static Specification<Room> hasType(String type) {
        return (root, query, builder) -> {
            if (type == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("roomType").get("name"), type);

        };
    }

    public static Specification<Room> priceBetween(Long minPrice, Long maxPrice) {
        return (root, query, builder) -> {
            if (minPrice != null && maxPrice != null) {
                return builder.between(root.get("roomType").get("price"), minPrice, maxPrice);
            }

            return builder.conjunction();
        };
    }

}
