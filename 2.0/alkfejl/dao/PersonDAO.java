package hu.alkfejl.dao;

import hu.alkfejl.model.Contract;
import hu.alkfejl.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDAO {

    public boolean add(Person p);

    public List<Person> getAll();

    public boolean addContract(Contract contract);

    public List<Contract> listContracts();
}
