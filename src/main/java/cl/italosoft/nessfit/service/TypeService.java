package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.Type;

import java.util.List;

/**
 * Interface for the Type Service
 */
public interface TypeService
{

    /**
     * Returns all deportive center types records.
     * @return a list containing the records.
     */
    public List<Type> list();
}
