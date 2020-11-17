package com.ogofit.game.store;

import com.ogofit.game.models.Client;
import com.ogofit.game.models.Instruction;

import java.util.List;

public interface IClientRepository {

    void add(Client client);

    List<Client> get();

    void delete(Client client);
}
