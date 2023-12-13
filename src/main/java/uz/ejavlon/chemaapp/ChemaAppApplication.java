package uz.ejavlon.chemaapp;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.ejavlon.chemaapp.dto.SignInDto;
import uz.ejavlon.chemaapp.entity.User;
import uz.ejavlon.chemaapp.enums.Role;
import uz.ejavlon.chemaapp.repository.UserRepository;
import uz.ejavlon.chemaapp.service.UserService;

import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
public class ChemaAppApplication {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ChemaAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(Environment environment){
		String initMode = environment.getProperty("spring.sql.init.mode");
		return args -> {
			if ("always".equals(initMode)){
				var admin = User.builder()
						.firstName("Javlon")
						.lastName("Ergashev")
						.username("root")
						.password(passwordEncoder.encode("root123"))
						.role(Role.ADMIN)
						.build();

				Optional<User> optionalUser = userRepository.findByUsername(admin.getUsername());
				if (optionalUser.isEmpty()){
					userRepository.save(admin);
				}
				userService.signIn(SignInDto.builder().username(admin.getUsername()).password("root123").build());
				System.out.println("Admin (ejavlon) signin system");
			}
		};
	}
}
