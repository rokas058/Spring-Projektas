package lt.codeacademy.learn.baigiamasis.user;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
	Optional<User> findByEmail(String email);

}
