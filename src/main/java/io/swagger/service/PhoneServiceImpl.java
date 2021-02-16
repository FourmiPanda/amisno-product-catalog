package io.swagger.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.swagger.model.Phone;
import io.swagger.repository.PhoneRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    private static final String BACKEND_A= "backendA";

    private Phone findOne(Long Id) {
        Phone instance = null;
        try {
            List<Phone> phoneList = phoneRepository.findAll();
            for (Phone phone : phoneList) {
                if (phone.getId().equals(Id)) {
                    instance = phone;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    @Override
    public Phone createPhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Override
    @CircuitBreaker(name = BACKEND_A)
    public List<Phone> listPhones() {
        return phoneRepository.findAll();
    }

    @Override
    public Phone updatePhone(Long id, Phone phone) {
//        Phone updateInstance = this.findOne(id);
//        updateInstance.setName(phone.getName());
        return phoneRepository.save(phone);
    }

    @Override
    public Phone deletePhone(Long id) {
        Phone instance = findOne(id);
        phoneRepository.delete(instance);
        return instance;
    }

    @Override
    public Phone getPhone(Long id) {//return phoneRepository.findById(id).get();
        return this.findOne(id);
    }
}