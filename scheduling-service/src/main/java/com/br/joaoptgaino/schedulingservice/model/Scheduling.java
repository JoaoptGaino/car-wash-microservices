package com.br.joaoptgaino.schedulingservice.model;

import com.br.joaoptgaino.schedulingservice.constants.SchedulingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "scheduling")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Scheduling implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "scheduling_department", joinColumns = @JoinColumn(name = "scheduling_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private Set<Department> department = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    private Date date;

    @Column(name = "paid_value")
    private Double paidValue;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SchedulingStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
