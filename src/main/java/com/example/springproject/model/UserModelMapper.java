package com.example.springproject.model;

public class UserModelMapper {
    public static UserModelDTO toDTO(UserModel user) {
        UserModelDTO userDTO = new UserModelDTO();
        if (user != null) {
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTO.setAddress(user.getAddress());
            userDTO.setCity(user.getCity());
        }
        return userDTO;
    }

    public static UserModel toEntity(UserModelDTO userDTO) {
        UserModel user = new UserModel();
        if (userDTO != null) {
            user.setId(userDTO.getId());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setAddress(userDTO.getAddress());
            user.setCity(userDTO.getCity());
        }
        return user;
    }

    public static void toEntity(UserModelDTO userDTO, UserModel user) {
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setCity(userDTO.getCity());
    }
}
