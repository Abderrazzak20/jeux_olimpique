package com.example.jeux_olimpique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.glassfish.jaxb.runtime.v2.runtime.output.Encoded;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.jeux_olimpique.models.User;
import com.example.jeux_olimpique.repository.UserRepository;
import com.example.jeux_olimpique.service.UserserviceImpl;
import com.example.jeux_olimpique.utils.Utilss;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class UserserviceImplTest {

	@InjectMocks
	private UserserviceImpl userserviceImpl;

	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private Utilss utilss;

	@Test
	void CreateUser_succes() {

		// CREAZIONE OGGETTO
		User user = new User();
		user.setEmail("test@example.com");
		user.setPassword("plaintext");

		// COMPORTAMENTO ATTESO
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
		when(utilss.generateKey()).thenReturn("GENERATED_KEY");
		when(passwordEncoder.encode("plaintext")).thenReturn("hashed");
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// CHIAMATA DEL METODO REALE
		User result = userserviceImpl.createUser(user);
		// VERIFICAZIONE
		assertEquals("GENERATED_KEY", result.getAccountKey());
		assertEquals("hashed", result.getPassword());
		verify(userRepository).save(any(User.class));

	}
	
	@Test
	void createUser_emailAlreadyExists() {
	    User user = new User();
	    user.setEmail("test@example.com");

	    when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

	    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	        userserviceImpl.createUser(user);
	    });

	    assertEquals("Email già in uso", exception.getMessage());
	}

	@Test
	void getUserById_succes() {

		User user = new User();
		user.setId(1L);

		when(userRepository.existsById(1L)).thenReturn(true);
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		Optional<User> result = userserviceImpl.getUserById(1L);
		assertEquals(1L, result.get().getId());

	}

	@Test
	void getUserById_notFound() {
		when(userRepository.existsById(1L)).thenReturn(false);
		
		RuntimeException exception = assertThrows(RuntimeException.class, ()->{
			userserviceImpl.getUserById(1L);
		});
		assertEquals("user pas trouve", exception.getMessage());
	}

	@Test
	void getAllUsers_success() {

		User user1 = new User();
		User user2 = new User();
		when(userRepository.findAll()).thenReturn(List.of(user1, user2));

		List<User> result = userserviceImpl.getAllUsers();
		assertEquals(2, result.size());
	}

	@Test
	void updateUser_succes() {
		User user = new User();
		user.setId(1L);
		when(userRepository.existsById(1L)).thenReturn(true);
		when(userRepository.save(user)).thenReturn(user);

		User result = userserviceImpl.updateUser(user);
		assertEquals(1L, result.getId());
	}

	@Test
	void updateUser_notfound() {

		User user = new User();
		user.setId(1L);
		when(userRepository.existsById(1L)).thenReturn(false);

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userserviceImpl.updateUser(user);
		});

		assertEquals("utente non esiste", exception.getMessage());

	}
	@Test
	void deleteUser_existingUser_success() {
	    when(userRepository.existsById(1L)).thenReturn(true);

	    userserviceImpl.deleteUser(1L);

	    verify(userRepository).deleteById(1L);
	
	}

	@Test
	void deleteUser_userNotFound() {
	    when(userRepository.existsById(1L)).thenReturn(false);

	    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	        userserviceImpl.deleteUser(1L);
	    });

	    assertEquals("user not find", exception.getMessage());
	}
}
