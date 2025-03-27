package es.ticketmaster.entrega1.dto.user;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.ticketmaster.entrega1.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ShowUserDTO toShowUserDTO(UserEntity user);
    List<ShowUserDTO> toShowUserDTOs(Collection<UserEntity> users);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "artistsList", ignore = true)
    @Mapping(target = "ticketList", ignore = true)
    @Mapping(target = "profilePicture", ignore = true)
    UserEntity toShowUserDTO(ShowUserDTO userDTO);
}