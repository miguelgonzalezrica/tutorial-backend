package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author migonzal
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Client get(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {
        return (List<Client>) this.clientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String save(Long id, ClientDto dto) {

        Client client;
        boolean nameIsNew = true;

        if (id == null) {
            //check if the client already exists
            List<Client> allClients = (List<Client>) this.clientRepository.findAll();
            for (Client allClient : allClients) {
                if (dto.getName().equals(allClient.getName())) {
                    nameIsNew = false;
                    break;
                }
            }
            if(nameIsNew){
                client = new Client();
                client.setName(dto.getName());
                this.clientRepository.save(client);
                return null;
            } else {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe un cliente con ese nombre"
                );
            }
        } else {
            client = this.get(id);
            client.setName(dto.getName());
            this.clientRepository.save(client);
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if(this.get(id) == null){
            throw new Exception("Not exists");
        }
        System.out.println(id);
        this.clientRepository.deleteById(id);
    }

}