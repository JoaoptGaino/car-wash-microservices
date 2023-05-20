package com.br.joaoptgaino.schedulingservice.messaging;

import com.br.joaoptgaino.schedulingservice.exceptions.BusinessException;
import com.br.joaoptgaino.schedulingservice.model.Department;
import com.br.joaoptgaino.schedulingservice.model.Scheduling;
import com.br.joaoptgaino.schedulingservice.repository.SchedulingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentConfirmationEvent {
    private final SchedulingRepository schedulingRepository;

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload Message message) {
        log.info("Scheduling paid: {}", message.getHeaders().get("scheduling_payment"));
        UUID schedulingId = UUID.fromString((String) Objects.requireNonNull(message.getHeaders().get("scheduling_payment")));
        Double paidValue = (Double) message.getHeaders().get("scheduling_payment_value");

        if (paidValue != null) {
            Scheduling scheduling = schedulingRepository.findById(schedulingId).orElseThrow();
            Hibernate.initialize(scheduling.getDepartment());
            Double schedulingValue = getSchedulingValue(scheduling.getDepartment());
            Double schedulingPaidValue = scheduling.getPaidValue();

            if (scheduling.getPaidValue() >= schedulingValue) {
                log.info("Scheduling {} fully paid", schedulingId);
            } else {
                if (paidValue > schedulingValue) {
                    log.info("Change: {}", paidValue - schedulingValue);
                }
                if (schedulingPaidValue == 0) {
                    scheduling.setPaidValue(paidValue);
                    schedulingRepository.save(scheduling);
                } else {
                    scheduling.setPaidValue(scheduling.getPaidValue() + paidValue);
                    schedulingRepository.save(scheduling);
                }
                log.info("Value paid: {},scheduling value: {}", paidValue, schedulingValue);
            }
        }
        log.info("Message: {}", message);
    }

    private Double getSchedulingValue(Set<Department> departments) {
        return departments.stream().mapToDouble(Department::getPrice).sum();
    }
}
