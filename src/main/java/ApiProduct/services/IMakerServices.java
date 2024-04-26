package ApiProduct.services;

import ApiProduct.models.Maker;

import java.util.List;
import java.util.Optional;

public interface IMakerServices {

    List<Maker> findAll();

    Optional<Maker> findById(Long id);

    void save(Maker maker);

    void deleteById(Long id);
}
