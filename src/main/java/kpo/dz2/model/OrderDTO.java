package kpo.dz2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import kpo.dz2.domain.OrderDish;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Integer id;

    @Size(max = 255)
    private String status;

    private String specialRequest;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer user;

    private List<Integer> dishSet;
}
