package hu.alkfejl.controller;

import hu.alkfejl.dao.PersonDAO;
import hu.alkfejl.dao.PersonDaoImpl;
import hu.alkfejl.model.Contract;
import hu.alkfejl.model.Person;

import java.util.List;
import java.util.Optional;

public class PersonController {

    private PersonDAO dao = new PersonDaoImpl();

    public PersonController() {

    }

    public boolean add(Person person) {
        return dao.add(person);
    }

    public List<Person> getAll() {
        return dao.getAll();
    }

    public boolean addContract(Contract contract) { return dao.addContract(contract); }

    public List<Contract> listContracts() { return dao.listContracts(); }
}
