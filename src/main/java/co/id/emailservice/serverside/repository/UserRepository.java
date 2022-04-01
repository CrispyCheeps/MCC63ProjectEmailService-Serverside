package co.id.emailservice.serverside.repository;

import co.id.emailservice.serverside.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    //mungkin karena bisa dua email atau username
//    Optional<User> findByUsernameOrEmployee_Email(String username, String email);

    //knp optional?
    //Kalau filenya dlm db itu unique sebaiknya menggunakan optional (krn bisa jd ada / tdk ada)
    //kalau tdk ada bisa di-throw
    Optional<User> findByEmail(String email);
}
