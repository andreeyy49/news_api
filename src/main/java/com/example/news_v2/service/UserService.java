package com.example.news_v2.service;

import com.example.news_v2.entity.Role;
import com.example.news_v2.entity.RoleType;
import com.example.news_v2.entity.User;
import com.example.news_v2.repository.UserRepository;
import com.example.news_v2.utils.BeanUtils;
import com.example.news_v2.web.model.filter.ObjectFilter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public List<User> findAll(ObjectFilter filter) {
        return userRepository.findAll(
                PageRequest.of(
                        filter.getPageNumber(),
                        filter.getPageSize()
                )
        ).getContent();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "Пользователь с ID: {0} не найдена", id
        )));
    }

    public User save(User user, RoleType roleType) {
        Role role = Role.from(roleType);
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);

        return userRepository.save(user);
    }

    public User update(User user){
        User existedUser = findById(user.getId());

        BeanUtils.copyNotNullProperties(user, existedUser);

        return userRepository.save(user);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found"));
    }
}
