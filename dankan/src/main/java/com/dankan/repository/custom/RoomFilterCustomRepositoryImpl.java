package com.dankan.repository.custom;

import com.dankan.domain.QRoom;
import com.dankan.domain.Room;
import com.dankan.dto.request.post.PostFilterRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Collections;
import java.util.List;

@Slf4j
public class RoomFilterCustomRepositoryImpl extends QuerydslRepositorySupport implements RoomFilterCustomRepository {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public RoomFilterCustomRepositoryImpl() {
        super(Room.class);
    }

    @Override
    public List<Room> findRoomByFilter(PostFilterRequestDto postFilterRequestDto) {
        QRoom qRoom = QRoom.room;
        BooleanBuilder builder = new BooleanBuilder();

        if (postFilterRequestDto.getAddress() != null) {
            builder.and(qRoom.roomAddress.address.contains(postFilterRequestDto.getAddress()));
        }

        if (postFilterRequestDto.getMinPayPrice()!=null && postFilterRequestDto.getMaxPayPrice()!=null) {
            builder.and(qRoom.roomCost.price.between(postFilterRequestDto.getMinPayPrice(),postFilterRequestDto.getMaxPayPrice()));
        }

        if (postFilterRequestDto.getMinDeposit()!=null && postFilterRequestDto.getMaxDeposit()!=null) {
            builder.and(qRoom.roomCost.deposit.between(postFilterRequestDto.getMinDeposit(),postFilterRequestDto.getMaxDeposit()));

        }
        
        if (postFilterRequestDto.getMinManagementCost()!=null && postFilterRequestDto.getMaxManagementCost()!=null) {
            builder.and(qRoom.roomCost.managementCost.between(postFilterRequestDto.getMinManagementCost(),postFilterRequestDto.getMaxManagementCost()));
        }
        
        if (postFilterRequestDto.getFloorList().size() > 0) {
            Long minFloorOptionValue = Collections.min(postFilterRequestDto.getFloorList());
            Long maxFloorOptionValue = Collections.max(postFilterRequestDto.getFloorList());

            Long minFloor = 0L;
            Long maxFloor = 0L;

            if (minFloorOptionValue == 0L) {
                minFloor = 1L;
            } else if (minFloorOptionValue == 1L) {
                minFloor = 6L;
            }

            if (maxFloorOptionValue == 0L) {
                maxFloor = 5L;
            } else if (maxFloorOptionValue >= 1L) {
                maxFloor = 10L;
            }

            if (minFloorOptionValue < 2L && maxFloorOptionValue <2L) {
                builder.and(qRoom.roomStructure.floor.between(minFloor,maxFloor));
            } else if (minFloorOptionValue < 2L && maxFloorOptionValue == 2L) {
                builder.and(qRoom.roomStructure.floor.goe(10L)
                        .or(qRoom.roomStructure.floor.between(minFloor,maxFloor)));
            } else {
                builder.and(qRoom.roomStructure.floor.goe(10L));
            }
        }
        
        if (postFilterRequestDto.getMinRoomSize()!=null && postFilterRequestDto.getMaxRoomSize()!=null) {
            builder.and(qRoom.roomStructure.roomSize.between(postFilterRequestDto.getMinRoomSize(),postFilterRequestDto.getMaxRoomSize()));
        }
        
        if (postFilterRequestDto.getElevator() != null) {
            builder.and(qRoom.elevatorOption.eq(1L));
        }

        if (postFilterRequestDto.getCanDeal()) {

        }

        if (!builder.hasValue()) {
            return null;
        }

        return jpaQueryFactory
                .selectFrom(qRoom)
                .where(builder)
                .fetch();
    }
}
