package br.edu.utfpr.turismoapi.turismoapi.repositories;

import br.edu.utfpr.turismoapi.turismoapi.models.Person;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

        public Optional<Person> findByEmail(String email);

}
