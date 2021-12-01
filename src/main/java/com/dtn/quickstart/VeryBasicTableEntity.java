package com.dtn.quickstart;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "very_basic_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
public class VeryBasicTableEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    public UUID id;

    @Column(name = "created_date")
    public LocalDateTime createdDate;

    @Column(name = "updated_date")
    public LocalDateTime updatedDate;

    @Column
    public String message;

    public static Uni<VeryBasicTableEntity> saveData(VeryBasicTableEntity entity) {
        return Panache.withTransaction(entity::persist).replaceWith(entity).ifNoItem().after(Duration.ofMillis(1000))
                .fail().onFailure().transform(e -> new IllegalStateException(e));
    }

    public static Uni<VeryBasicTableEntity> updateDate(UUID id, VeryBasicTableEntity entity) {
        return Panache.withTransaction(() -> findById(id).onItem().ifNotNull().transform(data -> {
            VeryBasicTableEntity table = (VeryBasicTableEntity) data;
            table.message = entity.message;
            return table;
        })).onFailure().recoverWithNull();
    }

    public static Uni<Boolean> delete(UUID id) {
        return Panache.withTransaction(()-> deleteById(id));
    }

}
