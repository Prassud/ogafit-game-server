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
        Client client = clientService.getClient(instruction.getClient().getId());
        instruction.setClient(client);
        instructionRepository.add(instruction);
        return instruction;
    }

    private void handleActiveInstruction(Instruction instruction, Instruction activeInstruction) {
        activeInstruction.setActive(false);
        if (!isMatchingInstruction(activeInstruction.getKey(), instruction)) {
            decrementScore(activeInstruction);
        } else {
            incrementScore(activeInstruction);
        }
    }

    private boolean isMatchingInstruction(String key, Instruction instruction) {
        return instruction.getKey().equals(key);
    }

    public void markAsExpired(Instruction instruction) {
        instruction.setExpired(true);
        notificationService.notify(Constants.INSTRCUTION_EXPIRED_NOTIFICATION_TYPE, instruction);
    }

    public boolean isReachedMaxContinuousAttempt(Client client) {
        List<Instruction> instructions = getInstruction(client);
        int continuousInstructionCount = 0;
        for (int index = 0; index < instructions.size(); index++) {
            Instruction instruction = instructions.get(index);
            if (!instruction.isExpired()) {
                continuousInstructionCount = 0;
                continue;
            }
            continuousInstructionCount++;
            if (continuousInstructionCount == gameConfig.getMaxContinuousAttempt()) {
                return true;
            }
        }
        return false;
    }

    public Instruction verify(Instruction instruction) {
        Optional<Instruction> instructions = getActiveInstruction(instruction);
        if (instructions.isPresent()) {
            handleActiveInstruction(instruction, instructions.get());
        }
        return instruction;
    }

    private Optional<Instruction> getActiveInstruction(Instruction instruction) {
        Client client = instruction.getClient();
        List<Instruction> instructions = instructionRepository.get(client.getId().toString());
        return instructions
                .stream()
                .filter(Instruction::isActive)
                .filter(instruction1 -> !instruction1.isExpired())
                .findFirst();
    }

    private void incrementScore(Instruction instruction) {
        instruction.getClient().getScore().increment();
        notificationService.notify(Constants.SCORE_CHANGE_NOTIFICATION_TYPE, instruction);
    }

    private void decrementScore(Instruction instruction) {
        instruction.getClient().getScore().decrement();
        notificationService.notify(Constants.SCORE_CHANGE_NOTIFICATION_TYPE, instruction);
    }
}
