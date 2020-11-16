package com.ogofit.game.controller;


import com.ogofit.game.models.Instruction;
import com.ogofit.game.service.InstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class InstructionController {

    private final InstructionService instructionService;

    @MessageMapping("/clients/{clientId}/instructions/{instructionId}")
    @SendTo("/topic/clients/{clientId}/instructions/{instructionId}")
    public Instruction create(@Payload Instruction instruction) throws Exception {
        return instructionService.createInstruction(instruction);
    }

}
