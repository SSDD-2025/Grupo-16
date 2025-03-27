package es.ticketmaster.entrega1.dto.user;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import es.ticketmaster.entrega1.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ShowUserDTO toShowUserDTO(UserEntity user);
    List<ShowUserDTO> toShowUserDTOs(Collection<UserEntity> users);
    UserEntity toShowUserDTO(ShowUserDTO userDTO);
}
