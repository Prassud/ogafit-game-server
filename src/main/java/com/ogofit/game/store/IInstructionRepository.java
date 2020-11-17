package com.ogofit.game.store;

import com.ogofit.game.models.Instruction;

import java.util.List;

public interface IInstructionRepository {

    void add(Instruction instruction);

    List<Instruction> get(String clientId);

    void delete(Instruction instruction);
}
