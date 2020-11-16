package com.ogofit.game.service;

import com.ogofit.game.configuration.GameConfig;
import com.ogofit.game.models.Client;
import com.ogofit.game.models.Instruction;
import com.ogofit.game.store.IInstructionRepository;
import com.ogofit.game.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class InstructionService {
    private final IInstructionRepository instructionRepository;
    private final ClientService clientService;
    private final NotificationService notificationService;
    private final GameConfig gameConfig;

    public List<Instruction> getInstruction(Client client) {
        return instructionRepository.get(client.getId().toString());
    }

    public Instruction createInstruction(Instruction instruction) {
        Optional<Instruction> activeInstruction = getActiveInstruction(instruction);
        if (activeInstruction.isPresent()) {
            handleActiveInstruction(instruction, activeInstruction);
        } else {
            instructionRepository.add(instruction);
        }
        return instruction;
    }

    private void handleActiveInstruction(Instruction instruction, Optional<Instruction> activeInstruction) {
        Instruction activeInst = activeInstruction.get();
        if (!isMatchingInstruction(activeInst.getKey(), instruction)) {
            clientService.decrementScore(activeInst.getClient());
        } else {
            clientService.incrementScore(instruction.getClient());
        }
    }

    private boolean isMatchingInstruction(String key, Instruction instruction) {
        return instruction.getKey().equals(key);
    }

    private Optional<Instruction> getActiveInstruction(Instruction instruction) {
        Client client = instruction.getClient();
        List<Instruction> instructions = instructionRepository.get(client.getId().toString());
        return instructions
                .stream()
                .filter(eachInst -> !eachInst.isExpired())
                .findFirst();
    }

    public void markAsExpired(Instruction instruction) {
        instruction.setExpired(true);
        notificationService.notify(Constants.INSTRCUTION_EXPIRED_NOTIFICATION_TYPE, instruction);
    }

    public boolean isReachedMaxContinuousAttempt(Client client) {
        List<Instruction> instructions = getInstruction(client);
        int continuousInstruction = 0;
        for (int index = 0; index < instructions.size(); index++) {
            Instruction instruction = instructions.get(0);
            if (!instruction.isExpired()) {
                continuousInstruction = 0;
                continue;
            }
            continuousInstruction++;
            if (continuousInstruction == gameConfig.getMaxContinuousAttempt()) {
                return true;
            }
        }
        return false;
    }
}
