package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.Type;
import cl.italosoft.nessfit.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TypeServiceImpl implements TypeService
{
    @Autowired
    private TypeRepository typeRepository;

    /**
     * Returns all deportive center types records.
     * @return a list containing the records.
     */
    @Override
    public List<Type> list() {
        return typeRepository.findAll();
    }
}
