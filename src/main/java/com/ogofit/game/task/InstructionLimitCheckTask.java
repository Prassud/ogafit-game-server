package com.ogofit.game.task;

import com.ogofit.game.configuration.GameConfig;
import com.ogofit.game.models.Client;
import com.ogofit.game.models.Instruction;
import com.ogofit.game.service.ClientService;
import com.ogofit.game.service.InstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InstructionLimitCheckTask extends AbstractTask {
    private static final String TASK_NAME = "Instruction Limit Check Task";

    private final InstructionService instructionService;

    private final ClientService clientService;

    private final GameConfig gameConfig;

    @Override
    public void execute() {
        List<Client> clients = clientService.getClients();
        clients
                .stream()
                .map(client -> instructionService.getInstruction(client))
                .flatMap(List::stream)
                .filter(instruction -> !instruction.isExpired())
                .filter(Instruction::isActive)
                .filter(instruction -> instruction.isExpired(gameConfig.getInstructionTimeLimit()))
                .forEach(instructionService::markAsExpired);
    }

    @Override
    public String getTaskName() {
        return TASK_NAME;
    }
}
